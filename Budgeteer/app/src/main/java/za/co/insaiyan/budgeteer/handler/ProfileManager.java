package za.co.insaiyan.budgeteer.handler;

import za.co.insaiyan.budgeteer.data.ProfileDAO;

/**
 * Created by Insaiyan on 10/23/2016.
 */

public class ProfileManager {

    private static ProfileManager instance;

    private ProfileDAO profile;

    private ProfileManager() {

    }

    public static ProfileManager getInstance() {
        if (instance == null) {
            instance = new ProfileManager();
        }
        return instance;
    }

    public void setProfileLoaded(ProfileDAO profile) {
        this.profile = profile;
    }

    public ProfileDAO getProfileLoaded() {
        return profile;
    }
}
