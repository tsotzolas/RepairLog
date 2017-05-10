package tsotzolas.ps.com.repairlog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import tsotzolas.ps.com.repairlog.Model.Repair;

import static tsotzolas.ps.com.repairlog.VehicleView.selectedVehicles;

/**
 * Created by tsotzolas on 27/4/2017.
 */

public class NewRepair extends AppCompatActivity {
    private static final String TAG = NewRepair.class.getSimpleName();

    private Realm realm;
    private Repair repair;

    private EditText dateEditText;
    private EditText costEditText;
    private EditText kmEditText;
    private EditText descEditText;


//    private MyAdapter1 myAdapter1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_repair);

        dateEditText = (EditText) findViewById(R.id.editDate);
        costEditText = (EditText) findViewById(R.id.editCost);
        kmEditText = (EditText) findViewById(R.id.editKm);
        kmEditText = (EditText) findViewById(R.id.editKm);
        descEditText= (EditText) findViewById(R.id.editDesc);





        //Αρχικοποίηση του Realm
        realm = Realm.getDefaultInstance();
//        repair = new Repair();
//        repair.setVehicleId(selectedVehicles.getId());
//        repair.setRepairCost("11");
//        repair.setRepairDate(new Date());
//        repair.setRepairDescription("Test11");

//        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        realm.copyToRealm(repair);
//        realm.commitTransaction();

        //Εκτελούμε το ερώτημα



    }





    private void gotoInsert(View view){
//        Intent ki = new Intent(this, ChooseToInsertActivity.class);
//        startActivity(ki);
        System.out.println("Test");
    }




    public void save(View view) {

        repair = new Repair();
        repair.setRepairCost(costEditText.getText().toString());
        repair.setRepairDate(dateEditText.getText().toString());
        repair.setRepairDescription(descEditText.getText().toString());
        repair.setVehicleKM(kmEditText.getText().toString());
        repair.setVehicleId(selectedVehicles.getId());


        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(repair);
        realm.commitTransaction();


        Toast.makeText(this, R.string.insert_vehicle_car_saved, Toast.LENGTH_SHORT).show();

        Intent ki = new Intent(this, VehicleCenter.class);
        startActivity(ki);


    }




}
