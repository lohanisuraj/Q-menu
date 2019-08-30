package com.project.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.restaurantapp.Adapters.ResturaAdapter;
import com.project.restaurantapp.Model.Restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResturantList extends AppCompatActivity {
    ListView listview_others;
    DatabaseReference databaseres;
    Spinner music,type,price;
    String[] mus = {"select","Yes","No"};
    String[] typ  = {"select","Fast Casual" , "Fast Food","casual dining","family"};
    String[] pric = {"select","Low" , "Medium" , "High"};
    ArrayList<Restaurant> finallist;
    Button btn_filter;

    //for listing all resturants
    ArrayList<Restaurant> resturants;
    //id of current restuarant
    public String resturant_id="1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_list);


        //init list
        finallist=new ArrayList<Restaurant>();
        //getting spinner

        music =(Spinner)findViewById(R.id.MusicSpinner);


//Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter MUSIC = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mus);
        MUSIC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        music.setAdapter(MUSIC);


        type =(Spinner)findViewById(R.id.TypeSpinner);



//Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter TYPE = new ArrayAdapter(this,android.R.layout.simple_spinner_item,typ);
        TYPE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        type.setAdapter(TYPE);

        price =(Spinner)findViewById(R.id.PriceSpinner);

//Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter PRICE = new ArrayAdapter(this,android.R.layout.simple_spinner_item,pric);
        PRICE.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner


        price.setAdapter(PRICE);


        //filter Button

        btn_filter=(Button)findViewById(R.id.btn_filter);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter();
            }
        });

        //getting id of the resturant
        resturant_id=getIntent().getExtras().getString("id","1");
        //listview for nearest restaurants
        listview_others=(ListView)findViewById(R.id.res_others);


        //getting all the branch of the resturants
        databaseres= FirebaseDatabase.getInstance().getReference("resturant");
        //get list of all restuarant for data comparision

        databaseres.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //initializing resturant list
                resturants=new ArrayList<Restaurant>();

                Restaurant r = new Restaurant(dataSnapshot.child(resturant_id));

                for (DataSnapshot postdata: dataSnapshot.getChildren()) {
                    if(r.Key.compareTo(postdata.getKey())!=0){
                        //declaring new resturant using the firebase data
                        Restaurant res=new Restaurant(postdata,r.lat,r.lon,r.ambience);

                        //add resturant int list
                        resturants.add(res);
                    }


                }

                //sorting resturants according to geo co-ordinates
                Collections.sort(resturants, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant r1, Restaurant r2) {
                        return Double.compare( r1.distance,r2.distance);
                    }
                });

              /*  //sorting resturants according to geo Knn Cluster (nearest first)
                Collections.sort(resturants, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant r1, Restaurant r2) {
                        return Double.compare( r1.KnnDistance,r2.KnnDistance);
                    }
                });
*/


                //select top 5 or less if there are less than 5
                int i=5;
                if(resturants.size()<5){
                    i=resturants.size();
                }


                for (int j=0;j<i;j++){
                    finallist.add(resturants.get(j));
                }



                ArrayAdapter resturantdapter = new ResturaAdapter(ResturantList.this,R.layout.resturantlistviewitem,finallist);
                listview_others.setAdapter(resturantdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }




    public  void filter(){
        ArrayList<Restaurant> nlist=new ArrayList<Restaurant>();
        for (Restaurant r:finallist) {

            double d=r.calculate(music.getSelectedItem().toString(),price.getSelectedItem().toString(),type.getSelectedItem().toString());

            Log.d("filter", "filter: "+Double.toString(d));

            if(r.calculate(music.getSelectedItem().toString(),price.getSelectedItem().toString(),type.getSelectedItem().toString())==0){
                nlist.add(r);
            }
        }
        ArrayAdapter resturantdapter = new ResturaAdapter(ResturantList.this,R.layout.resturantlistviewitem,nlist);
        listview_others.setAdapter(resturantdapter);
    }
}
