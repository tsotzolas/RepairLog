package tsotzolas.ps.com.repairlog.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

import tsotzolas.ps.com.repairlog.R;

/**
 * Created by tsotzolas on 3/4/2017.
 */

public class SettingActivity extends AppCompatActivity {

    private Button changeLanguageButton;
   static public Locale locale = Locale.getDefault();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);
        System.out.println("Test");
        changeLanguageButton = (Button) findViewById(R.id.chabgeLangbutton);
    }

    /**
     * Changes the {@link Locale} of the application and updates the {@link Configuration}
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void changeLocale(View view) {
        if (locale.getLanguage().equals("el")) {
            locale = new Locale("en");
        } else if (locale.getLanguage().equals("en")) {
            locale = new Locale("el");
        }

        Locale.setDefault(locale);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, displayMetrics);

        //We can check the version api with
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
        //}

        Toast.makeText(this, R.string.main_language_changed_message, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}