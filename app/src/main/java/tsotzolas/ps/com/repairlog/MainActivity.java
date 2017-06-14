package tsotzolas.ps.com.repairlog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;
import tsotzolas.ps.com.repairlog.GoogleSignIn.SignInActivity;
import tsotzolas.ps.com.repairlog.auth.google.GoogleAuth;

import static io.realm.ErrorCode.INVALID_CREDENTIALS;
import static io.realm.ErrorCode.UNKNOWN_ACCOUNT;
import static io.realm.SyncUser.currentUser;
import static tsotzolas.ps.com.repairlog.GoogleSignIn.SignInActivity.mGoogleApiClient;
import static tsotzolas.ps.com.repairlog.RealmTasksApplication.AUTH_URL;
import static tsotzolas.ps.com.repairlog.RealmTasksApplication.REALM_URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SyncUser.Callback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageButton carImageButton;
    private ImageButton motoImageButton;
    private FirebaseAuth mAuth;
    private Locale locale;
    private GoogleAuth googleAuth;
    public static GoogleSignInAccount acct;
    private String username = "";
    private String password = "";
    private SyncUser user;

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

//        user = SyncUser.currentUser();
//        if (user == null) {
//            // Create a RealmConfiguration for our user
//            SyncConfiguration config = new SyncConfiguration.Builder(currentUser(), REALM_URL)
//                    .initialData(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {
//
//                        }
//                    })
//                    .build();

            // This will automatically sync all changes in the background for as long as the Realm is open
            Realm realm = Realm.getDefaultInstance();
