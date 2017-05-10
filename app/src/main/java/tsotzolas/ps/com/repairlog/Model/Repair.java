package tsotzolas.ps.com.repairlog.Model;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by tsotzolas on 7/5/2017.
 */

public class Repair extends RealmObject {

    private String repairId;
    private String repairDate;
    private String vehicleKM;
    private String repairDescription;
    private String repairCost;
    private String vehicleId;

    public Repair() {

    }

    public Repair(String repairId, String repairDate, String vehicleKM, String repairDescription, String repairCost, String vehicleId) {
        this.repairId = repairId;
        this.repairDate = repairDate;
        this.vehicleKM = vehicleKM;
        this.repairDescription = repairDescription;
        this.repairCost = repairCost;
        this.vehicleId = vehicleId;
    }


    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(String repairDate) {
        this.repairDate = repairDate;
    }

    public String getVehicleKM() {
        return vehicleKM;
    }

    public void setVehicleKM(String vehicleKM) {
        this.vehicleKM = vehicleKM;
    }

    public String getRepairDescription() {
        return repairDescription;
    }

    public void setRepairDescription(String repairDescription) {
        this.repairDescription = repairDescription;
    }

    public String getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(String repairCost) {
        this.repairCost = repairCost;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
}
