package tsotzolas.ps.com.repairlog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import tsotzolas.ps.com.repairlog.Model.Repair;
import tsotzolas.ps.com.repairlog.Model.Vehicle;

import static tsotzolas.ps.com.repairlog.VehicleView.selectedVehicles;

/**
 * Created by tsotzolas on 27/4/2017.
 */

public class VehicleCenter extends AppCompatActivity {
    private static final String TAG = VehicleCenter.class.getSimpleName();

    private Realm realm;
    private RealmResults<Repair> repairRealmList;
    private List<Repair> repairList;
    private Repair repair;
    private MyAdapter1 myAdapter1;


//    private MyAdapter1 myAdapter1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_center);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
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
        repairRealmList = realm.where(Repair.class)
                .equalTo("vehicleId", selectedVehicles.getId())
                .findAll();
        repairList = realm.copyFromRealm(repairRealmList);

        System.out.println("-------------------->"+ repairRealmList.size());
        System.out.println("---->"+ repairRealmList.get(1).getRepairCost());
        System.out.println("---->"+ repairRealmList.get(1).getRepairDescription());



        myAdapter1 = new MyAdapter1(this, R.layout.view_list_row, repairList);

        ListView listView = (ListView) findViewById(R.id.vehicleList1);
        listView.setAdapter(myAdapter1);


    }



    private class MyAdapter1 extends ArrayAdapter<Repair> {
        public MyAdapter1(Context context, int resource, List<Repair> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.view_repairs_list_row, parent, false);

                Holder holder = new Holder();
                holder.dateTextView = (TextView) rowView.findViewById(R.id.date_repair_log1);
                holder.kmTextView = (TextView) rowView.findViewById(R.id.km_repair_log1);
                holder.costTextView = (TextView) rowView.findViewById(R.id.cost_repair_log1);
                holder.descTextView = (TextView) rowView.findViewById(R.id.desc_repair_log1);
                rowView.setTag(holder);
            }

            Holder holder = (Holder) rowView.getTag();
            holder.kmTextView.setText(getItem(position).getVehicleKM());
            holder.dateTextView.setText("test");
            holder.costTextView.setText(getItem(position).getRepairCost());
            holder.descTextView.setText(getItem(position).getRepairDescription());

            return rowView;
        }

        class Holder {
            TextView dateTextView;
            TextView kmTextView;
            TextView costTextView;
            TextView descTextView;
        }
    }



    private void gotoInsert(View view){
//        Intent ki = new Intent(this, ChooseToInsertActivity.class);
//        startActivity(ki);
        System.out.println("Test");
    }



}
