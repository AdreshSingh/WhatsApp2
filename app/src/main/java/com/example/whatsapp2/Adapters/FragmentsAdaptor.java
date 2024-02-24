package com.example.whatsapp2.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsapp2.Fragments.CallFragment;
import com.example.whatsapp2.Fragments.ChatsFragment;
import com.example.whatsapp2.Fragments.StatusFragment;

public class FragmentsAdaptor extends FragmentPagerAdapter {

    public FragmentsAdaptor(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new StatusFragment();
            case 2:
                return new CallFragment();
            default:
                return new ChatsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String[] title = {"Chats","Status","Calls"};
         super.getPageTitle(position);
         return title[position];

    }
}
