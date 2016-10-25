package za.co.insaiyan.budgeteer.data;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Insaiyan on 10/12/2016.
 */

public class ProfileDAO extends RealmObject {

    @PrimaryKey
    private String name;

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
}
