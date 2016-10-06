package retrofit.etos.it.sket.Model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

 
public class Kapal {

    @SerializedName("id_kapal")
    private int idKapal;
    @SerializedName("no_induk")
    private int noInduk;
    @SerializedName("nama_kapal")
    private String namaKapal;
    @SerializedName("pemilik")
    private String pemilik;
    @SerializedName("no_izin")
    private String noIzin;
    @SerializedName("alat_tangkap")
    private String alatTangkap;
    @SerializedName("status_izin")
    private int statusIzin;

    private String keyUnik;

    @SerializedName("id_kapal")
    public int getIdKapal() {
        return idKapal;
    }

    /**
     *
     * @param idKapal
     * The id_kapal
     */
    @SerializedName("id_kapal")
    public void setIdKapal(int idKapal) {
        this.idKapal = idKapal;
    }

    /**
     *
     * @return
     * The noInduk
     */
    @SerializedName("no_induk")
    public int getNoInduk() {
        return noInduk;
    }

    /**
     *
     * @param noInduk
     * The no_induk
     */
    @SerializedName("no_induk")
    public void setNoInduk(int noInduk) {
        this.noInduk = noInduk;
    }

    /**
     *
     * @return
     * The namaKapal
     */
    @SerializedName("nama_kapal")
    public String getNamaKapal() {
        return namaKapal;
    }

    /**
     *
     * @param namaKapal
     * The nama_kapal
     */
    @SerializedName("nama_kapal")
    public void setNamaKapal(String namaKapal) {
        this.namaKapal = namaKapal;
    }

    /**
     *
     * @return
     * The pemilik
     */
    @SerializedName("pemilik")
    public String getPemilik() {
        return pemilik;
    }

    /**
     *
     * @param pemilik
     * The pemilik
     */
    @SerializedName("pemilik")
    public void setPemilik(String pemilik) {
        this.pemilik = pemilik;
    }

    /**
     *
     * @return
     * The noIzin
     */
    @SerializedName("no_izin")
    public String getNoIzin() {
        return noIzin;
    }

    /**
     *
     * @param noIzin
     * The no_izin
     */
    @SerializedName("no_izin")
    public void setNoIzin(String noIzin) {
        this.noIzin = noIzin;
    }

    /**
     *
     * @return
     * The alatTangkap
     */
    @SerializedName("alat_tangkap")
    public String getAlatTangkap() {
        return alatTangkap;
    }

    /**
     *
     * @param alatTangkap
     * The alat_tangkap
     */
    @SerializedName("alat_tangkap")
    public void setAlatTangkap(String alatTangkap) {
        this.alatTangkap = alatTangkap;
    }

    /**
     *
     * @return
     * The statusIzin
     */
    @SerializedName("status_izin")
    public int getStatusIzin() {
        return statusIzin;
    }

    /**
     *
     * @param statusIzin
     * The status_izin
     */
    @SerializedName("status_izin")
    public void setStatusIzin(int statusIzin) {
        this.statusIzin = statusIzin;
    }

    public void setKeyUnik(String keyUnik)
    {
        this.keyUnik = keyUnik;
    }
    public String getKeyUnik()
    {
        return keyUnik;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [nama_kapal = "+ namaKapal+", noInduk= "+noInduk+", alatTangkap = "+alatTangkap+"]";
    }

    public JSONObject toJSON()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id_kapal",getIdKapal());
            jsonObject.put("no_induk",getNoIzin());
            jsonObject.put("no_izin",getNoIzin());
            jsonObject.put("nama_kapal",getNamaKapal());
            jsonObject.put("alat_tangkap",getAlatTangkap());
            jsonObject.put("key_unik",getKeyUnik());

            return  jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }

 
        return null;
    }


}
