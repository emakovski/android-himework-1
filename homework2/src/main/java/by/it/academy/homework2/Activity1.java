package by.it.academy.homework2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Activity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, Activity2.class);
        Random a = new Random();
        Random b = new Random();
        Set<Integer> array = new HashSet<>();
        int size = (a.nextInt(25)+1) * 2;
        System.out.println("Размер сгенерированного набора: "+size);
        for (int i = 1; i <= size; i++) {
            int el = b.nextInt(1000)+1;
            array.add(el);
        }
        ArrayList<Integer> collect = new ArrayList<>(array);
        System.out.println("Набор: "+collect);
        intent.putIntegerArrayListExtra("LIST",collect);
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1 && resultCode== Activity.RESULT_OK){
            double result_avr=data.getDoubleExtra("AVERAGE",0);
            int result_sum=data.getIntExtra("SUM",0);
            double result_res=data.getDoubleExtra("RES",0);
            System.out.println("Среднее арифметическое: "+result_avr);
            System.out.println("Сумма всех чисел: "+result_sum);
            System.out.println("Результат деления двух частей: "+result_res);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}