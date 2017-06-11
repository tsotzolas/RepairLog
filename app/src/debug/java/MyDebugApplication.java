import android.app.Application;

import com.facebook.stetho.Stetho;

//import com.facebook.stetho.Stetho;

/**
 * Created by tsotzolas on 26/4/2017.
 */

public class MyDebugApplication extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }




}
