package com.anningtex.pictureargbtest.three;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.anningtex.pictureargbtest.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public class ThreeActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private ThreeAdapter adapter;
    private List<String> mStringList = new ArrayList<>();
    private int num = 0;
    private Bitmap mSrc;
    private int mHeight, mWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        initView();
        getPixColor();
    }

    private void initView() {
        mRecycleView = findViewById(R.id.recycleView);
        adapter = new ThreeAdapter(R.layout.item_one, mStringList, mIntegerMap);
        mRecycleView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecycleView.setAdapter(adapter);
    }

    public void getPixColor() {
        mSrc = BitmapFactory.decodeResource(getResources(), R.drawable.pic1);
        mHeight = mSrc.getHeight();
        mWidth = mSrc.getWidth();
        runnable.run();
        adapter.notifyDataSetChanged();
    }

    private Map<String, Integer> mIntegerMap = new HashMap<>();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int A, R, G, B;
            int pixelColor;
            for (int y = 0; y < mHeight; y++) {
                if (y % 50 == 0) {
                    for (int x = 0; x < mWidth; x++) {
                        if (x % 50 == 0) {
                            pixelColor = mSrc.getPixel(x, y);
                            R = Color.red(pixelColor);
                            G = Color.green(pixelColor);
                            B = Color.blue(pixelColor);
                            Log.e("666_rgb", "R:" + R + "  " + "G:" + G + "  " + "B:" + B);

                            String rgb = R + "," + G + "," + B;
                            if (mIntegerMap.keySet().contains(rgb)) {
                                mIntegerMap.put(rgb, mIntegerMap.get(rgb) + 1);
                            } else {
                                mIntegerMap.put(rgb, 1);
                            }

                            if (!mStringList.contains(rgb)) {
                                mStringList.add(rgb);
                            }
                            num++;
                            Log.e("666***", "num: " + num + "    size: " + mStringList.size());
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        runnable = null;
    }
}