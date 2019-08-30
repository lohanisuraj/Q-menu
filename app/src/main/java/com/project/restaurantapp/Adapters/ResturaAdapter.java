package com.project.restaurantapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.project.restaurantapp.MainActivity;
import com.project.restaurantapp.Model.Restaurant;
import com.project.restaurantapp.R;
import com.project.restaurantapp.RestaurantViwer;

import java.util.ArrayList;

public class ResturaAdapter extends ArrayAdapter<Restaurant> {

    public Context context;
    public ArrayList<Restaurant> restaurants;
    public ResturaAdapter(Context context, ArrayList<Restaurant> restaurants) {
        super(context, 0);
        this.context=context;
        this.restaurants=restaurants;
    }

    public ResturaAdapter(Context context, int resource, ArrayList<Restaurant> restaurants) {
        super(context, resource, restaurants);
        this.context = context;
        this.restaurants = restaurants;
    }

    //Manage data in View
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.resturantlistviewitem,parent,false);
        final Restaurant restaurant=restaurants.get(position);
        //TextView for Name display
        TextView nameview=(TextView)convertView.findViewById(R.id.res_singlename);
        nameview.setText(restaurant.Name);

        //Button to View Data of The resturant
        Button button=(Button)convertView.findViewById(R.id.res_singlbutton);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open the selecte resturant detail
                Intent myIntent = new Intent(context, RestaurantViwer.class);
                myIntent.putExtra("id",restaurant.Key);
                context.startActivity(myIntent);
            }
        });

        return  convertView;
    }
}