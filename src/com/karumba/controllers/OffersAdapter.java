package com.karumba.controllers;

/**
 * Created by sammy on 7/3/2014.
 */


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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


public class OffersAdapter extends BaseAdapter implements ListAdapter {
    private final Activity activity;
    private final JSONArray jsonArray;

    public OffersAdapter(Activity activity, JSONArray jsonArray) {
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
                    R.layout.list_item_offer, null);
        

        TextView tvName = (TextView) convertView.findViewById(R.id.tvOfferName); // title
        TextView tvMessage = (TextView) convertView.findViewById(R.id.tvOfferMessage); // title

//        tvName.setTypeface(Fonting.getFont(parent.getContext(), Fonting.KEY_LIGHT));
//        tvPrice.setTypeface(Fonting.getFont(parent.getContext(), Fonting.KEY_LIGHT));


        JSONObject json_data = getItem(position);
        if (null != json_data) {
            try {
                String strName = json_data.getString("items_name");
                String strMessage = json_data.getString("offer_info");

                tvName.setText(strName);
                tvMessage.setText(strMessage);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }
}
