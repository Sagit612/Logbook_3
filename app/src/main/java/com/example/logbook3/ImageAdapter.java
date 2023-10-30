package com.example.logbook3;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private TypedArray mImages;
    private SparseBooleanArray mSelectedItems;
    private int mSelectedPosition = -1;

    public ImageAdapter(Context context, TypedArray images) {
        mContext = context;
        mImages = images;
        mSelectedItems = new SparseBooleanArray();
    }

    @Override
    public int getCount() {
        return mImages.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.grid_item_layout, null);
            ImageView imageView = itemView.findViewById(R.id.imageView);
            RadioButton radioButton = itemView.findViewById(R.id.radioButton);

            radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                mSelectedItems.put(position, isChecked);
                notifyDataSetChanged();
            });
        }

        ImageView imageView = itemView.findViewById(R.id.imageView);
        RadioButton radioButton = itemView.findViewById(R.id.radioButton);

        imageView.setImageResource(mImages.getResourceId(position, 0));

        // Set the checked state of the RadioButton
        radioButton.setChecked(position == mSelectedPosition);

        radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (mSelectedPosition != -1 && mSelectedPosition != position) {
                    RadioButton previousRadioButton = parent.getChildAt(mSelectedPosition)
                            .findViewById(R.id.radioButton);
                    if (previousRadioButton != null) {
                        previousRadioButton.setChecked(false);
                    }
                }
                mSelectedPosition = position;
            }
        });

        return itemView;
    }
    public int getSelectedResourceId() {
        if (mSelectedPosition != -1) {
            return mImages.getResourceId(mSelectedPosition, -1);
        }
        return -1; // Return -1 if no image is selected
    }
}