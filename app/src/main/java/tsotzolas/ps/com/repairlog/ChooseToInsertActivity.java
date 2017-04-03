package tsotzolas.ps.com.repairlog;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by tsotzolas on 3/4/2017.
 */

public class ChooseToInsertActivity extends AppCompatActivity{

    private Button changeLanguageButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_to_insert_main);


    }


    public void selectCar(View view){
        Toast.makeText(this, "Select Car", Toast.LENGTH_SHORT).show();
    }


    public void selectMoto(View view){

        Toast.makeText(this, "Select Moto", Toast.LENGTH_SHORT).show();

    }






}
