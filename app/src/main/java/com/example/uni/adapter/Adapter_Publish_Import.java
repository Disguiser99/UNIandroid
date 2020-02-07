package com.example.uni.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uni.R;
import com.example.uni.util.Publish_import;

import java.util.List;

public class Adapter_Publish_Import extends ArrayAdapter<Publish_import> {
    private int resourceId;
    public Adapter_Publish_Import(Context context , int textViewResourceId, List<Publish_import>objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView (int position , View convertView , ViewGroup parent){
        Publish_import publish_import = getItem(position);
        View view ;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.publish_image = (ImageView)view.findViewById(R.id.publish_import_image);
            viewHolder.publish_name = (TextView)view.findViewById(R.id.publish_import_name);
            view.setTag(viewHolder);
        }else{
         view = convertView;
         viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.publish_image.setImageResource(publish_import.getImageId());
        viewHolder.publish_name.setText(publish_import.getName());
        return view ;
    }
    class ViewHolder{
        ImageView publish_image;
        TextView publish_name;
    }
}
