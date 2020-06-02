package com.caltech.autoattend;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class OnboardFragmentAdapter extends FragmentStateAdapter {


    public OnboardFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment frag1 = new Onboard_1();
        Fragment frag2 = new Onboard_2();
        Fragment frag3 = new Onboard_3();
        Fragment[] list_frag = {frag1, frag2, frag3};
        return list_frag[position];
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
