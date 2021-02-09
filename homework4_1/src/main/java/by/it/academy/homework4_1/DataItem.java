package by.it.academy.homework4_1;

public class DataItem {

    private int imageID;
    private String titleName;
    private String titleContact;

    public DataItem (int imageID,String titleName,String titleContact){
        this.imageID=imageID;
        this.titleName=titleName;
        this.titleContact=titleContact;

    }

    public int getImageID(){
        return imageID;
    }
    public String getTitleName(){
        return titleName;
    }
    public String getTitleContact(){
        return titleContact;
    }
}
