package io.github.qzcsfchh.androidaop.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import io.github.qzcsfchh.androidaop.demo.databinding.ActivityMainBinding;
import io.github.qzcsfchh.android.aspectj.annotation.CheckTimeSpent;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;


    @CheckTimeSpent
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

    }
}