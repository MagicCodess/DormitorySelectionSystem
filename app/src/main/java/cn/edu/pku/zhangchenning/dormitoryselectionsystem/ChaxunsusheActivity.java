package cn.edu.pku.zhangchenning.dormitoryselectionsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChaxunsusheActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView wu,shisan,shisi,ba,jiu;
    private Button fanhui;
    private Dorm dorm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chaxunsushe);

        wu= (TextView) findViewById(R.id.wu);
        shisan= (TextView) findViewById(R.id.shisan);
        shisi= (TextView) findViewById(R.id.shisi);
        ba= (TextView) findViewById(R.id.ba);
        jiu= (TextView) findViewById(R.id.jiu);
        fanhui= (Button) findViewById(R.id.bt_fanhui2);
        fanhui.setOnClickListener(this);

        dorm=MyApplication.getInstance().getDorm();
        wu.setText(Integer.toString(dorm.getWu()));
        shisan.setText(Integer.toString(dorm.getShisan()));
        shisi.setText(Integer.toString(dorm.getShisi()));
        ba.setText(Integer.toString(dorm.getBa()));
        jiu.setText(Integer.toString(dorm.getJiu()));
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bt_fanhui2){
            Intent i = new Intent();
            finish();
        }
    }
}
