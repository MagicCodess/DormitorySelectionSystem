package cn.edu.pku.zhangchenning.dormitoryselectionsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuccActivity extends AppCompatActivity implements View.OnClickListener{
    private Button fanhui;

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bt_fanhui4){
            Intent i = new Intent(SuccActivity.this, ListActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succ);
        fanhui= (Button) findViewById(R.id.bt_fanhui4);
        fanhui.setOnClickListener(this);
    }
}
