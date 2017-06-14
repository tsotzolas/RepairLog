package tsotzolas.ps.com.repairlog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//import tsotzolas.ps.com.repairlog.Retrofit.Car;
//import tsotzolas.ps.com.repairlog.Retrofit.Makes.Example;
//import tsotzolas.ps.com.repairlog.Retrofit.Makes.Make;
import tsotzolas.ps.com.repairlog.Model.Vehicle;
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


    private String year;
    private String carMake;
    private String carModel;
    private String cc;
    private Bitmap bitmap;
    private Vehicle carVehicle;
    private boolean orientation = false;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    public static final String BASE_URL = "https://www.carqueryapi.com/" + "?callback=?";


    private Realm realm;

    private Spinner carMakeSpinner;
    private Spinner carmodelSpinner;
    private Spinner yearSpinner;
    private Spinner ccSpinner;
    private Button photoButton;
    private ImageView mImageView;


    //Για την εταιρία
    private Make makeList;
    private List<Make_> makeList1 = new ArrayList<>();
    private List<String> makeListString1 = new ArrayList<>();

    private MyAdapterMakes myadapter;


    private Model modelList;
    private List<Model_> modelList1 = new ArrayList<>();
    private List<String> modelListString1 = new ArrayList<>();


//    private Car car;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_car);


        //Αρχικοποίηση του Realm
        realm = Realm.getDefaultInstance();


        carMakeSpinner = (Spinner) findViewById(R.id.carBrand);
        carmodelSpinner = (Spinner) findViewById(R.id.carModel);
        yearSpinner = (Spinner) findViewById(R.id.spinnerYear);
        ccSpinner = (Spinner) findViewById(R.id.carCC);
        //Για να αλλάξεις το τριγωνάκι στον spinner
        yearSpinner.getBackground().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        carMakeSpinner.getBackground().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        carmodelSpinner.getBackground().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        ccSpinner.getBackground().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

        photoButton = (Button) findViewById(R.id.photoButton);
        mImageView = (ImageView) findViewById(R.id.imageView);

//        if(!orientation) {
//            addItemsOnSpinnerYear();
//        }
        addItemsOnSpinnerYear();


    }


    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        orientation = true;
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
                R.layout.spinner_item, yearList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
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


    /*********************************************
     * Για το pregress bar*********Start*******
     *********************************************/


    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /*********************************************
     * Για το pregress bar*********Finish*******
     *********************************************/


    /**************************************************
     *Για το Make*******************Start*************
     *************************************************/


    public void loadMakes() {
        showProgressDialog();
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
                    if (!makeListString1.contains(".......")) {
                        makeListString1.add(".......");
                    }
                    //Γεμίζουμε τη list με τα makes
                    for (int i = 0; i < makeList.getMakes().size(); i++) {
//                        makeList1.add(makeList.getMakes().get(i));

                        makeListString1.add(makeList.getMakes().get(i).getMakeDisplay());
                    }

                    //Καλούμε για να γεμίσουμε τις makes
                    fillListMakes();
                    hideProgressDialog();
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
                R.layout.spinner_item, makeListString1);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

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
        showProgressDialog();
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
//                    Log.i(TAG, "-------->" + call.request().url());
//                    Log.i(TAG, "-------->" + modelList.getModels().get(0).getModelName());

                    modelListString1 = new ArrayList<String>();
                    if (!modelListString1.contains(".......")) {
                        modelListString1.add(".......");
                    }
                    for (int i = 0; i < modelList.getModels().size(); i++) {
//                        modelList1.add(modelList.getModels().get(i));
                        modelListString1.add(modelList.getModels().get(i).getModelName());
                    }

                    fillListModels();
                    hideProgressDialog();
                } else {
                    Log.e(TAG, "Failed. Status: " + response.code());
                    Log.i(TAG, "-------->" + call.request().url());
                    hideProgressDialog();
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
                R.layout.spinner_item, modelListString1);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        carmodelSpinner.setAdapter(dataAdapter);

        carmodelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                carModel = carmodelSpinner.getSelectedItem().toString();
                Log.d(TAG + "____++++---->", carModel);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                Toast.makeText(Con, R.string.insert_vehicle_year, Toast.LENGTH_SHORT).show();
            }
        });

        addItemsOnSpinnerCC();
