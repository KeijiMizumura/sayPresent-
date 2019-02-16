package com.betatesters.saypresent30;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Details
    private static final String DATABASE_NAME = "Say_Present.db";
    private static final int DATABASE_VERSION = 1;

    // TABLES
    private static final String SUBJECT_TABLE = "Subject";
    private static final String STUDENTS_TABLE = "Students";
    private static final String DAILY_TABLE = "DailySubjects";
    private static final String RECORDS_TABLE = "DailyRecords";
    private static final String STATE_TABLE = "PresentState";
    private static final String WEEK_TABLE = "DaysOfTheWeek";

    // COLUMNS
    private static final String SUBJ_ID = "SubjectID";
    private static final String SUBJ_NAME = "SubjectName";
    private static final String SEC_NAME = "SectionName";
    private static final String TIME_IN = "TimeIn";
    private static final String TIME_OUT = "TimeOut";

    private static final String STUD_ID = "StudentID";
    private static final String FNAME = "FirstName";
    private static final String LNAME = "LastName";


    private static final String DAY_ID = "DayID";

    private static final String DAILY_ID = "DailyID";

    private static final String MONTH = "Month";
    private static final String DAY = "Day";
    private static final String YEAR = "Year";

    private static final String PRESENT_ID = "PresentID";


    // Subject Table

    private static final String CREATE_SUBJECT = "CREATE TABLE " + SUBJECT_TABLE + "(" +
            SUBJ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SUBJ_NAME + " TEXT, " +
            SEC_NAME + " TEXT, " +
            TIME_IN + " TEXT, " +
            TIME_OUT + " TEXT);";

    // Students Table

    private static final String CREATE_STUDENT = "CREATE TABLE " + STUDENTS_TABLE + "(" +
            STUD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FNAME + " TEXT," +
            LNAME + " TEXT," +
            SUBJ_ID + " INTEGER);";

    // Daily Table

    private static final String CREATE_DAILY = "CREATE TABLE " + DAILY_TABLE + "(" +
            SUBJ_ID + " INTEGER, " +
            DAY_ID + " INTEGER);";

    // Daily Records

    private static final String CREATE_RECORDS = "CREATE TABLE " + RECORDS_TABLE + "(" +
            DAILY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MONTH + " INTEGER, " +
            DAY + " INTEGER, " +
            YEAR + " INTEGER ," +
            SUBJ_ID + " INTEGER, " +
            STUD_ID + " INTEGER, " +
            PRESENT_ID + " INTEGER);";


    // CONSTRUCTOR

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Creates the tables
        db.execSQL(CREATE_SUBJECT);
        db.execSQL(CREATE_STUDENT);
        db.execSQL(CREATE_DAILY);
        db.execSQL(CREATE_RECORDS);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SUBJECT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STUDENTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DAILY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RECORDS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STATE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WEEK_TABLE);

        onCreate(db);
    }

    // ADD SUBJECT

    public boolean addSubject(String name, String section, String timeIn, String timeOut, ArrayList<Integer> week){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SUBJ_NAME, name);
        contentValues.put(SEC_NAME, section);
        contentValues.put(TIME_IN, timeIn);
        contentValues.put(TIME_OUT, timeOut);

        // INSERTS IT INTO THE SUBJECT TABLE
        long result = db.insert(SUBJECT_TABLE, null, contentValues);

        ContentValues weekValues = new ContentValues();

        for(int i = 0; i < week.size(); i++){
            weekValues.put(SUBJ_ID, result);
            weekValues.put(DAY_ID, week.get(i));
            db.insert(DAILY_TABLE, null, weekValues);
        }

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    // UPDATE SUBJECT

    public boolean updateSubject(String id, String name, String section, String timeIn, String timeOut, ArrayList<Integer> week){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SUBJ_NAME, name);
        contentValues.put(SEC_NAME, section);
        contentValues.put(TIME_IN, timeIn);
        contentValues.put(TIME_OUT, timeOut);

        long result = db.update(SUBJECT_TABLE, contentValues, SUBJ_ID + " = ?", new String[] {id});

        // Deletes all existing week values
        db.delete(DAILY_TABLE, SUBJ_ID + " = ?", new String[] {id});

        ContentValues weekValues = new ContentValues();

        for(int i = 0; i < week.size(); i++){
            weekValues.put(SUBJ_ID, Integer.parseInt(id));
            weekValues.put(DAY_ID, week.get(i));
            db.insert(DAILY_TABLE, null, weekValues);
        }


        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean updateStudent(String id, String fname, String lname, String subjID){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FNAME, fname);
        contentValues.put(LNAME, lname);

        long result = db.update(STUDENTS_TABLE, contentValues, SUBJ_ID + " = ? AND " + STUD_ID + " = ?", new String[] {subjID,id});

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    // DELETE

    public boolean deleteSubject(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long results = db.delete(SUBJECT_TABLE, SUBJ_ID + " = ?", new String[] {id});
        db.delete(STUDENTS_TABLE, SUBJ_ID + " = ?", new String[] {id});
        db.delete(DAILY_TABLE, SUBJ_ID + " = ?", new String[] {id});

        if(results == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean deleteStudent(String subjID, String studID){
        SQLiteDatabase db = this.getWritableDatabase();
        long results = db.delete(STUDENTS_TABLE, SUBJ_ID + " = ? AND " + STUD_ID + " = ? " , new String[] {subjID, studID} );

        if(results == -1){
            return false;
        }
        else{
            return true;
        }
    }

    // ADD STUDENT

    public boolean addStudent(String fName, String lName, int subjectID){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FNAME, fName);
        contentValues.put(LNAME, lName);
        contentValues.put(SUBJ_ID, subjectID);

        long results = db.insert(STUDENTS_TABLE, null, contentValues);


        if(results == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean addRecords(int month, int day, int year, int subjectID, int studentID, int presentID){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MONTH, month);
        contentValues.put(DAY, day);
        contentValues.put(YEAR, year);
        contentValues.put(SUBJ_ID, subjectID);
        contentValues.put(STUD_ID, studentID);
        contentValues.put(PRESENT_ID, presentID);

        long results = db.insert(RECORDS_TABLE, null, contentValues);

        if(results == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getRecords(int student_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + RECORDS_TABLE + " WHERE " + STUD_ID + " =" + student_id + " ORDER BY " + DAY + " ASC, " + MONTH + " ASC, " + YEAR + " ASC;";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getRecords(int month, int day, int year, int subjectID, int studentID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + RECORDS_TABLE + " WHERE " + SUBJ_ID + "= " + subjectID + " AND " + MONTH + "= " + month + " AND " + DAY + "= " + day + " AND " + YEAR + "= " + year + " AND " + STUD_ID + "= " + studentID + " ;";
        Cursor data;

        data = db.rawQuery(query, null);

        return data;
    }

    // get data (ALL THE ROWS)

    public Cursor getSubjects(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + SUBJECT_TABLE;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getDaysOfTheWeek(int index){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DAILY_TABLE + " WHERE " + SUBJ_ID + "= " + index;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getStudents(int index){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + STUDENTS_TABLE + " WHERE " + SUBJ_ID + "= " + index + " ORDER BY " + LNAME + " ASC;";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    // for the schedule

    public Cursor getCurrentSchedule(int week){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT Subject.SubjectName, Subject.SectionName, Subject.TimeIn, Subject.TimeOut, DailySubjects.DayID, Subject.SubjectID FROM Subject INNER JOIN DailySubjects ON Subject.SubjectID = DailySubjects.SubjectID WHERE DayID = " + week + " ORDER BY TimeIn;";
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    // get 1 row

    public Cursor getSubjects(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + SUBJECT_TABLE + " WHERE " + SUBJ_ID + " = " + id + ";";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getStudents(int id, int subId){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + STUDENTS_TABLE + " WHERE " + SUBJ_ID + "= " + subId + " AND " + STUD_ID + "= " + id + ";";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

}
