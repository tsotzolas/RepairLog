package tsotzolas.ps.com.repairlog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tsotzolas.ps.com.repairlog.Retrofit.Car;
import tsotzolas.ps.com.repairlog.Retrofit.CarService;
import tsotzolas.ps.com.repairlog.Retrofit.Makes.Make;
import tsotzolas.ps.com.repairlog.Retrofit.Makes.Make_;

/**
 * Created by tsotzolas on 3/4/2017.
 */

public class SelectCarMakeActivity extends AppCompatActivity {
    private static final String TAG = SelectCarMakeActivity.class.getSimpleName();


    public static final String BASE_URL = "https://www.carqueryapi.com/"+"?callback=?";
    private Make makeList;
    private List<Make_> makeList1  = new ArrayList<>();
    private List<String> makeListString1 ;
    private MyAdapter myadapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_car_make);

        loadMakes();

    }

    public void loadMakes() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))

                .build();


        CarService carService = retrofit.create(CarService.class);
        carService.getMake("getMakes","2000","0").enqueue(new Callback<Make>() {
            @Override
            public void onResponse(Call<Make> call, Response<Make> response) {
                if (response.isSuccessful()) {
                    makeList= response.body();
                    Log.i(TAG, "New Pet: " + makeList.getMakes().size());
                    Log.i(TAG,"-------->"+call.request().url());
                    Log.i(TAG,"-------->"+makeList.getMakes().get(0).getMakeDisplay());
                    Log.i(TAG,"-------->"+makeList.getMakes().get(0).getMakeIsCommon());

                    for(int i=0;i<makeList.getMakes().size();i++) {
                        makeList1.add(makeList.getMakes().get(i));
                    }


                    fillList();
                } else {
                    Log.e(TAG, "Failed. Status: " + response.code());
                    Log.i(TAG,"-------->"+call.request().url());
                }
            }


            @Override
            public void onFailure(Call<Make> call, Throwable t) {
                Log.e(TAG, "Failed. Error: " + t.getMessage());
            }
        });


    }




    public abstract class OnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }

        public abstract void onClick(SelectCarMakeActivity dialog, int which);
    }






    private void fillList(){
        myadapter= new MyAdapter(this,R.layout.sample_list_row,makeList1);

        ListView listView = (ListView) findViewById(R.id.listview_list);
        listView.setAdapter(myadapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SelectCarMakeActivity.this, "Clicked " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

                InsertCarActivity.setSelectedMake((Make_) parent.getItemAtPosition(position));

                Intent kii = new Intent(getApplicationContext(), InsertCarActivity.class);
                startActivity(kii);


            }
        });
    }



    private class MyAdapter extends ArrayAdapter<Make_> {
        public MyAdapter(Context context, int resource, List<Make_> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.sample_list_row, parent, false);
//            TextView idTextView = (TextView) rowView.findViewById(R.id.txt_list_row_id);
            TextView nameTextView = (TextView) rowView.findViewById(R.id.txt_list_row_name);

//            idTextView.setText(getItem(position).getMakeId());
            nameTextView.setText(getItem(position).getMakeDisplay());
            return rowView;
        }
    }




    private class MyAdapter1 extends ArrayAdapter<Make_> {
        public MyAdapter1(Context context, int resource, List<Make_> objects) {
            super(context, resource, objects);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.select_car_make, parent, false);

                Holder holder = new Holder();
//                holder.idTextView = (TextView) rowView.findViewById(R.id.txt_list_row_id);
                holder.nameTextView = (TextView) rowView.findViewById(R.id.txt_list_row_name);
                rowView.setTag(holder);
            }

            Holder holder = (Holder) rowView.getTag();
            holder.idTextView.setText(getItem(position).getMakeId());
            holder.nameTextView.setText(getItem(position).getMakeDisplay());
            return rowView;
        }

        class Holder {
            TextView idTextView;
            TextView nameTextView;
        }
    }







}
