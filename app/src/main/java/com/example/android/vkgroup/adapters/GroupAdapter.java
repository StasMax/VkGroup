package com.example.android.vkgroup.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.vkgroup.models.AppDatabase;
import com.example.android.vkgroup.models.GroupModel;
import com.example.android.vkgroup.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<GroupModel>mGroupModelList = new ArrayList<>();
    List<GroupModel>mSourseList = new ArrayList<>();
    CircleImageView mCivAvatar;
    TextView mTxtGroupName;
    TextView mTxtSubscribers;
    View mIsFavoriteImg;
    AppDatabase db;

    public void setupGroups(List<GroupModel>groupModelList){
      /*  mSourseList.clear();
        mSourseList.addAll(groupModelList);
        filter("");*/
        mGroupModelList.clear();
        mGroupModelList.addAll(groupModelList);
        notifyDataSetChanged();
    }

    public void filter(String query){
       /* mGroupModelList.clear();
        for (GroupModel gModel:mSourseList
             ) {
            if (gModel.getName().contains(query) || gModel.getName().contains(query.toLowerCase()) ){ mGroupModelList.add(gModel);}
        }
        notifyDataSetChanged();*/
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_group, viewGroup, false);

        return new GroupViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
if (viewHolder instanceof GroupViewHolder){
    ((GroupViewHolder) viewHolder).bind(mGroupModelList.get(i));
}
    }

    @Override
    public int getItemCount() {
        return mGroupModelList.size();
    }


    class GroupViewHolder extends RecyclerView.ViewHolder{

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);

            mCivAvatar = (CircleImageView)itemView.findViewById(R.id.groups_siv_avatar);
            mTxtGroupName = (TextView)itemView.findViewById(R.id.group_txt_name);
            mTxtSubscribers = (TextView)itemView.findViewById(R.id.group_txt_subscribers);
            mIsFavoriteImg = (View)itemView.findViewById(R.id.favorite_img);
        }

        public void bind(GroupModel groupModel){
mTxtGroupName.setText(groupModel.getName());
mTxtSubscribers.setText(groupModel.getSubscribers());
if (groupModel.getFavorite())mIsFavoriteImg.setVisibility(View.VISIBLE);
else mIsFavoriteImg.setVisibility(View.GONE);

if (groupModel.getAvatar() != null){
    Picasso.with(itemView.getContext()).load(groupModel.getAvatar()).into(mCivAvatar);
}
        }
    }
}
