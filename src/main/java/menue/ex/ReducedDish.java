package menue.ex;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.awt.*;

@Entity
@DiscriminatorValue(value = "Y")
public class ReducedDish extends Dish {
    private int discount;

    public ReducedDish() {}

    public ReducedDish(String dishName, int weight, double cost, int discount) {
        super(dishName, weight, cost);
        this.discount = discount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return super.toString() + ", discount " + discount + "%";
    }
}
