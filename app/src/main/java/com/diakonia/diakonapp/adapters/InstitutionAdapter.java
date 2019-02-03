package com.diakonia.diakonapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.diakonia.diakonapp.Nueva_donacion;
import com.diakonia.diakonapp.Perfil_Institucion;
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
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView  nombre, asistencia;
        Button btnLlamar, btnMaps, btnDonar;
        ImageView tipoAsistencia, principalImg;

        CardView cardView;

        OnCardListener onCardListener;

        public MyViewHolder(View itemView, OnCardListener onCardListener) {
            super(itemView);

            principalImg    = itemView.findViewById(R.id.imagenPrincipal);
            tipoAsistencia  = itemView.findViewById(R.id.imgTipo);
            nombre          = itemView.findViewById(R.id.textoName);
            asistencia      = itemView.findViewById(R.id.textoAsistencia);
            //btnLlamar          = itemView.findViewById(R.id.llamar);
            btnMaps       = itemView.findViewById(R.id.verMaps);
            cardView        = itemView.findViewById(R.id.cardviewInstituciones_id);
            btnDonar        = itemView.findViewById(R.id.donar);

            this.onCardListener = onCardListener;

            itemView.setOnClickListener(this);



            //PRE-LOLLIPOP VECTOR IN BUTTONS
            //Drawable callVector = AppCompatResources.getDrawable(mContext, R.drawable.ic_call_grey_24dp);
            Drawable directionVector = AppCompatResources.getDrawable(mContext, R.drawable.ic_directions_grey_24dp);
            Drawable donationVector = AppCompatResources.getDrawable(mContext, R.drawable.ic_new_donate_icon);

            //TINT RED
            //callVector.setColorFilter(mContext.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
            directionVector.setColorFilter(mContext.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);


            //btnLlamar.setCompoundDrawablesWithIntrinsicBounds( callVector,null, null, null);
            btnMaps.setCompoundDrawablesWithIntrinsicBounds  ( directionVector, null, null, null);
            btnMaps.setCompoundDrawablePadding(5);
            btnDonar.setCompoundDrawablesWithIntrinsicBounds ( donationVector, null,null, null);
            btnDonar.setCompoundDrawablePadding(5);




//            btnLlamar.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int requestCode = getAdapterPosition();
//                    Uri call = Uri.parse("tel:" + mData.get(requestCode).getTelefono());
//                    Intent callIntent = new Intent(Intent.ACTION_DIAL, call);
//
//                    if (callIntent.resolveActivity(mContext.getPackageManager()) != null)
//                    {
//                        mContext.startActivity(callIntent);
//                    }else{
//                        Toast.makeText(mContext, "NO CALLING APP INSTALLED", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });

            btnMaps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int requestCode = getAdapterPosition();
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + mData.get(requestCode).getDireccion());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(mContext.getPackageManager()) != null) {
                        mContext.startActivity(mapIntent);
                    }else{
                        Toast.makeText(mContext, "NO MAPS APP INSTALLED", Toast.LENGTH_SHORT).show();
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


