package com.handheld_english.mod.article;

import com.handheld_english.AppSession;
import com.handheld_english.Config;
import com.handheld_english.R;
import com.handheld_english.dao.MyDatabaseHelper;
import com.handheld_english.dao.NotesDAO;
import com.handheld_english.dao.WordDAO;
import com.handheld_english.data.Article;
import com.handheld_english.data.Readarticle;
import com.handheld_english.data.Word;
import com.handheld_english.mod.word.MywordInReadActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class ArticlecontentActivity extends Activity implements OnClickListener {
	protected static final int MSG_WEBVIEW_READY = 1;
	protected static final int MSG_WEBVIEW_WAIT = 2;
	
	private MyDatabaseHelper dbHelper;
	LinearLayout lay2;
	LinearLayout translationPanel;
	boolean flag = false;

	private String[] items = new String[] { "继续阅读", "记忆单词" };
	Article article;
	protected WebView webView;
	
	String webviewContentBuffer;	
	String webviewContent;
	 
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			switch (msg.what) {
			case MSG_WEBVIEW_WAIT:
				
				webviewContent = "<html><body>"+webviewContent+" (loading..)</body></html>";
				webView.loadData(webviewContent, "text/html", null);
				break;
			case MSG_WEBVIEW_READY:
				
				webviewContent = "<html><body>"+webviewContent+"</body></html>";
				webView.loadData(webviewContent, "text/html", null);
				break;
				
			}
		}
	};
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.articlecontent);

		dbHelper = MyDatabaseHelper.instance(this);

		Readarticle readarticle = (Readarticle) AppSession
				.get(AppSession.READARTICLE);

		/*
		 * Intent intent = getIntent(); Bundle bundle = intent.getExtras(); a_id
		 * = bundle.getInt("a_id");
		 */

		
		final TextView textView = (TextView) findViewById(R.id.textView2);
		// textView.setVisibility(View.GONE);
		translationPanel = (LinearLayout) findViewById(R.id.article1_layout);

		article = readarticle.getArticle();

		
		webView = (WebView) findViewById(R.id.webView1);
		
		initWebView(webView, textView);
		
		webView.loadData("(loading..)", "text/html", null);

		// 返回
		Button backBtn = (Button) findViewById(R.id.back1);
		backBtn.setOnClickListener(this);

		Button closeTranslationBtn = (Button) findViewById(R.id.no);
		closeTranslationBtn.setOnClickListener(this);

		// 笔记
		lay2 = (LinearLayout) findViewById(R.id.article2_layout);
		Button btn_note1 = (Button) findViewById(R.id.note1);
		btn_note1.setOnClickListener(this);
		
		// 保存笔记
		Button btn_save_note = (Button) findViewById(R.id.save);
		btn_save_note.setOnClickListener(this);

		// 完成阅读
		Button finishBtn = (Button) findViewById(R.id.bn_ok);
		finishBtn.setOnClickListener( this ); 
		
		 
		new Thread(new Runnable() {
			
			@Override
			public void run() {

				linkWords(article.getContent());
				
				Message msg = new Message();
				msg.what = MSG_WEBVIEW_READY;
				handler.sendMessage(msg);
			}
			}).start();

        
	}
	
	private void initWebView(WebView webView, final TextView textView) {
		webView.getSettings().setSupportZoom(true);// 双击缩放功能
		
		webView.getSettings().setBuiltInZoomControls(true); 
		webView.setInitialScale(100);

		WebSettings settings = webView.getSettings(); // 自动换行
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

		webView.setHorizontalScrollBarEnabled(false);
		webView.setHorizontalScrollbarOverlay(false);
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		
		webView.setWebViewClient(new WebViewClient() {
			/*
			 * 此处能拦截超链接的url,即拦截href请求的内容.
			 */
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
 
				// 获取文章阅读记录id

				// 从后台获取词义
				WordDAO dao = new WordDAO(dbHelper);
				Word word = dao.getWord(  url);

				Readarticle readarticle1 = (Readarticle) AppSession
						.get(AppSession.READARTICLE);
 
				dao.saveQuerywords(word.getId(), readarticle1.getId());
				
				translationPanel.setVisibility(View.VISIBLE);
				textView.setText(word.getTranslation()+"\n 例句： "+word.getExplain());

				// textView.setVisibility(View.VISIBLE);
				// textView.setText(explain);
				return true;
			}
		});
		
	}

	private String linkWords(String s0) {
		WordDAO word_dao = new WordDAO(dbHelper);

		s0 = s0.replace("?", " ? ");
		s0 = s0.replace(".", " . ");
		s0 = s0.replace(",", " , ");
		s0 = s0.replace("-", " - ");
		s0 = s0.replace("_", " _ ");
		s0 = s0.replace("\"", " \" ");
		s0 = s0.replace("'", " ' ");
		

		String[] words = s0.split(" ");
		webviewContentBuffer = "";//"<html><body>";
		
		int count = 0;
		for (String w : words) {
			count++;
			if (count == 100)
			{
				
				webviewContent = webviewContentBuffer;
				Message msg = new Message();
				msg.what = MSG_WEBVIEW_WAIT;
				handler.sendMessage(msg);				
			}
			
			if( article.getWords()!=null && article.getWords().contains( w )  ){
				w = "<a style='text-decoration: none;color:red' a href='" + w
						+ "'>" + w + "</a> ";
			}
			else if (word_dao.hasWord(w)) {
				// w = "<a href='" + w + "'>" + w + "</a> ";
				w = "<a style='text-decoration: none;color:blue' a href='" + w
						+ "'>" + w + "</a> ";
			}
			webviewContentBuffer += w + " ";
		}

		//webviewContentBuffer += "</body></html>";
		
		webviewContent = webviewContentBuffer;
		
		return webviewContent;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.back1) {
			Intent intent = new Intent(ArticlecontentActivity.this,
					ArticallistActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.no) {
			translationPanel.setVisibility(View.GONE); 
		} else if (v.getId() == R.id.note1) {

			// TODO Auto-generated method stub
			if (flag == false) {
				lay2.setVisibility(View.VISIBLE);
				flag = true;
			} else {
				lay2.setVisibility(View.GONE);
				flag = false;
			}
			// text1.setVisibility(View.GONE);
		}

		else if (v.getId() == R.id.save) {
			// TODO Auto-generated method stub
			if (flag == false) {
				lay2.setVisibility(View.VISIBLE);
				flag = true;

			} else {
				lay2.setVisibility(View.GONE);
				flag = false;

				String note = ((EditText) findViewById(R.id.txt_word2))
						.getText().toString();

				NotesDAO note_dao = new NotesDAO(dbHelper);
				note_dao.save_article_note(note);

			}

		}
		else if( v.getId()== R.id.bn_ok)
		{
			new AlertDialog.Builder(ArticlecontentActivity.this)
			.setTitle("完成阅读")
			.setItems(items, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog,
						int which) {
					switch (which) {
					case 0:
						Intent intent = new Intent(
								ArticlecontentActivity.this,
								ArticallistActivity.class);
						startActivity(intent);
						break;
					case 1:

						Intent intenta = new Intent(
								ArticlecontentActivity.this,
								MywordInReadActivity.class);
						startActivity(intenta);
						break;
					}
				}
			})
			.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							dialog.dismiss();
						}
					}).show();
		}
	}

}
