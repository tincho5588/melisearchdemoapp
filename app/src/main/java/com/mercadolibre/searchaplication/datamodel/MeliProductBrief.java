package com.mercadolibre.searchaplication.datamodel;

import com.google.gson.annotations.SerializedName;

public class MeliProductBrief {
    @SerializedName("id")
    private String item_id;

    @SerializedName("title")
    private String title;

    @SerializedName("price")
    private double price;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("available_quantity")
    private int available_quantity;

    public String getItem_id() {
        return item_id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(int available_quantity) {
        this.available_quantity = available_quantity;
    }
}
