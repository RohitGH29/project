package com.ritech.calltank.ContactList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ritech.calltank.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> implements Filterable {

    Activity activity;
    ArrayList<ContactModal> arrayList;
    ArrayList<ContactModal> backup;

    ArrayList<String> selectContactList ;
    RecyclerView  recyclerView2 ;
    SelectedContactAdapter selectedContactAdapter;
    View divider;

    String name;
    String pnumber;
    int tickvalue = 0;



    public ContactAdapter(Activity activity, ArrayList<ContactModal> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
        backup = new ArrayList<>(arrayList);
        selectContactList =  new ArrayList<String>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ContactModal modal = arrayList.get(position);

         name = modal.getName();
        Log.d("itsapp", "Name:" + name);
         pnumber = modal.getNumber();
        Log.d("itsapp", "Phone:" + pnumber);


        holder.tvname.setText(modal.getName());
        holder.tvnumber.setText(modal.getNumber());
        String profileName = modal.getName();
        holder.tvimg.setText(profileName.toUpperCase().substring(0, 1));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.relativeLayout.setBackgroundTintList(ColorStateList
                        .valueOf(ContextCompat.getColor(activity, R.color.grey)));

                postRequest();
                selectContactList.add(modal.getName());
               Log.d("itsapp", "Selected: " + selectContactList);

               recycler();





                if (tickvalue >0){
                    holder.tickimg.setVisibility(View.VISIBLE);
                }

            }
        });


    }

    private void recycler() {
        // holder.recyclerView2.setHasFixedSize(true);
        recyclerView2 = activity.findViewById(R.id.selected_contact_list);
        divider = activity.findViewById(R.id.divider2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false));
        selectedContactAdapter=new SelectedContactAdapter(activity,selectContactList);
        recyclerView2.setAdapter(selectedContactAdapter);
        divider.setVisibility(View.VISIBLE);
        recyclerView2.setVisibility(View.VISIBLE);
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // POST request
    private void postRequest()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        String url = "http://calltank.in/api/contact/create/";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("itsapp", "Json" + response);
                Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                tickvalue +=1;


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show();
                Log.d("itsapp", "Json" + error);

            }
        }){
            @Override
            protected Map<String,String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name",name);
                params.put("phone_number",pnumber);
                return params;
            }

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError{
                Log.d("itsapp", "Json: in headers");
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", "token 2788babf87302d7bd3c18fbf7a78c048329791f3ccd3d589045d4072f891dd4f");
                Log.d("itsapp", "Json: aa");
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    // This is for searching , in contact list
    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {

            ArrayList<ContactModal> filtereddata = new ArrayList<>();

            if (keyword.toString().isEmpty())
                filtereddata.addAll(backup);
            else {
                for (ContactModal obj : backup) {
                    if (obj.getName().toString().toUpperCase().contains(keyword.toString().toUpperCase()))
                        filtereddata.add(obj);
                }
            }

            FilterResults results = new FilterResults();
            results.values = filtereddata;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayList.clear();
            arrayList.addAll((ArrayList<ContactModal>) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvname, tvnumber, tvimg;
        RelativeLayout relativeLayout;
        ImageView tickimg;
//        RecyclerView selectedContactList;
        View divider;
        RecyclerView  recyclerView2;
        SelectedContactAdapter selectedContactAdapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvname = itemView.findViewById(R.id.name);
            tvnumber = itemView.findViewById(R.id.phoneNo);
            tvimg = itemView.findViewById(R.id.img_text_name);
            relativeLayout = itemView.findViewById(R.id.contactListRL);
          //  selectedContactList = itemView.findViewById(R.id.selected_contact_list);
            //selectedContactList.setVisibility(View.INVISIBLE);
            divider = itemView.findViewById(R.id.divider2);
            //divider.setVisibility(View.INVISIBLE);
            tickimg = itemView.findViewById(R.id.climg);
            recyclerView2 = itemView.findViewById(R.id.selected_contact_list);
            tickimg.setVisibility(View.INVISIBLE);


        }
    }
}
