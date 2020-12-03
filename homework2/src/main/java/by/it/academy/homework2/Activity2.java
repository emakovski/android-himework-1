package by.it.academy.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

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

        int sum = 0;
        for (Integer integer : data) {
            sum += integer;
        }

        List<Integer> sublist1= data.subList(0,(data.size()/2));
        double subsum =0;
        for (Integer integer : sublist1) {
            subsum += integer;
        }

        List <Integer> sublist2= data.subList((data.size()/2),data.size());
        double subdiv = 0;
        for (Integer integer : sublist2) {
            subdiv-=integer;
        }

        double res=subsum/subdiv;

        Intent intent=new Intent();
        intent.putExtra("AVERAGE",average);
        intent.putExtra("SUM",sum);
        intent.putExtra("RES",res);
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