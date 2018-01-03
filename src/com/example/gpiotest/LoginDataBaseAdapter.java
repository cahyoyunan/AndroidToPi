package com.example.gpiotest;



import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDataBaseAdapter 
{
		static final String DATABASE_NAME = "login.db";
		static final int DATABASE_VERSION = 1;
		public static final int NAME_COLUMN = 1;
		// TODO: Create public field for each column in your table.
		// SQL Statement to create a new database.
		static final String ID= "ID";
		static final String USERNAME= "USERNAME";
		static final String KETERANGAN= "KETERANGAN";
		static final String MASUK= "MASUK";
		public static String tableName = "MASUK";
		static final String DATABASE_CREATE = "create table "+"LOGIN"+
		                             "( " +" ID"+" integer primary key autoincrement,"+ "USERNAME  text,PASSWORD text,DATE timestamp); ";
		static final String DATABASE_CREATE2 = "create table "+"MASUK"+
              "( " +" ID"+" integer primary key autoincrement,"+ "USERNAME  text,KETERANGAN text,DATE timestamp); ";
		
		  
		// Variable to hold the database instance
		public  SQLiteDatabase db;
		// Context of the application using the database.
		
			
		private LoginDataBaseAdapter database;
		private final Context context;
		// Database open/upgrade helper
		
		private DataBaseHelper dbHelper;
		public  LoginDataBaseAdapter(Context _context) 
		{
			context = _context;
			dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		public  LoginDataBaseAdapter open() throws SQLException 
		{
			db = dbHelper.getWritableDatabase();
			return this;
		}
		public void close() 
		{
			db.close();
		}

		public  SQLiteDatabase getDatabaseInstance()
		{
			return db;
		}

		public void insertEntry(String userName,String password)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("USERNAME", userName);
			newValues.put("PASSWORD",password);
			newValues.put("Date",dateFormat.format(date));
			
			// Insert the row into your table
			db.insert("LOGIN", null, newValues);
			///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
		}
		
		public void masukEntry(String userName,String signin)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("USERNAME", userName);
			newValues.put("KETERANGAN", signin);
			newValues.put("Date",dateFormat.format(date));
			
			// Insert the row into your table
			db.insert("MASUK", null, newValues);
			///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
		}
		public int deleteEntry(String UserName)
		{
			//String id=String.valueOf(ID);
		    String where="USERNAME=?";
		    int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
	       // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
	        return numberOFEntriesDeleted;
		}	
		public String getSinlgeEntry(String userName)
		{
			Cursor cursor=db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
	        if(cursor.getCount()<1) // UserName Not Exist
	        {
	        	cursor.close();
	        	return "NOT EXIST";
	        }
		    cursor.moveToFirst();
			String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
			cursor.close();
			return password;				
		}
		/**
		public Cursor readData() {
			String[] allColumns = new String[] { LoginDataBaseAdapter.ID,
					LoginDataBaseAdapter.USERNAME,LoginDataBaseAdapter.KETERANGAN };
			Cursor c = database.query(database.MASUK, allColumns, null,
					null, null, null, null);
			if (c != null) {
				c.moveToFirst();
			}
			return c;
		}
		**/
		

		public void  updateEntry(String userName,String password)
		{
			
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("USERNAME", userName);
			updatedValues.put("PASSWORD",password);
			
			
	        String where="USERNAME = ?";
		    db.update("LOGIN",updatedValues, where, new String[]{userName});			   
		}		
}

