package com.industrialmaster.happywater;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class AddWaterActivity extends AppCompatActivity {

    TextView waterTxt;
    int waterAmount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_water);
        final SharedPreferences user=getSharedPreferences("user", Context.MODE_PRIVATE);
        String unit = user.getString("UNITOFMEASURE","METRIC");
        SeekBar seekbar = (SeekBar) findViewById(R.id.seekBar2);
        waterTxt = findViewById(R.id.waterTxt);
        TextView unitTxt = findViewById(R.id.unitTxt);

        if(unit.equals("METRIC")){
            seekbar.setMax(60);
            unitTxt.setText("ml");
        } else {
            seekbar.setMax(3);
            unitTxt.setText("oz");
        }

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                waterTxt.setText(progress+"");
                waterAmount = Integer.parseInt(waterTxt.getText().toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void doneClick(View view){
        final SharedPreferences water=getSharedPreferences("water", Context.MODE_PRIVATE);
        final SharedPreferences.Editor waterEditor = water.edit();
        int waterOld = water.getInt("WATERAMOUNT",0);
        waterEditor.putInt("WATERAMOUNT", waterOld+waterAmount);
        waterEditor.apply();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
