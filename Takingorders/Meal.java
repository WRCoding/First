package Takingorders;

public class Meal {
    private  String name;
    private  double price;
    public Meal(String name,double price){
        super();
        this.name=name;
        this.price=price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
