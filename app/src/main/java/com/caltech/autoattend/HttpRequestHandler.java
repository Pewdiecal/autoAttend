package com.caltech.autoattend;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequestHandler {

    private String attendanceURL;
    private String studentID;
    private String studentPwd;
    private String serverMsg;
    private Callback callback;
    private OkHttpClient client = new OkHttpClient();

    public HttpRequestHandler(String attendanceURL, String studentID, String studentPwd, Callback callback) {
        this.attendanceURL = attendanceURL;
        this.studentID = studentID;
        this.studentPwd = studentPwd;
        this.callback = callback;
    }

    private void initializeFormRequest(String attendanceURL) {

        Request request = new Request.Builder()
                .url(attendanceURL)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.onFailure(call, e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Document document = Jsoup.parse(response.body().string());
                String cookieID = response.header("Set-Cookie")
                        .substring(0, response.header("Set-Cookie").indexOf(';'));
                String tokenID = null;
                String timeTableID = null;
                String startTime = null;
                String endTime = null;
                String class_date = null;
                String class_id = null;

                Elements formDetails = document.getElementsByTag("form");
                String formPostRequestURL = formDetails.attr("action");

                Elements formInputs = document.getElementsByTag("input");
                Log.d("FormInputs", formInputs.toString());

                for (String attrName : formInputs.eachAttr("name")) {
                    switch (attrName) {
                        case "_token":
                            tokenID = formInputs.eachAttr("value").get(0);
                            break;

                        case "timetable_id":
                            timeTableID = formInputs.eachAttr("value").get(1);
                            break;

                        case "starttime":
                            startTime = formInputs.eachAttr("value").get(2);
                            break;

                        case "endtime":
                            endTime = formInputs.eachAttr("value").get(3);
                            break;

                        case "class_date":
                            class_date = formInputs.eachAttr("value").get(4);
                            break;

                        case "class_id":
                            class_id = formInputs.eachAttr("value").get(5);
                            break;
                    }
                }

                postFormRequest(tokenID, timeTableID, startTime, endTime, class_date, class_id, formPostRequestURL, cookieID);

            }
        });

    }

    private void postFormRequest(String token, String timeTableID, String startTime, String endTime, String classDate,
                                 String classID, String formPostRequestURL, String cookieID) {

        RequestBody formBody = new FormBody.Builder()
                .add("stud_id", studentID)
                .add("stud_pswrd", studentPwd)
                .add("_token", token)
                .add("timetable_id", timeTableID)
                .add("starttime", startTime)
                .add("endtime", endTime)
                .add("class_date", classDate)
                .add("class_id", classID)
                .build();

        Log.d("Content type", formBody.contentType().toString());

        Request request = new Request.Builder()
                .url(formPostRequestURL)
                .addHeader("Cookie", cookieID)
                .method("POST", formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Document document = Jsoup.parse(response.body().string());
                String cookieID = response.header("Set-Cookie").substring(0, response.header("Set-Cookie").indexOf(';'));

                finalGetRequest(cookieID);
            }

        });

    }

    private void finalGetRequest(String cookieID) {

        Request request = new Request.Builder()
                .addHeader("Cookie", cookieID)
                .url(attendanceURL)
                .get()
                .build();

        client.newCall(request).enqueue(callback);

    }

    public void submitForm() {
        initializeFormRequest(attendanceURL);
    }

}
