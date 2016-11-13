package cn.wydewy.medicalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.wydewy.medicalapp.model.Customer;
import cn.wydewy.medicalapp.model.Section;
import cn.wydewy.medicalapp.util.Constant;

import static cn.wydewy.medicalapp.R.id.activity_order2;

public class Order2_Activity extends AppCompatActivity {
    private List<Section> items = new ArrayList<>();
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order2);
        getSupportActionBar().hide();

        initListView();

        sections();
    }

    private void sections() {
        CustomRequest objectRequest = new CustomRequest(Request.Method.POST, Constant.API_SECTIONS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("TAG-response", jsonObject.toString());
                        String itemsStr = null;
                        try {
                            itemsStr = jsonObject.get("datum").toString();
                            items = JSON.parseObject(itemsStr, new TypeReference<List<Section>>() {
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
        ListView introlist = (ListView) findViewById(R.id.introduce_list2);

        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public Section getItem(int position) {
                return items.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView text = new TextView(Order2_Activity.this);
                Section section = getItem(position);
                text.setText(section.getSectionName());
                text.setPadding(50, 50, 0, 50);
                text.setTextColor(android.graphics.Color.rgb(0, 0, 0));
                text.setTextSize(15);
                return text;
            }
        };
        introlist.setAdapter(adapter);
        introlist.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(Order2_Activity.this, Order3_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.SECTION_ID, items.get(position).getSectionId());
                it.putExtras(bundle);
                startActivityForResult(it, 0);
            }
        });
    }
}
