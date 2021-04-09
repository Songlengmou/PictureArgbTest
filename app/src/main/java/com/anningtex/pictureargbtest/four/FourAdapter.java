package com.anningtex.pictureargbtest.four;

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
public class FourAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Map<String, Integer> rgbCountList;

    public FourAdapter(int layoutResId, @Nullable List<String> data, Map<String, Integer> rgbCountList) {
        super(layoutResId, data);
        this.rgbCountList = rgbCountList;
    }

    @Override
    protected void convert(BaseViewHolder helper, String rgb) {
        TextView tvArgb = helper.itemView.findViewById(R.id.tv_argb);
        long count = rgbCountList.get(rgb);
        tvArgb.setText("(" + rgb + ") count: " + count);
        String[] strings = rgb.split(",");
        int argb = Color.rgb(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2]));
        tvArgb.setBackgroundColor(argb);
    }
}
