package tsotzolas.ps.com.repairlog.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Locale;

import io.realm.Realm;
import tsotzolas.ps.com.repairlog.GooglePackages.GoogleSignIn.SignInActivity;
import tsotzolas.ps.com.repairlog.R;

import static tsotzolas.ps.com.repairlog.GooglePackages.GoogleSignIn.SignInActivity.mGoogleApiClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private Locale locale;
    public static GoogleSignInAccount acct;
    private final String FIREBASE_USERNAME = "tsotzolas@gmail.com";
    private final String FIREBASE_PASSWORD = "123123123";
    private volatile Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Eίναι για το κουμπάκι κάτω δεξιά για να κάνεις εισαγωγη κάποιο όχημα
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gotoInsert(view);
            }
        });


        // Κάνει τον έλεγχο άν ο χρήστης ειναι συνδεδεμένος στον Google Account
        if (mGoogleApiClient == null) {
            Intent ki = new Intent(this, SignInActivity.class);
            startActivity(ki);

        } else {
            Toast.makeText(this, "Login User:" + acct.getDisplayName(), Toast.LENGTH_SHORT).show();
        }


        //Firebase Login
        mAuth = FirebaseAuth.getInstance();
        login(FIREBASE_USERNAME, FIREBASE_PASSWORD);


        //Έλεγχος την γλώσσας που έχει ο χρήστης και αντίστοιχη ρύθμηση στην γλώσσα της εφαρμογής
        if (SettingActivity.locale == null) {
            locale.setDefault(locale);
        } else {
            locale = new Locale(SettingActivity.locale.getDisplayLanguage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onResume() {
        super.onResume();
        viewForLocale();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void viewForLocale() {
        Locale.setDefault(SettingActivity.locale);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(SettingActivity.locale);
        resources.updateConfiguration(configuration, displayMetrics);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            try {
                Intent k = new Intent(this, SettingActivity.class);
                startActivity(k);
            } catch (Exception e) {
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Σε πάει στην κεντρική σελίδα με το οχήματα
    public void carSelected(View view) {
        Intent ki = new Intent(this, VehicleView.class);
        startActivity(ki);
    }


    //Σε πάει στο Google maps
    public void startMapActivity(View view) {
        Intent ki = new Intent(this, MapsActivity.class);
        startActivity(ki);
    }



    // Μέθοδος που σε πάώ για να διαλέξει κάνεις insert κάποιο vehicle
    private void gotoInsert(View view) {
        Intent ki = new Intent(this, ChooseToInsertActivity.class);
        startActivity(ki);
    }

    // Είναι για το Firebase Login
    // Δεν το χρησιμοποιώ πουθενά απλά το έχω βάλει για να υπάρχει
    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Toast.makeText(MainActivity.this, getText(R.string.firebase_succes_login) + user.getEmail(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this,  getText(R.string.firebase_fail_login), Toast.LENGTH_SHORT).show();
        }
    }


    //Αλλάζουμε τη λειτουργία του Back press button για να σε βγάζει απο την εφαρμογή
    //Βάζοντας ένα AlertDialog με φωτογραφία μέσα
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle( R.string.exitHeader )
                .setMessage(R.string.exit);        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Κάνουμε exit απο την εφαρμοή
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        final AlertDialog dialog = builder.create();
                LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.exit_layout, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.show();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                ImageView image = (ImageView) dialog.findViewById(R.id.goProDialogImage);
                Bitmap icon = BitmapFactory.decodeResource(getResources(),
                        R.drawable.minion);
                float imageWidthInPX = (float)image.getWidth();

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                        Math.round(imageWidthInPX * (float)icon.getHeight() / (float)icon.getWidth()));
                image.setLayoutParams(layoutParams);
            }
        });
    }
}