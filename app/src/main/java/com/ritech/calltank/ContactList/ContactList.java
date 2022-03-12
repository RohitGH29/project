package com.ritech.calltank.ContactList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ritech.calltank.R;

import java.util.ArrayList;

public class ContactList extends AppCompatActivity {

    RecyclerView recyclerView ;
//    SelectedContactAdapter selectedContactAdapter;
//    View divider;
    ArrayList<ContactModal> arrayList = new ArrayList<ContactModal>();
   // ArrayList<ContactModal> selectedContacts = new ArrayList<ContactModal>();

    ContactAdapter adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);

        Toolbar toolbar = findViewById(R.id.aa_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark)));

        findId();

        getContactList();







    }

    private void findId() {

        recyclerView = findViewById(R.id.contact_recycler_view);


    }



    private void getContactList() {

        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME ;

        Cursor cursor = getContentResolver().query(
                uri, null, null, null, sort
        );

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {


                String id = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));

                Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?";

                Cursor phoneCursor = getContentResolver().query(
                        uriPhone, null, selection, new String[]{id}, null
                );

                if (phoneCursor.moveToNext()) {

                    String number = phoneCursor.getString(phoneCursor.getColumnIndexOrThrow(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));

                    ContactModal modal = new ContactModal();

                    modal.setName(name);

                    modal.setNumber(number);

                    arrayList.add(modal);

                    phoneCursor.close();

                }
            }
            cursor.close();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ContactAdapter(this,arrayList);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mainmenu,menu);
        MenuItem item = menu.findItem(R.id.search_menu);
        item.setIcon(getDrawable(R.drawable.search));


        SearchView searchView= (SearchView)item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}