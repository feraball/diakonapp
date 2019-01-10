package com.diakonia.diakonapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.introtoandroid.samplematerial.R;

import java.util.List;

public class AdaptadorInstituciones extends RecyclerView.Adapter<AdaptadorInstituciones.MyViewHolder>{

    public Context mContext;
    private static List<ClaseInstituciones> mData;




    public AdaptadorInstituciones(Context mContext, List<ClaseInstituciones> mData) {
        this.mContext = mContext;
        this.mData = mData;


    }



    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.instituciones_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //nombre, asistencia, telefono, longitud, latitud, cantidad, direccion, horarioDeAtencion,correo

        Log.d("prueba","sdffsfsdooi");
        holder.nombre.setText(mData.get(position).getName());
        holder.asistencia.setText(mData.get(position).getAsis());
        Glide
                .with(mContext)
                .load(mData.get(position).getUrlFoto())
                .into(holder.principalImg);

      //  Log.d("pruebaUrl", mData.get(position).getUrlFoto());

        switch (mData.get(position).getAsis()) {

           case"COMEDOR":



               holder.tipoAsistencia.setImageResource(R.drawable.comedor);

               break;
       }





        Log.d("prueba", mData.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;
        TextView asistencia;
        Button llamar;
        Button verEnMaps;
        ImageView tipoAsistencia;
        ImageView principalImg;

        public MyViewHolder(View itemView) {


            super(itemView);


            principalImg = (ImageView)itemView.findViewById(R.id.imagenPrincipal);
            tipoAsistencia=(ImageView)itemView.findViewById(R.id.imgTipo);
            nombre = (TextView) itemView.findViewById(R.id.textoName);
            asistencia=(TextView) itemView.findViewById(R.id.textoAsistencia);
            llamar=(Button) itemView.findViewById(R.id.llamar);
            verEnMaps=(Button) itemView.findViewById(R.id.verMaps);

            llamar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int requestCode = getAdapterPosition();
                    Uri call = Uri.parse("tel:"+mData.get(requestCode).getTelefono());
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, call);
                    mContext.startActivity(callIntent);


                }
            });

            verEnMaps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int requestCode = getAdapterPosition();
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q="+mData.get(requestCode).getDireccion());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(mContext.getPackageManager()) != null) {
                        mContext.startActivity(mapIntent);
                    }

                }


            });





        }
    }
}


