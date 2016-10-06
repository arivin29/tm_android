package retrofit.etos.it.sket.Api;

import okhttp3.ResponseBody;
import retrofit.etos.it.sket.Model.IkanRespon;
import retrofit.etos.it.sket.Model.Kapal;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by IT on 10/09/2016.
 */
public interface KapalInterface {
    @GET("kapal")
    Call<Kapal> getData(@Query("id") String id);

    @POST("timbang/simpan")
    Call<Kapal> simpanData(@Body Kapal kapal);
}
