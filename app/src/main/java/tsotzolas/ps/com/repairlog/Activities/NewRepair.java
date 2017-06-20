package tsotzolas.ps.com.repairlog.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.UUID;

import io.realm.Realm;
import tsotzolas.ps.com.repairlog.Model.Repair;
import tsotzolas.ps.com.repairlog.R;

/**
 * Created by tsotzolas on 27/4/2017.
 */

public class NewRepair extends AppCompatActivity  implements
        View.OnClickListener{
    private static final String TAG = NewRepair.class.getSimpleName();
    private Realm realm;
    private Repair repair;
    private EditText dateEditText;
    private EditText costEditText;
    private EditText kmEditText;
    private EditText descEditText;
    private int mYear, mMonth, mDay, mHour, mMinute;

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
    }

    private void gotoInsert(View view){
//        Intent ki = new Intent(this, ChooseToInsertActivity.class);
//        startActivity(ki);
        System.out.println("Test");
    }

    //Για να κάνουμε save το Repair
    public void save(View view) {
        repair = new Repair();
        //Κάνουμε έλεγχο ότι έχει συμπληρώσει όλα τα πεδία
        if ("".equals(dateEditText.getText().toString())){
            Toast.makeText(this, R.string.add_date, Toast.LENGTH_SHORT).show();
        }else if ("".equals(kmEditText.getText().toString())){
            Toast.makeText(this, R.string.add_km, Toast.LENGTH_SHORT).show();
        }else  if ("".equals(costEditText.getText().toString())){
            Toast.makeText(this, R.string.add_cost, Toast.LENGTH_SHORT).show();
        }else if ("".equals(descEditText.getText().toString())){
            Toast.makeText(this, R.string.add_desc, Toast.LENGTH_SHORT).show();
        }else {
            //Γεμίζουμε το αντικείμενο
            repair.setRepairCost(costEditText.getText().toString());
            repair.setRepairDate(dateEditText.getText().toString());
            repair.setRepairDescription(descEditText.getText().toString());
            repair.setVehicleKM(kmEditText.getText().toString());
            repair.setVehicleId(VehicleView.selectedVehicles.getId());
            repair.setRepairId(String.valueOf(UUID.randomUUID()));

            //Κάνουμε save το αντικείμενο
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.copyToRealm(repair);
            realm.commitTransaction();

            Toast.makeText(this, R.string.insert_repair_ok, Toast.LENGTH_SHORT).show();

            Intent ki = new Intent(this, VehicleRepairs.class);
            startActivity(ki);
        }
    }


    //Για το ημερολόγιο
    @Override
    public void onClick(View v) {
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
    }
}