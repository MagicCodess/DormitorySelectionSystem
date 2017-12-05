package cn.edu.pku.zhangchenning.dormitoryselectionsystem;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button bt_go;
    private EditText et_username,et_password;
    private static final int  LOG_SUCCESS=1;
    private String stu_id;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case LOG_SUCCESS:
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MainActivity.this, ListActivity.class);
                    i.putExtra("student", stu_id);
                    startActivityForResult(i, 1);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_go= (Button) findViewById(R.id.bt_go);
        bt_go.setOnClickListener(this);

        et_username= (EditText) findViewById(R.id.et_username);
        et_password= (EditText) findViewById(R.id.et_password);
    }


    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.bt_go){
            //Toast.makeText(MainActivity.this, et_username.getText().toString()+et_password.getText().toString(), Toast.LENGTH_LONG).show();
            final String address = "https://api.mysspku.com/index.php/V1/MobileCourse/Login?username="+et_username.getText().toString()+"&password="+et_password.getText().toString();
            stu_id=et_username.getText().toString();
            SharedPreferences preferences=getSharedPreferences("records",MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("studentid", stu_id);
            editor.commit();
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            HttpsURLConnection con = null;
                            try{

                                SSLContext sc = SSLContext.getInstance("TLS");
                                sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
                                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                                HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
                                URL url = new URL(address);
                                con = (HttpsURLConnection)url.openConnection();
                                con.setRequestMethod("GET");
                                con.setConnectTimeout(8000);
                                con.setReadTimeout(8000);
                                InputStream in = con.getInputStream();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                StringBuilder response = new StringBuilder();
                                String str;
                                while ((str=reader.readLine())!=null){
                                    response.append(str);
                                    System.out.println(str);
                                }

                                JSONTokener jsonParser = new JSONTokener(response.toString());
// 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
                                JSONObject loginfos = (JSONObject) jsonParser.nextValue();
// 接下来的就是JSON对象的操作了
                                int errcode=Integer.parseInt(loginfos.getString("errcode"));
                                if (errcode==0){
                                    Message msg =new Message();
                                    msg.what = LOG_SUCCESS;
                                    mHandler.sendMessage(msg);
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }finally {
                                if(con!=null){
                                    con.disconnect();
                                }
                            }
                        }
                    }
            ).start();
        }
    }



    class MyTrustManager implements X509TrustManager
    {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }

    }
    class MyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            // TODO Auto-generated method stub
            return true;
        }

    }
}