//        }





        if (acct==null) {
            Intent ki = new Intent(this, SignInActivity.class);
            startActivity(ki);

        } else {

            System.out.println("------------------>User is Sign in");
            Toast.makeText(this, "Login User:" + acct.getDisplayName(), Toast.LENGTH_SHORT).show();

        }











        //Firebase Login
        mAuth = FirebaseAuth.getInstance();
            login("tsotzolas@gmail.com", "123123123");

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World!");


        carImageButton = (ImageButton) findViewById(R.id.imageButtonCar);
        motoImageButton = (ImageButton) findViewById(R.id.imageButtonMoto);

        if (SettingActivity.locale == null) {
            locale.setDefault(locale);
        } else {
            locale = new Locale(SettingActivity.locale.getDisplayLanguage());
            Log.d("------------------>", locale.getDisplayCountry());
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void carSelected(View view) {

        Toast.makeText(this, "Select Car", Toast.LENGTH_SHORT).show();

        Intent ki = new Intent(this, VehicleView.class);
        startActivity(ki);


    }

    public void motoSelected(View view) {

        Toast.makeText(this, "Select Moto", Toast.LENGTH_SHORT).show();


    }


    private void gotoInsert(View view) {
        Intent ki = new Intent(this, ChooseToInsertActivity.class);
        startActivity(ki);
    }



    //Firebase Login
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
            Toast.makeText(MainActivity.this, "Sucesfull Login with user:" + user.getEmail(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }


    public void gotoGoogleSignIn(View view) {
        Intent ki = new Intent(this, SignInActivity.class);
        startActivity(ki);
        System.out.println("---------------------");
    }


    //TODO Θα πρεπει να δώ πώς θα κάνει login.θα πρεπει να διακρύνω τις περιπτώσεις του έχει δημουργηθεί βάση ή όχι
    public void realmSync(View view) {


        login(false);



//        SyncCredentials myCredentials = SyncCredentials.usernamePassword("tsotzo1@gmail.com", "106425184769527005706", false);
//
//
//        String authURL = "http://83.212.105.36:9080/auth";
//        user = SyncUser.login(myCredentials, authURL);














//        //Φτιαχνουμε το username και το password απο το google sign in
//        if (acct != null) {
//            if ("tsotzolas@gmail.com".equals(acct.getEmail())) {
//                username = "tsotzo1@gmail.com";
//            } else {
//                username = acct.getEmail();
//            }
//            password = acct.getId();
//        }
//
//
////        String authURL = "http://83.212.105.36:9080/auth";
//        SyncCredentials myCredentials = null;
//        try {
//            myCredentials = SyncCredentials.usernamePassword(
//                    username, password, false); //user is in Realm database
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        //TODO Εδώ θα πρέπει να μπαίνει όταν υπάρχει χρήστης στον Realm Object Server
//
//
//        if (myCredentials != null) {
//            SyncUser.loginAsync(myCredentials, AUTH_URL, new SyncUser.Callback() {
//                @Override
//                public void onSuccess(SyncUser user) {
//                    System.out.println("test");
//                }
//
//                @Override
//                public void onError(ObjectServerError error) {
//                    //Άμα δεν υπάρχει ο χρήστης τον δημιουργούμε
//                    SyncUser.loginAsync(SyncCredentials.usernamePassword(username, password, true), AUTH_URL, this);
//                }
//            });
//            Log.i("TINGLE", "credentials checked");
//            Realm.getDefaultInstance();
//        }
//        if(1==1) {
//            SyncConfiguration defaultConfig = new SyncConfiguration.Builder(
//                    currentUser(),
//                    REALM_URL).build();
//            Realm.setDefaultConfiguration(defaultConfig);
//        }









//        } else {
//            //TODO Εδώ θα πρέπει να μπαίνει όταν ΔΕΝ υπάρχει χρήστης στον Realm Object Server
//            //Φτιάχνουμε χρήστη στο Realm Object Server
//            SyncUser.loginAsync(SyncCredentials.usernamePassword(username, password, true), AUTH_URL, new SyncUser.Callback() {
//                @Override
//                public void onSuccess(SyncUser user) {
//                    registrationComplete(user);
//                }
//
//                @Override
//                public void onError(ObjectServerError error) {
////                showProgress(false);
//                    String errorMsg;
//                    switch (error.getErrorCode()) {
//                        case EXISTING_ACCOUNT:
//                            errorMsg = "Account already exists";
//                            break;
//                        default:
//                            errorMsg = error.toString();
//                    }
//                    Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_LONG).show();
//                }
//            });
        }

//        String authURL = "http://83.212.105.36:9080/auth";
//        SyncCredentials myCredentials = SyncCredentials.usernamePassword(
//                username, password, false); //user is in Realm database
//
//        SyncUser.loginAsync(myCredentials, authURL, this);
//        Log.i("TINGLE","credentials checked");
//
//        SyncConfiguration defaultConfig = new SyncConfiguration.Builder(
//                currentUser(),
//                "realm://83.212.105.36:9080/~/realmtasks").build();
//        Realm.setDefaultConfiguration(defaultConfig);


//
//
////        //Κάνουμε login
//        SyncCredentials myCredentials = SyncCredentials.usernamePassword(username, password, false);
//        Realm.getDefaultInstance();
////        Realm.getInstance(syncConfiguration);
////
//        String authURL = "http://83.212.105.36" + ":9080/auth";
//
////
//        SyncUser user = SyncUser.login(myCredentials, authURL);
////
//        String serverURL = "realm://http://83.212.105.36:9080/~/default";
//        SyncConfiguration syncConfiguration = new SyncConfiguration.Builder(user, serverURL).build();
//        Realm.getInstance(syncConfiguration);


//        SyncConfiguration config = new SyncConfiguration.Builder(user, authURL)
//                               .build();
//
//        RealmAsyncTask task = (RealmAsyncTask) Realm.getInstance(config);

//        user.logout();




    private void registrationComplete(SyncUser user) {
        UserManager.setActiveUser(user);
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onSuccess(SyncUser user) {

    }

    @Override
    public void onError(ObjectServerError error) {

    }








    public void login(boolean createUser) {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        if (acct != null) {
            if ("tsotzolas@gmail.com".equals(acct.getEmail())) {
                username = "tsotzo1@gmail.com";
            } else {
                username = acct.getEmail();
            }
            password = acct.getId();
        }

        SyncCredentials creds = SyncCredentials.usernamePassword(username,"123123123", createUser);
        String authUrl = AUTH_URL;
        SyncUser.Callback callback = new SyncUser.Callback() {
            @Override
            public void onSuccess(SyncUser user) {
                progressDialog.dismiss();
            }

            @Override
            public void onError(ObjectServerError error) {
                progressDialog.dismiss();
                String errorMsg;
                switch (error.getErrorCode()) {
                    case UNKNOWN_ACCOUNT:
                        errorMsg = "Account does not exists.";
                        System.out.println(errorMsg);
                        break;
                    case INVALID_CREDENTIALS:
                        errorMsg = "User name and password does not match";
                        System.out.println(errorMsg);
                        break;
                    default:
                        errorMsg = error.toString();
                }
            }
        };

        SyncUser.loginAsync(creds, authUrl, callback);
    }








}