package com.caltech.autoattend;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class QRScannerActivity extends AppCompatActivity {
    private SurfaceView surfaceView;
    private Toolbar toolbar;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private String intentData;
    private Handler handler = new Handler();
    private QRScannerViewModel qrScannerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);

        qrScannerViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(QRScannerViewModel.class);

        if (ActivityCompat.checkSelfPermission(QRScannerActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(QRScannerActivity.this, new
                    String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            intentData = bundle.getString("ActivitySource");
        }

        surfaceView = findViewById(R.id.surfaceView);
        toolbar = findViewById(R.id.qr_toolbar);

        toolbar.setTitle("Scan QR code");
        setSupportActionBar(toolbar);

    }

    private void initialiseDetectorsAndSources() {

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(QRScannerActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    if (barcodes.valueAt(0).url != null && barcodes.valueAt(0).displayValue.contains("https://mmls.mmu.edu.my/attendance:")) {
                        barcodeDetector.release();
                        signInDialog(barcodes.valueAt(0).displayValue);
                    } else {
                        barcodeDetector.release();
                        handler.post(() -> {
                            Toast.makeText(QRScannerActivity.this, R.string.QRScan_invalidLink, Toast.LENGTH_LONG).show();
                        });
                        finish();
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(QRScannerActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        initialiseDetectorsAndSources();
                        cameraSource.start(surfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            } else {
                alertDialog();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.QRScan_alertDialog_msg)
                .setNegativeButton(R.string.QRScan_alertDialog_OK_btn, (dialog, id) -> finish());

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void signInDialog(String attendanceLink) {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.view_link_verify, viewGroup, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(dialogView);

        handler.post(() -> {
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            linkVerification(alertDialog, attendanceLink);
        });
    }

    private void linkVerification(AlertDialog alertDialog, String attendanceLink) {

        qrScannerViewModel.getUserLiveData().observe(this, user -> {

            HttpRequestHandler httpRequestHandler = new HttpRequestHandler(attendanceLink,
                    user.student_id, user.password, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    handler.post(() -> {
                        Toast.makeText(QRScannerActivity.this, R.string.QRScan_error, Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        finish();
                    });

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    Document document = Jsoup.parse(response.body().string());
                    Elements redAlert = document.getElementsByClass("alert alert-danger");
                    Elements success = document.getElementsByClass("alert alert-success");
                    SimpleDateFormat dateFormat;
                    SimpleDateFormat timeFormat;

                    if (!redAlert.isEmpty()) {

                        Log.d("Red ALERT", redAlert.text());

                        handler.post(() -> {
                            Toast.makeText(QRScannerActivity.this, "Server message : " + redAlert.text(), Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        });

                        storeQRCode(attendanceLink, null, null);

                    } else if (!success.isEmpty()) {

                        Log.d("Success Msg", success.text());

                        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
                        timeFormat = new SimpleDateFormat("HH:mm", Locale.UK);

                        handler.post(() -> {
                            Toast.makeText(QRScannerActivity.this, "Server message : " + success.text(), Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        });
                        storeQRCode(attendanceLink, dateFormat, timeFormat);

                    }

                }
            });

            httpRequestHandler.submitForm();

        });

    }

    private void storeQRCode(String attendanceLink, SimpleDateFormat dateFormat, SimpleDateFormat timeFormat) {

        if (intentData != null) {
            Intent intent = new Intent(QRScannerActivity.this, AddNewSessionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("attendance url", attendanceLink);
            if (dateFormat != null && timeFormat != null) {
                bundle.putString("attendance date", dateFormat.format(new Date()));
                bundle.putString("attendance time", timeFormat.format(new Date()));
            }
            intent.putExtras(bundle);
            startActivity(intent);

        } else {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(QRScannerActivity.this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("attendance url", attendanceLink);
            if (dateFormat != null && timeFormat != null) {
                editor.putString("attendance date", dateFormat.format(new Date()));
                editor.putString("attendance time", timeFormat.format(new Date()));
            }
            editor.commit();
        }

        finish();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }
}