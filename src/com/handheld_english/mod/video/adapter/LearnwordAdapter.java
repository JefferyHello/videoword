package com.handheld_english.mod.video.adapter;
import java.util.List;

import com.handheld_english.R;   
import com.handheld_english.data.Learnword;
import com.handheld_english.data.Word;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class LearnwordAdapter extends ArrayAdapter<Learnword>{
	private int resourceId;
	public LearnwordAdapter(Context context,int textViewResourceId,List<Learnword> objects)
	{
		super(context,textViewResourceId,objects);
		resourceId=textViewResourceId;
	}
	@Override
	public View getView(int position,View convertView,ViewGroup parent)
	{
		Learnword learn=getItem(position);
		Word word = learn.getWord();
	 View view;
	 ViewHolder viewHolder;
	 if(convertView==null)
	  {
	   view=LayoutInflater.from(getContext()).inflate(resourceId,null);
	   viewHolder=new ViewHolder();
	   viewHolder.word=(TextView)view.findViewById(R.id.word);
	   viewHolder.wordTranslation=(TextView)view.findViewById(R.id.word_explain);
	   view.setTag(viewHolder);
	  }
	 else
	  {
	   view=convertView;
	   viewHolder=(ViewHolder)view.getTag();
	  }
	 viewHolder.word.setText(   word.getWord() +" ("+ learn.getScore()+"∑÷)" );
	 viewHolder.wordTranslation.setText("Ω‚ Õ£∫"+word.getTranslation());
	 return view;
	}
	class ViewHolder
	{
	 TextView word;
	 TextView wordTranslation;
	}

}
