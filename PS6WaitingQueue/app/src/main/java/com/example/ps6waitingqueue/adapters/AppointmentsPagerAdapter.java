package com.example.ps6waitingqueue.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ps6waitingqueue.fragments.FragmentAllAppointments;
import com.example.ps6waitingqueue.fragments.FragmentTodayAppointments;

public class AppointmentsPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public AppointmentsPagerAdapter(Context context, FragmentManager fm){
        super(fm);
        mContext = context;
    }
    @Override
    public Fragment getItem(int i) {
        if(i == 0){
            return new FragmentTodayAppointments();
        }else {
            return new FragmentAllAppointments();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return "Aujourd'hui";
            default:
                return "Tous";
        }
    }
}
