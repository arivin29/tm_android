package retrofit.etos.it.sket.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by IT on 05/09/2016.
 */
public class Ikan {

    @SerializedName("id_ikan")
    @Expose
    private int idIkan;

    @SerializedName("nilai_ikan")
    @Expose
    private String nilai_ikan;

    private String nama_ikan;

    public String getNilai_ikan ()
    {
        return nilai_ikan;
    }

    public void setNilai_ikan (String nilai_ikan)
    {
        this.nilai_ikan = nilai_ikan;
    }

    public int getIdIkan ()
    {
        return idIkan;
    }

    public void setIdikan (int idikan)
    {
        this.idIkan = idikan;
    }

    public String getNama_ikan ()
    {
        return nama_ikan;
    }

    public void setNama_ikan (String nama_ikan)
    {
        this.nama_ikan = nama_ikan;
    }

    public Ikan(int idIkan, String nama_ikan, String nilai_ikan) {
        this.idIkan = idIkan;
        this.nama_ikan = nama_ikan;
        this.nilai_ikan = nilai_ikan;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [nilai_ikan = "+nilai_ikan+", id_ikan = "+idIkan+", nama_ikan = "+nama_ikan+"]";
    }

}
