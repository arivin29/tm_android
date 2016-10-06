package retrofit.etos.it.sket.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by IT on 05/09/2016.
 */
public class IkanRespon {
    private int total;

    @SerializedName("list_ikan")
    @Expose
    private List<Ikan> listIkan;

    public int getTotal()
    {
        return total;
    }

    public List<Ikan> getListIkan()
    {
        return listIkan;
    }
}
