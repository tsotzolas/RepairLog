package tsotzolas.ps.com.repairlog.Activities;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

/**
 * Created by tsotzolas on 15/6/2017.
 * This class is used for Realm Sync
 */

public class SyncRealm {


    private static volatile Realm realm;


    public static void realmSync() {
        String username = "";
        String password = "";
        //Φτιαχνουμε το username και το password απο το google sign in
        if (MainActivity.acct != null) {
            if ("tsotzolas@gmail.com".equals(MainActivity.acct.getEmail())) {
                username = "tsotzo1@gmail.com";
            } else {
                username = MainActivity.acct.getEmail();
            }
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
                    final SyncConfiguration syncConfiguration = new SyncConfiguration.Builder(user, RealmTasksApplication.REALM_URL).build();
                    Realm.setDefaultConfiguration(syncConfiguration);
                    realm = Realm.getDefaultInstance();
//                  Realm.setDefaultConfiguration(defaultConfig);
                }

                @Override
                public void onError(ObjectServerError error) {
                    System.out.println("---------------------Not Valid User-------------------");
                }
            });
        }
    }
}
