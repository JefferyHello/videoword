package com.handheld_english.adapter;
import java.util.List;

import com.handheld_english.R;
import com.handheld_english.R.id;
import com.handheld_english.data.Article;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class ArticleAdapter extends ArrayAdapter<Article>{
	private int resourceId;
	public ArticleAdapter(Context context,int textViewResourceId,List<Article> objects)
	{
		super(context,textViewResourceId,objects);
		resourceId=textViewResourceId;
	}
	@Override
	public View getView(int position,View convertView,ViewGroup parent)
	{
	 Article article=getItem(position);
	 View view;
	 ViewHolder viewHolder;
	 if(convertView==null)
	  {
	   view=LayoutInflater.from(getContext()).inflate(resourceId,null);
	   viewHolder=new ViewHolder();
	   viewHolder.articleTitle=(TextView)view.findViewById(R.id.article_title);
//	   viewHolder.articleNumber=(TextView)view.findViewById(R.id.article_number);
	   view.setTag(viewHolder);
	  }
	 else
	  {
	   view=convertView;
	   viewHolder=(ViewHolder)view.getTag();
	  }
	 viewHolder.articleTitle.setText(article.getTitle());
//	 viewHolder.articleNumber.setText("µ¥´ÊÊý£º"+article.getNumber());
	 return view;
	}
	class ViewHolder
	{
	 TextView articleTitle;
//	 TextView articleNumber;
	}

}
