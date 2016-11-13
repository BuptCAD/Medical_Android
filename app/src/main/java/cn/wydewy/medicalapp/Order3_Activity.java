package cn.wydewy.medicalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
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
import java.util.List;
import java.util.Map;

import cn.wydewy.medicalapp.model.OutPatient;
import cn.wydewy.medicalapp.util.Constant;

public class Order3_Activity extends AppCompatActivity {

    private List<OutPatient> items = new ArrayList<>();
    private String sectionId;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order3);
        getSupportActionBar().hide();

        Intent intent = getIntent(); //用于激活它的意图对象：这里的intent获得的是上个Activity传递的intent
        Bundle bundle = intent.getExtras();
        sectionId = bundle.getString(Constant.SECTION_ID);
        initListView();

        outpatients();
    }

    private void outpatients() {
        Map<String, String> map = new HashMap<>();
        map.put("sectionId", sectionId);
        CustomRequest objectRequest = new CustomRequest(Request.Method.POST, Constant.API_SECTION_OUTPATIENT_LIST, map,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("TAG-response", jsonObject.toString());
                        String itemsStr = null;
                        try {
                            itemsStr = jsonObject.get("datum").toString();
                            items = JSON.parseObject(itemsStr, new TypeReference<List<OutPatient>>() {
                            });
                            adapter.notifyDataSetChanged();
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

    /**
     * 初始化listView
     */
    private void initListView() {
        ListView introlist = (ListView) findViewById(R.id.introduce_list3);

        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public OutPatient getItem(int position) {
                return items.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView text = new TextView(Order3_Activity.this);
                OutPatient outPatient = getItem(position);
                text.setText(outPatient.getOutpatientName());
                text.setPadding(50, 50, 0, 50);
                text.setTextColor(android.graphics.Color.rgb(0, 0, 0));
                text.setTextSize(15);
                return text;
            }
        };
        introlist.setAdapter(adapter);
        introlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(Order3_Activity.this, Schedule_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.OUTPATIENT_ID, items.get(position).getOutpatientId());
                it.putExtras(bundle);
                startActivity(it);
            }
        });
    }

}
