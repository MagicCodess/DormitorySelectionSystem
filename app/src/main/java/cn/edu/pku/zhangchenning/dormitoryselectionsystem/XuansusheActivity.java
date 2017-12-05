package cn.edu.pku.zhangchenning.dormitoryselectionsystem;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class XuansusheActivity extends AppCompatActivity implements View.OnClickListener{
    private Button xuanze,fanhui;
    private EditText stu1id,v1code,stu2id,v2code,stu3id,v3code;
    private TextView stuid,tv1,tv2,tv3;
    private Spinner num,buildingNo;
    private Student stu;
    private boolean flag=true;
    List<String> list = new ArrayList<String>();
    List<String> list2 = new ArrayList<String>();
    private static final int CHOOSE_SUCCESS=0;
    private static final int CHOOSE_1=1;
    private static final int CHOOSE_2=2;
    private static final int CHOOSE_3=3;
    private  int number=0;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CHOOSE_SUCCESS:
                    //Toast.makeText(XuansusheActivity.this, String.valueOf(flag), Toast.LENGTH_LONG).show();
                    if (flag==true){
                        if (number==0){
                        }else if (number==1){
                            if (stu1id.getText().toString().equals(""))
                                break;
                        }else if(number==2){
                            if (stu1id.getText().toString().equals(""))
                                break;
                            if (stu2id.getText().toString().equals(""))
                                break;
                        }else if (number==3){
                            if (stu1id.getText().toString().equals(""))
                                break;
                            if (stu2id.getText().toString().equals(""))
                                break;
                            if (stu3id.getText().toString().equals(""))
                                break;
                        }
                        Intent i = new Intent(XuansusheActivity.this, SuccActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(XuansusheActivity.this, "Oops!", Toast.LENGTH_LONG).show();
                    }
                    break;
                case CHOOSE_1:
                    flag=false;
                    break;
                case CHOOSE_2:
                    flag=false;
                    break;
                case CHOOSE_3:
                    flag=false;
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xuansushe);

        xuanze= (Button) findViewById(R.id.bt_xuanze);
        fanhui= (Button) findViewById(R.id.bt_fanhui3);
        xuanze.setOnClickListener(this);
        fanhui.setOnClickListener(this);

        num= (Spinner) findViewById(R.id.num);
        stu1id= (EditText) findViewById(R.id.stu1id);
        v1code= (EditText) findViewById(R.id.v1code);
        stu2id= (EditText) findViewById(R.id.stu2id);
        v2code= (EditText) findViewById(R.id.v2code);
        stu3id= (EditText) findViewById(R.id.stu3id);
        v3code= (EditText) findViewById(R.id.v3code);
        buildingNo= (Spinner) findViewById(R.id.buildingNo);
        tv1= (TextView) findViewById(R.id.tvv1);
        tv2= (TextView) findViewById(R.id.tvv2);
        tv3= (TextView) findViewById(R.id.tvv3);

        stuid= (TextView) findViewById(R.id.stuid);
        stu=MyApplication.getInstance().getStudent();
        stuid.setText(stu.getStudentid());
        stu1id.setFocusable(false);
        v1code.setFocusable(false);
        stu2id.setFocusable(false);
        v2code.setFocusable(false);
        stu3id.setFocusable(false);
        v3code.setFocusable(false);
        stu1id.setText("---");
        v1code.setText("---");
        stu2id.setText("---");
        v2code.setText("---");
        stu3id.setText("---");
        v3code.setText("---");

        list.add("1人");
        list.add("2人");
        list.add("3人");
        list.add("4人");

        list2.add("5号楼");
        list2.add("13号楼");
        list2.add("14号楼");
        list2.add("8号楼");
        list2.add("9号楼");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.spinner,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        num.setAdapter(adapter);
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this, R.layout.spinner,list2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingNo.setAdapter(adapter2);
        num.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                tv1.setText(adapter.getItem(position));
                number=position;
                if (position==0){
                    stu1id.setFocusable(false);
                    v1code.setFocusable(false);
                    stu2id.setFocusable(false);
                    v2code.setFocusable(false);
                    stu3id.setFocusable(false);
                    v3code.setFocusable(false);
                    stu1id.setFocusableInTouchMode(false);
                    v1code.setFocusableInTouchMode(false);
                    stu2id.setFocusableInTouchMode(false);
                    v2code.setFocusableInTouchMode(false);
                    stu3id.setFocusableInTouchMode(false);
                    v3code.setFocusableInTouchMode(false);
                    stu1id.setText("---");
                    v1code.setText("---");
                    stu2id.setText("---");
                    v2code.setText("---");
                    stu3id.setText("---");
                    v3code.setText("---");
                }else if (position==1){
                    stu1id.setFocusable(true);
                    v1code.setFocusable(true);
                    stu2id.setFocusable(false);
                    v2code.setFocusable(false);
                    stu3id.setFocusable(false);
                    v3code.setFocusable(false);
                    stu1id.setFocusableInTouchMode(true);
                    v1code.setFocusableInTouchMode(true);
                    stu2id.setFocusableInTouchMode(false);
                    v2code.setFocusableInTouchMode(false);
                    stu3id.setFocusableInTouchMode(false);
                    v3code.setFocusableInTouchMode(false);
                    stu1id.requestFocus();
                    stu1id.setText("");
                    v1code.setText("");
                    stu2id.setText("---");
                    v2code.setText("---");
                    stu3id.setText("---");
                    v3code.setText("---");
                }else if (position==2){
                    stu1id.setFocusable(true);
                    v1code.setFocusable(true);
                    stu2id.setFocusable(true);
                    v2code.setFocusable(true);
                    stu3id.setFocusable(false);
                    v3code.setFocusable(false);
                    stu1id.setFocusableInTouchMode(true);
                    v1code.setFocusableInTouchMode(true);
                    stu2id.setFocusableInTouchMode(true);
                    v2code.setFocusableInTouchMode(true);
                    stu3id.setFocusableInTouchMode(false);
                    v3code.setFocusableInTouchMode(false);
                    stu1id.requestFocus();
                    stu1id.setText("");
                    v1code.setText("");
                    stu2id.setText("");
                    v2code.setText("");
                    stu3id.setText("---");
                    v3code.setText("---");
                }else if (position==3){
                    stu1id.setFocusable(true);
                    v1code.setFocusable(true);
                    stu2id.setFocusable(true);
                    v2code.setFocusable(true);
                    stu3id.setFocusable(true);
                    v3code.setFocusable(true);
                    stu1id.setFocusableInTouchMode(true);
                    v1code.setFocusableInTouchMode(true);
                    stu2id.setFocusableInTouchMode(true);
                    v2code.setFocusableInTouchMode(true);
                    stu3id.setFocusableInTouchMode(true);
                    v3code.setFocusableInTouchMode(true);
                    stu1id.requestFocus();
                    stu1id.setText("");
                    v1code.setText("");
                    stu2id.setText("");
                    v2code.setText("");
                    stu3id.setText("");
                    v3code.setText("");
                }
            }
            //没有选中时的处理
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        buildingNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // parent： 为控件Spinner view：显示文字的TextView position：下拉选项的位置从0开始
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) parent.getAdapter();
                tv2.setText(adapter.getItem(position));
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bt_fanhui3){
            Intent i = new Intent();
            finish();
        }
        if (v.getId()==R.id.bt_xuanze){
            flag=true;
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            if (stu1id.getText().toString() .length()==10||stu1id.getText().toString()=="") {
                                HttpsURLConnection con1 = null;
                                try {

                                    String address = "https://api.mysspku.com/index.php/V1/MobileCourse/getDetail?stuid=" + stu1id.getText();
                                    SSLContext sc = SSLContext.getInstance("TLS");
                                    sc.init(null, new TrustManager[]{new XuansusheActivity.MyTrustManager()}, new SecureRandom());
                                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                                    HttpsURLConnection.setDefaultHostnameVerifier(new XuansusheActivity.MyHostnameVerifier());
                                    URL url = new URL(address);
                                    con1 = (HttpsURLConnection) url.openConnection();
                                    con1.setRequestMethod("GET");
                                    con1.setConnectTimeout(8000);
                                    con1.setReadTimeout(8000);
                                    InputStream in = con1.getInputStream();
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                    StringBuilder response = new StringBuilder();
                                    String str;
                                    while ((str = reader.readLine()) != null) {
                                        response.append(str);
                                        System.out.println(str);
                                    }

                                    JSONTokener jsonParser = new JSONTokener(response.toString());
// 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
                                    JSONObject stuinfos1 = (JSONObject) jsonParser.nextValue();
// 接下来的就是JSON对象的操作了
                                    int errcode = Integer.parseInt(stuinfos1.getString("errcode"));
                                    String info = stuinfos1.getString("data");
                                    JSONTokener jsonParser2 = new JSONTokener(info);
                                    JSONObject stuinfos = (JSONObject) jsonParser2.nextValue();
                                    if (errcode == 0) {
                                        if (!stuinfos.getString("gender") .equals(stu.getGender()) ) {
                                            Message msg =new Message();
                                            msg.what = CHOOSE_1;
                                            mHandler.sendMessage(msg);
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    if (con1 != null) {
                                        con1.disconnect();
                                    }
                                }
                            }
                            if (stu2id.getText().toString() .length()==10||stu2id.getText().toString()=="") {
                                HttpsURLConnection con2 = null;
                                try {

                                    String address = "https://api.mysspku.com/index.php/V1/MobileCourse/getDetail?stuid=" + stu2id.getText();
                                    SSLContext sc = SSLContext.getInstance("TLS");
                                    sc.init(null, new TrustManager[]{new XuansusheActivity.MyTrustManager()}, new SecureRandom());
                                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                                    HttpsURLConnection.setDefaultHostnameVerifier(new XuansusheActivity.MyHostnameVerifier());
                                    URL url = new URL(address);
                                    con2 = (HttpsURLConnection) url.openConnection();
                                    con2.setRequestMethod("GET");
                                    con2.setConnectTimeout(8000);
                                    con2.setReadTimeout(8000);
                                    InputStream in = con2.getInputStream();
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                    StringBuilder response = new StringBuilder();
                                    String str;
                                    while ((str = reader.readLine()) != null) {
                                        response.append(str);
                                        System.out.println(str);
                                    }

                                    JSONTokener jsonParser = new JSONTokener(response.toString());
// 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
                                    JSONObject stuinfos1 = (JSONObject) jsonParser.nextValue();
// 接下来的就是JSON对象的操作了
                                    int errcode = Integer.parseInt(stuinfos1.getString("errcode"));
                                    String info = stuinfos1.getString("data");
                                    JSONTokener jsonParser2 = new JSONTokener(info);
                                    JSONObject stuinfos = (JSONObject) jsonParser2.nextValue();
                                    if (errcode == 0) {
                                        if (!stuinfos.getString("gender") .equals(stu.getGender())) {
                                            Message msg =new Message();
                                            msg.what = CHOOSE_2;
                                            mHandler.sendMessage(msg);
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    if (con2 != null) {
                                        con2.disconnect();
                                    }
                                }
                            }
                            if (stu3id.getText().toString().length()==10||stu3id.getText().toString()=="") {
                                HttpsURLConnection con3 = null;
                                try {

                                    String address = "https://api.mysspku.com/index.php/V1/MobileCourse/getDetail?stuid=" + stu3id.getText();
                                    SSLContext sc = SSLContext.getInstance("TLS");
                                    sc.init(null, new TrustManager[]{new XuansusheActivity.MyTrustManager()}, new SecureRandom());
                                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                                    HttpsURLConnection.setDefaultHostnameVerifier(new XuansusheActivity.MyHostnameVerifier());
                                    URL url = new URL(address);
                                    con3 = (HttpsURLConnection) url.openConnection();
                                    con3.setRequestMethod("GET");
                                    con3.setConnectTimeout(8000);
                                    con3.setReadTimeout(8000);
                                    InputStream in = con3.getInputStream();
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                    StringBuilder response = new StringBuilder();
                                    String str;
                                    while ((str = reader.readLine()) != null) {
                                        response.append(str);
                                        System.out.println(str);
                                    }

                                    JSONTokener jsonParser = new JSONTokener(response.toString());
// 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
                                    JSONObject stuinfos1 = (JSONObject) jsonParser.nextValue();
// 接下来的就是JSON对象的操作了
                                    int errcode = Integer.parseInt(stuinfos1.getString("errcode"));
                                    String info = stuinfos1.getString("data");
                                    JSONTokener jsonParser2 = new JSONTokener(info);
                                    JSONObject stuinfos = (JSONObject) jsonParser2.nextValue();
                                    if (errcode == 0) {
                                        if (!stuinfos.getString("gender") .equals(stu.getGender())) {
                                            Message msg =new Message();
                                            msg.what = CHOOSE_3;
                                            mHandler.sendMessage(msg);
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    if (con3 != null) {
                                        con3.disconnect();
                                    }
                                }
                            }
                        }

                    }).start();
            //Toast.makeText(this, String.valueOf(flag), Toast.LENGTH_LONG).show();
            new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        HttpsURLConnection con = null;
                            try {
                                Thread.sleep(2000);
                                    String src = "{\n" +
                                            "    \"errcode\":0,\n" +
                                            "    \"data\":{\n" +
                                            "        \"num\":" + tv1.getText() + ",\n" +
                                            "        \"stuid\":" + stuid.getText() + ",\n" +
                                            "        \"stu1id\":" + stu1id.getText() + ",\n" +
                                            "        \"v1code\":" + v1code.getText() + ",\n" +
                                            "        \"stu2id\":" + stu2id.getText() + ",\n" +
                                            "        \"v2code\":" + v2code.getText() + ",\n" +
                                            "        \"stu3id\":" + stu3id.getText() + ",\n" +
                                            "        \"v3code\":" + v3code.getText() + ",\n" +
                                            "        \"buildingNo\":" + tv2.getText() + "\n" +
                                            "    }\n" +
                                            "}";
                                    byte[] data = src.getBytes();
                                    SSLContext sc = SSLContext.getInstance("TLS");
                                    sc.init(null, new TrustManager[]{new XuansusheActivity.MyTrustManager()}, new SecureRandom());
                                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                                    HttpsURLConnection.setDefaultHostnameVerifier(new XuansusheActivity.MyHostnameVerifier());
                                    String address = "https://api.mysspku.com/index.php/V1/MobileCourse/SelectRoom";
                                    URL url = new URL(address);
                                    con = (HttpsURLConnection) url.openConnection();
                                    con.setRequestMethod("POST");
                                    con.setConnectTimeout(8000);
                                    con.setReadTimeout(8000);
                                    con.setDoInput(true);                  //打开输入流，以便从服务器获取数据
                                    con.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
                                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                                    //设置请求体的长度
                                    con.setRequestProperty("Content-Length", String.valueOf(data.length));
                                    //获得输出流，向服务器写入数据
                                    OutputStream outputStream = con.getOutputStream();
                                    outputStream.write(data);
                                    InputStream in = con.getInputStream();
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                    StringBuilder response = new StringBuilder();
                                    String str;
                                    while ((str = reader.readLine()) != null) {
                                        response.append(str);
                                        System.out.println(str);
                                    }

                                    JSONTokener jsonParser = new JSONTokener(response.toString());
// 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
                                    JSONObject stuinfos1 = (JSONObject) jsonParser.nextValue();
// 接下来的就是JSON对象的操作了
                                    int errcode = Integer.parseInt(stuinfos1.getString("errcode"));
                                    if (errcode == 0) {
                                        Message msg = new Message();
                                        msg.what = CHOOSE_SUCCESS;
                                        mHandler.sendMessage(msg);
                                    }

                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                if (con != null) {
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
