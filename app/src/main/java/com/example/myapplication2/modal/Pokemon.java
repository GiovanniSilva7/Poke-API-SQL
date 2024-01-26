package com.example.myapplication2.modal;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Pokemon implements Parcelable {
    private String name;
    private String imageUrl;  // URL da imagem do Pokémon
    private double weight;
    private double height;
    private List<String> types;  // Tipos do Pokémon

    public Pokemon(String name, String imageUrl, double weight, double height, List<String> types) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.weight = weight;
        this.height = height;
        this.types = types;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
    protected Pokemon(Parcel in) {
        name = in.readString();
        imageUrl = in.toString();
    }

    public static final Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeDouble(weight);
        dest.writeDouble(height);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}


