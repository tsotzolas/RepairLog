package tsotzolas.ps.com.repairlog.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import tsotzolas.ps.com.repairlog.Model.Repair;
import tsotzolas.ps.com.repairlog.Model.Vehicle;
import tsotzolas.ps.com.repairlog.R;

/**
 * Created by tsotzolas on 27/4/2017.
 */

public class VehicleView extends AppCompatActivity {
    private static final String TAG = VehicleView.class.getSimpleName();
    private Realm realm;
    private RealmResults<Vehicle> vehicleRealmList;
    public static Repair selectedRepair = new Repair();
    public static Vehicle selectedVehicles = new Vehicle();
    private List<Vehicle> vehiclesList;
    private MyAdapter1 myAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_center);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //Αρχικοποίηση του Realm
        realm = Realm.getDefaultInstance();

        //Κάνουμε ερώτημα στη Βάση για να φέρουμε όλα τα Pets και τα βάζουμε σε μία RealmList
        vehicleRealmList = realm.where(Vehicle.class).findAll();

        setContentView(R.layout.vehicle_view);

        myAdapter1 = new MyAdapter1(this, R.layout.view_list_row, vehicleRealmList);

        ListView listView = (ListView) findViewById(R.id.vehicleList);
        listView.setAdapter(myAdapter1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedVehicles= (Vehicle) parent.getItemAtPosition(position);
                System.out.println("----->" + selectedVehicles.toString());
                goToVehicleCenter();
            }
        });
    }

    private class MyAdapter1 extends ArrayAdapter<Vehicle> {
        public MyAdapter1(Context context, int resource, List<Vehicle> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.view_list_row, parent, false);

                Holder holder = new Holder();
                holder.modelTextView = (TextView) rowView.findViewById(R.id.vehicleModelView);
                holder.makeTextView = (TextView) rowView.findViewById(R.id.vehicleMakeView);
                holder.yearlTextView = (TextView) rowView.findViewById(R.id.vehicleYearView);
                holder.ccTextView = (TextView) rowView.findViewById(R.id.vehicleCCView);
                holder.imageView = (ImageView) rowView.findViewById(R.id.imageVehicleView);
                rowView.setTag(holder);
            }

            Holder holder = (Holder) rowView.getTag();
            holder.modelTextView.setText(getItem(position).getModel());
            holder.makeTextView.setText(getItem(position).getMake());
            holder.yearlTextView.setText(getItem(position).getYear());
            holder.ccTextView.setText(getItem(position).getCc());
            holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(getItem(position).getPhoto(), 0, getItem(position).getPhoto().length));
            return rowView;
        }
        class Holder {
            TextView makeTextView;
            TextView modelTextView;
            TextView yearlTextView;
            TextView ccTextView;
            ImageView imageView;
        }
    }


     //Is the method for going to goToVehicleCenter page
    private void goToVehicleCenter() {
        Intent ki = new Intent(this, VehicleRepairs.class);
        startActivity(ki);
    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent ki = new Intent(this, MainActivity.class);
        startActivity(ki);
    }
}