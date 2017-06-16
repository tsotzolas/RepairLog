package tsotzolas.ps.com.repairlog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tsotzolas.ps.com.repairlog.Model.Vehicle;
import tsotzolas.ps.com.repairlog.Retrofit.CarService;
import tsotzolas.ps.com.repairlog.Retrofit.Makes.Make;
import tsotzolas.ps.com.repairlog.Retrofit.Makes.Make_;
import tsotzolas.ps.com.repairlog.Retrofit.Model.Model;

//import tsotzolas.ps.com.tsotzolas.ps.repairlog.Retrofit.Car;
//import tsotzolas.ps.com.tsotzolas.ps.repairlog.Retrofit.Makes.Example;
//import tsotzolas.ps.com.tsotzolas.ps.repairlog.Retrofit.Makes.Make;

/**
 * Created by tsotzolas on 3/4/2017.
 */

public class InsertMotoActivity extends AppCompatActivity {
    private static final String TAG = InsertMotoActivity.class.getSimpleName();

    private EditText motoMakeEditText;
    private EditText motoModelEditText;
    private EditText motoCCEditText;
    private Spinner yearSpinner;
    private Button photoButton;
    private ImageView mImageView;
    private String year;
    private String motoMake;
    private String motomodel;
    private String motocc;
    private Vehicle motoVehicle;
    private Bitmap bitmap;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_moto);

        //Αρχικοποίηση του Realm
        realm = Realm.getDefaultInstance();
        //Αρχικοποίηση των
        motoMakeEditText = (EditText) findViewById(R.id.motoBrand);
        motoModelEditText = (EditText) findViewById(R.id.motoModel);
        yearSpinner = (Spinner) findViewById(R.id.spinnerYear);
        motoCCEditText = (EditText) findViewById(R.id.motocc);
        //Για να αλλάξεις το τριγωνάκι στον spinner
        yearSpinner.getBackground().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);

        photoButton = (Button) findViewById(R.id.photoButton);
        mImageView = (ImageView) findViewById(R.id.imageView);

        //Γεμιζουμε τον spinner με τα year
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, yearList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        yearSpinner.setAdapter(dataAdapter);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = yearSpinner.getSelectedItem().toString();
                Log.d(TAG, year);
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
        public abstract void onClick(InsertMotoActivity dialog, int which);
    }





    /*****************************
     * Για την φωτογραφία
    /*****************************/

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

    //Για να κάνει την εισαγωγή της μηχανής
    public void saveMoto(View view) {
        motoMake = motoMakeEditText.getText().toString();
        motomodel = motoModelEditText.getText().toString();
        motocc = motoCCEditText.getText().toString();

        //Κάνουμε έλεγχο ότι έχει γεμίσει τα πεδία
        if (".......".equals(year)) {
            Toast.makeText(this, R.string.add_year, Toast.LENGTH_SHORT).show();
        }else if ("".equals(motoMakeEditText.getText().toString())) {
            Toast.makeText(this, R.string.add_make, Toast.LENGTH_SHORT).show();
        } else if ("".equals(motoModelEditText.getText().toString())) {
            Toast.makeText(this, R.string.add_model, Toast.LENGTH_SHORT).show();
        } else if ("".equals(motoCCEditText.getText().toString())) {
            Toast.makeText(this, R.string.add_cc, Toast.LENGTH_SHORT).show();
        } else {
            //Και άμα τα έχει γεμήσει
            //Γεμίζω το Realm Object
            motoVehicle = new Vehicle();
            motoVehicle.setCar(false);
            motoVehicle.setId(UUID.randomUUID().toString());
            motoVehicle.setMake(motoMake);
            motoVehicle.setModel(motomodel);
            motoVehicle.setYear(year);
            motoVehicle.setCc(motocc);


            if (bitmap != null) {
                //Για να βάλω την φωτογραφία
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                motoVehicle.setPhoto(stream.toByteArray());
            } else {
                //Αμα δεν έχει βάλει καμία φωτογραφία η χρήστης βάζουμε εμείς μία
                Drawable d = ResourcesCompat.getDrawableForDensity(getResources(), R.mipmap.ic_moto, DisplayMetrics.DENSITY_XXHIGH, getTheme());
                Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bitmapdata = stream.toByteArray();

                motoVehicle.setPhoto(bitmapdata);
            }

            //Για να αποθυκεύσω το Realm Object
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(motoVehicle);
            realm.commitTransaction();

            Toast.makeText(this, R.string.insert_vehicle_moto_saved, Toast.LENGTH_LONG).show();
            Intent ki = new Intent(this, MainActivity.class);
            startActivity(ki);
        }
    }
}