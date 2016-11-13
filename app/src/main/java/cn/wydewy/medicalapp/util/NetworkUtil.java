package cn.wydewy.medicalapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by wydewy on 2016/7/29.
 */
public class NetworkUtil {

    private Context context;
    private static NetworkUtil networkUtil = null;

    private NetworkUtil(Context context){
        this.context = context;
    }

    public static NetworkUtil getInstance(Context context){
        if(networkUtil==null){
            synchronized (NetworkUtil.class){
                networkUtil = new NetworkUtil(context);
            }
        }
        return networkUtil;
    }

    /**
     * 检测网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络   1：WIFI网络   2：WAP网络    3：NET网络
     */

    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;

    public int getNetworkType() {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!StringUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }



    public  static String getHTMLContentByPost(ArrayList<NameValuePair> nameValuePairArrayList, String url, boolean hasCredential, String username, String password) {
        InputStream inputStream = null;
        String line = "";
        String htmlContentString = "";
        HttpPost httpPost = new HttpPost(url);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        //当需要证书的时候
        //对HttpClient进行如下设置:
        if (hasCredential) {
            AuthScope authScope=new AuthScope(AuthScope.ANY_HOST,AuthScope.ANY_PORT);
            UsernamePasswordCredentials usernamePasswordCredentials=new UsernamePasswordCredentials(username, password);
            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(authScope, usernamePasswordCredentials);
            httpClient.setCredentialsProvider(credentialsProvider);
        }

        try {
            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 8000);
            HttpConnectionParams.setSoTimeout(httpClient.getParams(), 9000);
            HttpConnectionParams.setTcpNoDelay(httpClient.getParams(), true);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairArrayList, HTTP.UTF_8));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream, HTTP.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                while ((line = bufferedReader.readLine()) != null) {
                    htmlContentString += line;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clientShutdown(httpClient);
        return htmlContentString;
    }

    //关闭client链接
    public static void clientShutdown(HttpClient client) {
        client.getConnectionManager().shutdown();
    }

}
