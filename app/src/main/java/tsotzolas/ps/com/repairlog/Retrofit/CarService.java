package tsotzolas.ps.com.repairlog.Retrofit;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tsotzolas.ps.com.repairlog.Retrofit.Makes.Make;

/**
 * Created by tsotzolas on 8/4/2017.
 */
public interface CarService {

    @GET("/api/0.3")
    Call <Make> getMake(@Query("cmd") String cmd, @Query("year") String year, @Query("sold_in_us") String soldInUS);

    }
