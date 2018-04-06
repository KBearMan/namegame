package bearpack.kyle.namegame.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by A on 4/3/2018.
 */

public class PortraitRecyclerView extends RecyclerView.Adapter<PortraitRecyclerView.PortraitViewHolder>{

    @Override
    public PortraitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PortraitViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class PortraitViewHolder extends RecyclerView.ViewHolder
    {

        public PortraitViewHolder(View itemView) {
            super(itemView);
        }
    }
}
