package com.caltech.autoattend;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class SettingsFragmentViewModel extends AndroidViewModel {
    SettingsItem[] settingsItem;

    public SettingsFragmentViewModel(@NonNull Application application) {
        super(application);
        settingsItem = new SettingsItem[4];

        settingsItem[0] = new SettingsItem(application.getString(R.string.SettingsFragments_CredentialSettings),
                R.drawable.ic_baseline_lock_24);
        settingsItem[1] = new SettingsItem(application.getString(R.string.SettingsFragments_FailSafeSettings),
                R.drawable.ic_baseline_settings_backup_restore_24);
        settingsItem[2] = new SettingsItem(application.getString(R.string.SettingsFragments_NotificationSettings),
                R.drawable.ic_baseline_notification_important_24);
        settingsItem[3] = new SettingsItem(application.getString(R.string.SettingsFragments_Help),
                R.drawable.ic_baseline_help_24);
    }

    public SettingsItem[] getSettingsItem() {
        return settingsItem;
    }
}
