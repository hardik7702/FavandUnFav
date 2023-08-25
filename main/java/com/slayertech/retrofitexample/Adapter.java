package com.slayertech.retrofitexample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.slayertech.retrofitexample.databasenn.MyDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    List<Modelclass> list;
    List<Modelclass> listnew=new ArrayList<>();
    private List<Modelclass> exampleListFull;

    MyDatabase myDatabase;

    public Adapter(Context context, List<Modelclass> list) {
        this.list = list;
        this.context = context;
        exampleListFull = new ArrayList<>(list);
        MyDatabase myDatabase = new MyDatabase( context );
        this.myDatabase = myDatabase;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.alid.setText(list.get(position).getAlbumId()+"");
        holder.id.setText(list.get(position).getId() + "");
        holder.title.setText(list.get(position).getTitle());

        Picasso.get().load(list.get(position).getUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .into(holder.img);

        myDatabase.insertData(holder.alid.getText().toString(),Integer.parseInt(holder.id.getText().toString()), holder.title.getText().toString(),list.get(position).getUrl(),  "0" );

        Cursor cursor = myDatabase.getData();
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            int alid=cursor.getInt(0);
            int id = cursor.getInt(1);
            String title = cursor.getString(2);
            String url = cursor.getString(3);
            String bookmark = cursor.getString(4);

            Modelclass modelFavList = new Modelclass(alid,id, title,url, bookmark);

            listnew.add(modelFavList);

            cursor.moveToNext();

        }


//        if (listnew.contains(list.get(position).getBookmark().equalsIgnoreCase("1")))
//
//        if (list.contains(list.get(holder.getAdapterPosition()).getBookmark())) {
//            holder.iv_Fav.setVisibility(View.VISIBLE);
//            holder.iv_notFav.setVisibility(View.GONE);
//        } else if(listnew.contains(list.get(holder.getAdapterPosition()).getBookmark())) {
//            holder.iv_Fav.setVisibility(View.GONE);
//            holder.iv_notFav.setVisibility(View.VISIBLE);
//
//        }

//        if (listnew.size()!=0){
            if(listnew.get(holder.getAdapterPosition()).getBookmark().equalsIgnoreCase("1")){
                holder.iv_Fav.setVisibility(View.VISIBLE);
                holder.iv_notFav.setVisibility(View.GONE);

            }else if(listnew.get(holder.getAdapterPosition()).getBookmark().equalsIgnoreCase("0")){
                holder.iv_Fav.setVisibility(View.GONE);
                holder.iv_notFav.setVisibility(View.VISIBLE);

            }
//        }
//        else {
//            Log.e("bhdfghdy", "onBindViewHolder else: " );
//        }



        holder.iv_Fav.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                holder.iv_Fav.setVisibility(View.GONE);
                holder.iv_notFav.setVisibility(View.VISIBLE);

                    myDatabase.Update_converation( "" + list.get( position ).getId(), "0" );


            }
        });
//
        holder.iv_notFav.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                holder.iv_Fav.setVisibility(View.VISIBLE);
                holder.iv_notFav.setVisibility(View.GONE);

                    myDatabase.Update_converation( "" + list.get( position ).getId(), "1" );

            }
        });

        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putString("id", String.valueOf(list.get(position).getId()));
                bundle.putString("title",list.get(position).getTitle());
                bundle.putString("img",list.get(position).getUrl());
                bundle.putString("bookmark",list.get(position).getBookmark());
                bundle.putInt("pos",position);
                bundle.putString("list", String.valueOf(list.get(position)));
                Intent intent=new Intent(context,FullScreeView.class);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });

    }



    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Modelclass> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
                Log.e("jhfbnjhfn", "performFiltering   ifffff: " );
            }
            else {
                Log.e("jhfbnjhfn", "performFiltering   elsssz: " );
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Modelclass item : exampleListFull) {
                    if (item.getTitle().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
         ImageView img, iv_notFav, iv_Fav;
        TextView id, title,alid;

        public ViewHolder(@NonNull View view) {
            super(view);

            img = (ImageView) view.findViewById(R.id.img);
            iv_Fav = (ImageView) view.findViewById(R.id.iv_Fav);
            iv_notFav = (ImageView) view.findViewById(R.id.iv_notFav);
            id = (TextView) view.findViewById(R.id.id);
            title = (TextView) view.findViewById(R.id.title);
            alid = (TextView) view.findViewById(R.id.alid);

        }

    }
}
