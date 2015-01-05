package com.example.gradeplanner;

import java.util.ArrayList;

import com.example.gradeplanner.CourseContract.CourseEntry;
import com.example.gradeplanner.CourseContract.TestEntry;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class AddMarkActivity extends Activity {

	private CourseDBHelper dbHelper = new CourseDBHelper(this);
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_add_mark);
		
		Intent intent=getIntent();
		ArrayList<String> courseCodes=
				intent.getStringArrayListExtra(MainActivity.EXTRA_COURSECODES);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, courseCodes);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner sItems = (Spinner) findViewById(R.id.spinner1);
		sItems.setAdapter(adapter);
		
		ArrayList<String> evalTypes= new ArrayList<String>();
		evalTypes.add("Project");
		evalTypes.add("Homework");
		evalTypes.add("Quiz");
		evalTypes.add("Test");
		evalTypes.add("Midterm");
		evalTypes.add("Final");
		
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, evalTypes);
		Spinner sItems2 = (Spinner) findViewById(R.id.spinner2);
		sItems2.setAdapter(adapter2);

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void submitMark(View view){
		if(inputValid()){
			EditText grade = (EditText) findViewById(R.id.editText2);
			EditText weight = (EditText) findViewById(R.id.editText3);
			EditText name = (EditText) findViewById(R.id.editText1);
			
			Spinner spinner = (Spinner) findViewById(R.id.spinner1);
			Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
			
			String courseCode=spinner.getSelectedItem().toString();
			String type=spinner2.getSelectedItem().toString();
			
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor c = db.rawQuery("select * from Courses WHERE CourseCode =?", new String[]{courseCode});
			c.moveToFirst();
			if(c.getString(c.getColumnIndex(CourseEntry.COLUMN_NAME_SPECIAL)).equals("y")&&
					type.equals("Final"))
				type+="_SPECIAL";
			
			db = dbHelper.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(TestEntry.COLUMN_NAME_COURSE_CODE, courseCode);
			values.put(TestEntry.COLUMN_NAME_GRADE, 
					Double.parseDouble(grade.getText().toString()));
			values.put(TestEntry.COLUMN_NAME_TEST_NAME, name.getText().toString());
			values.put(TestEntry.COLUMN_NAME_WEIGHT, 
					Double.parseDouble(weight.getText().toString()));
			values.put(TestEntry.COLUMN_NAME_TYPE, type);
			
			db.insert(TestEntry.TABLE_NAME, null, values);
			db.close();
			
			Utility.computeGrade(courseCode,this);
			
			Intent intent= new Intent(this, MainActivity.class);
			finish();
			startActivity(intent);
		}
	}
	
	private boolean inputValid(){
		EditText grade = (EditText) findViewById(R.id.editText2);
		EditText weight = (EditText) findViewById(R.id.editText3);
		
		return (Utility.checkNumberInput(grade.getText().toString(), this)&&
				Utility.checkNumberInput(weight.getText().toString(), this));
	}
}
