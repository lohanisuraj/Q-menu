package com.project.restaurantapp.Model;

import android.location.Location;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.project.restaurantapp.Adapters.DistanceCalculator;
import com.project.restaurantapp.Adapters.KNNCalculator;


public class Restaurant {
    public String Name;
    public String Key;
    //latitude and longitude
    public double lat;
    public double lon;
    //physical distance between two
    public double distance;

    //Distance for Knn Clustering
    public Double KnnDistance;

    //Ambience filter for Knn
    public Ambience ambience;


    public double calculate(String mm,String pp,String tt){
        Ambience a=new Ambience();
        a.music=mm;
        a.price=pp;
        a.Type=tt;

        double m=0;
        double p=0;
        double t=0;
        double d=0;
        if(!(a.price.toLowerCase().equals("select"))){
            p= KNNCalculator.calculateKnnPointPrice(a)-KNNCalculator.calculateKnnPointPrice(this.ambience);

        }
        if(!(a.Type.toLowerCase().equals("select"))){
            t=KNNCalculator.calculateKnnPointType(a)-KNNCalculator.calculateKnnPointType(this.ambience);

        }
        if(!(a.music.toLowerCase().equals("select"))){
            m= KNNCalculator.calculateKnnPointMusic(a)-KNNCalculator.calculateKnnPointMusic(this.ambience);

        }



       return  d = Math.sqrt(Math.pow((p),2)+Math.pow((m),2)+Math.pow((t),2));
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "Name='" + Name + '\'' +
                ", Key='" + Key + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", distance=" + distance +
                ", Knndistance=" + KnnDistance +
                '}';
    }

    public Restaurant(String name, double lat, double lon) {
        Name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public Restaurant(DataSnapshot branch,double _lat,double _lon,Ambience oldambience){
        //getting name and location
        this.Name=branch.child("name").getValue().toString();
        this.lat=  branch.child("location").child("latitude").getValue(double.class);
        this.lon=  branch.child("location").child("longitude").getValue(double.class);
        this.ambience=new Ambience();
        this.ambience.music=branch.child("ambience").child("music").getValue().toString();
        this.ambience.price=branch.child("ambience").child("price").getValue().toString();
        this.ambience.Type=branch.child("type").getValue().toString();
        Log.d("initres123456", "amb: " +ambience.toString());

        //calculating distance


        this.distance= DistanceCalculator.getDistance(_lat,_lon,this.lat,this.lon);
        this.ambience.Type=branch.child("type").getValue().toString();


        //Calculate KnnDistance
        //this.KnnDistance= KNNCalculator.KnnDistance(oldambience,this.ambience);

        Log.d("initres", "Restaurant: " +Double.toString(this.distance)+","+Double.toString(this.KnnDistance));


        this.Key=branch.getKey();
    }

    public Restaurant(DataSnapshot branch){
        //getting name and location
        this.Name=branch.child("name").getValue().toString();
        this.lat=  branch.child("location").child("latitude").getValue(double.class);
        this.lon=  branch.child("location").child("longitude").getValue(double.class);
        this.ambience=new Ambience();
        this.ambience.music=branch.child("ambience").child("music").getValue().toString();
        this.ambience.price=branch.child("ambience").child("price").getValue().toString();
        this.ambience.Type=branch.child("type").getValue().toString();
        this.Key=branch.getKey();
    }



    public void setDistance(DataSnapshot branch,double lat,double lon){
        this.Name=branch.child("name").toString();
        this.lat=  branch.child("location").getValue(double.class);
        this.lon=  branch.child("location").getValue(double.class);
    }



}
