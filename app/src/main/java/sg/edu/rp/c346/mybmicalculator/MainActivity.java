package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etW, etH;
    Button btnC, btnR;
    TextView tvD, tvBMI, state;
    Float BMI;
    String datetime, outcome;
    SharedPreferences prefs;
    SharedPreferences.Editor prefEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        etW = findViewById(R.id.etWeight);
        etH = findViewById(R.id.etHeight);

        btnC = findViewById(R.id.btnCal);
        btnR = findViewById(R.id.btnReset);

        tvD = findViewById(R.id.tvDate);
        tvBMI = findViewById(R.id.tvBMI);
        state = findViewById(R.id.state);

        Calendar now = Calendar.getInstance();
        datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH) + 1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Float weight = Float.parseFloat(etW.getText().toString());
                Float height = Float.parseFloat(etH.getText().toString());
                height = height / 100;
                BMI = weight / (height * height);

                if(BMI<18.5){
                    outcome = "You are underweight";
                }else if(BMI>18.5&&BMI<24.9){
                    outcome = "Your BMI is normal";
                }else if(BMI>25&&BMI<29.9){
                    outcome = "You are overweight";
                }else{
                    outcome = "You are obese";
                }

                tvD.setText("Last Calculated Date: "+ datetime);
                tvBMI.setText("Last Calculated BMI: "+String.valueOf(String.format("%.3f", BMI)));
                state.setText(outcome);
            }
        });

        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etH.setText("");
                etW.setText("");
                tvD.setText("Last Calculated Date: ");
                tvBMI.setText("Last Calculated BMI: ");
                state.setText("");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefEdit = prefs.edit();

        prefEdit.putFloat("BMI", BMI);
        prefEdit.putString("Date", datetime);
        prefEdit.putString("State",outcome);
        prefEdit.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Float fBMI = prefs.getFloat("BMI", 0);
        String tvdate = prefs.getString("Date", null);
        String outcome = prefs.getString("State",null);

        tvD.setText("Last Calculated Date: "+tvdate);
        tvBMI.setText("Last Calculated BMI: "+String.valueOf(String.format("%.3f", fBMI)));
        state.setText(outcome);

    }
}
