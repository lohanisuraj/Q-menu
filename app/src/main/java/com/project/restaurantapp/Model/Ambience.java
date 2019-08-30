package com.project.restaurantapp.Model;

import com.google.firebase.database.DataSnapshot;
import com.project.restaurantapp.Adapters.KNNCalculator;

public class Ambience {
    public String music;
    public String price;
    public String Type;






    @Override
    public String toString() {
        return "Ambience{" +
                "music='" + music + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public Ambience() {

    }

    public Ambience(DataSnapshot dataSnapshot){

    }

    public Ambience(String music, String price,String Type) {
        this.music = music;
        this.price = price;
        this.Type=Type;

    }


    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
