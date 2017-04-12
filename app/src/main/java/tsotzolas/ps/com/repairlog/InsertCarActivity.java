package tsotzolas.ps.com.repairlog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tsotzolas.ps.com.repairlog.Retrofit.Car;
//import tsotzolas.ps.com.repairlog.Retrofit.Makes.Example;
//import tsotzolas.ps.com.repairlog.Retrofit.Makes.Make;
import tsotzolas.ps.com.repairlog.Retrofit.CarService;
import tsotzolas.ps.com.repairlog.Retrofit.Makes.Make;
import tsotzolas.ps.com.repairlog.Retrofit.Makes.Make_;
import tsotzolas.ps.com.repairlog.Retrofit.Model.Model;
import tsotzolas.ps.com.repairlog.Retrofit.Model.Model_;

/**
 * Created by tsotzolas on 3/4/2017.
 */

public class InsertCarActivity extends AppCompatActivity {
    private static final String TAG = InsertCarActivity.class.getSimpleName();

    private Spinner carMakeSpinner;
    private Spinner carmodelSpinner;
    private Spinner yearSpinner;
    private Button photoButton;
    private ImageView mImageView;
    private String year;
    private String carMake;
    private String model;


    public static final String BASE_URL = "https://www.carqueryapi.com/" + "?callback=?";
    //Για την εταιρία
    private Make makeList;
    private List<Make_> makeList1 = new ArrayList<>();
    private List<String> makeListString1 = new ArrayList<>();
    private MyAdapterMakes myadapter;


    private Model modelList;
    private List<Model_> modelList1 = new ArrayList<>();
    private List<String> modelListString1 = new ArrayList<>();


    private Car car;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_car);

        carMakeSpinner = (Spinner) findViewById(R.id.carBrand);
        carmodelSpinner = (Spinner) findViewById(R.id.carModel);
        yearSpinner = (Spinner) findViewById(R.id.spinnerYear);


        photoButton = (Button) findViewById(R.id.photoButton);
        mImageView = (ImageView) findViewById(R.id.imageView);

        addItemsOnSpinnerYear();

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Car car = realm.createObject(Car.class);
        car.setMake("Test");
        car.setModel("test");
        car.setYear("2000");

        realm.commitTransaction();

    }

    /*********************************************
     * Για το year*******************Start********
     *********************************************/

    // add items into spinner dynamically
    public void addItemsOnSpinnerYear() {

        List<String> yearList = new ArrayList<String>();
        yearList.add(".......");
        for (int i = 1974; i <= Calendar.getInstance().get(Calendar.YEAR); i++) {
            yearList.add(String.valueOf(i));
        }

//        carMakeSpinner = (Spinner) findViewById(R.id.carBrand);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, yearList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(dataAdapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = yearSpinner.getSelectedItem().toString();
                Log.d(TAG, year);
                //Για να φορτώσει τις μάρκες
                if (!year.equals(".......")) {
                    loadMakes();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                Toast.makeText(Con, R.string.insert_vehicle_year, Toast.LENGTH_SHORT).show();
            }
        });

    }
    /*********************************************
     * Για το year*******************Finish*******
     *********************************************/


    /**************************************************
     *Για το Make*******************Start*************
     *************************************************/


    public void loadMakes() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))

                .build();


        //Καλεί για να πάρει απο το API τα δεδομένα σύμφωνα με το έτος που έχουμε επιλέξει
        CarService carService = retrofit.create(CarService.class);
        carService.getMake("getMakes", year, "0").enqueue(new Callback<Make>() {
            @Override
            public void onResponse(Call<Make> call, Response<Make> response) {
                if (response.isSuccessful()) {
                    makeList = response.body();
                    Log.i(TAG, "New Pet: " + makeList.getMakes().size());
                    Log.i(TAG, "-------->" + call.request().url());
//                    Log.i(TAG, "-------->" + makeList.getMakes().get(0).getMakeDisplay());
//                    Log.i(TAG, "-------->" + makeList.getMakes().get(0).getMakeIsCommon());

                    makeListString1 = new ArrayList<String>();
                    //Βάζουμε αυτό στην αρχή για να ξέρουμε αν έχει επιλέξει κάτι ο χρήστης ή όχι
                    if(!makeListString1.contains(".......")) {
                        makeListString1.add(".......");
                    }
                    //Γεμίζουμε τη list με τα makes
                    for (int i = 0; i < makeList.getMakes().size(); i++) {
//                        makeList1.add(makeList.getMakes().get(i));

                        makeListString1.add(makeList.getMakes().get(i).getMakeDisplay());
                    }

                    //Καλούμε για να γεμίσουμε τις makes
                    fillListMakes();
                } else {
                    Log.e(TAG, "Failed. Status: " + response.code());
                    Log.i(TAG, "-------->" + call.request().url());
                }
            }


            @Override
            public void onFailure(Call<Make> call, Throwable t) {
                Log.e(TAG, "Failed. Error: " + t.getMessage());
            }
        });
    }

    private class MyAdapterMakes extends ArrayAdapter<Make_> {
        public MyAdapterMakes(Context context, int resource, List<Make_> objects) {
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


        // Έιναι ο adapter για τα μοντέλα


//        private class MyAdapter1 extends ArrayAdapter<Make_> {
//            public MyAdapter1(Context context, int resource, List<Make_> objects) {
//                super(context, resource, objects);
//            }
//
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View rowView = convertView;
//                if (rowView == null) {
//                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    rowView = inflater.inflate(R.layout.select_car_make, parent, false);
//
//                    InsertCarActivity.MyAdapter1.Holder holder = new SelectCarMakeActivity.MyAdapter1.Holder();
////                holder.idTextView = (TextView) rowView.findViewById(R.id.txt_list_row_id);
//                    holder.nameTextView = (TextView) rowView.findViewById(R.id.txt_list_row_name);
//                    rowView.setTag(holder);
//                }
//
//                SelectCarMakeActivity.MyAdapter1.Holder holder = (SelectCarMakeActivity.MyAdapter1.Holder) rowView.getTag();
//                holder.idTextView.setText(getItem(position).getMakeId());
//                holder.nameTextView.setText(getItem(position).getMakeDisplay());
//                return rowView;
//            }
//
//            class Holder {
//                TextView idTextView;
//                TextView nameTextView;
//            }
//        }
    }


    private void fillListMakes() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, makeListString1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Γεμίζουμε τον spinner
        carMakeSpinner.setAdapter(dataAdapter);

        carMakeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Βάζουμε σε ια μεταβλητή αυτό που έχει επιλέξηει ο χρήστης
                carMake = carMakeSpinner.getSelectedItem().toString();
                Log.d(TAG, carMake);
                //Ελέχουμε αν έχει επιλέξει κάτι
                if (!carMake.equals(".......")) {
                    //Και αν έχει επιλέξει γεμίζουμε το spinner
                    loadModels();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                Toast.makeText(Con, R.string.insert_vehicle_year, Toast.LENGTH_SHORT).show();
            }
        });


    }


