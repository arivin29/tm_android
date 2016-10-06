package retrofit.etos.it.sket.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit.etos.it.sket.Activity.MainActivity;
import retrofit.etos.it.sket.Model.Ikan;
import retrofit.etos.it.sket.R;

/**
 * Created by IT on 05/09/2016.
 */
public class IkanAdaptor extends BaseAdapter implements Filterable{

    private Context context;
    private List<Ikan> ikanList=null;
    private List<Ikan> filterIkan = null;
    private Filter mFilter;

    TextView pilihIkan;

    public IkanAdaptor(Context context, List<Ikan> data, TextView pilihIkan)
    {
        this.context = context;
        this.ikanList = data;
        this.filterIkan = data;
        this.pilihIkan = pilihIkan;
    }

    @Override
    public int getCount() {
        return filterIkan.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_ikan,null);
            holder = new ViewHolder();
            holder.text = (TextView)convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText("" + filterIkan.get(position).getNama_ikan());

//      Jiko di klik item
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.e("hasil", String.valueOf(filterIkan.get(position).getIdIkan()));
                pilihIkan.setText(filterIkan.get(position).getNama_ikan());
                MainActivity.id_ikan=filterIkan.get(position).getIdIkan();
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView text;
    }

    public Filter getFilter()
    {
        if(mFilter==null)
        {
            mFilter = new ItemFilter();
        }
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @SuppressLint("DefaultLocale")
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final List<Ikan> list = ikanList;

            int cout = list.size();
            final ArrayList<Ikan> nlist = new ArrayList<Ikan>(cout);

            String filterableString;

            for (int i = 0; i < cout; i++)
            {
                filterableString = "" + list.get(i).getNama_ikan();
                if(filterableString.toLowerCase().contains(filterString))
                {
                    Ikan ikan = list.get(i);
                    nlist.add(ikan);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            filterIkan = (ArrayList<Ikan>) results.values;
            notifyDataSetChanged();
        }
    }
}
