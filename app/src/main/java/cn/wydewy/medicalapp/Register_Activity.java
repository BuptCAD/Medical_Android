package cn.wydewy.medicalapp;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.wydewy.medicalapp.util.Constant;
import cn.wydewy.medicalapp.util.StringUtils;

public class Register_Activity extends AppCompatActivity {

    private static final int REGISTER_SUCCEED = 0;
    private static final int REGISTER_FIALED = 1;
    private EditText name, account, phonenumber, password, repeat_password;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REGISTER_SUCCEED:
                    Toast.makeText(Register_Activity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    Register_Activity.this.finish();
                    break;
                case REGISTER_FIALED:
                    Toast.makeText(Register_Activity.this, "注册失败！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        name = (EditText) findViewById(R.id.name);
        account = (EditText) findViewById(R.id.account);
        phonenumber = (EditText) findViewById(R.id.phone_number);
        password = (EditText) findViewById(R.id.password);
        repeat_password = (EditText) findViewById(R.id.repeat_password);

    }


    public void registerOnClick(View view) {
        final String n = name.getText().toString();
        final String a = account.getText().toString();
        final String ph = phonenumber.getText().toString();
        final String pa = password.getText().toString();
        final String repa = repeat_password.getText().toString();

        if (StringUtils.isEmpty(n)) {
            Toast.makeText(this, "姓名不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(a)) {
            Toast.makeText(this, "身份证号不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(ph)) {
            Toast.makeText(this, "电话不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(pa)) {
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(repa)) {
            Toast.makeText(this, "请再次输入密码！", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pa.equals(repa)) {
            Toast.makeText(this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
            return;
        }


        Map<String, String> map = new HashMap<>();
        map.put("account", account.getText().toString());
        map.put("idCard", account.getText().toString());
        map.put("phone", account.getText().toString());
        map.put("password", account.getText().toString());
        JSONObject data1 = new JSONObject(map);
        //进行HTTP通信
        System.out.println(data1.toString());
        final CustomRequest objectRequest = new CustomRequest(Request.Method.POST, Constant.API_REGISTER, map,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("TAG-response", jsonObject.toString());
                        try {
                            if (jsonObject.getBoolean("result")) {
                                handler.sendEmptyMessage(REGISTER_SUCCEED);
                            } else {
                                handler.sendEmptyMessage(REGISTER_FIALED);
                            }
                        } catch (JSONException e) {
                            handler.sendEmptyMessage(REGISTER_FIALED);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("TAG-error", volleyError.toString());
                handler.sendEmptyMessage(REGISTER_FIALED);
            }
        });
        MedicalApplication.getInstance().addToRequestQueue(objectRequest);
    }

}
