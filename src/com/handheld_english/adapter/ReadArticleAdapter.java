package com.handheld_english.adapter;

import java.util.List;

import com.handheld_english.R;
import com.handheld_english.R.id;
import com.handheld_english.data.SimpleReadarticle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ReadArticleAdapter extends ArrayAdapter<SimpleReadarticle> {
	private int resourceId;

	public ReadArticleAdapter(Context context, int textViewResourceId,
			List<SimpleReadarticle> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SimpleReadarticle read_article = getItem(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.articleTitle = (TextView) view
					.findViewById(R.id.article_title);
			viewHolder.readTime = (TextView) view
					.findViewById(R.id.read_article_time);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}

		viewHolder.articleTitle.setText(read_article.getTitle());
		String displayTime = read_article.getDatetime().toLocaleString();
		viewHolder.readTime.setText("ÔÄ¶ÁÊ±¼ä£º" + displayTime);
		return view;
	}

	class ViewHolder {
		TextView articleTitle;
		TextView readTime;
	}

}
