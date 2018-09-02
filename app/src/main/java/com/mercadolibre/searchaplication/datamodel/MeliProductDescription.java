package com.mercadolibre.searchaplication.datamodel;

import com.google.gson.annotations.SerializedName;

public class MeliProductDescription {
    @SerializedName("plain_text")
    private String plain_text;

    public String getPlain_text() {
        return plain_text;
    }

    public void setPlain_text(String plain_text) {
        this.plain_text = plain_text;
    }
}
