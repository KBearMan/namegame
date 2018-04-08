package bearpack.k.namegame.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by A on 4/3/2018.
 */

public class NameRecyclerView extends RecyclerView.Adapter<NameRecyclerView.EmployeeViewHolder>{

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder
    {

        public EmployeeViewHolder(View itemView) {
            super(itemView);
        }
    }
}
