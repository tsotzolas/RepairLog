package tsotzolas.ps.com.repairlog;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

import static tsotzolas.ps.com.repairlog.MainActivity.acct;
import static tsotzolas.ps.com.repairlog.RealmTasksApplication.AUTH_URL;
import static tsotzolas.ps.com.repairlog.RealmTasksApplication.REALM_URL;

/**
 * Created by tsotzolas on 15/6/2017.
 */

public class SyncRealm {

//    public static GoogleSignInAccount acct;
//    private static final String username = "tsotzo1@gmail.com";
//    private static final String password = "123456";
    private static volatile Realm realm;


    public static void realmSync() {
        String username = "";
        String password = "";
        //Φτιαχνουμε το username και το password απο το google sign in
        if (acct != null) {
            if ("tsotzolas@gmail.com".equals(acct.getEmail())) {
                username = "tsotzo1@gmail.com";
            } else {
                username = acct.getEmail();
            }
            password = acct.getId();
        }


//        String authURL = "http://83.212.105.36:9080/auth";

        SyncCredentials myCredentials = null;
        try {
            myCredentials = SyncCredentials.usernamePassword(
                    username, password, false); //user is in Realm database
        } catch (Exception e) {
            System.out.println(e);
        }
        //TODO Εδώ θα πρέπει να μπαίνει όταν υπάρχει χρήστης στον Realm Object Server
        if (myCredentials != null) {
//            SyncUser.loginAsync(SyncCredentials.usernamePassword(username, password, false), RealmTasksApplication.AUTH_URL, this);
            final SyncCredentials syncCredentials = SyncCredentials.usernamePassword(username, password, false);
            SyncUser.loginAsync(syncCredentials, AUTH_URL, new SyncUser.Callback() {
                @Override
                public void onSuccess(SyncUser user) {
                    final SyncConfiguration syncConfiguration = new SyncConfiguration.Builder(user, REALM_URL).build();
                    Realm.setDefaultConfiguration(syncConfiguration);
                    realm = Realm.getDefaultInstance();
//                    Realm.setDefaultConfiguration(defaultConfig);
                }

                @Override
                public void onError(ObjectServerError error) {
                    System.out.println("---------------------Not Valid User-------------------");
                }
            });


        }

    }

}
