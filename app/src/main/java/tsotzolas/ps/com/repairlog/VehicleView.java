package tsotzolas.ps.com.repairlog;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import tsotzolas.ps.com.repairlog.Model.Vehicle;

/**
 * Created by tsotzolas on 27/4/2017.
 */

public class VehicleView extends AppCompatActivity {
    private static final String TAG = VehicleView.class.getSimpleName();

    private Realm realm;
    private RealmResults<Vehicle> vehicleList;

    private MyAdapter1 myAdapter1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Αρχικοποίηση του Realm
        realm = Realm.getDefaultInstance();

        //Κάνουμε ερώτημα στη Βάση για να φέρουμε όλα τα Pets και τα βάζουμε σε μία RealmList
        vehicleList = realm.where(Vehicle.class).findAll();

        //Για να αποθηκέυσω το αντικείμενο pet
//        Vehicle pet = new (1, "Bob");
//        realm.beginTransaction();
//        realm.copyToRealmOrUpdate(pet);
//        realm.commitTransaction();


        //Try with resources
//        try(Realm r = Realm.getDefaultInstance()){
//            r.where();
//        }


//        i = 4;
        setContentView(R.layout.vehicle_view);

//        pets = new ArrayList<>();
//        pets.add(new Pet(1, "Andreas"));
//        pets.add(new Pet(2, "Foo"));
//        pets.add(new Pet(3, "Bar"));

        myAdapter1 = new MyAdapter1(this, R.layout.view_list_row, vehicleList);

        ListView listView = (ListView) findViewById(R.id.vehicleList);
        listView.setAdapter(myAdapter1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(VehicleView.this, "Clicked " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
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

//            Bitmap b = getItem(position).getPhoto();

//            BitmapFactory.decodeByteArray(getItem(position).getPhoto(), 0, getItem(position).getPhoto().length);

            holder.imageView.setImageBitmap( BitmapFactory.decodeByteArray(getItem(position).getPhoto(), 0, getItem(position).getPhoto().length));
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



}
