package by.it.academy.homework4_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditContact extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPhoneOrEmail;
    private Contact contact;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact);
        Intent data = getIntent();

        Toolbar toolbar = findViewById(R.id.toolbarEditContact);
        setSupportActionBar(toolbar);
        ImageButton imageButtonBack = findViewById(R.id.backEdit);
        ImageButton imageButtonSave = findViewById(R.id.saveEdit);
        Button buttonRemove = findViewById(R.id.buttonRemove);
        editTextName = findViewById(R.id.editName);
        editTextPhoneOrEmail = findViewById(R.id.editContactOrEmail);

        this.contact = data.getParcelableExtra("contact");
        int position = data.getIntExtra("position", 0);
        editTextName.setText(" ");
        editTextPhoneOrEmail.setText(" ");
        editTextName.setText(contact.getTextName());
        editTextPhoneOrEmail.setText(contact.getTextContact());

        imageButtonBack.setOnClickListener(v -> finish());

        imageButtonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                contact.setTextName(editTextName.getText().toString());
                contact.setTextContact(editTextPhoneOrEmail.getText().toString());
                data.putExtra("edit_contact", (Parcelable) contact);
                data.putExtra("position", position);
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                contact.setTextName("");
                contact.setTextContact("");
                contact.setImage(0);
                data.putExtra("edit_contact", (Parcelable) contact);
                data.putExtra("position", position);
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
    }
}

