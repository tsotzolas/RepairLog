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
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Locale;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.SyncUser;
import tsotzolas.ps.com.repairlog.GooglePachages.GoogleSignIn.SignInActivity;
import tsotzolas.ps.com.repairlog.GooglePachages.google.GoogleAuth;
import tsotzolas.ps.com.repairlog.R;

import static tsotzolas.ps.com.repairlog.GooglePachages.GoogleSignIn.SignInActivity.mGoogleApiClient;

public class MainActivity extends AppCompatActivity
        implements SyncUser.Callback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageButton carImageButton;
    private ImageButton motoImageButton;
    private FirebaseAuth mAuth;
    private Locale locale;
    private GoogleAuth googleAuth;
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





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                gotoInsert(view);

            }
        });

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

//        //Έλεγχος για το αν έχει κάνει google sign in ο χρήστης
//        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
//            System.out.println("------------------>User is Sign in");
//        }
//        else{
//            Intent ki = new Intent(this, SignInActivity.class);
//            startActivity(ki);
//
//        }


//        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
        if (mGoogleApiClient == null) {
            Intent ki = new Intent(this, SignInActivity.class);
            startActivity(ki);

        } else {

            System.out.println("------------------>User is Sign in");
            Toast.makeText(this, "Login User:" + acct.getDisplayName(), Toast.LENGTH_SHORT).show();
        }


        //Firebase Login
        mAuth = FirebaseAuth.getInstance();
        login(FIREBASE_USERNAME, FIREBASE_PASSWORD);

        carImageButton = (ImageButton) findViewById(R.id.imageButtonCar);
        motoImageButton = (ImageButton) findViewById(R.id.imageButtonMoto);

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

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

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

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }


    public void carSelected(View view) {
//        Toast.makeText(this, "Select Car", Toast.LENGTH_SHORT).show();
        Intent ki = new Intent(this, VehicleView.class);
        startActivity(ki);
    }

    public void startMapActivity(View view) {
//        Toast.makeText(this, "Select Car", Toast.LENGTH_SHORT).show();
        Intent ki = new Intent(this, MapsActivity.class);
        startActivity(ki);
    }



    // Μέθοδος που σε πάώ για να κάνεις insert
    private void gotoInsert(View view) {
        Intent ki = new Intent(this, ChooseToInsertActivity.class);
        startActivity(ki);
    }

    // Είναι για το Firebase Login Δεν το χρησιμοποιώ πουθενά απλά το έχω βάλει για να υπάρχει
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


    public void gotoGoogleSignIn(View view) {
        Intent ki = new Intent(this, SignInActivity.class);
        startActivity(ki);
        System.out.println("---------------------");
    }


    private void registrationComplete(SyncUser user) {
        UserManager.setActiveUser(user);
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onSuccess(SyncUser user) {
//        loginComplete(user);
        System.out.println("-------SUCCESS-------");
    }

    @Override
    public void onError(ObjectServerError error) {
        String errorMsg;
        switch (error.getErrorCode()) {
            case UNKNOWN_ACCOUNT:
                errorMsg = "Account does not exists.";
                break;
            case INVALID_CREDENTIALS:
                errorMsg = "The provided credentials are invalid!"; // This message covers also expired account token
                break;
            default:
                errorMsg = error.toString();
        }
        Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_LONG).show();
    }

    private void loginComplete(SyncUser user) {
        UserManager.setActiveUser(user);

//        createInitialDataIfNeeded();
//
//        Intent listActivity = new Intent(this, TaskListActivity.class);
//        Intent tasksActivity = new Intent(this, TaskActivity.class);
//        tasksActivity.putExtra(TaskActivity.EXTRA_LIST_ID, RealmTasksApplication.DEFAULT_LIST_ID);
//        startActivities(new Intent[] { listActivity, tasksActivity} );
        finish();
    }



    //Αλλάζουμε τη λειτουργία του Back press button για να μας πηγαίνει στην αρχική οθόνη
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");


//        AlertDialog.Builder dialog = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
//
//        dialog.setTitle( R.string.exitHeader )
//                .setIcon(R.mipmap.ic_minion)
//                .setMessage(R.string.exit)
//  .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//      public void onClick(DialogInterface dialoginterface, int i) {
//          dialoginterface.cancel();
//          }})
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialoginterface, int i) {
//                        System.exit(0);
//                    }
//                }).show();






        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle( R.string.exitHeader )
                .setMessage(R.string.exit);        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
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