package tsotzolas.ps.com.repairlog;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by tsotzolas on 3/4/2017.
 * Είναι η μέθοδος όπου επιλέγεις άμα θές να βάλεις αυτοκίνητο η μηχανή
 */

public class ChooseToInsertActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_to_insert_main);
    }

    public void selectCar(View view){
        Toast.makeText(this, "Select Car", Toast.LENGTH_SHORT).show();
        Intent ki = new Intent(this, InsertCarActivity.class);
        startActivity(ki);
    }


    public void selectMoto(View view){
        Toast.makeText(this, "Select Moto", Toast.LENGTH_SHORT).show();
        Intent ki = new Intent(this, InsertMotoActivity.class);
        startActivity(ki);
    }


    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent ki = new Intent(this, MainActivity.class);
        startActivity(ki);
    }

}
