package com.industrialmaster.happywater;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MetaDataActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta_data);
        Switch switch_button = (Switch) findViewById(R.id.training_switch);
        final SharedPreferences user=getSharedPreferences("user", Context.MODE_PRIVATE);
        final SharedPreferences.Editor userEditor = user.edit();
        waterLevelCalculate();
        switch_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    userEditor.putString("TRAINING", "YES");
                }
                else {
                    userEditor.putString("TRAINING", "NO");
                }
                userEditor.apply();
                waterLevelCalculate();
            }
        });
    }

    public void onUnitMeasureClick(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final SharedPreferences user=getSharedPreferences("user", Context.MODE_PRIVATE);
        final SharedPreferences.Editor userEditor = user.edit();

        alertDialogBuilder.setTitle("UNITS OF MEASURE")
                .setItems(R.array.unitmeasure, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String unit;
                        if(which==0){
                            unit = "METRIC";
                        } else {
                            unit = "IMPERIAL";
                        }
                        userEditor.putString("UNITOFMEASURE", unit);
                        userEditor.apply();
                        waterLevelCalculate();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        final SharedPreferences user=getSharedPreferences("user", Context.MODE_PRIVATE);
        final SharedPreferences.Editor userEditor = user.edit();
        int weight = numberPicker.getValue();
        String unit = user.getString("UNITOFMEASURE","METRIC");
        if(unit.equals("METRIC")){
            userEditor.putString("WEIGHT", weight+" Kg");
        }
        else {
            userEditor.putString("WEIGHT", weight+" LBS");
        }
        userEditor.apply();
        waterLevelCalculate();
    }

    public void onWeightClick(View view){
        WeightPicker newFragment = new WeightPicker();
        newFragment.setValueChangeListener(this);
        newFragment.show(getSupportFragmentManager(), "weight picker");
    }

    public void onGenderClick(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final SharedPreferences user=getSharedPreferences("user", Context.MODE_PRIVATE);
        final SharedPreferences.Editor userEditor = user.edit();

        alertDialogBuilder.setTitle("GENDER")
                .setItems(R.array.genderArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String gend;
                        if(which==0){
                            gend = "MALE";
                        } else {
                            gend = "FEMALE";
                        }
                        userEditor.putString("GENDER", gend);
                        userEditor.apply();
                        waterLevelCalculate();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void waterLevelCalculate(){
        final SharedPreferences user=getSharedPreferences("user", Context.MODE_PRIVATE);
        final SharedPreferences.Editor userEditor = user.edit();
        String unit = user.getString("UNITOFMEASURE","METRIC");
        String weight = user.getString("WEIGHT","50 Kg");
        String gender = user.getString("GENDER","MALE");
        String training = user.getString("TRAINING","NO");

        TextView unit_selected = findViewById(R.id.unit_selected);
        TextView weight_selected = findViewById(R.id.weight_selected);
        TextView gender_selected = findViewById(R.id.gender_selected);
        Switch training_switch = findViewById(R.id.training_switch);
        TextView calculate_txt = findViewById(R.id.calculate_txt);
        TextView water_level_txt = findViewById(R.id.water_level_txt);
        SeekBar seekBar = findViewById(R.id.seek_bar);

        unit_selected.setText(unit);
        if(unit.equals("METRIC") && !weight.substring(weight.length()-3).equals(" Kg")){
            weight = Math.round(Double.parseDouble(weight.replace(weight.substring(weight.length()-4),""))/2.20462)+" Kg";
        } else if(unit.equals("IMPERIAL") && weight.substring(weight.length()-3).equals(" Kg")){
            weight = Math.round(Double.parseDouble(weight.replace(weight.substring(weight.length()-3),""))*2.20462)+" LBS";
        } else if(unit.equals("METRIC") && weight.substring(weight.length()-3).equals(" Kg")){
            weight = Math.round(Double.parseDouble(weight.replace(weight.substring(weight.length()-3),"")))+" Kg";
        } else {
            weight = Math.round(Double.parseDouble(weight.replace(weight.substring(weight.length()-4),"")))+" LBS";
        }
        weight_selected.setText(weight);
        userEditor.putString("WEIGHT", weight);
        userEditor.apply();

        gender_selected.setText(gender);
        if(training.equals("YES")){
            training_switch.setChecked(true);
        } else{
            training_switch.setChecked(false);
        }

        double waterLevel;
        double weightNew;
        if(weight.substring(weight.length()-3).equals(" Kg")){
            weightNew = Double.parseDouble(weight.replace(weight.substring(weight.length()-3),""))*2.20462;
            seekBar.setMax(7560);
            waterLevel = Math.round(((weightNew*2*29.5735)/3)*100.0)/100.0;
            if(gender.equals("FEMALE")){
                waterLevel = Math.round((waterLevel - 1000)*100.0)/100.0;
            }
            if(training.equals("YES")){
                waterLevel = Math.round((waterLevel + 710)*100.0)/100.0;
            }
            calculate_txt.setText("its important for you to drink "+Math.round((waterLevel/1000)*100.0)/100.0+" L of water a day, But if you wish to change this number");
            water_level_txt.setText(waterLevel+" ml");
        } else {
            weightNew = Double.parseDouble(weight.replace(weight.substring(weight.length()-4),""));
            seekBar.setMax(256);
            waterLevel = Math.round(((weightNew*2)/3)*100.0)/100.0;
            if(gender.equals("FEMALE")){
                waterLevel = Math.round((waterLevel - 33.814)*100.0)/100.0;
            }
            if(training.equals("YES")){
                waterLevel = Math.round((waterLevel + 24.00796)*100.0)/100.0;
            }
            calculate_txt.setText("its important for you to drink "+waterLevel+" oz of water a day, But if you wish to change this number");
            water_level_txt.setText(waterLevel+" oz");
        }

        userEditor.putInt("WATERLEVEL",(int)waterLevel);
        userEditor.apply();
        seekBar.setProgress((int) waterLevel);

    }
}
