package tsotzolas.ps.com.repairlog.GooglePackages.GoogleSignIn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.*;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.SyncCredentials;
import io.realm.SyncUser;
import tsotzolas.ps.com.repairlog.Activities.MainActivity;
import tsotzolas.ps.com.repairlog.R;
import tsotzolas.ps.com.repairlog.Activities.SyncRealm;

import static tsotzolas.ps.com.repairlog.Activities.MainActivity.acct;
import static tsotzolas.ps.com.repairlog.Activities.RealmTasksApplication.AUTH_URL;

/**
 * Activity to demonstrate basic retrieval of the Google user's ID, email address, and basic
 * profile.
 */
public class SignInActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, SyncUser.Callback {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    public static GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    @VisibleForTesting
    private ProgressDialog mProgressDialog;
    @VisibleForTesting
    private ProgressDialog mProgressDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_sign_in);

        // Views
        mStatusTextView = (TextView) findViewById(R.id.status);

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.disconnect_button).setOnClickListener(this);

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [END build_client]

        // [START customize_button]
        // Set the dimensions of the sign-in button.
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        // [END customize_button]
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideProgressDialog();
    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            acct = result.getSignInAccount();
            String username = "";
            String password = "";
            //Κάνουμε έναν έλεγχο άμα ο χρήστης έχει το συγκεκριμένο email να του το αλλάξουμε
            // γιατί με το email αυτό είναι ο λογαρισμός του Database Administrator στο Realm Object Server
            if (acct != null) {
                if ("tsotzolas@gmail.com".equals(acct.getEmail())) {
                    username = "tsotzo1@gmail.com";
                } else {
                    //σαν username στο Realm βάζουμε το email του χρήστη απο το Google Sign In
                    username = acct.getEmail();
                }
                //Σαν password στο Realm βάζουμε το google id
                password = acct.getId();
            }

            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            Realm.init(getApplicationContext());
            //Αφού ο χρήστης κάνει google Sign In μετα φτιάχνουμε χρήστη στο Realm Object Server
            SyncUser.loginAsync(SyncCredentials.usernamePassword(username, password, true), AUTH_URL, new SyncUser.Callback() {
                @Override
                public void onSuccess(SyncUser user) {
//                    registrationComplete(user);
                    Toast.makeText(SignInActivity.this, "Create User in Realm Object Server", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(ObjectServerError error) {
                    String errorMsg;
                    switch (error.getErrorCode()) {
                        case EXISTING_ACCOUNT:
                            errorMsg = "Account already exists";
                            break;
                        default:
                            errorMsg = error.toString();
                    }
                }
            });
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }

    @Override
    public void onSuccess(SyncUser user) {

    }

    @Override
    public void onError(ObjectServerError error) {

    }

    // [END handleSignInResult]

    // [START signIn]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    // [END signIn]

    // [START signOut]
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]

    // [START revokeAccess]
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        if (mProgressDialog1 != null) {
            mProgressDialog1.dismiss();
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog1.setIndeterminate(true);
            mProgressDialog1.setCanceledOnTouchOutside(false);

        }
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }


    private void showProgressDialog1() {
        if (mProgressDialog1 == null) {
            mProgressDialog1 = new ProgressDialog(this);
            mProgressDialog1.setMessage(getString(R.string.login_to_Realm));
            mProgressDialog1.setIndeterminate(true);
            mProgressDialog1.setCanceledOnTouchOutside(false);



        }

        mProgressDialog1.show();
    }


    private void hideProgressDialog1() {
        if (mProgressDialog1 != null && mProgressDialog1.isShowing()) {
            mProgressDialog1.hide();
        }
    }


    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
            findViewById(R.id.go_to_main).setVisibility(View.VISIBLE);
//            showProgressDialog1();
        } else {
            mStatusTextView.setText(R.string.signed_out);
            findViewById(R.id.go_to_main).setVisibility(View.GONE);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;
        }
    }


    public void gotoMain(View view) {
        showProgressDialog1();

        //Κάνουμε Συγχρονισμό του Realm με τον Realm Object Server
        SyncRealm.realmSync();

        //Καθηστερούμε την όλη διαδικασία για να προλαβει να κάνει συγχρονισμο το Realm
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                hideProgressDialog1();
                Intent ki = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(ki);

            }

        }, 5000); // 5000ms delay
    }
}