/**************************************************
 *Για το Make*******************Finish*************
 *************************************************/

    /**************************************************
     *Για το Models***************Srart****************
     *************************************************/


    // add items into spinner dynamically
    public void loadModels() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        //Καλούμε το API για να μας φέρει τα μοντέλα σύφμωνα με το Make που έχουμε επιλέξει.
        CarService carService = retrofit.create(CarService.class);
        carService.getModel("getModels", carMake, year).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()) {
                    modelList = response.body();
                    Log.i(TAG, "New Pet: " + modelList.getModels().size());
                    Log.i(TAG, "-------->" + call.request().url());
                    Log.i(TAG, "-------->" + modelList.getModels().get(0).getModelName());

                    modelListString1 = new ArrayList<String>();
                    if(!modelListString1.contains(".......")) {
                        modelListString1.add(".......");
                    }
                    for (int i = 0; i < modelList.getModels().size(); i++) {
//                        modelList1.add(modelList.getModels().get(i));
                        modelListString1.add(modelList.getModels().get(i).getModelName());
                    }

                    fillListModels();
                } else {
                    Log.e(TAG, "Failed. Status: " + response.code());
                    Log.i(TAG, "-------->" + call.request().url());
                }
            }


            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "Failed. Error: " + t.getMessage());
            }
        });



    }

    private void fillListModels() {
        //Εδω είναι που γεμίζεις τον spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, modelListString1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carmodelSpinner.setAdapter(dataAdapter);

        carmodelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                model = carmodelSpinner.getSelectedItem().toString();
                Log.d(TAG+"____++++---->", model);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                Toast.makeText(Con, R.string.insert_vehicle_year, Toast.LENGTH_SHORT).show();
            }
        });



    }


    /**************************************************
     *Για το Models***************Finish***************
     *************************************************/


    /*****************************
     * Για την φωτογραφία
     */

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }

    public void selectForPhoto(View view) {


    }


    public abstract class OnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }

        public abstract void onClick(InsertCarActivity dialog, int which);
    }












}






