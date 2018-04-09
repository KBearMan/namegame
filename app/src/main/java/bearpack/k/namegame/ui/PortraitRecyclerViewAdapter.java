package bearpack.k.namegame.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bearpack.k.namegame.R;
import bearpack.k.namegame.model.Profile;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by A on 4/3/2018.
 */

public class PortraitRecyclerViewAdapter extends RecyclerView.Adapter<PortraitRecyclerViewAdapter.PortraitViewHolder>{

    List<Profile> mDataset = new ArrayList<>();
    Context context;

    public PortraitRecyclerViewAdapter(Context context)
    {
        this.context = context;
    }

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

        Picasso.get()
                .load("http:".concat(profile.getHeadshot().getUrl()))
                .fit()
                .placeholder(R.drawable.rotation)
                .error(R.drawable.error)
                .into(holder.portrait);
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
            ButterKnife.bind(this,itemView);
        }
    }
}
