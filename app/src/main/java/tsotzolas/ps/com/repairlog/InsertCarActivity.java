package tsotzolas.ps.com.repairlog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import tsotzolas.ps.com.repairlog.Retrofit.CarService;
import tsotzolas.ps.com.repairlog.Retrofit.Makes.Make;

/**
 * Created by tsotzolas on 3/4/2017.
 */

public class InsertCarActivity extends AppCompatActivity {
    private static final String TAG = InsertCarActivity.class.getSimpleName();

    private EditText carBrand;
    private EditText carmodel;
    private Button  photoButton;
    private ImageView mImageView;

    public static final String BASE_URL = "https://www.carqueryapi.com/"+"?callback=?";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_car);

        carBrand = (EditText) findViewById(R.id.carBrand);
        carmodel = (EditText) findViewById(R.id.carmodel);
        photoButton = (Button) findViewById(R.id.photoButton);
        mImageView =(ImageView) findViewById(R.id.imageView);


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

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))

                .build();


        CarService carService = retrofit.create(CarService.class);
        carService.getMake("getMakes","2000","1").enqueue(new Callback<List<Make>>() {
            @Override
            public void onResponse(Call<List<Make>> call, Response<List<Make>> response) {
                if (response.isSuccessful()) {
                    List<Make> makeList = response.body();
                    Log.i(TAG, "New Pet: " + makeList.size());
                    Log.i(TAG,"-------->"+call.request().url());
                } else {
                    Log.e(TAG, "Failed. Status: " + response.code());
                    Log.i(TAG,"-------->"+call.request().url());
                }
            }


            @Override
            public void onFailure(Call<List<Make>> call, Throwable t) {
                Log.e(TAG, "Failed. Error: " + t.getMessage());
            }
        });
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


}
