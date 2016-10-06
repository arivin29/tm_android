package retrofit.etos.it.sket.Model;

import java.util.List;

/**
 * Created by IT on 06/09/2016.
 */
public class TimbangRespon {

    private int total;

    private List<Timbang> list_timbang;

    public int getTotal ()
    {
        return total;
    }

    public void setTotal (int total)
    {
        this.total = total;
    }

    public List<Timbang> getList_timbang ()
    {
        return list_timbang;
    }

    public void setList_timbang (List<Timbang> list_timbang)
    {
        this.list_timbang = list_timbang;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [total = "+total+", list_timbang = "+list_timbang+"]";
    }
}
