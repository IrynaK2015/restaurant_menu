package menue.ex;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Objects;

public class App {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("BankApp");
        em = emf.createEntityManager();

        try {
            initMenue();
            Menu menu = new Menu(em);

            List<ReducedDish> dishList = menu.getDishesWithDiscount();
            System.out.println("\nDishes with discount");
            printMenu(dishList);

            List<Dish> dishList1 = menu.getDishesByCost(100.00, 400.00);
            if (Objects.equals(dishList1, null)) {
                System.out.println("\nDishes with cost range not found");
            } else {
                System.out.println("\nDishes with cost range");
                printMenu(dishList1);
            }

            List<Dish> dishList2 = menu.getDishesByMaxWeight(1000);
            System.out.println("\nDishes with max summary weight");
            printMenu(dishList2);

        } finally {
            em.close();
            emf.close();
        }
    }

    static void initMenue() {
        Menu menu = new Menu(em);
        menu.addDish(new Dish("Kyiv chicken", 200, 450.00));
        menu.addDish(new Dish("Greek salad", 300, 180.00));
        menu.addDish(new Dish("Dunkel beer", 500, 150.00));
        menu.addDish(new ReducedDish("Tuna salad", 230, 399.90, 10));
        menu.addDish(new ReducedDish("Pork steak", 350, 450, 15));
        menu.addDish(new ReducedDish("Cheese cake", 100, 150, 5));
    }

    static void printMenu(List<? extends Dish> dishList) {
        for (Dish dish: dishList) {
            System.out.println(dish);
        }
    }
}
