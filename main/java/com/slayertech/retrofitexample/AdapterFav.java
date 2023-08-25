package com.slayertech.retrofitexample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.slayertech.retrofitexample.databasenn.MyDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class AdapterFav  extends RecyclerView.Adapter<AdapterFav.ViewHolder> {

    Context context;
    List<Modelclass> dataList;
    LayoutInflater inflater;
    //   boolean isFav = true;
    MyDatabase myDatabase;



    public AdapterFav(FragmentActivity dashBordActivity, List<Modelclass> dataList) {
        context = dashBordActivity;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
        myDatabase = new MyDatabase(context);
    }


    public void clearRecyclerView() {
        int size = this.dataList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                dataList.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.favlist, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.id.setText(dataList.get(position).getId()+"");
        holder.title.setText(dataList.get(position).getTitle());
        Picasso.get().load(dataList.get(position).getUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .into(holder.img);


        holder.iv_Fav.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {

                myDatabase.Update_converation( "" + dataList.get( position ).getId(), "0" );
                dataList.remove( position );
                notifyDataSetChanged();

            }
        });

    }


    @Override
    public int getItemCount() {
        return dataList.size();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img,iv_notFav,iv_Fav;
        TextView id,title;

        public ViewHolder(@NonNull View view) {
            super(view);

            img = (ImageView) view.findViewById(R.id.img);
            iv_Fav = (ImageView) view.findViewById(R.id.iv_Fav);
            iv_notFav = (ImageView) view.findViewById(R.id.iv_notFav);
            id = (TextView) view.findViewById(R.id.id);
            title = (TextView) view.findViewById(R.id.title);

        }
    }

}
