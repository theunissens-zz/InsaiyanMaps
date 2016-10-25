package za.co.insaiyan.budgeteer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.realm.Realm;
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

        ProfileDAO profile = ProfileManager.getInstance().getProfileLoaded();
        String name = profile.getName();

        RealmResults<FixedIncomeDAO> incomeResults = this.realm.where(FixedIncomeDAO.class).equalTo("profileName", profile.getName()).findAll();
//        RealmResults<FixedIncomeDAO> incomeResults = this.realm.where(FixedIncomeDAO.class).findAll();

        double totalIncome = 0;
        for (int i = 0; i < incomeResults.size(); i++) {
            totalIncome += incomeResults.get(i).getAmount();
        }

        RealmResults<FixedExpenseDAO> expenseResults = this.realm.where(FixedExpenseDAO.class).equalTo("profileName", profile.getName()).findAll();
//        RealmResults<FixedExpenseDAO> expenseResults = this.realm.where(FixedExpenseDAO.class).findAll();

        double totalExpense = 0;
        for (int i = 0; i < expenseResults.size(); i++) {
            totalExpense += expenseResults.get(i).getAmount();
        }

        TextView balance =  (TextView)this.findViewById(R.id.text_display_balance);
        TextView availableBalance =  (TextView)this.findViewById(R.id.text_display_available_balance);

        String result = Double.toString(totalIncome - totalExpense);

        balance.setText(result);
        availableBalance.setText(result);
    }
}

