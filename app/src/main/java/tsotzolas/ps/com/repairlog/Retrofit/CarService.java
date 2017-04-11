package tsotzolas.ps.com.repairlog.Retrofit;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tsotzolas.ps.com.repairlog.Retrofit.Makes.Make;
import tsotzolas.ps.com.repairlog.Retrofit.Model.Model;

/**
 * Created by tsotzolas on 8/4/2017.
 */
public interface CarService {

    /** Έιναι το Interface για το Retrofit για να καλέσουμε και να μας φέρει τις μάρκες των αυτοκινήτων
     *
     * @param cmd
     * @param year  το έτος κατασκευής
     * @param soldInUS αν πωλείται μόνο στην Αμερική ή όχι.
     * @return
     */
    @GET("/api/0.3")
    Call<Make> getMake(@Query("cmd") String cmd, @Query("year") String year, @Query("sold_in_us") String soldInUS);


    @GET("/api/0.3")
    Call<Model> getModel(@Query("cmd") String cmd, @Query("make") String make, @Query("year") String year);

}
