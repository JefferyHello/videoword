package com.handheld_english.mod.word;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.handheld_english.AppSession;
import com.handheld_english.R;
import com.handheld_english.R.id;
import com.handheld_english.R.layout;
import com.handheld_english.dao.LearnWordsDAO;
import com.handheld_english.dao.MyDatabaseHelper;
import com.handheld_english.dao.NotesDAO;
import com.handheld_english.dao.QuerywordDAO;
import com.handheld_english.data.Learnword;
import com.handheld_english.data.Notes;
import com.handheld_english.data.Readarticle;
import com.handheld_english.data.User;
import com.handheld_english.data.Word;
import com.handheld_english.mod.article.ArticallistActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MywordInReadActivity extends Activity implements
		OnGestureListener, OnClickListener {
	private MyDatabaseHelper dbHelper;
	LinearLayout lay;
	boolean flag = false;
	// ViewFlipper实例
	ViewFlipper flipper;
	// 定义手势检测器实例
	GestureDetector detector;
	// 定义一个动画数组，用于为ViewFlipper指定切换动画效果
	Animation[] animations = new Animation[4];
	// 定义手势动作两点之间的最小距离
	final int FLIP_DISTANCE = 50;

	TextView wordname1;
	TextView wordmean1;
	TextView txt_word1;
	TextView score;
	int wordIndex = 0;
	private List<Learnword> learnwords;

	User user;

	LinearLayout explainLay;
	LinearLayout translationLay;
	LinearLayout forscore_3;
	LinearLayout forscore_2;
	LinearLayout forscore_1;

	LinearLayout noteLay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wordremember);
		explainLay = (LinearLayout) findViewById(R.id.explain);
		translationLay = (LinearLayout) findViewById(R.id.translation);
		forscore_3 = (LinearLayout) findViewById(R.id.forscore_3);
		forscore_2 = (LinearLayout) findViewById(R.id.forscore_2);
		forscore_1 = (LinearLayout) findViewById(R.id.forscore_1);
		noteLay = (LinearLayout) findViewById(R.id.layout_note);

		//initial();
		
		
		user = (User) AppSession.get(AppSession.USER);

		dbHelper = MyDatabaseHelper.instance(this);
		lay = (LinearLayout) findViewById(R.id.bword_layout);
		// 返回按钮
		Button button1 = (Button) findViewById(R.id.back3);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MywordInReadActivity.this,
						ArticallistActivity.class);
				startActivity(intent);
			}
		});

		// initWord();

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Readarticle readarticle = (Readarticle) AppSession
				.get(AppSession.READARTICLE);
		int rdaId = readarticle.getId();

		QuerywordDAO dao = new QuerywordDAO(dbHelper);

		List<Word> words = dao.getQueryWord(db, rdaId);

		learnwords = new ArrayList<Learnword>();
		for (int i = 0; i < words.size(); i++) {
			Learnword lw = new Learnword();
			lw.setWord(words.get(i));
			lw.setUser(user);
			lw.setDate(new Date());
			lw.setScore(0);// TODO
			learnwords.add(lw);
		}

		wordname1 = (TextView) findViewById(R.id.wordname);
		wordmean1 = (TextView) findViewById(R.id.wordmean);
		txt_word1 = (TextView) findViewById(R.id.txt_word);
		score = (TextView) findViewById(R.id.score);
		displayWord(0);

		// 声音

		Button btn_sound = (Button) findViewById(R.id.btn_sound);
		btn_sound.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		Button rules = (Button) findViewById(R.id.rules);
		rules.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MywordInReadActivity.this,
						InReadScore_RulesActivity.class);
				startActivity(intent);
			}
		});
		// 笔记
		Button btn_note = (Button) findViewById(R.id.btn_note);
		btn_note.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flag == false) {
					lay.setVisibility(View.VISIBLE);
					flag = true;
				} else {
					lay.setVisibility(View.GONE);
					flag = false;
				}
				// text1.setVisibility(View.GONE);

			}
		});
		// 保存笔记
		Button btn_save_note = (Button) findViewById(R.id.save);
		btn_save_note.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (flag == false) {
					lay.setVisibility(View.VISIBLE);
					flag = true;

				} else {
					lay.setVisibility(View.GONE);
					flag = false;
					String note = ((EditText) findViewById(R.id.txt_word2))
							.getText().toString();

					NotesDAO note_dao = new NotesDAO(dbHelper);
					note_dao.save_word_note(note, learnwords.get(wordIndex)
							.getWord().getWord());

				}
			}
		});
		// 创建手势检测器
		detector = new GestureDetector(this);

		findViewById(R.id.forscore_3_yes).setOnClickListener(this);
		findViewById(R.id.forscore_3_no).setOnClickListener(this);
		findViewById(R.id.forscore_2_yes).setOnClickListener(this);
		findViewById(R.id.forscore_2_no).setOnClickListener(this);
		findViewById(R.id.forscore_1_yes).setOnClickListener(this);
		findViewById(R.id.forscore_1_no).setOnClickListener(this);

		// 认识
		findViewById(R.id.next).setOnClickListener(this);
		findViewById(R.id.up).setOnClickListener(this);

	}

	private void displayWord(int index) {

		wordname1.setText((learnwords.get(index).getWord()).getWord());

		wordmean1.setText((learnwords.get(index).getWord()).getTranslation());

		txt_word1.setText((learnwords.get(index).getWord()).getExplain());

	}

	public void done()
	{

		Intent intent = new Intent(
				MywordInReadActivity.this,
				ArticallistActivity.class);
		startActivity(intent); 
	}
	
	public void wordnext() {
		wordIndex++;

		if (wordIndex >= learnwords.size()) {
			wordIndex = wordIndex - 1;
			AlertDialog.Builder builder = new Builder(MywordInReadActivity.this);
			builder.setMessage("提交学习记录吗");
			builder.setTitle("提示");
			builder.setPositiveButton("确定",new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0,
						int arg1) {
					// TODO Auto-generated method stub
					arg0.dismiss();

					//提交学习记录
					
					LearnWordsDAO dao = new LearnWordsDAO(dbHelper);
					dao.save_learn_words(learnwords);
					done();
				}
					});
			builder.setNegativeButton(
					"取消",
					new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							dialog.dismiss();
						}
					});
			builder.create().show();
		}

		displayWord(wordIndex);
		
	}

	public void wordup() {
		wordIndex--;

		if (wordIndex < 0) {
			wordIndex = 0;
		}

		displayWord(wordIndex);
	}

	public boolean onFling(MotionEvent event1, MotionEvent event2,
			float velocityX, float velocityY) {
		/*
		 * 如果第一个触点事件的X座标大于第二个触点事件的X座标超过FLIP_DISTANCE 也就是手势从右向左滑。
		 */
		if (event1.getX() - event2.getX() > FLIP_DISTANCE) {

			wordnext();
			initial();

			// 为flipper设置切换的的动画效果

			// this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,
			// R.anim.left_in));
			// this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
			// R.anim.left_out));
			// this.flipper.showNext();
			return true;
		}
		/*
		 * 如果第二个触点事件的X座标大于第一个触点事件的X座标超过FLIP_DISTANCE 也就是手势从左向右滑。
		 */
		else if (event2.getX() - event1.getX() > FLIP_DISTANCE) {

			wordup();
			initial();
			// 为flipper设置切换的的动画效果
			// this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,
			// R.anim.right_in));
			// this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
			// R.anim.right_out));
			// this.flipper.showPrevious();

			return true;

		}
		return false;
	}

	public void initial() {

		explainLay.setVisibility(View.INVISIBLE);
		translationLay.setVisibility(View.INVISIBLE);
		forscore_3.setVisibility(View.VISIBLE);
		forscore_2.setVisibility(View.GONE);
		forscore_1.setVisibility(View.GONE);
		score.setText("");
		noteLay.setVisibility(View.INVISIBLE);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 将该Activity上的触碰事件交给GestureDetector处理
		return detector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.up) {
			wordup();
			initial();
		} else if (v.getId() == R.id.next) {
			wordnext();
			initial();
		} else if (v.getId() == R.id.forscore_3_yes) {

			learnwords.get(wordIndex).setScore(3);
            score.setText("3分");
			explainLay.setVisibility(View.VISIBLE);
			translationLay.setVisibility(View.VISIBLE);
			forscore_3.setVisibility(View.VISIBLE);
			forscore_2.setVisibility(View.GONE);
			forscore_1.setVisibility(View.GONE);
			noteLay.setVisibility(View.VISIBLE);
		} else if (v.getId() == R.id.forscore_3_no) {

			explainLay.setVisibility(View.VISIBLE);
			translationLay.setVisibility(View.INVISIBLE);
			forscore_3.setVisibility(View.GONE);
			forscore_2.setVisibility(View.VISIBLE);
			forscore_1.setVisibility(View.GONE);
			noteLay.setVisibility(View.INVISIBLE);
		} else if (v.getId() == R.id.forscore_2_yes) {

			learnwords.get(wordIndex).setScore(2);
			score.setText("2分");
			explainLay.setVisibility(View.VISIBLE);
			translationLay.setVisibility(View.VISIBLE);
			forscore_3.setVisibility(View.GONE);
			forscore_2.setVisibility(View.VISIBLE);
			forscore_1.setVisibility(View.GONE);
			noteLay.setVisibility(View.VISIBLE);
		} else if (v.getId() == R.id.forscore_2_no) {
			explainLay.setVisibility(View.VISIBLE);
			translationLay.setVisibility(View.VISIBLE);
			forscore_3.setVisibility(View.GONE);
			forscore_2.setVisibility(View.GONE);
			forscore_1.setVisibility(View.VISIBLE);
			noteLay.setVisibility(View.VISIBLE);
		}else if (v.getId() == R.id.forscore_1_yes) {
			
			learnwords.get(wordIndex).setScore(1);
			score.setText("1分");
			explainLay.setVisibility(View.VISIBLE);
			translationLay.setVisibility(View.VISIBLE);
			forscore_3.setVisibility(View.GONE);
			forscore_2.setVisibility(View.GONE);
			forscore_1.setVisibility(View.VISIBLE);
			noteLay.setVisibility(View.VISIBLE);
		}
		else if (v.getId() == R.id.forscore_1_no) {
			
			learnwords.get(wordIndex).setScore(0);
			score.setText("0分");
			explainLay.setVisibility(View.VISIBLE);
			translationLay.setVisibility(View.VISIBLE);
			forscore_3.setVisibility(View.GONE);
			forscore_2.setVisibility(View.GONE);
			forscore_1.setVisibility(View.VISIBLE);
			noteLay.setVisibility(View.VISIBLE);
		}
	}

}