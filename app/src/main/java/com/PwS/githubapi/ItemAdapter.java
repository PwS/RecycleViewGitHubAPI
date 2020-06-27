package com.PwS.githubapi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.PwS.githubapi.controller.DetailActivity;
import com.PwS.githubapi.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by PwS
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Item> items;
    private Context context;

    public ItemAdapter(List<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewgroup, int i) {
        View view = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.row_item, viewgroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tittle.setText(items.get(i).getLogin());
        viewHolder.githublink.setText(items.get(i).getHtmlUrl());

        Picasso.get()
                .load(items.get(i).getImgUserUrl())
                .placeholder(R.drawable.load)
                .into(viewHolder.imgUser);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tittle, githublink;
        private ImageView imgUser;

        public ViewHolder(View view) {
            super(view);
            tittle = view.findViewById(R.id.tittle);
            githublink = view.findViewById(R.id.githublink);
            imgUser = view.findViewById(R.id.imgUser);

            //OnItemClick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Item clickedDataItem = items.get(position);
                        Intent gotoDetail = new Intent(context, DetailActivity.class);
                        //PutExtra(KEY,position)
                        gotoDetail.putExtra("login", items.get(position).getLogin());
                        gotoDetail.putExtra("html_url", items.get(position).getHtmlUrl());
                        gotoDetail.putExtra("avatar_url", items.get(position).getImgUserUrl());
                        gotoDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(gotoDetail);
                        Toast.makeText(v.getContext(), "Clicked Data" + clickedDataItem.getLogin(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
