package ru.spmi.temnov.lab14;

abstract public class Appliances {
    protected String name;
    protected static int num = 0;
    public static void zeroize(){
        num = 0;
    }
    Appliances(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public static void incrNum(){
        ++num;
    }

    public static int getNum(){
        return num;
    }

    abstract public String show();
    void countApp(String name){
        if(this.name.equals(name))
            ++num;
    }
}
