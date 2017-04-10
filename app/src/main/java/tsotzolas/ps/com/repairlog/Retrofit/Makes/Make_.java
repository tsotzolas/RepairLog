
package tsotzolas.ps.com.repairlog.Retrofit.Makes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Make_ {

    @SerializedName("make_id")
    @Expose
    private String makeId;
    @SerializedName("make_display")
    @Expose
    private String makeDisplay;
    @SerializedName("make_is_common")
    @Expose
    private String makeIsCommon;
    @SerializedName("make_country")
    @Expose
    private String makeCountry;

    public Make_(String makeId, String makeDisplay, String makeIsCommon, String makeCountry) {
        this.makeId = makeId;
        this.makeDisplay = makeDisplay;
        this.makeIsCommon = makeIsCommon;
        this.makeCountry = makeCountry;
    }

    @Override
    public String toString() {
        return "Make_{" +
                "makeId='" + makeId + '\'' +
                ", makeDisplay='" + makeDisplay + '\'' +
                ", makeIsCommon='" + makeIsCommon + '\'' +
                ", makeCountry='" + makeCountry + '\'' +
                '}';
    }

    public String getMakeId() {
        return makeId;
    }

    public void setMakeId(String makeId) {
        this.makeId = makeId;
    }

    public String getMakeDisplay() {
        return makeDisplay;
    }

    public void setMakeDisplay(String makeDisplay) {
        this.makeDisplay = makeDisplay;
    }

    public String getMakeIsCommon() {
        return makeIsCommon;
    }

    public void setMakeIsCommon(String makeIsCommon) {
        this.makeIsCommon = makeIsCommon;
    }

    public String getMakeCountry() {
        return makeCountry;
    }

    public void setMakeCountry(String makeCountry) {
        this.makeCountry = makeCountry;
    }

}
