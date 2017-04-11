
package tsotzolas.ps.com.repairlog.Retrofit.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model {

    @SerializedName("Models")
    @Expose
    private List<Model_> models = null;

    public List<Model_> getModels() {
        return models;
    }

    public void setModels(List<Model_> models) {
        this.models = models;
    }

}
