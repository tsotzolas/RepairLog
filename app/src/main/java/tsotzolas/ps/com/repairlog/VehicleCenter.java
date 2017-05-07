package tsotzolas.ps.com.repairlog;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
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
    private RealmResults<Repair> repairList;
    private Repair repair;


//    private MyAdapter1 myAdapter1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicle_center);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        //Αρχικοποίηση του Realm
        realm = Realm.getDefaultInstance();
        repair = new Repair();
        repair.setRepairCost("12");
        repair.setRepairDate(new Date());
        repair.setRepairDescription("Test");

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(repair);
        realm.commitTransaction();


        repairList = realm.where(Repair.class).findAll();
        System.out.println("-------------------->"+repairList.size());



    }






//
//    private class MyAdapter1 extends ArrayAdapter<Vehicle> {
//        public MyAdapter1(Context context, int resource, List<Vehicle> objects) {
//            super(context, resource, objects);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View rowView = convertView;
//            if (rowView == null) {
//                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                rowView = inflater.inflate(R.layout.view_list_row, parent, false);
//
//                Holder holder = new Holder();
//                holder.modelTextView = (TextView) rowView.findViewById(R.id.vehicleModelView);
//                holder.makeTextView = (TextView) rowView.findViewById(R.id.vehicleMakeView);
//                holder.yearlTextView = (TextView) rowView.findViewById(R.id.vehicleYearView);
//                holder.ccTextView = (TextView) rowView.findViewById(R.id.vehicleCCView);
//                holder.imageView = (ImageView) rowView.findViewById(R.id.imageVehicleView);
//                rowView.setTag(holder);
//            }
//
//            Holder holder = (Holder) rowView.getTag();
//            holder.modelTextView.setText(getItem(position).getModel());
//            holder.makeTextView.setText(getItem(position).getMake());
//            holder.yearlTextView.setText(getItem(position).getYear());
//            holder.ccTextView.setText(getItem(position).getCc());
//
////            Bitmap b = getItem(position).getPhoto();
//
////            BitmapFactory.decodeByteArray(getItem(position).getPhoto(), 0, getItem(position).getPhoto().length);
//
//            holder.imageView.setImageBitmap( BitmapFactory.decodeByteArray(getItem(position).getPhoto(), 0, getItem(position).getPhoto().length));
//            return rowView;
//        }
//
//        class Holder {
//            TextView makeTextView;
//            TextView modelTextView;
//            TextView yearlTextView;
//            TextView ccTextView;
//            ImageView imageView;
//        }
//    }
//


    private void gotoInsert(View view){
//        Intent ki = new Intent(this, ChooseToInsertActivity.class);
//        startActivity(ki);
        System.out.println("Test");
    }



}
