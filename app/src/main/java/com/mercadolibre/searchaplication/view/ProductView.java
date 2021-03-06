package com.mercadolibre.searchaplication.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mercadolibre.searchaplication.R;
import com.mercadolibre.searchaplication.common.DownloadImageTask;
import com.mercadolibre.searchaplication.datamodel.MeliFullProduct;
import com.mercadolibre.searchaplication.datamodel.MeliProductDescription;

public class ProductView extends RelativeLayout {
    public ProductView(Context context, MeliFullProduct product, MeliProductDescription description ) {
        super(context);
        initViews(context, product, description);
    }

    private void initViews(Context context, MeliFullProduct product, MeliProductDescription description) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        inflater.inflate(R.layout.product_view, this, true);

        ImageView productImage = findViewById(R.id.productImage);
        TextView productTitle = findViewById(R.id.productTitleTextView);
        TextView productPrice = findViewById(R.id.productPriceTextView);
        TextView productQuantity = findViewById(R.id.productQuantityTextView);
        TextView productDescription = findViewById(R.id.descriptionBody);
        String price = context.getString(R.string.currency_symbol) + Double.toString(product.getPrice());
        String quantity = context.getString(R.string.available_quantity) + Integer.toString(product.getAvailable_quantity());

        if (product.getPictures() != null) {
            new DownloadImageTask(productImage).execute(product.getPictures()[0].getUrl());
        } else {
            new DownloadImageTask(productImage).execute(product.getThumbnail());
        }
        if (description != null) {
            productDescription.setText(description.getPlain_text());
        }
        productTitle.setText(product.getTitle());
        productPrice.setText(price);
        productQuantity.setText(quantity);
    }

    public void setBuyButtonOnClickListener(OnClickListener listener) {
        Button button = findViewById(R.id.buyButton);
        button.setOnClickListener(listener);
    }
}
