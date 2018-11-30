package com.example.hema.cableapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        globalVariable.setconstr("http://192.168.0.116:8011/api/");

        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        viewPager = (ViewPager)findViewById(R.id.viewpages);

        ViewPagerAdapterAdmin adapter = new ViewPagerAdapterAdmin(getSupportFragmentManager());
        adapter.AddFragment(new LoginFragment(),"Login");
        adapter.AddFragment(new RegistrationFragment(),"Registration");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
