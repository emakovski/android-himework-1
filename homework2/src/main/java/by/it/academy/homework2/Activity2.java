package by.it.academy.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        int data=getIntentData();
        int result=5*data;
        Intent intent=new Intent();
        intent.putExtra("RESULT",result);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    private int getIntentData(){
        Intent intent=getIntent();
        if (intent!=null){
            return intent.getIntExtra("KEY",0);
        }
        return 0;
    }
}