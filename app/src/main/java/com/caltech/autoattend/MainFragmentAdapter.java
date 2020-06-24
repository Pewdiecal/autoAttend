package com.caltech.autoattend;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainFragmentAdapter extends FragmentStateAdapter {

    public MainFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment sub_list = new SubjectList();
        Fragment history = new History();
        Fragment settings = new SettingFragment();
        Fragment[] fragments_array = {sub_list, history, settings};
        return fragments_array[position];
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
