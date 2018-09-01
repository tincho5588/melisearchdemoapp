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
        inflater.inflate(R.layout.product_view,
                this, true);

        ImageView productThumbnail = findViewById(R.id.productThumbnail);
        TextView productTitle = findViewById(R.id.productTitleTextView);
        TextView productPrice = findViewById(R.id.productPrice);
        TextView productQuantity = findViewById(R.id.productQuantity);

        new DownloadImageTask(productThumbnail).execute(product.getThumbnail());
        productTitle.setText(product.getTitle());
        productTitle.setTextSize(20);
        productQuantity.setText("Cantidad Disponible: " + product.getAvailable_quantity());
        productQuantity.setTextSize(15);
        productPrice.setText("$" + product.getPrice());
        productPrice.setTextSize(15);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
