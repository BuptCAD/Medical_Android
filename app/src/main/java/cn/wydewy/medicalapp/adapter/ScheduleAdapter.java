package cn.wydewy.medicalapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wydewy on 2016/11/11.
 */

public class ScheduleAdapter extends BaseAdapter {
    private Context mContext;
    private DisplayMetrics mDisplayMetrics;
    private Map<String, List<String>> mItems;
    private int itemWidth = 0;
    private int itemHeight = 0;
    private List<String> keys = new ArrayList<>();
    private int mTitleHeight = 70;
    private int mDataHeight;

    public ScheduleAdapter(Context c, DisplayMetrics metrics, Map<String, List<String>> items, int w, int h) {
        mContext = c;
        mDisplayMetrics = metrics;
        mItems = items;


        itemWidth = w;
        itemHeight = h;

        getKeys();//获得所以的键

//        mDataHeight = (mDisplayMetrics.heightPixels - mTitleHeight
//                - (itemHeight * (w + 1)) - getBarHeight()) / (itemHeight + 1);
        mDataHeight = (mDisplayMetrics.heightPixels - mTitleHeight
                - (itemHeight * (w + 1)) - getBarHeight()) / 7;
    }

    private int getBarHeight() {
        switch (mDisplayMetrics.densityDpi) {
            case DisplayMetrics.DENSITY_HIGH:
                return 48;
            case DisplayMetrics.DENSITY_MEDIUM:
                return 32;
            case DisplayMetrics.DENSITY_LOW:
                return 24;
            default:
                return 48;
        }
    }


    @Override
    public int getCount() {
        if (mItems != null) {
            return mItems.size() * (itemHeight + 1);
        }
        return 0;
    }

    @Override
    public String getItem(int i) {
        if(itemWidth==0){
            return "";
        }

        if (mItems != null) {
            int width = i % itemWidth;
            int height = i / itemWidth;

            Log.d("TAG", "i:" + i + " width:" + width + " height:" + height);

            if (height == 0) {
                return keys.get(width);
            } else {
                List<String> items = mItems.get(keys.get(width));
                if (items != null) {
                    if (height < items.size()) {
                        return items.get(height);
                    } else {
                        return "无";
                    }
                } else {
                    return "2";
                }

            }
        }
        return "3";
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        if(getItem(position)!=null) {
            textView.setText(getItem(position));
        }else {
            textView.setText("无");
        }
        textView.setTextColor(Color.BLACK);
        if (position < mItems.size()) {
            textView.setBackgroundColor(Color.argb(100, 10, 80, 255));
            textView.setHeight(mTitleHeight);
        } else {
            textView.setBackgroundColor(Color.rgb(244, 244, 244));
            textView.setHeight(mDataHeight);
        }
        return textView;
    }


    public void update(){
        notifyDataSetChanged();
    }

    private void getKeys() {
        Set set = mItems.keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            keys.add(key);
        }
    }
}
