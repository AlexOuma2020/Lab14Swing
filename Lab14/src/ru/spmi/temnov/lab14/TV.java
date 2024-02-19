package ru.spmi.temnov.lab14;

public class TV extends Appliances{
    private final int screen;
    TV(String name, int screen){
        super(name);
        this.screen = screen;
    }
    @Override
    public String show(){
        return "Телевиор компании " + name +  " с диагональю " + screen + "''\n";
    }
}
