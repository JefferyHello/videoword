package com.handheld_english.adapter;
import java.util.List;
import com.handheld_english.R;
import com.handheld_english.R.id;
import com.handheld_english.data.Video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class VideoAdapter extends ArrayAdapter<Video>{
	private int resourceId;
	public VideoAdapter(Context context,int textViewResourceId,List<Video> objects)
	{
		super(context,textViewResourceId,objects);
		resourceId=textViewResourceId;
	}
	@Override
	public View getView(int position,View convertView,ViewGroup parent)
	{
		Video video=getItem(position);
	 View view;
	 ViewHolder viewHolder;
	 if(convertView==null)
	  {
	   view=LayoutInflater.from(getContext()).inflate(resourceId,null);
	   viewHolder=new ViewHolder();
	   viewHolder.videoImage=(ImageView) view.findViewById(R.id.video_picture);
	   viewHolder.videoName=(TextView)view.findViewById(R.id.video_name);
	   view.setTag(viewHolder);
	  }
	 else
	  {
	   view=convertView;
	   viewHolder=(ViewHolder)view.getTag();
	  }
	 viewHolder.videoImage.setImageResource(video.getImageVideo());
	 viewHolder.videoName.setText(video.getName());
	 return view;
	}
	class ViewHolder
	{
	 
	  ImageView videoImage;
	TextView videoName;
	}

}
