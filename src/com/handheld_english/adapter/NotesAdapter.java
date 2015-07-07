package com.handheld_english.adapter;
import java.util.List;

import com.handheld_english.R;
import com.handheld_english.R.id;
import com.handheld_english.data.Notes;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class NotesAdapter extends ArrayAdapter<Notes>{
	private int resourceId;
	public NotesAdapter(Context context,int textViewResourceId,List<Notes> objects)
	{
		super(context,textViewResourceId,objects);
		resourceId=textViewResourceId;
	}
	@Override
	public View getView(int position,View convertView,ViewGroup parent)
	{
	 Notes notes=getItem(position);
	 View view;
	 ViewHolder viewHolder;
	 if(convertView==null)
	  {
	   view=LayoutInflater.from(getContext()).inflate(resourceId,null);
	   viewHolder=new ViewHolder();
	   viewHolder.noteID=(TextView)view.findViewById(R.id.note_id);
	   viewHolder.noteContent=(TextView)view.findViewById(R.id.note);
	   view.setTag(viewHolder);
	  }
	 else
	  {
	   view=convertView;
	   viewHolder=(ViewHolder)view.getTag();
	  }
	 viewHolder.noteID.setText(String.valueOf(notes.getId()));
	 viewHolder.noteContent.setText(notes.getNotes());

	 return view;
	}
	class ViewHolder
	{
	 TextView noteID;
	 TextView noteContent;
	}

}
