package com.mercadolibre.searchaplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
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
    public static String PRODUCT_ID_EXTRA = "product_id_extra";

    private MeliFullProduct mFullProduct;
    private MeliProductDescription mMeliProductDescription;
    private ProgressDialog mLoadingDialog;

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

    /**
     * Listener to receive and process the API response for a search. Methods in here will get
     * called whenever we make an async GET request to the Meli API passing this object as argument.
     * This particular instance of the listener is meant to be used along with the
     * {@link #DESCRIPTION_COMMAND} constant.
     *
     * @see #DESCRIPTION_COMMAND
     * @see Meli#asyncGet(String, ApiRequestListener)
     */
    private final ApiRequestListener mDescriptionResultListener = new ApiRequestListener() {
        @Override
        public void onRequestProcessed(int requestCode, ApiResponse payload) {
            String responseText = payload.getContent();
            MeliProductDescription[] description = new Gson().fromJson(responseText, MeliProductDescription[].class);
            if (description.length > 0) {
                mMeliProductDescription = description[0];
            }
            updateUI();
            mLoadingDialog.dismiss();
        }

        @Override
        public void onRequestStarted(int requestCode) {
        }
    };

    /**
     * Listener to receive and process the API response for a search. Methods in here will get
     * called whenever we make an async GET request to the Meli API passing this object as argument.
     * This particular instance of the listener is meant to be used along with the
     * {@link #DESCRIPTION_COMMAND} constant.
     *
     * @see #DESCRIPTION_COMMAND
     * @see Meli#asyncGet(String, ApiRequestListener)
     */
    private final ApiRequestListener mItemResultListener = new ApiRequestListener() {
        @Override
        public void onRequestProcessed(int requestCode, ApiResponse payload) {
            String responseText = payload.getContent();
            mFullProduct = new Gson().fromJson(responseText, MeliFullProduct.class);
            Meli.asyncGet(String.format(DESCRIPTION_COMMAND, mFullProduct.getItem_id()), mDescriptionResultListener);
        }

        @Override
        public void onRequestStarted(int requestCode) {
            mLoadingDialog = ProgressDialog.show(ProductActivity.this, "",
                    "Cargando. Por favor aguarde...", true);
        }
    };

    private final View.OnClickListener mBuyButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast toast = Toast.makeText(ProductActivity.this, "Funcion No Disponible", Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent i = getIntent();
        String productIdExtra = i.getStringExtra(PRODUCT_ID_EXTRA);
        Meli.asyncGet(String.format(ITEM_COMMAND, productIdExtra), mItemResultListener);

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

    public void updateUI() {
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
}
