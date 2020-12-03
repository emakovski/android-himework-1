package by.it.academy.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ArrayList<Integer> data=getIntentData();
        double average = 0;
        for (Integer value : data) {
            average += value;
        }
        average/=data.size();
     //   System.out.println("Среднее арифметическое: "+average);

        int sum = 0;
        for (Integer integer : data) {
            sum += integer;
        }
     //   System.out.println("Сумма всех чисел: "+sum);

        Intent intent=new Intent();
        intent.putExtra("AVERAGE",average);
        intent.putExtra("SUM",sum);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    private ArrayList<Integer> getIntentData(){
        Intent intent=getIntent();
        if (intent!=null){
            return intent.getIntegerArrayListExtra("LIST");
        }
        return null;
    }
}