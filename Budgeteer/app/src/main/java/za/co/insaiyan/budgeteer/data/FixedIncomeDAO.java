package za.co.insaiyan.budgeteer.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Insaiyan on 10/6/2016.
 */

public class FixedIncomeDAO extends RealmObject {

    private String name;
    private double amount;
    private String date;

    public FixedIncomeDAO() {

    }

    public FixedIncomeDAO(String name, double amount, String date) {
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAmount(double amount) {
        this.amount = amount ;
    }

    public double getAmount() {
        return amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
