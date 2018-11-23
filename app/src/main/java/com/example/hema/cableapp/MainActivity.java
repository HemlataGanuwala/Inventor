package com.example.hema.cableapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager = (ViewPager)findViewById(R.id.viewpager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new DashBoardFragment(),"Dashboard");
        adapter.AddFragment(new RegFragment(),"Register");
        adapter.AddFragment(new PackageFragment(),"Package");
        adapter.AddFragment(new AgentFragment(),"Agent");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();
    }

    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Dashboard");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_dashboard, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Register");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_reg, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Package");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_package, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabfour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabfour.setText("Agent");
        tabfour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_grpagent, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabfour);
    }
}
