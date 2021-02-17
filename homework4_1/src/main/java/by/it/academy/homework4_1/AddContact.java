package by.it.academy.homework4_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddContact extends AppCompatActivity {

    private EditText nameAdd;
    private EditText phoneOrEmailAdd;
    private RadioButton radioButtonPhoneNumber;
    private Contact contact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);
        Intent intent = getIntent();
        Toolbar toolbar = findViewById(R.id.toolbarAddContact);
        setSupportActionBar(toolbar);

        findViewById(R.id.backAdd).setOnClickListener(v -> finish());

        phoneOrEmailAdd = findViewById(R.id.addContactOrEmail);
        nameAdd = findViewById(R.id.addName);
        radioButtonPhoneNumber = findViewById(R.id.radioPhone);

        ImageButton saveAddon = findViewById(R.id.saveAdd);
        saveAddon.setOnClickListener(v -> {

            int imageId;
            if (radioButtonPhoneNumber.isChecked()) {
                imageId = R.drawable.ic_baseline_contact_phone_24;
            } else {
                imageId = R.drawable.ic_baseline_contact_mail_24;
            }

            String name = this.nameAdd.getText().toString();
            String phoneOrEmail = this.phoneOrEmailAdd.getText().toString();

            if (name.isEmpty() || phoneOrEmail.isEmpty()) {
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
            } else {
                contact = new Contact(imageId, name, phoneOrEmail);
                intent.putExtra("add_contact", (Parcelable) contact);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}