//

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }


    /**************************************************
     *Για το Models***************Finish***************
     *************************************************/


    // add items into spinner dynamically
    public void addItemsOnSpinnerCC() {

        List<String> ccList = new ArrayList<String>();
        ccList.add(".......");
        int ccc = 900;
        ccList.add("900");
        ccList.add("1000");
        ccList.add("1100");
        ccList.add("1200");
        ccList.add("1300");
        ccList.add("1400");
        ccList.add("1500");
        ccList.add("1600");
        ccList.add("1700");
        ccList.add("1800");
        ccList.add("1900");
        ccList.add("2000");
        ccList.add("2200");
        ccList.add("2400");

//        while (ccc <2500){
//            int k = ccc;
//            k = k+100;
//            ccList.add(String.valueOf(k));
//        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, ccList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        ccSpinner.setAdapter(dataAdapter);

        ccSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cc = ccSpinner.getSelectedItem().toString();
                Log.d(TAG, year);
//                if (!year.equals(".......")) {
//                    loadMakes();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                Toast.makeText(Con, R.string.insert_vehicle_year, Toast.LENGTH_SHORT).show();
            }
        });

    }


    public abstract class OnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }

        public abstract void onClick(InsertCarActivity dialog, int which);
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putAll(outState);
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }


    /*****************************
     * Για την φωτογραφία
     */

    int RESULT_LOAD_IMAGE = 346;

    public void LoadImage(View view) {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

//            Bitmap resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, (int) (roughBitmap.getWidth() * values[0]), (int) (roughBitmap.getHeight() * values[4]), true);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = sampleSize;

            final int maxSize = 960;
            int outWidth;
            int outHeight;
            int inWidth = BitmapFactory.decodeFile(picturePath).getWidth();
            int inHeight = BitmapFactory.decodeFile(picturePath).getHeight();
            if (inWidth > inHeight) {
                outWidth = maxSize;
                outHeight = (inHeight * maxSize) / inWidth;
            } else {
                outHeight = maxSize;
                outWidth = (inWidth * maxSize) / inHeight;
            }

            bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(picturePath), outWidth, outHeight, false);
            mImageView.setImageBitmap(bitmap);

        }


    }


    public void saveCar(View view) {
        //Γεμίζω το Realm Object
        carVehicle = new Vehicle();
        carVehicle.setCar(true);
        carVehicle.setId(UUID.randomUUID().toString());
        carVehicle.setMake(carMake);
        carVehicle.setModel(carModel);
        carVehicle.setYear(year);
        carVehicle.setCc(cc);


        if (bitmap != null) {
            //Για να βάλω την φωτογραφία
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            carVehicle.setPhoto(stream.toByteArray());
        } else {
            //Αμα δεν έχει βάλει καμία φωτογραφία η χρήστης βάζουμε εμείς μία
            Drawable d = ResourcesCompat.getDrawableForDensity(getResources(), R.mipmap.ic_car, DisplayMetrics.DENSITY_XXHIGH, getTheme());
            ; // the drawable (Captain Obvious, to the rescue!!!)
            Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();

            carVehicle.setPhoto(bitmapdata);
        }

        //Για να αποθυκεύσω το Realm Object
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(carVehicle);
        realm.commitTransaction();



        if (carVehicle != null) {

            mFirebaseInstance = FirebaseDatabase.getInstance();
            //Add make
            mFirebaseInstance.getReference("user")
                    .child("tsotzolas")
                    .child("vehicle")
                    .child(carVehicle.getId())
                    .child("make").setValue(carVehicle.getMake());

            //Add model
            mFirebaseInstance.getReference("user")
                    .child("tsotzolas")
                    .child("vehicle")
                    .child(carVehicle.getId())
                    .child("model").setValue(carVehicle.getModel());
            //Add year
            mFirebaseInstance.getReference("user")
                    .child("tsotzolas")
                    .child("vehicle")
                    .child(carVehicle.getId())
                    .child("year").setValue(carVehicle.getYear());
            //Add model
            mFirebaseInstance.getReference("user")
                    .child("tsotzolas")
                    .child("vehicle")
                    .child(carVehicle.getId())
                    .child("photo").setValue(Base64.encodeToString(carVehicle.getPhoto(), Base64.DEFAULT));

        }


        Toast.makeText(this, R.string.insert_vehicle_car_saved, Toast.LENGTH_LONG).show();
        Intent ki = new Intent(this, MainActivity.class);
        startActivity(ki);

    }

    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent ki = new Intent(this, MainActivity.class);
        startActivity(ki);
    }


}






