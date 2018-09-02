package com.mercadolibre.searchaplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mercadolibre.searchaplication.datamodel.MeliProductBrief;
import com.mercadolibre.searchaplication.view.ListItemProductView;

import java.util.ArrayList;
import java.util.List;

public class ProductsListAdapter extends BaseAdapter {
    private List<MeliProductBrief> mProductsList = new ArrayList<>();
    private Context mContext;

    public ProductsListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mProductsList.size();
    }

    @Override
    public MeliProductBrief getItem(int position) {
        return mProductsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        MeliProductBrief product = mProductsList.get(position);
        return new ListItemProductView(mContext, product);
    }

    public void addItem(MeliProductBrief product) {
        mProductsList.add(product);
    }
}
