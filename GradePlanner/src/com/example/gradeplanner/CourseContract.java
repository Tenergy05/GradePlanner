package com.example.gradeplanner;

import android.provider.BaseColumns;

public final class CourseContract {
	public CourseContract(){}
	
	public static final class CourseEntry implements BaseColumns{
		public static final String TABLE_NAME="Courses";
		public static final String COLUMN_NAME_COURSE_CODE="CourseCode";
		public static final String COLUMN_NAME_GRADE="Grade";
		public static final String COLUMN_NAME_GOAL="Goal";
		public static final String COLUMN_NAME_SPECIAL="Special";
	}
	public static final class TestEntry implements BaseColumns{
		public static final String TABLE_NAME="Tests";
		public static final String COLUMN_NAME_TEST_NAME="TestName";
		public static final String COLUMN_NAME_COURSE_CODE="CourseCode";
		public static final String COLUMN_NAME_GRADE="Grade";
		public static final String COLUMN_NAME_WEIGHT="Weight";
		public static final String COLUMN_NAME_TYPE="Type";
	}
}

