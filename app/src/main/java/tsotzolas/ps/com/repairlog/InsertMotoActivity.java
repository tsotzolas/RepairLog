package tsotzolas.ps.com.repairlog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tsotzolas.ps.com.repairlog.Retrofit.CarService;
import tsotzolas.ps.com.repairlog.Retrofit.Makes.Make;
import tsotzolas.ps.com.repairlog.Retrofit.Makes.Make_;
import tsotzolas.ps.com.repairlog.Retrofit.Model.Model;

//import tsotzolas.ps.com.repairlog.Retrofit.Car;
//import tsotzolas.ps.com.repairlog.Retrofit.Makes.Example;
//import tsotzolas.ps.com.repairlog.Retrofit.Makes.Make;

/**
 * Created by tsotzolas on 3/4/2017.
 */

public class InsertMotoActivity extends AppCompatActivity {
    private static final String TAG = InsertMotoActivity.class.getSimpleName();

    private EditText motoMakeEditText;
    private EditText motoModelEditText;
    private Spinner yearSpinner;
    private Button photoButton;
    private ImageView mImageView;
    private String year;
    private String motoMake;
    private String model;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_moto);

        motoMakeEditText = (EditText) findViewById(R.id.motoBrand);
        motoModelEditText = (EditText) findViewById(R.id.motoModel);
        yearSpinner = (Spinner) findViewById(R.id.spinnerYear);
        //Για να αλλάξεις το τριγωνάκι στον spinner
        yearSpinner.getBackground().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

        photoButton = (Button) findViewById(R.id.photoButton);
        mImageView = (ImageView) findViewById(R.id.imageView);

        addItemsOnSpinnerYear();


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

        public abstract void onClick(InsertMotoActivity dialog, int which);
    }


}






