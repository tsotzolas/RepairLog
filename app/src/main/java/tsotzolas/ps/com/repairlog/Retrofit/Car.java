package tsotzolas.ps.com.repairlog.Retrofit;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by tsotzo on 10/4/2017.
 */

public class Car extends RealmObject{
    private String make;
    private String year;
    private String model;

    public Car(String make, String year, String model) {
        this.make = make;
        this.year = year;
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
