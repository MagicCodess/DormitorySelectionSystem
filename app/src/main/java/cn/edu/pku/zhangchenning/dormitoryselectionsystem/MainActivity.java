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
import android.widget.TextView;
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
    private static final int  LOG_FAIL1 =2;
    private static final int  LOG_FAIL2 =3;
    private static final int  LOG_FAIL3 =4;
    private String stu_id;
    private TextView password;

    private Student stu;
    private Dorm dorm;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case LOG_SUCCESS:
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MainActivity.this, ListActivity.class);
                    i.putExtra("student", stu_id);
                    startActivityForResult(i, 1);
                    break;
                case LOG_FAIL1:
                    Toast.makeText(MainActivity.this, "学号不存在", Toast.LENGTH_LONG).show();
                    break;
                case LOG_FAIL2:
                    Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_LONG).show();
                    break;
                case LOG_FAIL3:
                    Toast.makeText(MainActivity.this, "参数错误", Toast.LENGTH_LONG).show();
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

        password= (TextView) findViewById(R.id.password);
        password.setOnClickListener(this);

        et_username= (EditText) findViewById(R.id.et_username);
        et_password= (EditText) findViewById(R.id.et_password);
        stu=MyApplication.getInstance().getStudent();
        dorm=MyApplication.getInstance().getDorm();
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
                                    getInfo();
                                }else if(errcode==40002){
                                    Message msg =new Message();
                                    msg.what = LOG_FAIL2;
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
        if (v.getId()==R.id.password){
            Intent i = new Intent(MainActivity.this, PasswordActivity.class);
            startActivity(i);
        }
    }


    public void getInfo() {
        SharedPreferences preferences=getSharedPreferences("records", MODE_PRIVATE);
        String stu_id=preferences.getString("studentid", "101010100");
        stu=MyApplication.getInstance().getStudent();
        dorm=MyApplication.getInstance().getDorm();
        final String address = "https://api.mysspku.com/index.php/V1/MobileCourse/getDetail?stuid="+stu_id;
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        HttpsURLConnection con = null;
                        try{

                            SSLContext sc = SSLContext.getInstance("TLS");
                            sc.init(null, new TrustManager[]{new MainActivity.MyTrustManager()}, new SecureRandom());
                            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                            HttpsURLConnection.setDefaultHostnameVerifier(new MainActivity.MyHostnameVerifier());
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
                            JSONObject stuinfos1 = (JSONObject) jsonParser.nextValue();
// 接下来的就是JSON对象的操作了
                            int errcode=Integer.parseInt(stuinfos1.getString("errcode"));
                            String info=stuinfos1.getString("data");
                            JSONTokener jsonParser2 = new JSONTokener(info);
                            JSONObject stuinfos = (JSONObject) jsonParser2.nextValue();
                            if (errcode==0){
                                stu.setStudentid(stuinfos.getString("studentid"));
                                stu.setName(stuinfos.getString("name"));
                                stu.setGender(stuinfos.getString("gender"));
                                stu.setVcode(stuinfos.getString("vcode"));
                                stu.setRoom(stuinfos.getString("room"));
                                stu.setBuilding(stuinfos.getString("building"));
//                                stu.setLocation(stuinfos.getString("location"));
//                                stu.setGrade(stuinfos.getString("grade"));
                                Message msg =new Message();
                                msg.what = LOG_SUCCESS;
                                mHandler.sendMessage(msg);
                            }else if(errcode==40001){
                                Message msg =new Message();
                                msg.what = LOG_FAIL1;
                                mHandler.sendMessage(msg);
                            }else if(errcode==40002){
                                Message msg =new Message();
                                msg.what = LOG_FAIL2;
                                mHandler.sendMessage(msg);
                            }

                            String address2 = "https://api.mysspku.com/index.php/V1/MobileCourse/getRoom?gender=1";
                            System.out.print(stu.getGender());
                            if (stu.getGender().equals("女")){
                                address2 = "https://api.mysspku.com/index.php/V1/MobileCourse/getRoom?gender=2";
                                System.out.print(stu.getGender());
                            }
                            URL url2 = new URL(address2);
                            con = (HttpsURLConnection)url2.openConnection();
                            con.setRequestMethod("GET");
                            con.setConnectTimeout(8000);
                            con.setReadTimeout(8000);
                            InputStream in2 = con.getInputStream();
                            BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2));
                            StringBuilder response2 = new StringBuilder();
                            String str2;
                            while ((str2=reader2.readLine())!=null){
                                response2.append(str2);
                                System.out.println(str2);
                            }

                            JSONTokener jsonParser3 = new JSONTokener(response2.toString());
// 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
                            JSONObject stuinfos12 = (JSONObject) jsonParser3.nextValue();
// 接下来的就是JSON对象的操作了
                            int errcode2=Integer.parseInt(stuinfos12.getString("errcode"));
                            String info2=stuinfos12.getString("data");
                            JSONTokener jsonParser22 = new JSONTokener(info2);
                            JSONObject dorminfo = (JSONObject) jsonParser22.nextValue();
                            if (errcode2==0){
                                dorm.setWu(dorminfo.getInt("5"));
                                dorm.setShisan(dorminfo.getInt("13"));
                                dorm.setShisi(dorminfo.getInt("14"));
                                dorm.setBa(dorminfo.getInt("8"));
                                dorm.setJiu(dorminfo.getInt("9"));
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

//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        HttpsURLConnection con = null;
//
//                        try{
//                            Thread.sleep(1500);
//                            SSLContext sc = SSLContext.getInstance("TLS");
//                            sc.init(null, new TrustManager[]{new ListActivity.MyTrustManager()}, new SecureRandom());
//                            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//                            HttpsURLConnection.setDefaultHostnameVerifier(new ListActivity.MyHostnameVerifier());
//
//
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }finally {
//                            if(con!=null){
//                                con.disconnect();
//                            }
//                        }
//                    }
//                }
//        ).start();
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
