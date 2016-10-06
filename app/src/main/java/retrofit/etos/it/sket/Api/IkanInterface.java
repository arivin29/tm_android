package retrofit.etos.it.sket.Api;

import java.util.List;

import retrofit.etos.it.sket.Model.Ikan;
import retrofit.etos.it.sket.Model.IkanRespon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by IT on 05/09/2016.
 */
public interface IkanInterface {
    @GET("ikan")
    Call<IkanRespon> getData();

}
