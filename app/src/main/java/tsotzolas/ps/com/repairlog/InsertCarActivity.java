package tsotzolas.ps.com.repairlog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by tsotzolas on 3/4/2017.
 */

public class InsertCarActivity extends AppCompatActivity {

    private EditText carBrand;
    private EditText carmodel;
    private Button  photoButton;
    private ImageView mImageView;




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
