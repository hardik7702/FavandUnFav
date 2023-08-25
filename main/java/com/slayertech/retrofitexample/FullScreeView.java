package com.slayertech.retrofitexample;



import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.slayertech.retrofitexample.databasenn.MyDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FullScreeView extends AppCompatActivity {

    ImageView fullImg,iv_Fav,iv_notFav;
    TextView  fullId,fullTitle;

    MyDatabase myDatabase;
    List<Modelclass> listnew=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_scree_view);


        myDatabase = new MyDatabase(this);

        Cursor cursor = myDatabase.getData();
        cursor.moveToFirst();


        fullImg=findViewById(R.id.fullImg);
        iv_Fav=findViewById(R.id.iv_Fav);
        fullId=findViewById(R.id.fullId);
        fullTitle=findViewById(R.id.fullTitle);
        iv_notFav=findViewById(R.id.iv_notFav);


        Bundle bundle=getIntent().getExtras();
        String id=bundle.getString("id");
        String title=bundle.getString("title");
        String img=bundle.getString("img");
        String bookmark=bundle.getString("bookmark");
        int pos=bundle.getInt("pos");
        String list=bundle.getString("list");

        Picasso.get().load(img)
                .placeholder(R.drawable.ic_launcher_background)
                .fit()
                .into(fullImg);

        fullId.setText(id);
        fullTitle.setText(title);


        for (int i = 0; i < cursor.getCount(); i++) {

            int alid2=cursor.getInt(0);
            int id2 = cursor.getInt(1);
            String title2 = cursor.getString(2);
            String url2 = cursor.getString(3);
            String bookmark2 = cursor.getString(4);

            Modelclass modelFavList = new Modelclass(alid2,id2, title2,url2, bookmark2);
            listnew.add(modelFavList);
            cursor.moveToNext();

        }

//        if (listnew.size()!=0){
            if(listnew.get(pos).getBookmark().equalsIgnoreCase("1")){
                iv_Fav.setVisibility(View.VISIBLE);
                iv_notFav.setVisibility(View.GONE);

            }else if(listnew.get(pos).getBookmark().equalsIgnoreCase("0")){
                iv_Fav.setVisibility(View.GONE);
                iv_notFav.setVisibility(View.VISIBLE);


//            }
        }



        iv_Fav.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {

                myDatabase.Update_converation( "" + listnew.get( pos ).getId(), "0" );
                listnew.remove( pos );
                iv_Fav.setVisibility(View.GONE);
                iv_notFav.setVisibility(View.VISIBLE);


            }
        });
//
        iv_notFav.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {


                myDatabase.Update_converation( "" + listnew.get( pos ).getId(), "1" );
                listnew.remove( pos );
                iv_Fav.setVisibility(View.VISIBLE);
                iv_notFav.setVisibility(View.GONE);

            }
        });




    }
}