package com.project.restaurantapp.Adapters;

import android.util.Log;

import com.project.restaurantapp.Model.Ambience;

import java.util.ArrayList;


//knn algorith using eculedian formula
public class KNNCalculator {



    public static Double KnnDistance(Ambience first,Ambience Second){
        Double d=0d;
        Log.d("initres", "("+first.toString()+"),("+Second.toString()+")");

        //grading the price parameter
        Double p1=calculateKnnPointPrice(first);
        Double p2=calculateKnnPointPrice(Second);
        //grading the music parameter
        Double m1=calculateKnnPointMusic(first);
        Double m2=calculateKnnPointMusic(Second);
        //grading the type parameter
        Double t1=calculateKnnPointType(first);
        Double t2=calculateKnnPointType(Second);



        d=Math.sqrt(Math.pow((p1-p2),2)+Math.pow((m1-m2),2)+Math.pow((t1-t2),2));
        return d;
    }



    //grading point for music in ambience
    public static Double calculateKnnPointMusic(Ambience amb){


        if(amb.music.toLowerCase().equals("yes")){
            return 2d;

        }else if(amb.music.toLowerCase().equals("no") ){
            return 1d;

        }
        return 0d;

    }

    //Fast food-1
    //Fast casual-2
    //Casual dining-3
    //Family-4
    public  static  Double calculateKnnPointType(Ambience amb){
        if(amb.Type.toLowerCase().equals("family") ){
            return 4d;

        }else if(amb.Type.toLowerCase().equals("casual dining") ){
            return 3d;

        }
        else if(amb.Type.toLowerCase().equals("fast casual") ){
            return 2d;
        }
        else if(amb.Type.toLowerCase().equals("fast food") ){
            return 1d;
        }
        return 0d;
    }
    //grading point for price in ambience
    public static Double calculateKnnPointPrice(Ambience amb){


        Log.d("Knnprice", "calculateKnnPointPrice: "+ amb.price);

        if(amb.price.toLowerCase().equals("high") ){
            return 3d;

        }else if(amb.price.toLowerCase().equals("medium") ){
            return 2d;

        }
        else if(amb.price.toLowerCase().equals("low") ){
            return 1d;


        }



        return 0d;
    }




}
