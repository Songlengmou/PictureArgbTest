package com.anningtex.pictureargbtest.four;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.anningtex.pictureargbtest.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator
 */
public class FourActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnSort;
    private RecyclerView mRecycleView;
    private FourAdapter adapter;
    private List<String> mStringList = new ArrayList<>();
    private int num = 0;
    private Bitmap mSrc;
    private int mHeight, mWidth;
    private Map<String, Integer> mIntegerMap = new HashMap<>();
    private boolean show = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        initView();
        getPixColor();
    }

    private void initView() {
        mRecycleView = findViewById(R.id.recycleView);
        adapter = new FourAdapter(R.layout.item_one, mStringList, mIntegerMap);
        mRecycleView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecycleView.setAdapter(adapter);
        mBtnSort = findViewById(R.id.btn_sort);
        mBtnSort.setOnClickListener(this);
    }

    public void getPixColor() {
        mSrc = BitmapFactory.decodeResource(getResources(), R.mipmap.pic2);
        mHeight = mSrc.getHeight();
        mWidth = mSrc.getWidth();
        runnable.run();
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
                            num++;
                            Log.e("666***", "num: " + num);
                        }
                    }
                }
            }
            mStringList.addAll(mIntegerMap.keySet());
            adapter.notifyDataSetChanged();
        }
    };

    private void listSort() {
        Collections.sort(mStringList, (o1, o2) -> {
            if (mIntegerMap.get(o1) > mIntegerMap.get(o2)) {
                return -1;
            } else if (mIntegerMap.get(o1) < mIntegerMap.get(o2)) {
                return 1;
            }
            return 0;
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        runnable = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sort:
                runOnUiThread(() -> {
                    if (show) {
                        mStringList.clear();
                        mStringList.addAll(mIntegerMap.keySet());
                        adapter.notifyDataSetChanged();
                        show = false;
                    } else {
                        listSort();
                        show = true;
                    }
                });
                break;
            default:
                break;
        }
    }
}