package com.project.restaurantapp.Model;

import java.util.ArrayList;
import java.util.List;

public class Menus {

    public List<Item> item;

    public Menus() {
        item=new ArrayList<>();
    }

    public List<String> items(){
        List<String> items=new ArrayList<String>();
        for ( Item i:item){
            items.add(i.toString());
        }
        return items;
    }

    @Override
    public String toString() {
        String str= "Menus{" +
                ", item=" ;
        for ( Item i:item){
            str+=i.toString() +",\n";
        }
             str+=   '}';
             return str;
    }

    public Menus(List<Item> item) {
        this.item = item;
    }



    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }
}
