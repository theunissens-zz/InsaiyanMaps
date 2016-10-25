package za.co.insaiyan.budgeteer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import za.co.insaiyan.budgeteer.data.FixedExpenseDAO;
import za.co.insaiyan.budgeteer.data.FixedIncomeDAO;
import za.co.insaiyan.budgeteer.data.ProfileDAO;
import za.co.insaiyan.budgeteer.handler.ProfileManager;

public class BudgeteerMainActivity extends AppCompatActivity {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.realm = Realm.getDefaultInstance();

        setContentView(R.layout.activity_budgeteer_main);

        // Load the profile
        ProfileDAO profile = realm.where(ProfileDAO.class).equalTo("name", ProfileManager.getInstance().getProfileLoaded()).findFirst();

        RealmList<FixedIncomeDAO> incomeItems = profile.getIncomeItems();
        RealmList<FixedExpenseDAO> expenseItems = profile.getExpenseItems();

        double totalIncome = 0;
        for (int i = 0; i < incomeItems.size(); i++) {
            totalIncome += incomeItems.get(i).getAmount();
        }

        double totalExpense = 0;
        for (int i = 0; i < expenseItems.size(); i++) {
            totalExpense += expenseItems.get(i).getAmount();
        }

        TextView balance =  (TextView)this.findViewById(R.id.text_display_balance);
        TextView availableBalance =  (TextView)this.findViewById(R.id.text_display_available_balance);

        String result = Double.toString(totalIncome - totalExpense);

        balance.setText(result);
        availableBalance.setText(result);
    }
}

