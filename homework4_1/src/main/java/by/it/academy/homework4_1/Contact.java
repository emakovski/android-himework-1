package by.it.academy.homework4_1;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements  Parcelable {

    private int image;
    private String textName;
    private String textContact;

    public Contact(int image, String textName, String textContact) {
        this.image = image;
        this.textName = textName;
        this.textContact = textContact;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            int image = source.readInt();
            String textName = source.readString();
            String textContact = source.readString();
            return new Contact(image, textName, textContact);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public int getImage() {
        return image;
    }

    public void setImage (int image) {
        this.image = image;
    }

    public String getTextName() {
        return textName;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public String getTextContact() {
        return textContact;
    }

    public void setTextContact (String textContact) { this.textContact = textContact; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image);
        dest.writeString(textName);
        dest.writeString(textContact);
    }
}
