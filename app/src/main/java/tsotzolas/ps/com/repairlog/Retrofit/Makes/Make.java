
package tsotzolas.ps.com.repairlog.Retrofit.Makes;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Make {

    @SerializedName("Makes")
    @Expose
    private List<Make_> makes = null;

    public Make(List<Make_> makes) {
        this.makes = makes;
    }

    public List<Make_> getMakes() {
        return makes;
    }

    public void setMakes(List<Make_> makes) {
        this.makes = makes;
    }

}
