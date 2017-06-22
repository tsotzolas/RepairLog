package tsotzolas.ps.com.repairlog.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import tsotzolas.ps.com.repairlog.Model.Repair;
import tsotzolas.ps.com.repairlog.Model.Vehicle;
import tsotzolas.ps.com.repairlog.R;

/**
 * Created by tsotzolas on 27/4/2017.
 */

public class VehicleRepairs extends AppCompatActivity {
    private static final String TAG = VehicleRepairs.class.getSimpleName();

    private Realm realm;
    private RealmResults<Repair> repairRealmList;
    private List<Repair> repairList;
    private MyAdapter1 myAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_center);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        //Αρχικοποίηση του Realm
        realm = Realm.getDefaultInstance();

        //Εκτελούμε το ερώτημα
        repairRealmList = realm.where(Repair.class)
                .equalTo("vehicleId", VehicleView.selectedVehicles.getId())
                .findAll();
        repairList = realm.copyFromRealm(repairRealmList);

        myAdapter1 = new MyAdapter1(this, R.layout.view_list_row, repairList);

        ListView listView = (ListView) findViewById(R.id.vehicleList1);
        listView.setAdapter(myAdapter1);


        //Για το απλό click δέιχνουμε στο χρήστη την εργασία
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(VehicleRepairs.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(VehicleRepairs.this);
                }



                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                final View dialogView = inflater.inflate(R.layout.view_repairs_list_row_for_alert, null);

                //Αρχικοποιούμε τα πεδία
                VehicleView.selectedRepair = (Repair) adapter.getItemAtPosition(position);
                TextView date = (TextView) dialogView
                        .findViewById(R.id.date_repair_log1);
                TextView km = (TextView) dialogView
                        .findViewById(R.id.km_repair_log1);
                TextView cost = (TextView) dialogView
                        .findViewById(R.id.cost_repair_log1);
                TextView desc = (TextView) dialogView
                        .findViewById(R.id.desc_repair_log1);


                //Γεμίζουμε τις τιμές
                date.setText(VehicleView.selectedRepair.getRepairDate().toString());
                km.setText(VehicleView.selectedRepair.getVehicleKM());
                cost.setText(VehicleView.selectedRepair.getRepairCost());
                desc.setText(VehicleView.selectedRepair.getRepairDescription());

                builder.setView(dialogView);
                builder.setTitle(R.string.repair_details)
                      .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(R.drawable.ic_action_name)
                        .show();
            }
        });



        //Με το longClick διαγράψουμε μια εργασία
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int arg2, long arg3) {

                VehicleView.selectedRepair = (Repair) parent.getItemAtPosition(arg2);
                // Can't manage to remove an item here
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(VehicleRepairs.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(VehicleRepairs.this);
                }
                builder.setTitle(R.string.delete_entry)
                        .setMessage(R.string.Question_delete_entry)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                //For delete the selected Repair
                               realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            RealmResults<Repair> result1 = realm.where(Repair.class)
                                                    .equalTo("repairId", VehicleView.selectedRepair.getRepairId())
                                                    .findAll();
                                            result1.deleteAllFromRealm();


                                            repairRealmList = realm.where(Repair.class)
                                                    .equalTo("vehicleId", VehicleView.selectedVehicles.getId())
                                                    .findAll();
                                            repairList = realm.copyFromRealm(repairRealmList);
                                            myAdapter1 = new MyAdapter1(VehicleRepairs.this, R.layout.view_list_row, repairList);

                                            ListView listView = (ListView) findViewById(R.id.vehicleList1);
                                            listView.setAdapter(myAdapter1);
                                        }
                                    });
                                }

                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                myAdapter1.notifyDataSetChanged();
                return true;
            }
        });
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
            holder.dateTextView.setText(getItem(position).getRepairDate().toString());
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


    public void addNewRepair(View view) {
        Intent ki = new Intent(this, NewRepair.class);
        startActivity(ki);
    }

    //Για να διαγράψουμε μια εγγραφή
    public void deleteVehicle(View view) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(VehicleRepairs.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(VehicleRepairs.this);
                }
                builder.setTitle(R.string.delete_entry)
                        .setMessage(R.string.Question_delete_entry)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                //For delete the selected Repair
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        RealmResults<Vehicle> result1 = realm.where(Vehicle.class)
                                                .equalTo("id", VehicleView.selectedVehicles.getId())
                                                .findAll();
                                        result1.deleteAllFromRealm();

                                        //Μετά την διαγραφή να πάει στην MainActivity
                                        Intent ki = new Intent(VehicleRepairs.this, MainActivity.class);
                                        startActivity(ki);
                                    }
                                });
                            }

                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
    }

    //Αλλάζουμε την λειτουργία του BackPress για να μας πηγαίνει στην MainActivity
    @Override
    public void onBackPressed() {
        Intent ki = new Intent(this, VehicleView.class);
        startActivity(ki);
    }
}