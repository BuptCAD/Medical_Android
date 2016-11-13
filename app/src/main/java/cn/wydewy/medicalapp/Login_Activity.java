package cn.wydewy.medicalapp;

import android.content.Intent;
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

public class Login_Activity extends AppCompatActivity {

    private static final int LOG_IN_SUCCEED = 0;
    private static final int LOG_IN_FIALED = 1;
    private EditText name, account, phonenumber, password;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOG_IN_SUCCEED:
                    Toast.makeText(Login_Activity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    MedicalApplication.getInstance().setLog(true);
                    Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    Login_Activity.this.finish();
                    break;
                case LOG_IN_FIALED:
                    Toast.makeText(Login_Activity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        name = (EditText) findViewById(R.id.name);
        account = (EditText) findViewById(R.id.account);
        phonenumber = (EditText) findViewById(R.id.phone_number);
        password = (EditText) findViewById(R.id.password);

    }

    public void loginOnClick(View view) {
        final String n = name.getText().toString();
        final String a = account.getText().toString();
        final String ph = phonenumber.getText().toString();
        final String pa = password.getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put("account", a);
        map.put("idCard", account.getText().toString());
        map.put("phone", account.getText().toString());
        map.put("password", account.getText().toString());
        CustomRequest objectRequest = new CustomRequest(Request.Method.POST, Constant.API_LOG_IN, map,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("TAG-response", jsonObject.toString());
                        try {
                            if (jsonObject.getBoolean("result")) {
                                handler.sendEmptyMessage(LOG_IN_SUCCEED);
                            } else {
                                handler.sendEmptyMessage(LOG_IN_FIALED);
                            }
                        } catch (JSONException e) {
                            handler.sendEmptyMessage(LOG_IN_FIALED);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("TAG-error", volleyError.toString());
                handler.sendEmptyMessage(LOG_IN_FIALED);
            }
        });
        MedicalApplication.getInstance().addToRequestQueue(objectRequest);
    }

    public void goRegisterOnClick(View view) {
        startActivity(new Intent(this, Register_Activity.class));
    }

}

