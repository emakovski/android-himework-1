package by.it.academy.homework4_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static List<DataItem> TITLES=new ArrayList<DataItem>(){{
        add(new DataItem(R.drawable.ic_baseline_contact_phone_24, "Egor Makovsky", "+375291048584"));
        add(new DataItem(R.drawable.ic_baseline_contact_mail_24, "Alesya Makovskaya", "alesya.popok.41@gmail.com"));
        add(new DataItem(R.drawable.ic_baseline_contact_phone_24, "Egor Makovsky", "+375291048584"));
        add(new DataItem(R.drawable.ic_baseline_contact_mail_24, "Alesya Makovskaya", "alesya.popok.41@gmail.com"));
        add(new DataItem(R.drawable.ic_baseline_contact_phone_24, "Egor Makovsky", "+375291048584"));
        add(new DataItem(R.drawable.ic_baseline_contact_mail_24, "Alesya Makovskaya", "alesya.popok.41@gmail.com"));
        add(new DataItem(R.drawable.ic_baseline_contact_phone_24, "Egor Makovsky", "+375291048584"));
        add(new DataItem(R.drawable.ic_baseline_contact_mail_24, "Alesya Makovskaya", "alesya.popok.41@gmail.com"));
        add(new DataItem(R.drawable.ic_baseline_contact_phone_24, "Egor Makovsky", "+375291048584"));
        add(new DataItem(R.drawable.ic_baseline_contact_mail_24, "Alesya Makovskaya", "alesya.popok.41@gmail.com"));
        add(new DataItem(R.drawable.ic_baseline_contact_phone_24, "Egor Makovsky", "+375291048584"));
        add(new DataItem(R.drawable.ic_baseline_contact_mail_24, "Alesya Makovskaya", "alesya.popok.41@gmail.com"));
        add(new DataItem(R.drawable.ic_baseline_contact_phone_24, "Egor Makovsky", "+375291048584"));
        add(new DataItem(R.drawable.ic_baseline_contact_mail_24, "Alesya Makovskaya", "alesya.popok.41@gmail.com"));
        add(new DataItem(R.drawable.ic_baseline_contact_phone_24, "Egor Makovsky", "+375291048584"));
        add(new DataItem(R.drawable.ic_baseline_contact_mail_24, "Alesya Makovskaya", "alesya.popok.41@gmail.com"));
        add(new DataItem(R.drawable.ic_baseline_contact_phone_24, "Egor Makovsky", "+375291048584"));
        add(new DataItem(R.drawable.ic_baseline_contact_mail_24, "Alesya Makovskaya", "alesya.popok.41@gmail.com"));
        add(new DataItem(R.drawable.ic_baseline_contact_phone_24, "Egor Makovsky", "+375291048584"));
        add(new DataItem(R.drawable.ic_baseline_contact_mail_24, "Alesya Makovskaya", "alesya.popok.41@gmail.com"));
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new DataItemAdapter(TITLES));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

    }

    private static class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.DataItemViewHolder>{
        private List<DataItem> dataItemList;

        public DataItemAdapter(List<DataItem> dataItemList) {
            this.dataItemList = dataItemList;
        }

        @NonNull
        @Override
        public DataItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact,parent,false);
            return new DataItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DataItemViewHolder holder, int position) {
            holder.bind(dataItemList.get(position));
        }

        @Override
        public int getItemCount() {
            return dataItemList!=null ? dataItemList.size() : 0;
        }

        class DataItemViewHolder extends RecyclerView.ViewHolder{
            private ImageView image;
            private TextView textName;
            private TextView textContact;

            public DataItemViewHolder(@NonNull View itemView) {
                super(itemView);
                image=itemView.findViewById(R.id.imageContact);
                textName=itemView.findViewById(R.id.name);
                textContact=itemView.findViewById(R.id.number);
            }
            void bind(DataItem dataItem){
                image.setImageResource(dataItem.getImageID());
                textName.setText(dataItem.getTitleName());
                textContact.setText(dataItem.getTitleContact());
            }
        }

    }
}