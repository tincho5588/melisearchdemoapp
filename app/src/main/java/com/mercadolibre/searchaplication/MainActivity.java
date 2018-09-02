package com.mercadolibre.searchaplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mercadolibre.android.sdk.Meli;

public class MainActivity extends AppCompatActivity {

    /**
     * Listener for the SearchView widget on the toolbar. Methods in this listener will be called
     * when the user writes or submits text on the SearchView text field.
     */
    private final SearchView.OnQueryTextListener mSearchViewListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            Intent intent = new Intent(MainActivity.this, SearchResultsActivity.class);
            intent.putExtra(SearchResultsActivity.SEARCH_TEXT_EXTRA, s);
            MainActivity.this.startActivity(intent);

            return true;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Meli.initializeSDK(getApplicationContext());
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        SearchView searchViewAndroidActionBar = (SearchView) searchViewItem.getActionView();

        // We want the search view to just expanded when clicked
        searchViewAndroidActionBar.setIconifiedByDefault(false);

        searchViewAndroidActionBar.setSubmitButtonEnabled(true);
        searchViewAndroidActionBar.setOnQueryTextListener(mSearchViewListener);
        return super.onCreateOptionsMenu(menu);
    }
}
