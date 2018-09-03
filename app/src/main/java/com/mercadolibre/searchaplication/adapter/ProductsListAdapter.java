package com.mercadolibre.searchaplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mercadolibre.searchaplication.R;
import com.mercadolibre.searchaplication.common.DownloadImageTask;
import com.mercadolibre.searchaplication.datamodel.MeliProductBrief;

import java.util.ArrayList;
import java.util.List;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductViewHolder> {
    private static ItemClickListener mItemClickListener;
    private final Context mContext;
    private List<MeliProductBrief> mProductsList;

    public ProductsListAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mProductsList = new ArrayList<>();
        mItemClickListener = listener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.product_list_view_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        MeliProductBrief product = mProductsList.get(position);
        String price = mContext.getString(R.string.currency_symbol) + Double.toString(product.getPrice());
        String quantity = mContext.getText(R.string.available_quantity) + Integer.toString(product.getAvailable_quantity());

        new DownloadImageTask(holder.productThumbnail).execute(product.getThumbnail());
        holder.productTitle.setText(product.getTitle());
        holder.productQuantity.setText(quantity);
        holder.productPrice.setText(price);
    }

    @Override
    public int getItemCount() {
        return mProductsList.size();
    }

    public void addItem(MeliProductBrief product) {
        mProductsList.add(product);
    }

    public MeliProductBrief getItem(int position) {
        return mProductsList.get(position);
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView productThumbnail;
        private TextView productTitle;
        private TextView productQuantity;
        private TextView productPrice;

        ProductViewHolder(View itemView) {
            super(itemView);
            productThumbnail = itemView.findViewById(R.id.productCardViewThumbnail);
            productTitle = itemView.findViewById(R.id.productCardViewTitle);
            productQuantity = itemView.findViewById(R.id.productCardViewQuantity);
            productPrice = itemView.findViewById(R.id.productCardViewPrice);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View v, int position);
    }
}
