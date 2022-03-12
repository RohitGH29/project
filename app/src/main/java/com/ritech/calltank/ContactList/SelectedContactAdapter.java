package com.ritech.calltank.ContactList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ritech.calltank.R;

import java.util.ArrayList;

public class SelectedContactAdapter extends RecyclerView.Adapter<SelectedContactAdapter.ViewHolder> {

    Activity context;
    ArrayList<String> selectedContacts;

    public SelectedContactAdapter(Activity context, ArrayList<String> selectedContacts) {
        this.context = context;
        this.selectedContacts = selectedContacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.selected_contact_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

         String l = selectedContacts.get(position);


        holder.nameTV.setText(l);

        holder.imgTV.setText(l.toUpperCase().substring(0, 1));

    }

    @Override
    public int getItemCount() {
        return selectedContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView imgTV, nameTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTV = itemView.findViewById(R.id.img_text_name);
            nameTV = itemView.findViewById(R.id.selected_contact_name);

        }
    }
}
