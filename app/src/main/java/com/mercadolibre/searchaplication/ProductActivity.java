package com.mercadolibre.searchaplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mercadolibre.android.sdk.ApiRequestListener;
import com.mercadolibre.android.sdk.ApiResponse;
import com.mercadolibre.android.sdk.Meli;
import com.mercadolibre.searchaplication.datamodel.MeliFullProduct;
import com.mercadolibre.searchaplication.datamodel.MeliProductDescription;
import com.mercadolibre.searchaplication.view.ProductView;

public class ProductActivity extends AppCompatActivity {
    public static final String PRODUCT_ID_EXTRA = "product_id_extra";

    private MeliFullProduct mFullProduct;
    private MeliProductDescription mMeliProductDescription;

    /**
     * Command to get the description of an item using the Meli API. Use this constant along with
     * {@link String#format(String, Object...)} to insert the product you want to search into
     * the GET command.
     *
     * @see String#format(String, Object...)
     * @see Meli#get(String)
     * @see Meli#asyncGet(String, ApiRequestListener)
     */
    private final String DESCRIPTION_COMMAND = "/items/%s/descriptions";

    /**
     * Command to get the full information of an item using the Meli API. Use this constant along with
     * {@link String#format(String, Object...)} to insert the product you want to search into
     * the GET command.
     *
     * @see String#format(String, Object...)
     * @see Meli#get(String)
     * @see Meli#asyncGet(String, ApiRequestListener)
     */
    private final String ITEM_COMMAND = "/items/%s";

    private final View.OnClickListener mBuyButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast toast = Toast.makeText(ProductActivity.this, getString(R.string.function_not_available), Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent i = getIntent();
        String productIdExtra = i.getStringExtra(PRODUCT_ID_EXTRA);
        new GetProductTask().execute(productIdExtra);

        // Set the back button on the action bar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private void updateUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ProductView productView = new ProductView(getApplicationContext(), mFullProduct, mMeliProductDescription);
                ScrollView container = findViewById(R.id.productContainer);
                container.addView(productView);
                productView.setBuyButtonOnClickListener(mBuyButtonClickListener);
            }
        });
    }

    public class GetProductTask extends AsyncTask<String, Void, Boolean> {
        private ApiResponse mProductResponse;
        private ApiResponse mDescriptionResponse;
        private ProgressDialog mLoadingDialog;

        @Override
        protected void onPreExecute() {
            mLoadingDialog = ProgressDialog.show(ProductActivity.this, "",
                    getString(R.string.loading_dialog), true);
        }

        protected Boolean doInBackground(String... productId) {
            mProductResponse = Meli.get(String.format(ITEM_COMMAND, productId));
            mDescriptionResponse = Meli.get(String.format(DESCRIPTION_COMMAND, productId));
            return true;
        }

        protected void onPostExecute(Boolean arg) {
            String productResponseText = mProductResponse.getContent();
            String descriptionResponseText = mDescriptionResponse.getContent();
            mFullProduct = new Gson().fromJson(productResponseText, MeliFullProduct.class);
            MeliProductDescription[] description = new Gson().fromJson(descriptionResponseText, MeliProductDescription[].class);
            if (description.length > 0) {
                mMeliProductDescription = description[0];
            }
            updateUI();
            mLoadingDialog.dismiss();
        }
    }
}
