package tsotzolas.ps.com.repairlog.Activities;

import android.app.Application;

//import com.facebook.stetho.Stetho;
//import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;

import static android.R.attr.key;

/**
 * Created by tsotzolas on 24/4/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("RepairLog.realm")
//                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);


        // Use the config
        Realm realm = Realm.getInstance(config);

        // Είναι για το Stetho για να μπορείς να βλέπεις την Realm Βαση σου στον Chrome
        //https://github.com/uPhyca/stetho-realm
        //Για κάποιο λόγο σε εμένα δεν δούλεψε σωστά
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        realm.close();
    }
}
