package de.repair.repairondemand.AuftragList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.repair.repairondemand.R;
import de.repair.repairondemand.SQLlite.Modells.Anfrage;

public class AuftragAdapter extends BaseAdapter {

    List<Anfrage> anfrageList;
    List<byte[]> byteList;

    Context context;
    private static LayoutInflater inflater = null;

    public AuftragAdapter(Context context, List<Anfrage> anfrageList, List<byte[]> byteList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.anfrageList = anfrageList;
        this.byteList = byteList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return anfrageList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return anfrageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    static class ViewHolder {
        protected TextView beschreibung;
        protected ImageView bild;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view = null;
        if (convertView == null){
            view = inflater.inflate(R.layout.auftrag_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.beschreibung = view.findViewById(R.id.beschreibung);
            viewHolder.bild = view.findViewById(R.id.bild);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        // hier jeweils Werte übergeben
        holder.beschreibung.setText(anfrageList.get(position).getmBeschreibung());
        holder.bild.setImageDrawable(new BitmapDrawable(context.getResources(),
                BitmapFactory.decodeByteArray(byteList.get(position),
                        0, byteList.get(position).length)));

        return view;
    }
}
