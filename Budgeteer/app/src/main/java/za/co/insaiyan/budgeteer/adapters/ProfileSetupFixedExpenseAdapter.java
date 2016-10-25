package za.co.insaiyan.budgeteer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import za.co.insaiyan.budgeteer.ProfileSetupFixedExpenseFragment;
import za.co.insaiyan.budgeteer.R;
import za.co.insaiyan.budgeteer.data.FixedExpenseDAO;

/**
 * Created by Insaiyan on 10/9/2016.
 */

public class ProfileSetupFixedExpenseAdapter extends RealmRecyclerViewAdapter<FixedExpenseDAO, ProfileSetupFixedExpenseAdapter.MyViewHolder> {

    public ProfileSetupFixedExpenseAdapter(ProfileSetupFixedExpenseFragment activity, OrderedRealmCollection<FixedExpenseDAO> data) {
        super(activity.getContext(), data, true);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.row_fixed_expense, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FixedExpenseDAO obj = getData().get(position);
        holder.data = obj;
        holder.title.setText(obj.getName());
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView title;
        public FixedExpenseDAO data;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.row_fixed_expense_title);
            view.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            return true;
        }
    }
}
