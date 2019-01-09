package com.introtoandroid.samplematerial;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;


public class ReciclerAdapter extends RecyclerView.Adapter<ReciclerAdapter.ViewHolder> {
    private static final String DEBUG_TAG = "ReciclerAdapter";

    public Context context;
    public ArrayList<Instituciones> cardsList;

    public ReciclerAdapter(Context context, ArrayList<Instituciones> cardsList) {
        this.context = context;
        this.cardsList = cardsList;
    }

    public  String phone="";






    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        String name = cardsList.get(position).getName();
        int color = cardsList.get(position).getColorResource();
        phone = cardsList.get(position).getTelefono();
        TextView initial = viewHolder.initial;
        TextView nameTextView = viewHolder.name;
        ImageButton callB = viewHolder.callButton;



        nameTextView.setText(name);
        initial.setBackgroundColor(color);
        initial.setText(Character.toString(name.charAt(0)));

    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder viewHolder) {
        super.onViewDetachedFromWindow(viewHolder);
        viewHolder.itemView.clearAnimation();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewAttachedToWindow(ViewHolder viewHolder) {
        super.onViewAttachedToWindow(viewHolder);
        animateCircularReveal(viewHolder.itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateCircularReveal(View view) {
        int centerX = 0;
        int centerY = 0;
        int startRadius = 0;
        int endRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animation = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        view.setVisibility(View.VISIBLE);
        animation.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animateCircularDelete(final View view, final int list_position) {
        int centerX = view.getWidth();
        int centerY = view.getHeight();
        int startRadius = view.getWidth();
        int endRadius = 0;
        Animator animation = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);

        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                Log.d(DEBUG_TAG, "ReciclerAdapter onAnimationEnd for Edit adapter position " + list_position);
                Log.d(DEBUG_TAG, "ReciclerAdapter onAnimationEnd for Edit cardId " + getItemId(list_position));

                view.setVisibility(View.INVISIBLE);
                cardsList.remove(list_position);
                notifyItemRemoved(list_position);
            }
        });
        animation.start();
    }

    public void addCard(String name, int color) {
        Instituciones instituciones = new Instituciones();
        instituciones.setName(name);
        instituciones.setColorResource(color);
        instituciones.setId(getItemCount());
        cardsList.add(instituciones);
        ((ListaInstituciones) context).doSmoothScroll(getItemCount());
        notifyItemInserted(getItemCount());
    }

    public void updateCard(String name, int list_position) {
        cardsList.get(list_position).setName(name);
        Log.d(DEBUG_TAG, "list_position is " + list_position);
        notifyItemChanged(list_position);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void deleteCard(View view, int list_position) {
        animateCircularDelete(view, list_position);
    }

    @Override
    public int getItemCount() {
        if (cardsList.isEmpty()) {
            return 0;
        } else {
            return cardsList.size();
        }
    }

    @Override
    public long getItemId(int position) {
        return cardsList.get(position).getId();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(viewGroup.getContext());
        View v = li.inflate(R.layout.card_view_holder, viewGroup, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView initial;
        private TextView name;
        private Button deleteButton;
        private ImageButton callButton;

        private ImageButton locationButton;

        public ViewHolder(View v) {
            super(v);
            initial = (TextView) v.findViewById(R.id.initial);
            name = (TextView) v.findViewById(R.id.name);
            callButton = (ImageButton) v.findViewById(R.id.call);
            locationButton = (ImageButton) v.findViewById(R.id.locate);



//            deleteButton = (Button) v.findViewById(R.id.delete_button);
//
            callButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onClick(View v) {
                    int requestCode = getAdapterPosition();
                    String phone = cardsList.get(requestCode).getTelefono();
                    Log.d("a", phone + "funciona");
                    Intent callIntent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));
                    context.startActivity(callIntent);
                }
            });

            locationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //context.getPackageManager();
//geo:0,0?q=my+street+address
                    int requestCode = getAdapterPosition();
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q="+cardsList.get(requestCode).getDireccion());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(mapIntent);
                    }



                }
            });


//            deleteButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    animateCircularDelete(itemView, getAdapterPosition());
//                }
//            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("RestrictedApi")
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    Pair<View, String> p1 = Pair.create((View) initial, ListaInstituciones.TRANSITION_INITIAL);
                    Pair<View, String> p2 = Pair.create((View) name, ListaInstituciones.TRANSITION_NAME);
                    Pair<View, String> p3 = Pair.create((View) deleteButton, ListaInstituciones.TRANSITION_DELETE_BUTTON);

                    ActivityOptionsCompat options;
                    Activity act = (AppCompatActivity) context;
                    options = ActivityOptionsCompat.makeSceneTransitionAnimation(act, p1, p2, p3);

                    int requestCode = getAdapterPosition();
                    String name = cardsList.get(requestCode).getName();
                    int color = cardsList.get(requestCode).getColorResource();

                    Log.d(DEBUG_TAG, "ReciclerAdapter itemView listener for Edit adapter position " + requestCode);

                    Intent transitionIntent = new Intent(context, TransitionEditActivity.class);
                    transitionIntent.putExtra(ListaInstituciones.EXTRA_NAME, name);
                    transitionIntent.putExtra(ListaInstituciones.EXTRA_INITIAL, Character.toString(name.charAt(0)));
                    transitionIntent.putExtra(ListaInstituciones.EXTRA_COLOR, color);
                    transitionIntent.putExtra(ListaInstituciones.EXTRA_UPDATE, false);
                    transitionIntent.putExtra(ListaInstituciones.EXTRA_DELETE, false);
                    ((AppCompatActivity) context).startActivityForResult(transitionIntent, requestCode, options.toBundle());
                }
            });
        }
    }
}
