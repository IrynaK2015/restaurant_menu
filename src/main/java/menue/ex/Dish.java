package menue.ex;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "is_discount", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue(value = "N")
public class Dish {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length=200, nullable = false)
    private String name;

    @Column(precision=5, nullable = false)
    private int weight;

    @Column(precision=10, scale=2, nullable = false)
    private double cost;

   /* @Column(precision=2, nullable = true)
    private int discount;*/

    public Dish() {}

    public Dish(String name, int weight, double cost) {
        this.name = name;
        this.weight = weight;
        this.cost = cost;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    /*public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }*/

    @Override
    public String toString() {
        return String.format(
            "%d : \"%s\" %d gr %.2f uah", id, name, weight, cost
        );
    }
}
