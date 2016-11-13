package cn.wydewy.medicalapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.wydewy.medicalapp.model.ReleaseNum;
import cn.wydewy.medicalapp.util.Constant;


public class Schedule_Activity extends AppCompatActivity {

    private List<ReleaseNum> releaseNumList = new ArrayList<>();
    private GridView mGridView;
    private Map<String, List<ReleaseNum>> items = new LinkedHashMap<>();
    private String outpatientId;
    private ScheduleAdapter adapter;
    private int maxheight = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);


        Intent intent = getIntent(); //用于激活它的意图对象：这里的intent获得的是上个Activity传递的intent
        Bundle bundle = intent.getExtras();
        outpatientId = bundle.getString(Constant.OUTPATIENT_ID);


        mGridView = (GridView) findViewById(R.id.gridview);
        adapter = new ScheduleAdapter(this, metrics);
        mGridView.setAdapter(adapter);


        releaseNum("星期一");
        releaseNum("星期二");
        releaseNum("星期三");
        releaseNum("星期四");
        releaseNum("星期五");
    }

    private void releaseNum(final String week) {
        Map<String, String> map = new HashMap<>();
        map.put("outpatientId", outpatientId);
        map.put("week", week);
        CustomRequest objectRequest = new CustomRequest(Request.Method.POST, Constant.API_RELEASENUM, map,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("TAG-response", jsonObject.toString());
                        String itemsStr = null;
                        try {
                            itemsStr = jsonObject.get("datum").toString();
                            releaseNumList = JSON.parseObject(itemsStr, new TypeReference<List<ReleaseNum>>() {
                            });
                            items.put(week, releaseNumList);
                            maxheight = Math.max(maxheight, releaseNumList.size());
                            Log.d("TAG", "maxheight:" + maxheight);
                            adapter.update();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        } finally {

                        }
                    }
                }

                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("TAG-error", volleyError.toString());
            }
        }

        );
        MedicalApplication.getInstance().

                addToRequestQueue(objectRequest);
    }


    public class ScheduleAdapter extends BaseAdapter {
        private Context mContext;
        private DisplayMetrics mDisplayMetrics;
        private List<String> keys = new ArrayList<>();
        private int mTitleHeight = 70;
        private int mDataHeight;

        public ScheduleAdapter(Context c, DisplayMetrics metrics) {
            mContext = c;
            mDisplayMetrics = metrics;
            getKeys();//获得所以的键
//        mDataHeight = (mDisplayMetrics.heightPixels - mTitleHeight
//                - (itemHeight * (w + 1)) - getBarHeight()) / (itemHeight + 1);
            mDataHeight = (mDisplayMetrics.heightPixels - mTitleHeight
                    - (maxheight * (items.size() + 1)) - getBarHeight()) / 7;
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
            if (items != null) {
                return items.size() * (maxheight + 1);
            }
            return 0;
        }

        @Override
        public ReleaseNum getItem(int i) {
            if (items.size() == 0) {
                return null;
            }

            if (items != null) {
                int width = i % items.size();
                int height = i / items.size();

                Log.d("TAG", "i:" + i + " width:" + width + " height:" + height);

                if (height == 0) {
                    ReleaseNum r = new ReleaseNum();
                    r.setRemark(keys.get(width));
                    return r;
                } else {
                    String week = keys.get(width);
                    Log.d("TAG", week);
                    List<ReleaseNum> its = items.get(week);
                    if (its != null) {
                        if ((height - 1) < its.size()) {
                            return its.get(height - 1);
                        } else {
                            ReleaseNum r = new ReleaseNum();
                            r.setRemark("无");
                            return r;
                        }
                    } else {
                        ReleaseNum r = new ReleaseNum();
                        r.setRemark("2");
                        return r;
                    }

                }
            }
            ReleaseNum r = new ReleaseNum();
            r.setRemark("3");
            return r;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            final TextView textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            textView.setText(getItem(position).getRemark());
            textView.setTextColor(Color.BLACK);
            if (position < items.size()) {
                textView.setBackgroundColor(Color.argb(100, 10, 80, 255));
                textView.setHeight(mTitleHeight);
            } else {
                textView.setBackgroundColor(Color.rgb(244, 244, 244));
                textView.setHeight(mDataHeight);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ReleaseNum r = getItem(position);
                        Intent it = new Intent(Schedule_Activity.this, Confirm_Activity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.RELEASE_ID, r.getReleaseId());
                        it.putExtras(bundle);
                        startActivity(it);
                    }
                });
            }
            return textView;
        }


        public void update() {
            notifyDataSetChanged();
        }

        private void getKeys() {
            keys.add("星期一");
            keys.add("星期二");
            keys.add("星期三");
            keys.add("星期四");
            keys.add("星期五");
        }
    }


}
