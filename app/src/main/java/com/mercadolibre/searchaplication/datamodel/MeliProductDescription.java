package com.mercadolibre.searchaplication.datamodel;

import com.google.gson.annotations.SerializedName;

public class MeliProductDescription {
    @SerializedName("plain_text")
    private String plain_text;

    @SerializedName("error")
    private String error;

    public String getPlain_text() {
        return plain_text;
    }

    public void setPlain_text(String plain_text) {
        this.plain_text = plain_text;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
