package com.mercadolibre.searchaplication.datamodel;

import com.google.gson.annotations.SerializedName;

public class MeliFullProduct extends MeliProductBrief {
    @SerializedName("pictures")
    private MeliProductPictures[] pictures;

    public MeliProductPictures[] getPictures() {
        return pictures;
    }

    public void setPictures(MeliProductPictures[] pictures) {
        this.pictures = pictures;
    }
}
