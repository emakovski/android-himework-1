package by.it.academy.homework4_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> implements Filterable {

    private final List<Contact> contactList;
    private final List<Contact> contactListFull;
    private Listener listener;

    public ContactAdapter(List<Contact> contactList) {
        this.contactList = contactList;
        contactListFull = new ArrayList<>(contactList);
        notifyDataSetChanged();
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactAdapter.ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder holder, final int position) {

        holder.bind(contactList.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    notifyDataSetChanged();
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList != null ? contactList.size() : 0;
    }

    public void add(Contact contact) {
        contactList.add(contact);
        contactListFull.add(contact);
        notifyItemChanged(contactList.indexOf(contact));
        notifyDataSetChanged();
    }

    public void edit(int position, Contact contact) {
        contactList.set(position, contact);
        contactListFull.set(position, contact);
        notifyDataSetChanged();
        notifyItemChanged(position);
    }

    public void remove(int position) {
        contactList.remove(position);
        contactListFull.remove(position);
        notifyItemRemoved(position);
    }

    interface Listener {
        void onClick(int position);
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {

        String textName;
        String textInfo;
        private final TextView textViewName;
        private final TextView textViewNumber;
        private final ImageView imageView;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.name);
            textViewNumber = itemView.findViewById(R.id.number);
            imageView = itemView.findViewById(R.id.imageContact);
        }

        void bind(Contact contact) {
            imageView.setImageResource(contact.getImage());
            textViewName.setText(contact.getTextName());
            textViewNumber.setText(contact.getTextContact());
            textName = contact.getTextName();
            textInfo = contact.getTextContact();
        }
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Contact> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(contactListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Contact item : contactListFull) {
                    if (item.getTextName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            contactList.clear();
            contactList.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };

}

