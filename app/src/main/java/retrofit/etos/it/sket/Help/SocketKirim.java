package retrofit.etos.it.sket.Help;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.Socket;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.etos.it.sket.Adapter.TimbangAdaptor;
import retrofit.etos.it.sket.Api.KapalInterface;
import retrofit.etos.it.sket.Api.TimbangInterface;
import retrofit.etos.it.sket.ApiCLient;
import retrofit.etos.it.sket.Data.Db_kapals;
import retrofit.etos.it.sket.Data.Db_timbang;
import retrofit.etos.it.sket.Model.Kapal;
import retrofit.etos.it.sket.Model.Timbang;
import retrofit.etos.it.sket.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by IT on 15/09/2016.
 */
public class SocketKirim {
    public static Context context;
    public ListView listView_timbang;

    public Void kirimHasilKapal(Context context, Socket mSocket, final Db_kapals db_kapal, Kapal kapal) {
        db_kapal.addKapal(kapal);
        mSocket.emit("send", kapal.toJSON());

        KapalInterface kapalInterface = ApiCLient.getClient().create(KapalInterface.class);
        Call<Kapal> call = kapalInterface.simpanData(kapal);
        call.enqueue(new Callback<Kapal>() {
            @Override
            public void onResponse(Call<Kapal> call, Response<Kapal> response) {

                String str = "";
                response.code();
                //db_kapal.getAllKapal();

                //Log.e("data berhasil di kirm","sds" + str);
                Log.i("Informasi data", "data :" + "Code " + response.code() + ", Message " + response.message());
            }

            @Override
            public void onFailure(Call<Kapal> call, Throwable t) {
                Log.e("data gagal di kirm", "sds");
            }
        });


        return null;
    }

    public Void kirimHasilTimbang(Context contexts, Socket mSocket, final Db_timbang db_timbang, final Timbang timbang, ListView listViews) {


        db_timbang.addTimbang(timbang);
        mSocket.emit("send", timbang.toJSON());
        context = contexts;
        listView_timbang = listViews;

        Toast.makeText(context, "Data Berhasil di simpan Local", Toast.LENGTH_SHORT).show();

        TimbangInterface timbangInterface = ApiCLient.getClient().create(TimbangInterface.class);
        Call<Timbang> call = timbangInterface.simpanData(timbang);
        call.enqueue(new Callback<Timbang>() {
            @Override
            public void onResponse(Call<Timbang> call, Response<Timbang> response) {
                if (response.code() == 200) {
                    Toast.makeText(context, "Data Berhasil di simpan Server", Toast.LENGTH_SHORT).show();
                    //update data status timbang ke 1
                    db_timbang.updateData(timbang.getKeyUnik().toString());

                    //tampilkan ka main Aktiviri
                    ArrayList<HashMap<String, String>> data = db_timbang.countTimbang(timbang.getKeyUnik().toString());
                    TimbangAdaptor(data, listView_timbang);
                } else {
                    Toast.makeText(context, "Data Gagal simpan Server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Timbang> call, Throwable t) {

            }
        });

        return null;
    }

    public void TimbangAdaptor(ArrayList<HashMap<String, String>> data, ListView listView_timbang) {
        int cout = data.size();
        for (int i = 0; i < cout; i++) {
            HashMap<String, String> isi = data.get(i);
            Log.e("loop", isi.get("jml_timbang"));
        }
        TimbangAdaptor timbangAdaptor = new TimbangAdaptor(context, R.id.list_hasil_timbang, data);
        listView_timbang.setAdapter(timbangAdaptor);

    }


}