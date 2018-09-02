package com.mercadolibre.searchaplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mercadolibre.searchaplication.R;
import com.mercadolibre.searchaplication.common.DownloadImageTask;
import com.mercadolibre.searchaplication.datamodel.MeliProductBrief;

import java.io.InputStream;

public class ListItemProductView extends RelativeLayout {
    public static String TAG = ListItemProductView.class.getSimpleName();

    public ListItemProductView(Context context, MeliProductBrief product) {
        super(context);
        initViews(context, product);
    }

    private void initViews(Context context, MeliProductBrief product) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.product_list_view_item,
                this, true);

        ImageView productThumbnail = findViewById(R.id.productThumbnail);
        TextView productTitle = findViewById(R.id.productTitleTextView);
        TextView productPrice = findViewById(R.id.productPrice);
        TextView productQuantity = findViewById(R.id.productQuantity);

        new DownloadImageTask(productThumbnail).execute(product.getThumbnail());
        productTitle.setText(product.getTitle());
        productQuantity.setText("Cantidad Disponible: " + product.getAvailable_quantity());
        productPrice.setText("$" + product.getPrice());
    }

}
