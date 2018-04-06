package bearpack.kyle.namegame.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by A on 4/3/2018.
 */

public class StatsRecyclerView extends RecyclerView.Adapter<StatsRecyclerView.StatsViewHolder>{

    @Override
    public StatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(StatsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class StatsViewHolder extends RecyclerView.ViewHolder
    {
        TextView statName;
        TextView statValue;

        public StatsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
