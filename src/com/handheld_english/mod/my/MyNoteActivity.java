package com.handheld_english.mod.my;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.handheld_english.R;
import com.handheld_english.R.id;
import com.handheld_english.R.layout;
import com.handheld_english.StudyActivity;
import com.handheld_english.adapter.NotesAdapter;
import com.handheld_english.dao.MyDatabaseHelper;
import com.handheld_english.data.Notes;
import com.handheld_english.data.User;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyNoteActivity extends Activity {
	private MyDatabaseHelper dbHelper;
	private List<Notes> noteList = new ArrayList<Notes>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mynotelist);

		dbHelper = MyDatabaseHelper.instance(this);

		Button button1 = (Button) findViewById(R.id.back5);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MyNoteActivity.this,
						StudyActivity.class);
				startActivity(intent);
			}
		});
		
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor1 = db
				.query("Notes", null, null, null, null, null, null);
		if (cursor1.moveToFirst()) {
			do {
				int id = cursor1.getInt(cursor1.getColumnIndex("n_id"));
				String note = cursor1.getString(cursor1
						.getColumnIndex("note"));
				
				Notes notes = new Notes(id,note);
				noteList.add(notes);
			} while (cursor1.moveToNext());
		}
		cursor1.close();

		NotesAdapter adapter = new NotesAdapter(MyNoteActivity.this,
				R.layout.mynote, noteList);
		ListView listview = (ListView) findViewById(R.id.listView1);
		listview.setAdapter(adapter);
	}

}