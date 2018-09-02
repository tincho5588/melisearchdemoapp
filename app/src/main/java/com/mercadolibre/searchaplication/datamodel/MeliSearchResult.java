package com.mercadolibre.searchaplication.datamodel;

import com.google.gson.annotations.SerializedName;

public class MeliSearchResult {
    @SerializedName("results")
    private MeliProductBrief[] results;

    public MeliProductBrief[] getResults() {
        return results;
    }

    public void setResults(MeliProductBrief[] results) {
        this.results = results;
    }
}
