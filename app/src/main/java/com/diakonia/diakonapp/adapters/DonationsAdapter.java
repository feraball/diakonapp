package com.diakonia.diakonapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.diakonia.diakonapp.R;
import com.diakonia.diakonapp.models.Donacion;

import java.util.List;

public class DonationsAdapter extends RecyclerView.Adapter<DonationsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Donacion> mData;
    private OnCardListener mOnCardListener;


    public DonationsAdapter(Context mContext, List<Donacion> mData, OnCardListener onCardListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mOnCardListener=onCardListener;
    }

    public DonationsAdapter(){

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cardview_donation_history_item, viewGroup, false);

        return new MyViewHolder(view, mOnCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.producto.setText(mData.get(position).getProducto());
        holder.beneficiario.setText(mData.get(position).getBeneficiario());
        holder.fecha.setText(mData.get(position).getFechaDonacion());
        holder.puntos.setText(mData.get(position).getPuntos());
        holder.fotoDonacion.setImageBitmap(StringToBitMap(mData.get(position).getFoto()));




    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView producto,beneficiario, fecha, puntos;
        ImageView fotoDonacion;
        OnCardListener onCardListener;



        public MyViewHolder(View itemView, OnCardListener onCardListener){
            super(itemView);

            producto = (TextView)itemView.findViewById(R.id.textView_producto);
            beneficiario = (TextView)itemView.findViewById(R.id.textView_beneficiario);
            fecha = (TextView)itemView.findViewById(R.id.textView_fechaDonacion);
            puntos = (TextView)itemView.findViewById(R.id.textView_puntosDonacion);
            fotoDonacion=(ImageView)itemView.findViewById(R.id.imageView_foto_donacion);
            this.onCardListener = onCardListener;
            itemView.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {
            onCardListener.onCardClick(getAdapterPosition());
        }
    }


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }



    public interface OnCardListener{
        void onCardClick(int position);
    }


}



