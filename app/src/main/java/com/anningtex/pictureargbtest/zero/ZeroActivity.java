package com.anningtex.pictureargbtest.zero;

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
import java.util.List;

/**
 * @author Administrator
 * desc:原始的ARGB
 */
public class ZeroActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private ZeroAdapter adapter;
    private List<String> mStringList = new ArrayList<>();
    private Bitmap mSrc;
    private int mHeight, mWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zero);
        initView();
        getPixColor();
    }

    private void initView() {
        mRecycleView = findViewById(R.id.recycleView);
        adapter = new ZeroAdapter(R.layout.item_one, mStringList);
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

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int A, R, G, B;
            int pixelColor;
            for (int y = 0; y < mHeight; y++) {
                for (int x = 0; x < mWidth; x++) {
                    if (mStringList.size() == 500) {
                        break;
                    }
                    pixelColor = mSrc.getPixel(x, y);
                    A = Color.alpha(pixelColor);
                    R = Color.red(pixelColor);
                    G = Color.green(pixelColor);
                    B = Color.blue(pixelColor);
                    Log.e("666_argb", "A:" + A + "  " + "R:" + R + "  " + "G:" + G + "  " + "B:" + B);

                    String argb = A + "," + R + "," + G + "," + B;
                    if (!mStringList.contains(argb)) {
                        mStringList.add(argb);
                    }
                    Log.e("666***", " mStringListSize: " + mStringList.size());
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