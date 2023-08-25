package com.slayertech.retrofitexample;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.slayertech.retrofitexample.databasenn.MyDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Fav_Frag extends Fragment {
    View view;
    public static MyDatabase myDatabase;
   public static RecyclerView recyclerView;
  public static List<Modelclass> dataList = new ArrayList<>();
   public static AdapterFav favAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fav_fragment, container, false);



        recyclerView = view.findViewById(R.id.recycler);

        myDatabase = new MyDatabase(getActivity());
        favAdapter = new AdapterFav(getActivity(), dataList);


        refreshData();

        return view;
    }

    public static void refreshData() {


        favAdapter.clearRecyclerView();

        Cursor cursor = myDatabase.getData();
        cursor.moveToFirst();



        for (int i = 0; i < cursor.getCount(); i++) {

            int alid=cursor.getInt(0);
            int id = cursor.getInt(1);
            String title = cursor.getString(2);
            String url = cursor.getString(3);
            String bookmark = cursor.getString(4);

            Modelclass modelFavList = new Modelclass(alid,id, title,url, bookmark);

            if (modelFavList.getBookmark().equalsIgnoreCase("1")){
                Log.e("dbjhdik", "if: "+i );
                dataList.add(modelFavList);
            }else {
                Log.e("dbjhdik", "else: "+i );
            }

            cursor.moveToNext();

        }


        RecyclerView.LayoutManager manager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(favAdapter);



    }



}