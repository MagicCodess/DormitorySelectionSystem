package cn.edu.pku.zhangchenning.dormitoryselectionsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class ChaxungerenActivity extends AppCompatActivity implements View.OnClickListener{
    private Button bt_fanhui;
    private Student stu;

    private TextView studentid,name,gender,vcode,room,building,location,grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chaxungeren);
        bt_fanhui= (Button) findViewById(R.id.bt_fanhui);
        bt_fanhui.setOnClickListener(this);
        studentid= (TextView) findViewById(R.id.studentid);
        name= (TextView) findViewById(R.id.name);
        gender= (TextView) findViewById(R.id.gender);
        vcode= (TextView) findViewById(R.id.vcode);
//        room= (TextView) findViewById(R.id.room);
//        building= (TextView) findViewById(R.id.building);
//        location= (TextView) findViewById(R.id.location);
//        grade= (TextView) findViewById(R.id.grade);

        stu=MyApplication.getInstance().getStudent();
        studentid.setText(stu.getStudentid());
        name.setText(stu.getName());
        gender.setText(stu.getGender());
        vcode.setText(stu.getVcode());
//        building.setText(stu.getBuilding());
//        room.setText(stu.getRoom());
//        location.setText(stu.getLocation());
//        grade.setText(stu.getGrade());

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bt_fanhui){
            Intent i = new Intent();
            finish();
        }
    }
}
