package com.anningtex.pictureargbtest.zero;

import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.anningtex.pictureargbtest.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Map;

/**
 * @Author Song
 */
public class ZeroAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ZeroAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String rgb) {
        TextView tvArgb = helper.itemView.findViewById(R.id.tv_argb);
        tvArgb.setText("(" + rgb + ")");
        String[] strings = rgb.split(",");
        int argb = Color.argb(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]));
        tvArgb.setBackgroundColor(argb);
    }
}
