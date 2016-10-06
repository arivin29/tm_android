package retrofit.etos.it.sket.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.etos.it.sket.Model.Timbang;
import retrofit.etos.it.sket.R;

/**
 * Created by IT on 06/09/2016.
 */
public class TimbangAdaptor extends ArrayAdapter<HashMap<String, String>> {
    private Context context;
    private ArrayList<HashMap<String, String>> timbangs;

    public TimbangAdaptor(Context context,int resource, ArrayList<HashMap<String, String>> objeks)
    {
        super(context,resource,objeks);
        this.context = context;
        this.timbangs = objeks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_timbang_activity ,viewGroup,false);
        HashMap<String, String> timbang = timbangs.get(position);

        TextView textView = (TextView) view.findViewById(R.id.nomor_urut);
        textView.setText("" + timbang.get("no") );

        TextView jml_timbang = (TextView) view.findViewById(R.id.jml_timbang);
        jml_timbang.setText(timbang.get("jml_timbang") + " Kali");

        TextView id_timbang = (TextView) view.findViewById(R.id.id_timbang);
        id_timbang.setText(timbang.get("nama_ikan") + " " + timbang.get("jml_timbang"));

        TextView berat = (TextView) view.findViewById(R.id.details_timbang);
        berat.setText("Berat " + timbang.get("jml_berat") + "Kg");

        return view;
    }
}
