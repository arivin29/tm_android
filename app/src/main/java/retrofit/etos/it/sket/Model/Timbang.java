package retrofit.etos.it.sket.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by IT on 06/09/2016.
 */
public class Timbang {

    private Double berat;

    private int id_user;

    private String kode_timbang;

    private String nama_ikan;

    private int status_timbang;

    private String tanggal_timbang;

    private String satuan;

    private int id_kapal;

    private int id_ikan;

    private int id_timbang;

    private String upi;

    private String faktor_b;

    private String faktor_a;

    private int id_timbang_detail;

    private int harga;

    private String keyUnik;

    public Double getBerat ()
    {
        return berat;
    }

    public void setBerat (Double berat)
    {
        this.berat = berat;
    }

    public String getNama_ikan()
    {
        return nama_ikan;
    }
    public void setNama_ikan(String nama_ikan)
    {
        this.nama_ikan = nama_ikan;
    }

    public int getId_user ()
    {
        return id_user;
    }

    public void setId_user (int id_user)
    {
        this.id_user = id_user;
    }

    public String getKode_timbang ()
    {
        return kode_timbang;
    }

    public void setKode_timbang (String kode_timbang)
    {
        this.kode_timbang = kode_timbang;
    }

    public int getStatus_timbang ()
    {
        return status_timbang;
    }

    public void setStatus_timbang (int status_timbang)
    {
        this.status_timbang = status_timbang;
    }

    public String getTanggal_timbang ()
    {
        return tanggal_timbang;
    }

    public void setTanggal_timbang (String tanggal_timbang)
    {
        this.tanggal_timbang = tanggal_timbang;
    }

    public String getSatuan ()
    {
        return satuan;
    }

    public void setSatuan (String satuan)
    {
        this.satuan = satuan;
    }

    public int getId_kapal ()
    {
        return id_kapal;
    }

    public void setId_kapal (int id_kapal)
    {
        this.id_kapal = id_kapal;
    }

    public int getId_ikan ()
    {
        return id_ikan;
    }

    public void setId_ikan (int id_ikan)
    {
        this.id_ikan = id_ikan;
    }

    public int getId_timbang ()
    {
        return id_timbang;
    }

    public void setId_timbang (int id_timbang)
    {
        this.id_timbang = id_timbang;
    }

    public String getUpi ()
    {
        return upi;
    }

    public void setUpi (String upi)
    {
        this.upi = upi;
    }

    public String getFaktor_b ()
    {
        return faktor_b;
    }

    public void setFaktor_b (String faktor_b)
    {
        this.faktor_b = faktor_b;
    }

    public String getFaktor_a ()
    {
        return faktor_a;
    }

    public void setFaktor_a (String faktor_a)
    {
        this.faktor_a = faktor_a;
    }

    public int getId_timbang_detail ()
    {
        return id_timbang_detail;
    }

    public void setId_timbang_detail (int id_timbang_detail)
    {
        this.id_timbang_detail = id_timbang_detail;
    }

    public int getHarga ()
    {
        return harga;
    }

    public void setHarga (int harga)
    {
        this.harga = harga;
    }

    public String getKeyUnik()
    {
        return keyUnik;
    }

    public void setKeyUnik(String keyUnik) {
        this.keyUnik = keyUnik;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [berat = "+berat+", id_user = "+id_user+", kode_timbang = "+kode_timbang+", status_timbang = "+status_timbang+", tanggal_timbang = "+tanggal_timbang+", satuan = "+satuan+", id_kapal = "+id_kapal+", id_ikan = "+id_ikan+", id_timbang = "+id_timbang+", upi = "+upi+", faktor_b = "+faktor_b+", faktor_a = "+faktor_a+", id_timbang_detail = "+id_timbang_detail+", harga = "+harga+"]";
    }

    public JSONObject toJSON()
    {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("berat",getBerat());
            jsonObject.put("id_user",getId_user());
            jsonObject.put("kode_timbang",getKode_timbang());
            jsonObject.put("nama_ikan",getNama_ikan());
            jsonObject.put("status_timbang",getStatus_timbang());
            jsonObject.put("tanggal_timbang",getTanggal_timbang());
            jsonObject.put("satuan",getSatuan());
            jsonObject.put("id_kapal",getId_kapal());
            jsonObject.put("id_ikan",getId_ikan());
            jsonObject.put("id_timbang",getId_timbang());
            jsonObject.put("upi",getUpi());
            jsonObject.put("faktor_b",getFaktor_a());
            jsonObject.put("faktor_a",getFaktor_b());
            jsonObject.put("id_timbang_detail",getId_timbang_detail());
            jsonObject.put("harga",getHarga());
            jsonObject.put("keyUnik",getKeyUnik());

            return  jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}
