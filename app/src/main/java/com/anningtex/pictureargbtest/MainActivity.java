package com.anningtex.pictureargbtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.anningtex.pictureargbtest.four.FourActivity;
import com.anningtex.pictureargbtest.one.OneActivity;
import com.anningtex.pictureargbtest.three.ThreeActivity;
import com.anningtex.pictureargbtest.two.TwoActivity;
import com.anningtex.pictureargbtest.zero.ZeroActivity;

/**
 * @author Administrator
 * desc:获取任意图片里的RGB值以及形成色块展示到列表中
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnZero, mBtnOne, mBtnTwo, mBtnThree, mBtnFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtnZero = findViewById(R.id.btn_zero);
        mBtnOne = findViewById(R.id.btn_one);
        mBtnTwo = findViewById(R.id.btn_two);
        mBtnThree = findViewById(R.id.btn_three);
        mBtnFour = findViewById(R.id.btn_four);
        mBtnZero.setOnClickListener(this);
        mBtnOne.setOnClickListener(this);
        mBtnTwo.setOnClickListener(this);
        mBtnThree.setOnClickListener(this);
        mBtnFour.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_zero:
                startActivity(new Intent(MainActivity.this, ZeroActivity.class));
                break;
            case R.id.btn_one:
                startActivity(new Intent(MainActivity.this, OneActivity.class));
                break;
            case R.id.btn_two:
                startActivity(new Intent(MainActivity.this, TwoActivity.class));
                break;
            case R.id.btn_three:
                startActivity(new Intent(MainActivity.this, ThreeActivity.class));
                break;
            case R.id.btn_four:
                startActivity(new Intent(MainActivity.this, FourActivity.class));
                break;
            default:
                break;
        }
    }
}