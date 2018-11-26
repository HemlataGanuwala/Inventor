package com.example.hema.cableapp;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    View view;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbarlogin);

        tabLayout = (TabLayout)view.findViewById(R.id.tablogin);
        tabLayout.setupWithViewPager(viewPager);

        viewPager = (ViewPager)view.findViewById(R.id.viewpagerlogin);

        ViewPagerAdapterLogin adapter = new ViewPagerAdapterLogin(getFragmentManager());
        adapter.AddFragment(new LoginPinFragment(),"PIN");
        adapter.AddFragment(new LoginPasswordFragment(),"PASSWORD");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.colortextbg), getResources().getColor(R.color.colorPrimary));
        return view;
    }

}
