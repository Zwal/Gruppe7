package de.repair.repairondemand.AuftragList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.repair.repairondemand.AuftragAnsicht;
import de.repair.repairondemand.AufträgeSuchen;
import de.repair.repairondemand.NotAvailable;
import de.repair.repairondemand.R;
import de.repair.repairondemand.SQLlite.Modells.Adresse;
import de.repair.repairondemand.SQLlite.Modells.Anfrage;

public class AuftragAdapter extends BaseAdapter {

    private ArrayList<Anfrage> anfrageList;
    private ArrayList<byte[]> byteList;
    private AufträgeSuchen aufträgeSuchen;
    private ListView mLv;
    private ArrayList<Adresse> addressList;
    private Intent startActivityIntent;
    private String kategorie, anfang, ende, radius;

    Context context;
    private static LayoutInflater inflater = null;

    public AuftragAdapter(Context context, ListView mLv, ArrayList<Anfrage> anfrageList,
                          ArrayList<byte[]> byteList, ArrayList<Adresse> addressList, String kategorie,
                          String anfang, String ende, String radius) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.anfrageList = anfrageList;
        this.byteList = byteList;
        this.addressList = addressList;
        this.mLv = mLv;
        this.kategorie = kategorie;
        this.anfang = anfang;
        this.ende = ende;
        this.radius = radius;
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
        protected Button lupe;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view = null;
        aufträgeSuchen = new AufträgeSuchen();
        if (convertView == null){
            view = inflater.inflate(R.layout.auftrag_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.beschreibung = view.findViewById(R.id.beschreibung);
            viewHolder.bild = view.findViewById(R.id.bild);
            viewHolder.lupe = view.findViewById(R.id.lupe);

            viewHolder.lupe.setOnClickListener(mOnLupeClickListener);

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

    public View.OnClickListener mOnLupeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int position = mLv.getPositionForView((View) v.getParent());
            Log.e("beschr.", ""+ position);
            String anfrageId = anfrageList.get(position).getmId();

            startActivityIntent =  new Intent(context, AuftragAnsicht.class);
            startActivityIntent.putExtra("anfrageId", anfrageId);
            startActivityIntent.putExtra("kategorie", kategorie);
            startActivityIntent.putExtra("anfang", anfang);
            startActivityIntent.putExtra("ende", ende);
            startActivityIntent.putExtra("radius", radius);

            context.startActivity(startActivityIntent);
        }
    };


}
