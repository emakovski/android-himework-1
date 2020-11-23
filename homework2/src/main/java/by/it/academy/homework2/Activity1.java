package by.it.academy.homework2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Activity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, Activity2.class);
        intent.putExtra("KEY",100);
        startActivityForResult(intent,1000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1000 && resultCode== Activity.RESULT_OK){
            int result=data.getIntExtra("RESULT",0);
            System.out.println("результат расчета: "+result);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}