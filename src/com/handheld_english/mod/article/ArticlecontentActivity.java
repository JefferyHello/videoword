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

	private String[] items = new String[] { "�����Ķ�", "���䵥��" };
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

		// ����
		Button backBtn = (Button) findViewById(R.id.back1);
		backBtn.setOnClickListener(this);

		Button closeTranslationBtn = (Button) findViewById(R.id.no);
		closeTranslationBtn.setOnClickListener(this);

		// �ʼ�
		lay2 = (LinearLayout) findViewById(R.id.article2_layout);
		Button btn_note1 = (Button) findViewById(R.id.note1);
		btn_note1.setOnClickListener(this);
		
		// ����ʼ�
		Button btn_save_note = (Button) findViewById(R.id.save);
		btn_save_note.setOnClickListener(this);

		// ����Ķ�
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
		webView.getSettings().setSupportZoom(true);// ˫�����Ź���
		
		webView.getSettings().setBuiltInZoomControls(true); 
		webView.setInitialScale(100);

		WebSettings settings = webView.getSettings(); // �Զ�����
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

		webView.setHorizontalScrollBarEnabled(false);
		webView.setHorizontalScrollbarOverlay(false);
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		
		webView.setWebViewClient(new WebViewClient() {
			/*
			 * �˴������س����ӵ�url,������href���������.
			 */
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
 
				// ��ȡ�����Ķ���¼id

				// �Ӻ�̨��ȡ����
				WordDAO dao = new WordDAO(dbHelper);
				Word word = dao.getWord(  url);

				Readarticle readarticle1 = (Readarticle) AppSession
						.get(AppSession.READARTICLE);
 
				dao.saveQuerywords(word.getId(), readarticle1.getId());
				
				translationPanel.setVisibility(View.VISIBLE);
				textView.setText(word.getTranslation()+"\n ���䣺 "+word.getExplain());

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
			.setTitle("����Ķ�")
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
			.setNegativeButton("ȡ��",
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
