package cn.edu.pku.zhangchenning.dormitoryselectionsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText xuehao,mima;
    private Button fanhui,mima1;

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bt_fanhui6){
            Intent i = new Intent(PasswordActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        if (v.getId()==R.id.mimazhaohui1){
            mima.setText(xuehao.getText());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        fanhui= (Button) findViewById(R.id.bt_fanhui6);
        fanhui.setOnClickListener(this);
        xuehao= (EditText) findViewById(R.id.xuehaozhaohui);
        mima= (EditText) findViewById(R.id.mimazhaohui);

        mima1= (Button) findViewById(R.id.mimazhaohui1);
        mima1.setOnClickListener(this);


    }
}
