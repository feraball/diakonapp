package com.diakonia.diakonapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.diakonia.diakonapp.Nueva_donacion;
import com.diakonia.diakonapp.R;
import com.diakonia.diakonapp.models.Institution;


import java.util.List;

public class InstitutionAdapter extends RecyclerView.Adapter<InstitutionAdapter.MyViewHolder>{


    public Context mContext;
    private static List<Institution> mData;
    private OnCardListener mOnCardListener;


    //CONSTRUCTOR
    public InstitutionAdapter(Context mContext, List<Institution> mData, OnCardListener onCardListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mOnCardListener = onCardListener;
    }

    public InstitutionAdapter() {

    }



    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_institutions_item, parent, false);

        return new MyViewHolder(view, mOnCardListener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //nombre, asistencia, telefono, longitud, latitud, cantidad, direccion, horarioDeAtencion,correo

        holder.nombre.setText(mData.get(position).getNombre());
        holder.asistencia.setText(mData.get(position).getAsistencia());

        Glide.with(mContext)
             .load(mData.get(position).getUrlFoto())
             .into(holder.principalImg);

      //   load tipo asistencia ImageView
        switch (mData.get(position).getAsistencia()) {
           case"COMEDOR":
               holder.tipoAsistencia.setImageResource(R.drawable.ic_restaurant_orange);
               break;
       }

        //set listener to open more information about selected card
//       holder.cardView.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//
//               Intent intent = new Intent(view.getContext(), Perfil_Institucion.class);
//               intent.putExtra("UrlFoto", mData.get(position).getUrlFoto());
//               intent.putExtra("tipoAsistencia", mData.get(position).getAsistencia());
//               intent.putExtra("nombre", mData.get(position).getNombre());
//               intent.putExtra("direccion", mData.get(position).getDireccion());
//               intent.putExtra("telefono", mData.get(position).getTelefono());
//               intent.putExtra("cantidadPersonas", mData.get(position).getCantidadPersonasAtendidas());
//               intent.putExtra("horario", mData.get(position).getHorarioAtencion());
//               intent.putExtra("correo", mData.get(position).getCorreo());
//
//
//               view.getContext().startActivity(intent);
//           }
//       });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView  nombre, asistencia;
        ImageButton llamar, verEnMaps, btnDonar;
        ImageView tipoAsistencia, principalImg;

        CardView cardView;

        OnCardListener onCardListener;

        public MyViewHolder(View itemView, OnCardListener onCardListener) {
            super(itemView);



            principalImg    = itemView.findViewById(R.id.imagenPrincipal);
            tipoAsistencia  = itemView.findViewById(R.id.imgTipo);
            nombre          = itemView.findViewById(R.id.textoName);
            asistencia      = itemView.findViewById(R.id.textoAsistencia);
            llamar          = itemView.findViewById(R.id.llamar);
            verEnMaps       = itemView.findViewById(R.id.verMaps);
            cardView        = itemView.findViewById(R.id.cardviewInstituciones_id);
            btnDonar        = itemView.findViewById(R.id.donar);



            this.onCardListener = onCardListener;

            itemView.setOnClickListener(this);



            llamar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int requestCode = getAdapterPosition();
                    Uri call = Uri.parse("tel:" + mData.get(requestCode).getTelefono());
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, call);
                    mContext.startActivity(callIntent);
                }
            });

            verEnMaps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int requestCode = getAdapterPosition();
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + mData.get(requestCode).getDireccion());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(mContext.getPackageManager()) != null) {
                        mContext.startActivity(mapIntent);
                    }
                }
            });


            btnDonar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int requestCode = getAdapterPosition();

                    String beneficiario = mData.get(requestCode).getNombre();
                    Intent donarIntent = new Intent(mContext,Nueva_donacion.class);

                    donarIntent.putExtra("beneficiario", beneficiario);



                    mContext.startActivity(donarIntent);


                }
            });


        }

        @Override
        public void onClick(View v) {
            onCardListener.onCardClick(getAdapterPosition());
        }
    }

    public interface OnCardListener{
        void onCardClick(int position);
    }

}


