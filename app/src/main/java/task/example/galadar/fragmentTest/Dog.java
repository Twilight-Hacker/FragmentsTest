package task.example.galadar.fragmentTest;

import android.os.Parcel;
import android.os.Parcelable;

public class Dog implements Parcelable {

    private String Breed;
    private int length;
    private String ImageLink;
    private String FavFood;
    private String Served;
    private String Colour;
    private String Size;
    private int height;

    public Dog() {
    }

    protected Dog(Parcel in) {
        Breed = in.readString();
        length = in.readInt();
        ImageLink = in.readString();
        FavFood = in.readString();
        Served = in.readString();
        Colour = in.readString();
        Size = in.readString();
        height = in.readInt();
    }

    public static final Creator<Dog> CREATOR = new Creator<Dog>() {
        @Override
        public Dog createFromParcel(Parcel in) {
            return new Dog(in);
        }

        @Override
        public Dog[] newArray(int size) {
            return new Dog[size];
        }
    };

    public String getBreed() {
        return this.Breed;
    }

    public void setBreed(String breed) {
        this.Breed = breed;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getImageLink() {
        return this.ImageLink;
    }

    public void setImageLink(String imageLink) {
        this.ImageLink = imageLink;
    }

    public String getFavFood() {
        return this.FavFood;
    }

    public String getServed(){
        return this.Served;
    }

    public void setFavFood(String favFood, String served) {
        this.FavFood = favFood;
        this.Served = served;
    }

    public String getColor() {
        return this.Colour;
    }

    public void setColor(String cl) {
        this.Colour = cl;
    }

    public String getSize() {
        return this.Size;
    }

    public void setSize(String size) {
        this.Size = size;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Breed);
        dest.writeInt(length);
        dest.writeString(ImageLink);
        dest.writeString(FavFood);
        dest.writeString(Served);
        dest.writeString(Colour);
        dest.writeString(Size);
        dest.writeInt(height);
    }
}
