package bearpack.k.namegame.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.test.ProviderTestCase2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bearpack.k.namegame.R;
import bearpack.k.namegame.model.Profile;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KBearMan on 4/3/2018.
 */

public class PortraitRecyclerViewAdapter extends RecyclerView.Adapter<PortraitRecyclerViewAdapter.PortraitViewHolder>{

    List<Profile> mDataset = new ArrayList<>();
    Context context;
    OnPortraitClickedListener mListener;

    public interface OnPortraitClickedListener
     {
         public void onItemClicked(Profile clickedProfile);
     }

    public PortraitRecyclerViewAdapter(Context context, OnPortraitClickedListener listener)
    {
        this.context = context;
        this.mListener = listener;
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

        try
        {
            Picasso.get()
                    .load("http:".concat(profile.getHeadshot().getUrl()))
                    .fit()
                    .placeholder(R.drawable.rotation)
                    .error(R.drawable.error)
                    .into(holder.portrait);
        }
        catch(NullPointerException e)
        {
            Picasso.get()
                    .load(R.drawable.error)
                    .fit()
                    .into(holder.portrait);
        }
        holder.profile = profile;
        holder.portrait.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.onItemClicked(holder.profile);
            }
        });
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


    public void removeItem(int position)
    {
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,mDataset.size());
    }

    class PortraitViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.imageView) ImageView portrait;
        Profile profile;

        public PortraitViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
