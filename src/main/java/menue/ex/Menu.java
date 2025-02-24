package menue.ex;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class Menu {

    final private EntityManager em;

    public Menu(EntityManager em) {
        this.em = em;
    }

    public void addDish(Dish dish) {
        if (isDishExists(dish.getName())) {
            throw new RuntimeException("Dish \"" + dish.getName() + "\" already exists");
        }
        em.getTransaction().begin();
        try {
            em.persist(dish);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new RuntimeException(ex.getMessage());
        }
    }

    public boolean isDishExists(String dishName) {
        Query q = em.createQuery("SELECT d FROM Dish d WHERE d.name = :name", Dish.class);
        q.setParameter("name", dishName);
        List<ReducedDish> dishList = q.getResultList();

        return !dishList.isEmpty();
    }

    public List<ReducedDish> getDishesWithDiscount() {
        TypedQuery<ReducedDish> q = em.createQuery(
            "SELECT d FROM ReducedDish d", ReducedDish.class
        );

        return q.getResultList();
    }

    public List<Dish> getDishesByCost(double valueFrom, double valueTo) {
        List<Object[]> rows = em.createNativeQuery(
        "WITH tmp AS (SELECT d.*, coalesce((d.cost * (100 - d.discount)/100), d.cost) as reduced_cost FROM dish d) "
            + "SELECT id FROM tmp WHERE reduced_cost BETWEEN :value_from AND :value_to"
        )
        .setParameter("value_from", valueFrom)
        .setParameter("value_to", valueTo)
        .getResultList();
        if (!rows.isEmpty()) {
            String sql = "SELECT d FROM Dish d WHERE d.id IN (" + org.apache.commons.lang3.StringUtils.join(rows, ",") + ")";
            System.out.println(sql);
            TypedQuery<Dish> q = em.createQuery(sql, Dish.class);

            return q.getResultList();
        }

        return null;
    }

    public List<Dish> getDishesByMaxWeight(double weight) {
        Query q = em.createQuery("SELECT d FROM Dish d ORDER BY weight", Dish.class);
        List<Dish> dishList = q.getResultList();

        return filterMenuByWeight(dishList, weight);
    }

    private List<Dish> filterMenuByWeight(List<Dish> sortedDishList, double weight) {
        double sum = 0d;
        List<Dish> filtereddDishList = new ArrayList<>();
        for (Dish dish : sortedDishList) {
            if (sum + dish.getWeight() > weight) {
                break;
            }
            filtereddDishList.add(dish);
            sum += dish.getWeight();
        }

        return filtereddDishList;
    }
}
