package com.mercadolibre.searchaplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.mercadolibre.android.sdk.ApiRequestListener;
import com.mercadolibre.android.sdk.ApiResponse;
import com.mercadolibre.android.sdk.Meli;
import com.mercadolibre.searchaplication.adapter.ProductsListAdapter;
import com.mercadolibre.searchaplication.datamodel.MeliProductBrief;
import com.mercadolibre.searchaplication.datamodel.MeliSearchResult;

public class SearchResultsActivity extends AppCompatActivity {
    public static final String SEARCH_TEXT_EXTRA = "search_text_extra";

    private ProductsListAdapter mAdapter;

    /**
     * Search command for the Meli API. Use this constant along with {@link String#format(String, Object...)}
     * to insert the product you want to search into the GET command.
     *
     * @see String#format(String, Object...)
     * @see Meli#get(String)
     * @see Meli#asyncGet(String, ApiRequestListener)
     */
    private final String SEARCH_COMMAND = "/sites/MLA/search?q=%s";

    /**
     * Listener to receive and process the API response for a search. Methods in here will get
     * called whenever we make an async GET request to the Meli API passing this object as argument.
     * This particular instance of the listener is meant to be used along with the
     * {@link #SEARCH_COMMAND} constant.
     *
     * @see #SEARCH_COMMAND
     * @see Meli#asyncGet(String, ApiRequestListener)
     */
    private final ApiRequestListener mSearchResultListener = new ApiRequestListener() {
        private ProgressDialog mLoadingDialog;

        @Override
        public void onRequestProcessed(int requestCode, ApiResponse payload) {
            String responseText = payload.getContent();
            MeliSearchResult searchResult = new Gson().fromJson(responseText, MeliSearchResult.class);
            for (MeliProductBrief product : searchResult.getResults()) {
                mAdapter.addItem(product);
            }
            mAdapter.notifyDataSetChanged();
            mLoadingDialog.dismiss();
        }

        @Override
        public void onRequestStarted(int requestCode) {
            mLoadingDialog = ProgressDialog.show(SearchResultsActivity.this, "",
                    getString(R.string.loading_dialog), true);
        }
    };

    /**
     * Listener for the ListView on this activity. Methods on this listener will be called when the
     * user clicks an item of the list.
     */
    private final ProductsListAdapter.ItemClickListener mItemClickListener = new ProductsListAdapter.ItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            Intent intent = new Intent(SearchResultsActivity.this, ProductActivity.class);
            intent.putExtra(ProductActivity.PRODUCT_ID_EXTRA, mAdapter.getItem(position).getItem_id());
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        RecyclerView itemsContainer = findViewById(R.id.itemsContainer);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mAdapter = new ProductsListAdapter(this, mItemClickListener);

        itemsContainer.setLayoutManager(layoutManager);
        itemsContainer.setAdapter(mAdapter);

        // Set the back button on the action bar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        handleSearch();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleSearch();
    }

    private void handleSearch() {
        Intent intent = getIntent();
        String textToSearch = intent.getStringExtra(SEARCH_TEXT_EXTRA);
        if (textToSearch != null) {
            String searchCommand = String.format(SEARCH_COMMAND, textToSearch);
            Meli.asyncGet(searchCommand, mSearchResultListener);
        }
    }
}
