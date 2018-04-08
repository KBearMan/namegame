package bearpack.k.namegame.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import bearpack.k.namegame.R;
import bearpack.k.namegame.model.Profile;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by A on 4/3/2018.
 */

public class PortraitRecyclerViewAdapter extends RecyclerView.Adapter<PortraitRecyclerViewAdapter.PortraitViewHolder>{

    List<Profile> mDataset;

    @Override
    public PortraitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_list_item_portrait, parent, false);

        PortraitViewHolder vh = new PortraitViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PortraitViewHolder holder, int position)
    {
        Profile profile = mDataset.get(position);
        Glide.with(holder.itemView.getContext())
                .load(profile.getHeadshot().getUrl())
                .into(holder.portrait)
                .onLoadStarted(holder.itemView.getContext().getDrawable(R.drawable.loading));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setListItems(List<Profile> list)
    {
        mDataset = list;
        notifyDataSetChanged();
    }

    class PortraitViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.imageView) ImageView portrait;
        public PortraitViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
