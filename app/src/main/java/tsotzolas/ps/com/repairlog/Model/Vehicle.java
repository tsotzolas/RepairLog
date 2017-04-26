package tsotzolas.ps.com.repairlog.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tsotzolas on 26/4/2017.
 */

public class Vehicle extends RealmObject{

    @PrimaryKey
    private String id;
    private String make;
    private String year;
    private String model;
    private String cc;
    private byte[] photo;
    private boolean isCar;

    public Vehicle() {
    }

    public Vehicle(String id, String make, String year, String model, String cc, byte[] photo, boolean isCar) {
        this.id = id;
        this.make = make;
        this.year = year;
        this.model = model;
        this.cc = cc;
        this.photo = photo;
        this.isCar = isCar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public boolean isCar() {
        return isCar;
    }

    public void setCar(boolean car) {
        isCar = car;
    }
}
