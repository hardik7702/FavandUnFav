package com.slayertech.retrofitexample;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Main_Frag extends Fragment {
    RecyclerView recycler;
    String url = "https://jsonplaceholder.typicode.com/";
    View view;
    public static Adapter adapten;
    SearchView searchView;
    List<Modelclass> list;
    int getUrlalbumPos = 1;
    ProgressBar progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.main_fragment, container, false);

        list=new ArrayList<>();

        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        searchView = (SearchView) view.findViewById(R.id.searchView);
        progress = (ProgressBar) view.findViewById(R.id.progress);




        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(llm);

        adapten = new Adapter(getContext(), list);
        recycler.setHasFixedSize(false);
        recycler.setItemViewCacheSize(100000);
        recycler.setAdapter(adapten);

        loadData(getUrlalbumPos);


        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (llm.findLastCompletelyVisibleItemPosition() == list.size() - 1) {
                    Log.e("hdgydb", "onScrolled: " );
                    recycler.suppressLayout(false);
                    if(getUrlalbumPos<10){
                        progress.setVisibility(View.VISIBLE);
                        getUrlalbumPos++;
                        loadData(getUrlalbumPos);
                    }
                }
            }

        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("fbnfjn", "onQueryTextChange: " );
                adapten.getFilter().filter(newText);
                return false;

            }
        });

        return view;
    }

    private void loadData(int albunId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyAPI api = retrofit.create(MyAPI.class);

        Call<List<Modelclass>> call = api.getmodels("photos?albumId=" + albunId);

        call.enqueue(new Callback<List<Modelclass>>() {
            @Override
            public void onResponse(Call<List<Modelclass>> call, Response<List<Modelclass>> response) {
                if (response.isSuccessful()) {
//                    List<Modelclass> data=response.body();
                    Log.e("JFKJFK", "onResponse: " + response.message());
                    list.addAll(response.body());
                    adapten.notifyDataSetChanged();
                    progress.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<List<Modelclass>> call, Throwable t) {
                Log.e("JFKJFK", "Something Went Wrong:" + t.getMessage());
                Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }


}