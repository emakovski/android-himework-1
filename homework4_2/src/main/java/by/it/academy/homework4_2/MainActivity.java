package by.it.academy.homework4_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private CustomView customView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SwitchCompat switchCompat = findViewById(R.id.switch1);
        customView = findViewById(R.id.customView);
        switchCompat.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if(isChecked){
            customView.setOnCustomViewActionListener((x, y, color) -> Snackbar.make(customView, "x= "+(int) x+", y= "+ (int) y, Snackbar.LENGTH_LONG)
                    .setTextColor(color)
                    .setBackgroundTint(Color.LTGRAY)
                    .show());
        }
        else {
            customView.setOnCustomViewActionListener((x, y, color) -> Toast.makeText(getApplicationContext(), "x= "+x+", y= "+y,Toast.LENGTH_LONG).show());
        }
    }
}