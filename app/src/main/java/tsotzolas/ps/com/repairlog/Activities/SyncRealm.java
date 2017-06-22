package tsotzolas.ps.com.repairlog.Activities;

import com.facebook.stetho.Stetho;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

import static io.realm.internal.SyncObjectServerFacade.getApplicationContext;

/**
 * Created by tsotzolas on 15/6/2017.
 * Είναι για τον συγχρονισμό του Realm με τον Realm Object Server
 */

public class SyncRealm {


    private static volatile Realm realm;


    public static void realmSync() {
        String username = "";
        String password = "";
        //Φτιαχνουμε το username και το password απο το google sign in άμα ο χρήστης δεν υπάρχει
        if (MainActivity.acct != null) {
            //Κάνουμε έναν έλεγχο άμα ο χρήστης έχει το συγκεκριμένο email να του το αλλάξουμε
            // γιατί με το email αυτό είναι ο λογαρισμός του Database Administrator στο Realm
            if ("tsotzolas@gmail.com".equals(MainActivity.acct.getEmail())) {
                username = "tsotzo1@gmail.com";
            } else {
                //σαν username στο Realm βάζουμε το email του χρήστη απο το Google Sign In
                username = MainActivity.acct.getEmail();
            }
            //Σαν password στο Realm βάζουμε το google id
            password = MainActivity.acct.getId();
        }

        SyncCredentials myCredentials = null;
        try {
            myCredentials = SyncCredentials.usernamePassword(
                    username, password, false); //user is in Realm database
        } catch (Exception e) {
            System.out.println(e);
        }
        //Κάνει login στον Realm Object Server για να κάνει τον συγχρονισμό
        if (myCredentials != null) {
            final SyncCredentials syncCredentials = SyncCredentials.usernamePassword(username, password, false);
            SyncUser.loginAsync(syncCredentials, RealmTasksApplication.AUTH_URL, new SyncUser.Callback() {
                @Override
                public void onSuccess(SyncUser user) {
                    //Στο επιτυχημένο login κάνουμε και συγχρονισμό των δεδομένων
                    final SyncConfiguration syncConfiguration = new SyncConfiguration.Builder(user, RealmTasksApplication.REALM_URL).build();
                    try {
                        Realm.setDefaultConfiguration(syncConfiguration);
                        realm = Realm.getDefaultInstance();
//                  Realm.setDefaultConfiguration(defaultConfig);
                    } catch (Exception e) {
                        System.out.println(e);

                    }

                }

                @Override
                public void onError(ObjectServerError error) {
                    System.out.println("---------------------Not Valid User-------------------");
                }
            });
        }
    }


}
