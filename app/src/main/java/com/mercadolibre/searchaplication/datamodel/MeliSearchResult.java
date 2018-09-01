package com.mercadolibre.searchaplication.datamodel;

import com.google.gson.annotations.SerializedName;

public class MeliSearchResult {
    @SerializedName("results")
    private MeliProductBrief[] results;

    @SerializedName("available_quantity")
    private String error;

    public MeliProductBrief[] getResults() {
        return results;
    }

    public void setResults(MeliProductBrief[] results) {
        this.results = results;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
