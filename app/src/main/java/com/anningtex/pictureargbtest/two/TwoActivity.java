package com.anningtex.pictureargbtest.two;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anningtex.pictureargbtest.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author Administrator
 */
public class TwoActivity extends AppCompatActivity {
    private TextView tvRgb;
    private ImageView ivImage;
    private Bitmap bitmap;
    private Button btnColor;
    public static final int NONE = 0;
    /**
     * 缩放
     */
    public static final int PHOTO_ZOOM = 2;
    /**
     * 结果
     */
    public static final int PHOTO_RESULT = 3;
    public static final String IMAGE_UNSPECIFIED = "image/*";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        tvRgb = findViewById(R.id.textView);
        btnColor = findViewById(R.id.btnColor);
        ivImage = findViewById(R.id.iv_image);

        btnColor.setOnClickListener(v -> openAlbum());
        ivImage.setOnTouchListener((v, event) -> {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int color = bitmap.getPixel(x, y);
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);
                int a = Color.alpha(color);
                Log.e("666_RGB", "r=" + r + ",g=" + g + ",b=" + b);
                tvRgb.setText("a=" + a + ",r=" + r + ",g=" + g + ",b=" + b);
                btnColor.setTextColor(Color.rgb(r, g, b));
            }
            return true;
        });
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, PHOTO_ZOOM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == NONE) {
                return;
            }
            if (data == null) {
                return;
            }
            // 读取相册缩放图片
            if (requestCode == PHOTO_ZOOM) {
                if (data != null) {
                    startPhotoZoom(data.getData());
                }
            }
            // 处理结果
            if (requestCode == PHOTO_RESULT) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    bitmap = extras.getParcelable("data");
                    comp(bitmap);
                    ivImage.setImageBitmap(bitmap);
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baoStr = new ByteArrayOutputStream();
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baoStr中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baoStr);
        int options = 100;
        while (baoStr.toByteArray().length / 1024 > 100) {
            //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            //重置baoStr即清空baoStr
            baoStr.reset();
            //这里压缩options%，把压缩后的数据存放到baos中
            image.compress(Bitmap.CompressFormat.JPEG, options, baoStr);
            //每次都减少10
            options -= 10;
        }
        //把压缩后的数据baoStr存放到ByteArrayInputStream中
        ByteArrayInputStream isBm = new ByteArrayInputStream(baoStr.toByteArray());
        //把ByteArrayInputStream数据生成图片
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }

    private Bitmap comp(Bitmap image) {
        ByteArrayOutputStream baoStr = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baoStr);
        if (baoStr.toByteArray().length / 1024 > 1024) {
            //判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            //重置baoStr即清空baoStr
            baoStr.reset();
            image.compress(Bitmap.CompressFormat.JPEG, 50, baoStr);
            //这里压缩50%，把压缩后的数据存放到baoStr中
        }
        ByteArrayInputStream isBm;
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap;
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*500分辨率，所以高和宽我们设置为
        //这里设置高度为800f
        float hh = 800f;
        //这里设置宽度为500f
        float ww = 500f;
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        //be=1表示不缩放
        int be = 1;
        if (w > h && w > ww) {
            //如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            //如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) {
            be = 1;
        }
        //设置缩放比例
        newOpts.inSampleSize = be;
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baoStr.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        //压缩好比例大小后再进行质量压缩
        return compressImage(bitmap);
    }

    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_RESULT);
    }
}