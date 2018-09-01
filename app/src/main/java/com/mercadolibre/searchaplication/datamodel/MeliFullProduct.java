package com.mercadolibre.searchaplication.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class MeliFullProduct {
    @SerializedName("id")
    private String item_id;

    @SerializedName("title")
    private String title;

    @SerializedName("price")
    private double price;

    @SerializedName("descriptions")
    private MeliDescriptionIDs[] descriptions;

    @SerializedName("pictures")
    private MeliProductPictures[] pictures;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public MeliDescriptionIDs[] getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(MeliDescriptionIDs[] descriptions) {
        this.descriptions = descriptions;
    }

    public MeliProductPictures[] getPictures() {
        return pictures;
    }

    public void setPictures(MeliProductPictures[] pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return "MeliFullProduct{" +
                "item_id='" + item_id + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", descriptions=" + Arrays.toString(descriptions) +
                ", pictures=" + Arrays.toString(pictures) +
                '}';
    }
}
