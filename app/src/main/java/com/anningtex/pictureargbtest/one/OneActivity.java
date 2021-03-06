package com.anningtex.pictureargbtest.one;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anningtex.pictureargbtest.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public class OneActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private OneAdapter adapter;
    private List<String> mStringList = new ArrayList<>();
    private int num = 0;
    private Bitmap mSrc;
    private int mHeight, mWidth;
    private Map<String, Integer> mIntegerMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        initView();
        getPixColor();
    }

    private void initView() {
        mRecycleView = findViewById(R.id.recycleView);
        adapter = new OneAdapter(R.layout.item_one, mStringList, mIntegerMap);
        mRecycleView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecycleView.setAdapter(adapter);
    }

    public void getPixColor() {
        mSrc = BitmapFactory.decodeResource(getResources(), R.mipmap.pic2);
        mHeight = mSrc.getHeight();
        mWidth = mSrc.getWidth();
        runnable.run();
        adapter.notifyDataSetChanged();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int R, G, B;
            int pixelColor;
            for (int y = 0; y < mHeight; y++) {
                if (y % 3 == 0) {
                    for (int x = 0; x < mWidth; x++) {
                        if (x % 3 == 0) {
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
                            Log.e("666***", "num: " + num + "    mStringListSize: " + mStringList.size());
                        }
                    }
                }
            }

            //根据数字顺序排序
            Collections.sort(mStringList, (o1, o2) -> {
                if (o1.contains(",") && o2.contains(",")) {
                    String[] split = o1.split(",");
                    String[] strings = o2.split(",");
                    int i = Integer.parseInt(split[0]) - Integer.parseInt(strings[0]);
                    if (i == 0) {
                        int j = Integer.parseInt(split[1]) - Integer.parseInt(strings[1]);
                        if (j == 0) {
                            int w = Integer.parseInt(split[2]) - Integer.parseInt(strings[2]);
                            return w;
                        } else {
                            return j;
                        }
                    } else {
                        return i;
                    }
                }
                return 0;
            });
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        runnable = null;
    }
}