package com.example.myapplication.activities.stores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.db.StoreTable;

import org.json.JSONObject;

public class StoreAdapter extends RecyclerView.Adapter {

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public Button del;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            del = itemView.findViewById(R.id.del);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_store, viewGroup, false);

        RecyclerView.ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        JSONObject item = StoreTable.inst().get(i);
        try{
            ViewHolder v = (ViewHolder)viewHolder;
            v.name.setText(item.getString("name"));
            v.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StoreTable.inst().del(i);
                    notifyDataSetChanged();
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return StoreTable.inst().size();
    }
}
