package com.example.android.vkgroup.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android.vkgroup.activities.GroupActivity;
import com.example.android.vkgroup.models.AppDatabase;
import com.example.android.vkgroup.models.GroupModel;
import com.example.android.vkgroup.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.android.vkgroup.models.AppDatabase.setFavorite;
import static com.example.android.vkgroup.models.AppDatabase.setOutFavorite;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.MyViewHolder> {

    List<GroupModel> mGroupModelList = new ArrayList<>();
    List<GroupModel> mSourseList = new ArrayList<>();
    CircleImageView mCivAvatar;
    TextView mTxtGroupName;
    TextView mTxtSubscribers;


    public void setupGroups(List<GroupModel> groupModelList) {
        mSourseList.clear();
        mSourseList.addAll(groupModelList);
        filter("");
       /* mGroupModelList.clear();
        mGroupModelList.addAll(groupModelList);
        notifyDataSetChanged();*/
    }

    public void setupFavoriteGroups(List<GroupModel> groupModelList) {

        mGroupModelList.clear();
        mGroupModelList.addAll(groupModelList);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        mGroupModelList.clear();
        for (GroupModel gModel : mSourseList
        ) {
            if (gModel.getName().contains(query) || gModel.getName().contains(query.toLowerCase())) {
                mGroupModelList.add(gModel);
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GroupAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_group, viewGroup, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {


        myViewHolder.bind(mGroupModelList.get(i));

        myViewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myViewHolder.mCheckBox.isChecked()) {
                    mGroupModelList.get(i).setFavorite(true);
                    setFavorite(mGroupModelList.get(i));
                } else {
                    mGroupModelList.get(i).setFavorite(false);
                    setOutFavorite(mGroupModelList.get(i));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mGroupModelList.size();
    }

    // Не понятно что делают эти два метода
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CheckBox mCheckBox;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mCivAvatar = (CircleImageView) itemView.findViewById(R.id.groups_siv_avatar);
            mTxtGroupName = (TextView) itemView.findViewById(R.id.group_txt_name);
            mTxtSubscribers = (TextView) itemView.findViewById(R.id.group_txt_subscribers);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.isFavorite_checkBox);
        }


        public void bind(final GroupModel groupModel) {
            mTxtGroupName.setText(groupModel.getName());
            mTxtSubscribers.setText(groupModel.getSubscribers());

            if (groupModel.getFavorite()) mCheckBox.setChecked(true);
            else mCheckBox.setChecked(false);

            if (groupModel.getAvatar() != null) {
                Picasso.with(itemView.getContext()).load(groupModel.getAvatar()).into(mCivAvatar);
            }
        }
    }
}
