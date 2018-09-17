package Takingorders;

import java.util.LinkedList;

public class MealMenu {
    LinkedList<Meal> mealMenu;
    public MealMenu(){
        mealMenu=new LinkedList<Meal>();
    }
    public void addItem(String name,double price){
        Meal meal=new Meal(name,price);
        mealMenu.add(meal);
    }
    public LinkedList<Meal> getMeatMenu(){
        return mealMenu;
    }
}
