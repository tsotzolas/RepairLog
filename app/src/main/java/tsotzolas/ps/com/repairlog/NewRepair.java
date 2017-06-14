package tsotzolas.ps.com.repairlog;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.UUID;

import io.realm.Realm;
import tsotzolas.ps.com.repairlog.Model.Repair;

import static tsotzolas.ps.com.repairlog.VehicleView.selectedVehicles;

/**
 * Created by tsotzolas on 27/4/2017.
 */

public class NewRepair extends AppCompatActivity implements
        View.OnClickListener {
    private static final String TAG = NewRepair.class.getSimpleName();

    private Realm realm;
    private Repair repair;

    private EditText dateEditText;
    private EditText costEditText;
    private EditText kmEditText;
    private EditText descEditText;

    private int mYear, mMonth, mDay, mHour, mMinute;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;


//    private MyAdapter1 myAdapter1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_repair);

        dateEditText = (EditText) findViewById(R.id.editDate);
        costEditText = (EditText) findViewById(R.id.editCost);
        kmEditText = (EditText) findViewById(R.id.editKm);
        kmEditText = (EditText) findViewById(R.id.editKm);
        descEditText = (EditText) findViewById(R.id.editDesc);


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


    private void gotoInsert(View view) {
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
        repair.setRepairId(String.valueOf(UUID.randomUUID()));


        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(repair);
        realm.commitTransaction();


        Toast.makeText(this, R.string.insert_vehicle_car_saved, Toast.LENGTH_SHORT).show();


        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
//        mFirebaseDatabase = mFirebaseInstance.getReference("repair");
        // store app title to 'app_title' node
//        mFirebaseInstance.getReference("repair").setValue(repair);

        Gson gson = new Gson();

//        //Add Date
//        mFirebaseInstance.getReference("user")
//                .child("tsotzolas")
//                .child("vehicle")
//                .child(repair.getVehicleId())
//                .child("repair")
//                .child(repair.getRepairId())
//                .child("repair_date").setValue(repair.getRepairDate());
//        //Add Descreption
//        mFirebaseInstance.getReference("user")
//                .child("tsotzolas")
//                .child("vehicle")
//                .child(repair.getVehicleId())
//                .child("repair")
//                .child(repair.getRepairId())
//                .child("repair_desc").setValue(repair.getRepairDescription());
//
//        //Add Cost
//        mFirebaseInstance.getReference("user")
//                .child("tsotzolas")
//                .child("vehicle")
//                .child(repair.getVehicleId())
//                .child("repair")
//                .child(repair.getRepairId())
//                .child("repair_cost").setValue(repair.getRepairCost());
//
//        //Add Km
//        mFirebaseInstance.getReference("user")
//                .child("tsotzolas")
//                .child("vehicle")
//                .child(repair.getVehicleId())
//                .child("repair")
//                .child(repair.getRepairId())
//                .child("vehicle_km").setValue(repair.getVehicleKM());


        mFirebaseInstance.getReference("user")
                .child("tsotzolas")
                .child("vehicle")
                .child(repair.getVehicleId())
                .child("repair")
                .setValue(repair);


        String json = gson.toJson(repair);
        System.out.println(json);
        System.out.println(json);


        Intent ki = new Intent(this, VehicleRepairs.class);
        startActivity(ki);


    }


    @Override
    public void onClick(View v) {

//        if (v == btnDatePicker) {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
//        }
//        if (v == btnTimePicker) {
//
//            // Get Current Time
//            final Calendar c = Calendar.getInstance();
//            mHour = c.get(Calendar.HOUR_OF_DAY);
//            mMinute = c.get(Calendar.MINUTE);

//            // Launch Time Picker Dialog
//            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
//                    new TimePickerDialog.OnTimeSetListener() {
//
//                        @Override
//                        public void onTimeSet(TimePicker view, int hourOfDay,
//                                              int minute) {
//
//                            txtTime.setText(hourOfDay + ":" + minute);
//                        }
//                    }, mHour, mMinute, false);
//            timePickerDialog.show();
//        }
    }


}
