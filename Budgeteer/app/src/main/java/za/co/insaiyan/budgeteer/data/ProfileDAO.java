package za.co.insaiyan.budgeteer.data;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Insaiyan on 10/12/2016.
 */

public class ProfileDAO extends RealmObject {

    @PrimaryKey
    private String name;
    private RealmList<FixedIncomeDAO> incomeItems;
    private RealmList<FixedExpenseDAO> expenseItems;

    public ProfileDAO() {

    }

    public ProfileDAO(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public RealmList<FixedIncomeDAO> getIncomeItems() {
        return incomeItems;
    }

    public void setIncomeItems(RealmList<FixedIncomeDAO> incomeItems) {
        this.incomeItems = incomeItems;
    }

    public RealmList<FixedExpenseDAO> getExpenseItems() {
        return expenseItems;
    }

    public void setExpenseItems(RealmList<FixedExpenseDAO> expenseItems) {
        this.expenseItems = expenseItems;
    }
}
