package by.it.academy.homework4_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Contact contact;
    private ContactAdapter contactAdapter;
    private static final int ADD_CONTACT = 0;
    private static final int EDIT_CONTACT = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        SearchView searchView = findViewById(R.id.searchViewActivityMain);

        if (savedInstanceState != null) {
            contactAdapter = new ContactAdapter(savedInstanceState.getParcelableArrayList("list"));
        } else {
            contactAdapter = new ContactAdapter(new ArrayList<>());
        }


        recyclerView.setAdapter(contactAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        checkContacts(contactAdapter);
        contactAdapter.setListener(new ContactAdapter.Listener() {

            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, EditContact.class);
                contact = contactAdapter.getContactList().get(position);
                setResult(Activity.RESULT_OK, intent);
                intent.putExtra("contact", contact);
                intent.putExtra("position", position);
                startActivityForResult(intent, EDIT_CONTACT);
            }
        });

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactAdapter.getFilter().filter(newText);
                return false;
            }
        });


        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContact.class);
                startActivityForResult(intent,ADD_CONTACT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Contact nContact;

        if (resultCode == RESULT_OK && requestCode == ADD_CONTACT) {
            assert data != null;
            nContact = data.getParcelableExtra("add_contact");
            contactAdapter.add(nContact);
            checkContacts(contactAdapter);


        } else if (resultCode == RESULT_OK && requestCode == EDIT_CONTACT) {
            assert data != null;
            nContact = data.getParcelableExtra("edit_contact");
            int position = data.getIntExtra("position", 0);

            if (nContact != null) {
                contactAdapter.edit(position, nContact);
                checkContacts(contactAdapter);
            } else {
                recyclerView.invalidate();
                checkContacts(contactAdapter);
                contactAdapter.remove(data.getIntExtra("position", 0));
            }
        }
    }

    public void checkContacts(@NonNull ContactAdapter contactAdapter) {
        if (contactAdapter.getContactList().isEmpty()) {
            findViewById(R.id.ifNoContacts).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.ifNoContacts).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (contactAdapter.getContactList().size() != 0) {
            outState.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) contactAdapter.getContactList());
        } else {
            outState.putParcelableArrayList("list", new ArrayList<>());
        }
    }
}
