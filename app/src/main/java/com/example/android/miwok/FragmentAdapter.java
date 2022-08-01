package com.example.android.miwok;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class FragmentAdapter extends FragmentStateAdapter {
    private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" ,"tab4"};


    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==0){
            return new NumbersFragment();
        }else if(position==1){
            return new FamilyFragment();
        }else if(position==2){
            return new ColourFragment();
        }else {
            return new PhrasesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}
