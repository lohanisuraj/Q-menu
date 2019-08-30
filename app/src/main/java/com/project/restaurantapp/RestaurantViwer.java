package com.project.restaurantapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.restaurantapp.Adapters.ResturaAdapter;
import com.project.restaurantapp.Model.Ambience;
import com.project.restaurantapp.Model.Item;
import com.project.restaurantapp.Model.Menus;
import com.project.restaurantapp.Model.Restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RestaurantViwer extends AppCompatActivity {

    //listvieew for menu
    ListView listview_dinner;
    ListView listview_breakfast;
    ListView listview_lunch;
    ListView listview_harddrinks;
    ListView listview_softdrinks;
    ListView listview_fastfood;

    //Ambience filter for Knn
    Ambience ambience;

    //id of current restuarant
    public String resturant_id="1";
    //location of the restuarant
    public double lat;
    public double lon;
    //Detail show
    TextView res_name;
    TextView res_music;
    TextView res_contact;
    TextView res_price;


    DatabaseReference databaseres;
    //for listing all resturants
    ArrayList<Restaurant> resturants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_viwer);
        //getting id of the resturant
        resturant_id=getIntent().getExtras().getString("id","1");
        try {
            //init the restaurant list
            resturants=new ArrayList<>();

            //restaurtant name
            res_name=(TextView)findViewById(R.id.res_name);
            //restaurant  music
            res_music=(TextView)findViewById(R.id.res_music);
            //restaurant  pricing
            res_price=(TextView)findViewById(R.id.res_price);
            //restaurant  music
            res_contact=(TextView)findViewById(R.id.res_contact);




            //preparing listview to load data
            //listview for breakfast items
            listview_breakfast=(ListView)findViewById(R.id.res_menu_breakfast);
            //listview for dinner items
            listview_dinner=(ListView)findViewById(R.id.res_menu_dinner);
            //listview for lunch items
            listview_lunch=(ListView)findViewById(R.id.res_menu_lunch);
            //listview for harddrinks items
            listview_harddrinks=(ListView)findViewById(R.id.res_menu_harddrinks);
            //listview for softdrinks items
            listview_softdrinks=(ListView)findViewById(R.id.res_menu_softdrinks);
            //listview for fastfood items
            listview_fastfood=(ListView)findViewById(R.id.res_menu_fastfood);






            //getting all the branch of the resturants
            databaseres= FirebaseDatabase.getInstance().getReference("resturant");

            //fetching data from firebase
            databaseres.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //selecting currently selected branch
                    DataSnapshot selectedresturant=dataSnapshot.child(resturant_id);
                    //extracting non list datas
                    String name=selectedresturant.child("name").getValue().toString();
                    String contact=selectedresturant.child("contact").getValue().toString();
                    String price=selectedresturant.child("ambience").child("price").getValue().toString();
                    String music=selectedresturant.child("ambience").child("music").getValue().toString();
                    //showing data in textview
                    res_name.setText(name);
                    res_contact.setText(contact);
                    //get ambience
                    ambience=new Ambience();
                    ambience.music=selectedresturant.child("ambience").child("music").getValue().toString();
                    ambience.price=selectedresturant.child("ambience").child("price").getValue().toString();
                    ambience.Type= selectedresturant.child("type").getValue().toString();
                    res_price.setText(price);
                    res_music.setText(music);
                    //get location of selected resturant
                    lat=selectedresturant.child("location").child("latitude").getValue(double.class);
                    lon=selectedresturant.child("location").child("longitude").getValue(double.class);

                    //breakfast
                    Menus b=new Menus();
                    //listing all breakfast items
                    for (DataSnapshot bsnap:selectedresturant.child("menu").child("breakfast").getChildren()) {
                        b.item.add(bsnap.getValue(Item.class));
                        //price=asd
                        //name=asdf
                    }

                    ArrayAdapter breakfastadapter = new ArrayAdapter<String>(RestaurantViwer.this,
                            android.R.layout.simple_list_item_1, b.items());
                    listview_breakfast.setAdapter(breakfastadapter);

                    //end breakfast

                    //dinner
                    Menus dinner=new Menus();
                    //listing all dinner items
                    for (DataSnapshot dsnap:selectedresturant.child("menu").child("dinner").getChildren()) {
                        dinner.item.add(dsnap.getValue(Item.class));
                    }

                    ArrayAdapter dinneradapter = new ArrayAdapter<String>(RestaurantViwer.this,
                            android.R.layout.simple_list_item_1, dinner.items());
                    listview_dinner.setAdapter(dinneradapter);

                    //end dinner

                    //harddrink
                    Menus hardrink=new Menus();
                    //listing all harddrink items
                    for (DataSnapshot hdsnap:selectedresturant.child("menu").child("drink").child("hard").getChildren()) {
                        hardrink.item.add(hdsnap.getValue(Item.class));
                    }

                    ArrayAdapter harddrinkadapter = new ArrayAdapter<String>(RestaurantViwer.this,
                            android.R.layout.simple_list_item_1, hardrink.items());
                    listview_harddrinks.setAdapter(harddrinkadapter);

                    //end harddrink

                    //softdrink
                    Menus softdrink=new Menus();
                    //listing all softdrink items
                    for (DataSnapshot ssnap:selectedresturant.child("menu").child("drink").child("soft").getChildren()) {
                        softdrink.item.add(ssnap.getValue(Item.class));
                    }

                    ArrayAdapter softdrinkadapter = new ArrayAdapter<String>(RestaurantViwer.this,
                            android.R.layout.simple_list_item_1, softdrink.items());
                    listview_softdrinks.setAdapter(softdrinkadapter);

                    //end softdrink


                    //fastfood
                    Menus fastfood=new Menus();
                    //listing all softdrink items
                    for (DataSnapshot ffsnap:selectedresturant.child("menu").child("fastfood").getChildren()) {
                        fastfood.item.add(ffsnap.getValue(Item.class));
                    }

                    ArrayAdapter fastfoodadapter = new ArrayAdapter<String>(RestaurantViwer.this,
                            android.R.layout.simple_list_item_1, fastfood.items());
                    listview_fastfood.setAdapter(fastfoodadapter);

                    //end fastfood

                    //Lunch
                    Menus Lunch=new Menus();
                    //listing all softdrink items
                    for(DataSnapshot ffsnap:selectedresturant.child("menu").child("lunch").getChildren()) {
                        Lunch.item.add(ffsnap.getValue(Item.class));
                    }

                    ArrayAdapter Lunchadapter = new ArrayAdapter<String>(RestaurantViwer.this,
                            android.R.layout.simple_list_item_1, Lunch.items());
                    listview_lunch.setAdapter(Lunchadapter);

                    //end lunch
//
//




                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }catch (Exception ex){
            MainActivity.txt.setText(ex.getMessage());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.filtermenu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.filter:
                Intent myIntent = new Intent(this, ResturantList.class);
                myIntent.putExtra("id",resturant_id);
                this.startActivity(myIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
