package com.mercadolibre.searchaplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.mercadolibre.android.sdk.ApiRequestListener;
import com.mercadolibre.android.sdk.ApiResponse;
import com.mercadolibre.android.sdk.Meli;
import com.mercadolibre.searchaplication.adapter.ProductsListAdapter;
import com.mercadolibre.searchaplication.datamodel.MeliProductBrief;
import com.mercadolibre.searchaplication.datamodel.MeliSearchResult;

public class MainActivity extends AppCompatActivity {

    private ProductsListAdapter mAdapter;
    private ProgressDialog mLoadingDialog;

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

        }
    };

    /**
     * Listener for the SearchView widget on the toolbar. Methods in this listener will be called
     * when the user writes or submits text on the SearchView text field.
     */
    private final SearchView.OnQueryTextListener mSearchViewListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            mLoadingDialog = ProgressDialog.show(MainActivity.this, "",
                    "Cargando. Por favor aguarde...", true);
            String searchCommand = String.format(SEARCH_COMMAND, s);
            mAdapter.emptyAdapter();
            Meli.asyncGet(searchCommand, mSearchResultListener);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    };

    /**
     * Listener for the ListView on this activity. Methods on this listener will be called when the
     * user clicks an item of the list.
     */
    private final AdapterView.OnItemClickListener mListViewClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(MainActivity.this, ProductActivity.class);
            intent.putExtra(ProductActivity.PRODUCT_ID_EXTRA, mAdapter.getItem(i).getItem_id());
            startActivity(intent);
        }
    };

    MenuItem.OnMenuItemClickListener mSearchButtonClickListener = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            Log.d ("tag", "clicked!");
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Meli.initializeSDK(getApplicationContext());

        mAdapter = new ProductsListAdapter(getApplicationContext());
        ListView listView = findViewById(R.id.resultsListView);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(mListViewClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        SearchView searchViewAndroidActionBar = (SearchView) searchViewItem.getActionView();

        // We want the search view expanded by default
        searchViewAndroidActionBar.setIconifiedByDefault(false);

        searchViewAndroidActionBar.setSubmitButtonEnabled(true);
        searchViewAndroidActionBar.setOnQueryTextListener(mSearchViewListener);

        return super.onCreateOptionsMenu(menu);
    }
}
