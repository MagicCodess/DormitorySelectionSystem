package cn.edu.pku.zhangchenning.dormitoryselectionsystem;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MyApplication extends Application {
    private static MyApplication mApplication;
    private Student student;
    private Dorm dorm;

    public Dorm getDorm() {
        return dorm;
    }

    public Student getStudent() {
        return student;
    }

    @Override

    public void onCreate() {
        Log.d("MAA","MyApplication->OnCreate");
        super.onCreate();
        mApplication=this;
        student=new Student();
        dorm=new Dorm();
    }

    public static MyApplication getInstance(){
        return mApplication;
    }

}
