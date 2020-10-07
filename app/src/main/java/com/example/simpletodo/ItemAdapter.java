package com.example.simpletodo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    public interface OnClickListener{
        void onItemClicked(int position);
    }

    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;
    OnClickListener clickListener;

    public ItemAdapter(List<String> items, OnLongClickListener longClickListener, OnClickListener clickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View toolView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
        return new ViewHolder(toolView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        String item = items.get(i);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem;

         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             tvItem = itemView.findViewById(android.R.id.text1);
         }

        public void bind(String item) {
             tvItem.setText(item);

             tvItem.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     clickListener.onItemClicked(getAdapterPosition());
                 }
             });

             tvItem.setOnLongClickListener(new View.OnLongClickListener(){

                 @Override
                 public boolean onLongClick(View v) {
                     // notify the position
                     longClickListener.onItemLongClicked(getAdapterPosition());
                     return true;
                 }
             });
        }
    }
}
