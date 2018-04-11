package bearpack.k.namegame.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bearpack.k.namegame.R;
import bearpack.k.namegame.model.Profile;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by KBearMan on 4/3/2018.
 */

public class NameRecyclerViewAdapter extends RecyclerView.Adapter<NameRecyclerViewAdapter.NameViewHolder>{

    List<Profile> mDataset = new ArrayList<>();
    Context context;
    OnNameClickedListener mListener;

    public interface OnNameClickedListener
    {
        public void onItemClicked(Profile clickedProfile);
    }

    public NameRecyclerViewAdapter(Context context, OnNameClickedListener listener)
    {
        this.context = context;
        this.mListener = listener;
    }


    @Override
    public NameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_list_item_name, parent, false);

        NameViewHolder vh = new NameViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(NameViewHolder holder, int position)
    {
        Profile profile = mDataset.get(position);
        holder.profile = profile;
        holder.name.setText(profile.getFirstName() + " " + profile.getLastName());
        holder.name.setOnClickListener(new View.OnClickListener()
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

    class NameViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.textView) TextView name;
        Profile profile;
        public NameViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
