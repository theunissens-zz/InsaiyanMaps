package za.co.insaiyan.budgeteer.data;

import io.realm.Realm;

/**
 * Created by Insaiyan on 10/8/2016.
 */

public class RealmController {

    private Realm realm;
    private static RealmController instance;

    private RealmController() {
        realm = Realm.getDefaultInstance();
    }

    public void addFixedIncomeItem(FixedIncomeDAO fixedIncomeItem) {

    }

    public static RealmController getInstance() {
        if (instance == null) {
            instance = new RealmController();
        }
        return instance;
    }
}
