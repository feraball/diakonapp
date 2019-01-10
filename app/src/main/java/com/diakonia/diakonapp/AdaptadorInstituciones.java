package com.diakonia.diakonapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //nombre, asistencia, telefono, longitud, latitud, cantidad, direccion, horarioDeAtencion,correo

        Log.d("prueba","sdffsfsdooi");
        holder.nombre.setText(mData.get(position).getName());
        holder.asistencia.setText(mData.get(position).getAsis());
        Glide
                .with(mContext)
                .load(mData.get(position).getUrlFoto())
                .into(holder.principalImg);

      //   load tipo asistencia ImageView

        switch (mData.get(position).getAsis()) {

           case"COMEDOR":



               holder.tipoAsistencia.setImageResource(R.drawable.comedor);

               break;
       }

        //set listener to open more information about selected card
       holder.cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent intent = new Intent(mContext, Institucion_Activity.class);
               intent.putExtra("UrlFoto", mData.get(position).getUrlFoto());
               intent.putExtra("tipoAsistencia", mData.get(position).getAsis());
               intent.putExtra("nombre", mData.get(position).getName());
               intent.putExtra("direccion", mData.get(position).getDireccion());
               intent.putExtra("telefono", mData.get(position).getTelefono());
               intent.putExtra("cantidadPersonas", mData.get(position).getCantidadPersonasAtendidas());
               intent.putExtra("horario", mData.get(position).getHorarioAtencion());
               intent.putExtra("correo", mData.get(position).getCorreo());


               mContext.startActivity(intent);


           }
       });




















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

        CardView cardView;

        public MyViewHolder(View itemView) {


            super(itemView);


            principalImg = (ImageView)itemView.findViewById(R.id.imagenPrincipal);
            tipoAsistencia=(ImageView)itemView.findViewById(R.id.imgTipo);
            nombre = (TextView) itemView.findViewById(R.id.textoName);
            asistencia=(TextView) itemView.findViewById(R.id.textoAsistencia);
            llamar=(Button) itemView.findViewById(R.id.llamar);
            verEnMaps=(Button) itemView.findViewById(R.id.verMaps);
            cardView = (CardView) itemView.findViewById(R.id.cardviewInstituciones_id);


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


