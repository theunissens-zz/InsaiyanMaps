package za.co.insaiyan.budgeteer.handler;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import za.co.insaiyan.budgeteer.data.FixedExpenseDAO;
import za.co.insaiyan.budgeteer.data.FixedIncomeDAO;
import za.co.insaiyan.budgeteer.data.ProfileDAO;

/**
 * Created by Insaiyan on 10/23/2016.
 */

public class ProfileManager {

    private static ProfileManager instance;

    private String profileName;

    private ProfileManager() {

    }

    public static ProfileManager getInstance() {
        if (instance == null) {
            instance = new ProfileManager();
        }
        return instance;
    }

    public void setProfileLoaded(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileLoaded() {
        return profileName;
    }

    public RealmList<FixedIncomeDAO> getIncomeItems(Realm realm) {
        return realm.where(ProfileDAO.class).equalTo("name", profileName).findFirst().getIncomeItems();
    }

    public RealmList<FixedExpenseDAO> getExpenseItems(Realm realm) {
        return realm.where(ProfileDAO.class).equalTo("name", profileName).findFirst().getExpenseItems();
    }
}
