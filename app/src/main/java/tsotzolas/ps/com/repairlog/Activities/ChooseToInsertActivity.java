package tsotzolas.ps.com.repairlog.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import tsotzolas.ps.com.repairlog.R;

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
//        Toast.makeText(this, "Select Car", Toast.LENGTH_SHORT).show();
        Intent ki = new Intent(this, InsertCarActivity.class);
        startActivity(ki);
    }


    public void selectMoto(View view){
//        Toast.makeText(this, "Select Moto", Toast.LENGTH_SHORT).show();
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
