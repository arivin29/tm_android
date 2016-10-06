package retrofit.etos.it.sket;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit.etos.it.sket.Activity.Pengaturan;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by IT on 05/09/2016.
 */
public class ApiCLient{
    //public static final String BASE_URL = "http://arivin.xyz/TM/public/";
    public static final String BASE_URL = Pengaturan.url;
    public static boolean LOGIN = false;
    private static Retrofit retrofit = null;

    public static Retrofit getClient()
    {

        Log.i("URL",BASE_URL);
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();
       // client.addInterceptor(logging);
        if(retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create()).build();


        }
        return retrofit;
    }
}
