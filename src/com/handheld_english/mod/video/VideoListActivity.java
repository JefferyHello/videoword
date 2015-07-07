package com.handheld_english.mod.video;

import java.util.Date;
import java.util.List;

import com.handheld_english.AppSession; 
import com.handheld_english.R;
import com.handheld_english.StudyActivity;
import com.handheld_english.adapter.ArticleAdapter;
import com.handheld_english.dao.ArticleDAO;
import com.handheld_english.dao.LearnWordsDAO;
import com.handheld_english.dao.MyDatabaseHelper;
import com.handheld_english.dao.ReadArticleDAO;
import com.handheld_english.data.Article;
import com.handheld_english.data.Readarticle;
import com.handheld_english.data.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class VideoListActivity extends Activity {
	private MyDatabaseHelper dbHelper;
	private List<Article> articleList;
	public ListView listview;
	public ArticleAdapter adapter;
	public User user;
	public Article article = null;
	protected static final int MSG_REFRESH = 0;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == MSG_REFRESH) {
				List<Article> articles = (List<Article>) msg.obj;
				articleList.addAll(articles);
				adapter.notifyDataSetChanged();
				// listview.requestFocusFromTouch();
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.videolist);

		user = (User) AppSession.get(AppSession.USER);
		dbHelper = MyDatabaseHelper.instance(this);
		ArticleDAO dao = new ArticleDAO(dbHelper);
		articleList = dao.getLocalRecommentArticle();
		adapter = new ArticleAdapter(VideoListActivity.this,
				R.layout.article, articleList);		
		listview = (ListView) findViewById(R.id.listView1);

		setAdapter(listview);

		Button button1 = (Button) findViewById(R.id.back1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(VideoListActivity.this,
						StudyActivity.class);
				startActivity(intent);
			}
		});

		Button button2 = (Button) findViewById(R.id.bn_refresh);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				articleList.clear();
				adapter.notifyDataSetChanged();

				sendRequestWithHttpClient();
			}
		});

	}

	private void setAdapter(ListView listview) {
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 获取时间

				// long m;
				// Date date = new Date(m);

				Readarticle readarticle1 = new Readarticle();

				readarticle1.setDate(new Date());
				readarticle1.setUser(user);

				article = articleList.get(position);
				readarticle1.setArticle(article);

				// TODO: 插入阅读记录
				ReadArticleDAO dao = new ReadArticleDAO(dbHelper);
				dao.save(readarticle1);

				AppSession.put(AppSession.READARTICLE, readarticle1);

				Intent intent = new Intent(VideoListActivity.this,
						VideoPlayActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("a_id", article.getId());
				intent.putExtras(bundle);
				startActivity(intent);

				// Intent intent = new Intent(ArticallistActivity.this,
				// ArticlecontentActivity.class);
				// Bundle bundle = new Bundle();
				// bundle.putInt("a_id", article.getId());
				// intent.putExtras(bundle);
				// startActivity(intent);

			}
		});
	}

	private void sendRequestWithHttpClient() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {

					List<Article> articles = null;

					LearnWordsDAO learnWordsDAO = new LearnWordsDAO(dbHelper);

					List<String> words = learnWordsDAO.getTargetWords(user.getUId());

					ArticleDAO articleDAO = new ArticleDAO(dbHelper);

					articles = articleDAO.getRemoteArticles(words);

					articleDAO.saveLocalArticles(articles);

					if (articles != null) {
						Message msg = new Message();
						msg.what = MSG_REFRESH;
						msg.obj = articles;
						handler.sendMessage(msg);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}