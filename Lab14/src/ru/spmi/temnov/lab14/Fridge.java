package ru.spmi.temnov.lab14;

public class Fridge extends Appliances{
    private final int shelf_num;
    Fridge(String name, int shelf_num){
        super(name);
        this.shelf_num = shelf_num;
    }
    @Override
    public String show(){
        return "Холодильник компании " + name +  " с диагональю " + shelf_num + "''\n";
    }
}
