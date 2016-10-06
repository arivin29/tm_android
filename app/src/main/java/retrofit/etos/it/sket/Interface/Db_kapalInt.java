package retrofit.etos.it.sket.Interface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.etos.it.sket.Model.Kapal;

/**
 * Created by IT on 15/09/2016.
 */
public interface Db_kapalInt {
    public void addKapal(Kapal kapal);
    public ArrayList<Kapal> getAllKapal();
    public Kapal getKapal(String keyUnik);
    public int getKapalCount();
    public JSONObject toJSONallKapal() throws JSONException;
}
