package cn.edu.pku.zhangchenning.dormitoryselectionsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

public class ListActivity extends AppCompatActivity implements View.OnClickListener{
    private Button bt_chaxungeren,bt_chaxunchuangwei,bt_xuanzesushe,bt_map;
    private String stu_id;
    private Student stu;
    private Dorm dorm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        bt_chaxungeren= (Button) findViewById(R.id.bt_chaxungeren);
        bt_chaxunchuangwei= (Button) findViewById(R.id.bt_chaxunchuangwei);
        bt_xuanzesushe= (Button) findViewById(R.id.bt_xuanzesushe);
        bt_map= (Button) findViewById(R.id.bt_map);
        bt_chaxungeren.setOnClickListener(this);
        bt_chaxunchuangwei.setOnClickListener(this);
        bt_xuanzesushe.setOnClickListener(this);
        bt_map.setOnClickListener(this);

        getInfo();
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
                            sc.init(null, new TrustManager[]{new ListActivity.MyTrustManager()}, new SecureRandom());
                            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                            HttpsURLConnection.setDefaultHostnameVerifier(new ListActivity.MyHostnameVerifier());
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
                                stu.setLocation(stuinfos.getString("location"));
                                stu.setGrade(stuinfos.getString("grade"));
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

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        HttpsURLConnection con = null;
                        try{

                            SSLContext sc = SSLContext.getInstance("TLS");
                            sc.init(null, new TrustManager[]{new ListActivity.MyTrustManager()}, new SecureRandom());
                            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                            HttpsURLConnection.setDefaultHostnameVerifier(new ListActivity.MyHostnameVerifier());
                            String address2 = "https://api.mysspku.com/index.php/V1/MobileCourse/getRoom?gender=1";
                            if (stu.getGender()=="女"){
                                address2 = "https://api.mysspku.com/index.php/V1/MobileCourse/getRoom?gender=2";
                            }
                            URL url = new URL(address2);
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
                            JSONObject dorminfo = (JSONObject) jsonParser2.nextValue();
                            if (errcode==0){
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
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bt_chaxungeren){
            Intent i = new Intent(ListActivity.this, ChaxungerenActivity.class);
            startActivity(i);
        }
        if (v.getId()==R.id.bt_chaxunchuangwei){
            Intent i = new Intent(ListActivity.this, ChaxunsusheActivity.class);
            startActivity(i);
        }
        if (v.getId()==R.id.bt_xuanzesushe){
            Intent i = new Intent(ListActivity.this, XuansusheActivity.class);
            startActivity(i);
        }
        if (v.getId()==R.id.bt_map){
            Intent i = new Intent(ListActivity.this, MapActivity.class);
            startActivity(i);
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


