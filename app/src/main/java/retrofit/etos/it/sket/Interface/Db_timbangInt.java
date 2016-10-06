package retrofit.etos.it.sket.Interface;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.etos.it.sket.Model.Kapal;
import retrofit.etos.it.sket.Model.Timbang;

/**
 * Created by IT on 15/09/2016.
 */
public interface Db_timbangInt {
    public void addTimbang(Timbang timbang);
    public ArrayList<Timbang> getAllTimbang(int id);
    public Timbang getTimbang(int id);
    public int getTimbangCount(int id);
    public boolean updateData(String keyUnik);
    public  ArrayList<Timbang> ambilData(String keyUnik);
    public ArrayList<HashMap<String, String>> countTimbang(String keyUnik);
    public JSONObject toJSONallTimbang() throws JSONException;
}
