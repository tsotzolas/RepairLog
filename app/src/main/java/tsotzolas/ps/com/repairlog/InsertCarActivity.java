package tsotzolas.ps.com.repairlog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tsotzolas.ps.com.repairlog.Retrofit.Car;
import tsotzolas.ps.com.repairlog.Retrofit.CarService;
//import tsotzolas.ps.com.repairlog.Retrofit.Makes.Example;
//import tsotzolas.ps.com.repairlog.Retrofit.Makes.Make;
import tsotzolas.ps.com.repairlog.Retrofit.Makes.Make_;

/**
 * Created by tsotzolas on 3/4/2017.
 */

public class InsertCarActivity extends AppCompatActivity {
    private static final String TAG = InsertCarActivity.class.getSimpleName();

    private EditText carBrand;
    private EditText carmodel;
    private Button  photoButton;
    private ImageView mImageView;
    private static Make_ selectedMake ;
//    private Make makeList;
    private Car car;

    public static final String BASE_URL = "https://www.carqueryapi.com/"+"?callback=?";

    public static Make_ getSelectedMake() {
        return selectedMake;
    }

    public static void setSelectedMake(Make_ selectedMake) {
        InsertCarActivity.selectedMake = selectedMake;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_car);

        carBrand = (EditText) findViewById(R.id.carBrand);
        carmodel = (EditText) findViewById(R.id.carmodel);
        photoButton = (Button) findViewById(R.id.photoButton);
        mImageView =(ImageView) findViewById(R.id.imageView);


    }


    @Override
    protected void onResume() {
        super.onResume();
        if (selectedMake!=null)
        carBrand.setText(selectedMake.getMakeDisplay());
    }

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






    public void loadMakes(View view) {

        Intent kii = new Intent(this, SelectCarMakeActivity.class);
        startActivity(kii);
    }









    public void selectForPhoto(View view){


    }


    public void selectCar(View view) {
        Toast.makeText(this, "Select Car", Toast.LENGTH_SHORT).show();
        Intent ki = new Intent(this, InsertCarActivity.class);
        startActivity(ki);

    }


    public void selectMoto(View view) {

        Toast.makeText(this, "Select Moto", Toast.LENGTH_SHORT).show();

    }


    public abstract class OnClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }

        public abstract void onClick(InsertCarActivity dialog, int which);
    }
}
