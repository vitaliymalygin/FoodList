package com.example.vitaliy.foodlist;

import android.content.Context;
import android.graphics.Color;

import java.util.*;



import android.view.*;
import android.widget.*;

public class ShowFoodAdapter extends ArrayAdapter<Dish> {

    int resource;

    public ShowFoodAdapter(Context _context, int _resource,
                           List<Dish> _foodItems) {
        super(_context, _resource, _foodItems);
        resource = _resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout showItemView;

        Dish item = getItem(position);

        if (convertView == null) {
            showItemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
                    inflater);
            vi.inflate(resource, showItemView, true);
        } else {
            showItemView = (LinearLayout) convertView;
        }

    //    RelativeLayout back = (RelativeLayout) showItemView
    //            .findViewById(R.id.background);

        String name = item.getName();
        String left = name;
        String carbo = item.getCarbo();
        String fat = item.getLipid();
        String prot = item.getProtein();
        String right=carbo;
/**
        if (weight != 0) {
            right = String.valueOf(weight) + " гр.";

        } else {
            if (item.getProfit() == 0) {
                right = "";
            } else {
                right = String.valueOf(carbo) + "/" + String.valueOf(fat) + "/"
                        + String.valueOf(prot);
            }

        }**/

        TextView rightView = (TextView) showItemView.findViewById(R.id.textView1);
        TextView leftView = (TextView) showItemView.findViewById(R.id.textView1);

        rightView.setText(right);
        leftView.setText(left);
        // check weather this product is good, then use the color
        /**int profit = item.getProfit();
        if (profit < 4 && profit > 0) {
            back.setBackgroundColor(getContext().getResources().getColor(
                    R.color.back_red));
        } else if (profit > 7) {
            back.setBackgroundColor(getContext().getResources().getColor(
                    R.color.back_green));
        } else if (profit < 8 && profit > 3) {
            back.setBackgroundColor(getContext().getResources().getColor(
                    R.color.back_yellow));
        } else {
            back.setBackgroundColor(Color.WHITE);
        }**/
        return showItemView;

    }
}