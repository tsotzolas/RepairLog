
package tsotzolas.ps.com.repairlog.Retrofit.Makes;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("Makes")
    @Expose
    private List<Make> makes = null;

    public List<Make> getMakes() {
        return makes;
    }

    public void setMakes(List<Make> makes) {
        this.makes = makes;
    }

}