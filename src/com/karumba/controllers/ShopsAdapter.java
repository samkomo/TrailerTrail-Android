package com.karumba.controllers;

/**
 */


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.karumba.trailertrail.R;


public class ShopsAdapter extends BaseAdapter implements ListAdapter {
    private final Activity activity;
    private final JSONArray jsonArray;
    
	private LayoutInflater layoutInflater;


    public ShopsAdapter(Activity activity, JSONArray jsonArray) {
        assert activity != null;
        assert jsonArray != null;

        this.jsonArray = jsonArray;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        if (null == jsonArray)
            return 0;
        else
            return jsonArray.length();
    }

    @Override
    public JSONObject getItem(int position) {
        if (null == jsonArray)
            return null;
        else
            return jsonArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        JSONObject jsonObject = getItem(position);

        return jsonObject.optLong("id");

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = activity.getLayoutInflater().inflate(
                    R.layout.list_row_shops,null);

        TextView tvName = (TextView) convertView.findViewById(R.id.tvShopNames); // title
        TextView tvDesc = (TextView) convertView.findViewById(R.id.tvShopDescs); // title
        ImageView tvImage = (ImageView) convertView.findViewById(R.id.imgShopLogos); // title

//        tvName.setTypeface(Fonting.getFont(parent.getContext(), Fonting.KEY_LIGHT));
//        tvPrice.setTypeface(Fonting.getFont(parent.getContext(), Fonting.KEY_LIGHT));
        
        JSONObject json_data = getItem(position);
        
        Log.i("Json output", json_data.toString());
        if (null != json_data) {
            try {
                String strName = json_data.getString("shop_name");
                String strDesc = json_data.getString("shop_info");
                String strImage = json_data.getString("shop_icon");

                try {
                    tvName.setText(strName);
                    tvDesc.setText(strDesc);
                    byte[] myByteArray = Base64
                            .decode(strImage,
                                    Base64.DEFAULT);

                    Bitmap b = BitmapFactory.decodeByteArray(
                            myByteArray, 0,
                            myByteArray.length);

                    Bitmap scaled = Bitmap.createScaledBitmap(b, 256, 256, true);

                    tvImage.setImageBitmap(scaled);
                } catch (NullPointerException e) {
                    tvName.setText(strName);
                    tvDesc.setText(strDesc);

                    String strImage_placeholder="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";
                    byte[] myByteArray = Base64
                            .decode(strImage_placeholder,
                                    Base64.DEFAULT);

                    Bitmap b = BitmapFactory.decodeByteArray(
                            myByteArray, 0,
                            myByteArray.length);

                    Bitmap scaled = Bitmap.createScaledBitmap(b, 256, 256, true);

                    tvImage.setImageBitmap(scaled);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }
}
