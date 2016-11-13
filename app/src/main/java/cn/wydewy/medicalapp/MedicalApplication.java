package cn.wydewy.medicalapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import cn.wydewy.medicalapp.util.Constant;

/**
 * Created by caosh on 2016/10/25.
 */

public class MedicalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }


    private static final String TAG = MedicalApplication.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static MedicalApplication mInstance;


    public static synchronized MedicalApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public Context getGlobalContext() {
        return getApplicationContext();
    }


    public boolean isLog() {
        SharedPreferences preferences = getSharedPreferences(Constant.DATA_BASE_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(Constant.IS_LOG_IN, false);
    }

    public void setLog(boolean b) {
        SharedPreferences preferences = getSharedPreferences(Constant.DATA_BASE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constant.IS_LOG_IN, b);
        editor.commit();
    }
}
