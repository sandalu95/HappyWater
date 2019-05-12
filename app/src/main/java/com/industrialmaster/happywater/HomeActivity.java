package com.industrialmaster.happywater;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class HomeActivity extends Activity {

    private TextView mTextMessage;
    private TextView toDrinkTxt;
    private SeekBar seekBar;
    int drank = 0;
    int water_level=0;
    String unit;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final SharedPreferences user=getSharedPreferences("user", Context.MODE_PRIVATE);
        water_level = user.getInt("WATERLEVEL",1000);
        unit = user.getString("UNITOFMEASURE","METRIC");
        mTextMessage = (TextView) findViewById(R.id.message);
        toDrinkTxt = (TextView) findViewById(R.id.toDrink_txt);
        seekBar = (SeekBar) findViewById(R.id.seekBar2);
        seekBar.setMax(water_level);
        seekBar.setEnabled(false);
        toDrinkTxt.setText(drank+" of "+water_level+(unit.equals("METRIC")?"ML":"OZ"));
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences water=getSharedPreferences("water", Context.MODE_PRIVATE);
        drank = water.getInt("WATERAMOUNT",0);
        toDrinkTxt.setText(drank+" of "+water_level+(unit.equals("METRIC")?"ML":"OZ"));
//        ImageView watercup = new ImageView(this);
//        watercup.setImageResource(R.drawable.seek1);
//        LinearLayout linearParent = (LinearLayout) findViewById(R.id.linearParent);
//        LinearLayout a = new LinearLayout(this);
//        a.setOrientation(LinearLayout.VERTICAL);
//        a.addView(watercup);
//        linearParent.addView(a);
        seekBar.setProgress(drank);
    }

    public void addWater(View view){
        Intent intent = new Intent(this, AddWaterActivity.class);
        startActivity(intent);
    }

}
