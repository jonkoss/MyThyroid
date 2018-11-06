package jonas.mythyroid;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class DbCon{

    Context myContext;
    SQLiteDatabase db;
    DbHelper dbHelper;

    public DbCon(Context c) {
        myContext = c;
    }
    public String globalFilePath;
    public int rowCounter;

    public DbCon open() {
        try {
            dbHelper = new DbHelper(myContext);
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return this;
        }
    }

    public void close() {
        db.close();
    }

    public void insert(String date, String tsh, String ft4, String ft4_upper_value, String ft4_lower_value, String ft3, String ft3_upper_value, String ft3_lower_value, String constitution, String comment, String tshwithoutstring, String ft4percentage, String ft3percentage, String ft4percentagewithoutstring, String ft3percentagewithoutstring) {
        ContentValues values = new ContentValues();
        values.put(dbHelper.DATE, date);
        values.put(dbHelper.TSH, tsh);
        values.put(dbHelper.FT4, ft4);
        values.put(dbHelper.FT4_UPPER_VALUE, ft4_upper_value);
        values.put(dbHelper.FT4_LOWER_VALUE, ft4_lower_value);
        values.put(dbHelper.FT3, ft3);
        values.put(dbHelper.FT3_UPPER_VALUE, ft3_upper_value);
        values.put(dbHelper.FT3_LOWER_VALUE, ft3_lower_value);
        values.put(dbHelper.CONSTITUTION, constitution);
        values.put(dbHelper.COMMENT, comment);
        values.put(dbHelper.TSHWITHOUTSTRING, tshwithoutstring);
        values.put(dbHelper.FT4Percentage, ft4percentage);
        values.put(dbHelper.FT3Percentage, ft3percentage);
        values.put(dbHelper.FT4PercentageWITHOUTSTRING, ft4percentagewithoutstring);
        values.put(dbHelper.FT3PercentageWITHOUTSTRING, ft3percentagewithoutstring);
        db.insert(dbHelper.TABLE_NAME, null, values);
    }

    public Cursor readAll() {
        String[] columns = new String[]{dbHelper.ID, dbHelper.DATE, dbHelper.TSH, dbHelper.FT4, dbHelper.FT4_UPPER_VALUE, dbHelper.FT4_LOWER_VALUE, dbHelper.FT3, dbHelper.FT3_UPPER_VALUE, dbHelper.FT3_LOWER_VALUE, dbHelper.CONSTITUTION, dbHelper.COMMENT, dbHelper.TSHWITHOUTSTRING, dbHelper.FT4Percentage, dbHelper.FT3Percentage, dbHelper.FT4PercentageWITHOUTSTRING, dbHelper.FT3PercentageWITHOUTSTRING};
        Cursor c = db.query(dbHelper.TABLE_NAME, columns, null, null, null, null, dbHelper.DATE + " DESC");
        //countRows = c.getInt(0);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public String getFirstID() {
        String getfirstid = "1";
        Cursor cursor = db.query(dbHelper.TABLE_NAME, new String[]{"*"},
                null,
                null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                getfirstid = cursor.getString(0);
            }
            cursor.close();
        }
        return getfirstid;
    }

    public long getID() {
        long numRows = DatabaseUtils.queryNumEntries(db, dbHelper.TABLE_NAME);
        return numRows;
    }

    public String[] getFt3(){
        open();
        rowCounter=0;
        String[] FT3Values = new String[30];
        String[] columns = new String[]{dbHelper.ID, dbHelper.DATE, dbHelper.TSH, dbHelper.FT4, dbHelper.FT4_UPPER_VALUE, dbHelper.FT4_LOWER_VALUE, dbHelper.FT3, dbHelper.FT3_UPPER_VALUE, dbHelper.FT3_LOWER_VALUE, dbHelper.CONSTITUTION, dbHelper.COMMENT, dbHelper.TSHWITHOUTSTRING, dbHelper.FT4Percentage, dbHelper.FT3Percentage, dbHelper.FT4PercentageWITHOUTSTRING, dbHelper.FT3PercentageWITHOUTSTRING};
        Cursor c = db.query(dbHelper.TABLE_NAME, columns, null, null, null, null, dbHelper.DATE + " DESC");
        if (c != null) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                    FT3Values[i] = c.getString(15);
                    rowCounter++;
                    c.moveToNext();
            }
        }
        close();
        return FT3Values;
    }

    public String[] getFt4(){
        open();
        rowCounter=0;
        String[] FT4Values = new String[30];
        String[] columns = new String[]{dbHelper.ID, dbHelper.DATE, dbHelper.TSH, dbHelper.FT4, dbHelper.FT4_UPPER_VALUE, dbHelper.FT4_LOWER_VALUE, dbHelper.FT3, dbHelper.FT3_UPPER_VALUE, dbHelper.FT3_LOWER_VALUE, dbHelper.CONSTITUTION, dbHelper.COMMENT, dbHelper.TSHWITHOUTSTRING, dbHelper.FT4Percentage, dbHelper.FT3Percentage, dbHelper.FT4PercentageWITHOUTSTRING, dbHelper.FT3PercentageWITHOUTSTRING};
        Cursor c = db.query(dbHelper.TABLE_NAME, columns, null, null, null, null, dbHelper.DATE + " DESC");
        if (c != null) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                String checkForNullValue = c.getString(12);
                if (checkForNullValue.matches("Kein Wert")) {

                } else {
                    FT4Values[i] = c.getString(14);
                    rowCounter++;
                    c.moveToNext();
                }
            }
        }
        close();
        return FT4Values;
    }

    public Cursor selected(long id) {
        String[] columns = new String[]{dbHelper.ID, dbHelper.DATE, dbHelper.TSH, dbHelper.FT4, dbHelper.FT4_UPPER_VALUE, dbHelper.FT4_LOWER_VALUE, dbHelper.FT3, dbHelper.FT3_UPPER_VALUE, dbHelper.FT3_LOWER_VALUE, dbHelper.CONSTITUTION, dbHelper.COMMENT, dbHelper.TSHWITHOUTSTRING, dbHelper.FT4Percentage, dbHelper.FT3Percentage, dbHelper.FT4PercentageWITHOUTSTRING, dbHelper.FT3PercentageWITHOUTSTRING};
        Cursor c = db.query(dbHelper.TABLE_NAME, columns, dbHelper.ID + "=" + id, null, null, null, null);
        if (c != null) {c.moveToFirst();}
        return c;
    }

    public void delete(long id) {
        open();
        db.delete(dbHelper.TABLE_NAME, dbHelper.ID + "=" + id, null);
        close();
    }

    public void update(long id, String date, String tsh, String ft4, String ft4_upper_value, String ft4_lower_value, String ft3, String ft3_upper_value, String ft3_lower_value, String constitution, String comment, String tshwithoutstring, String ft4percentage, String ft3percentage, String ft4percentagewithoutstring, String ft3percentagewithoutstring) {
        open();
        ContentValues values = new ContentValues();
        values.put(dbHelper.DATE, date);
        values.put(dbHelper.TSH, tsh);
        values.put(dbHelper.FT4, ft4);
        values.put(dbHelper.FT4_UPPER_VALUE, ft4_upper_value);
        values.put(dbHelper.FT4_LOWER_VALUE, ft4_lower_value);
        values.put(dbHelper.FT3, ft3);
        values.put(dbHelper.FT3_UPPER_VALUE, ft3_upper_value);
        values.put(dbHelper.FT3_LOWER_VALUE, ft3_lower_value);
        values.put(dbHelper.CONSTITUTION, constitution);
        values.put(dbHelper.COMMENT, comment);
        values.put(dbHelper.TSHWITHOUTSTRING, tshwithoutstring);
        values.put(dbHelper.FT4Percentage, ft4percentage);
        values.put(dbHelper.FT3Percentage, ft3percentage);
        values.put(dbHelper.FT4PercentageWITHOUTSTRING, ft4percentagewithoutstring);
        values.put(dbHelper.FT3PercentageWITHOUTSTRING, ft3percentagewithoutstring);
        db.update(dbHelper.TABLE_NAME, values, dbHelper.ID + "=" + id, null);
        close();
    }

    public void exportToCsv() {
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Backup/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        SqliteToExcel sqliteToExcel = new SqliteToExcel(myContext, dbHelper.DB_NAME );
        sqliteToExcel.exportSingleTable(dbHelper.TABLE_NAME, "table1.xls", new SqliteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }
            @Override
            public void onCompleted(String filePath) {
                globalFilePath = filePath;
                Toast.makeText(myContext, myContext.getString(R.string.export_successfully) + filePath, Toast.LENGTH_SHORT).show();
                Intent intentShareFile = new Intent(Intent.ACTION_SEND);
                File fileWithinMyDir = new File(filePath);

                if (fileWithinMyDir.exists()) {
                    intentShareFile.setType("MyThyroid/csv");
                    intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + filePath));

                    intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
                            myContext.getString(R.string.MyThyroid_Data));
                    intentShareFile.putExtra(Intent.EXTRA_TEXT, myContext.getString(R.string.MyThyroid_Data));

                    myContext.startActivity(Intent.createChooser(intentShareFile, myContext.getString(R.string.Share_file)));
                }
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(myContext, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class DbHelper extends SQLiteOpenHelper {

        public static final String DB_NAME = "DatabaseThyreoCalc.db";
        public static final String TABLE_NAME = "ThyreoCalcTable";
        public static final String ID = "_id";
        public static final String DATE = "date";
        public static final String TSH = "tsh";
        public static final String FT4 = "ft4";
        public static final String FT4_UPPER_VALUE = "ft4_upper_value";
        public static final String FT4_LOWER_VALUE = "ft4_lower_value";
        public static final String FT4Percentage = "ft4percentage";
        public static final String FT3 = "ft3";
        public static final String FT3_UPPER_VALUE = "ft3_upper_value";
        public static final String FT3_LOWER_VALUE = "ft3_lower_value";
        public static final String FT3Percentage = "ft3percentage";
        public static final String CONSTITUTION = "constitution";
        public static final String COMMENT= "comment";
        public static final String TSHWITHOUTSTRING = "tshwithoutstring";
        public static final String FT4PercentageWITHOUTSTRING = "ft4percentagewithoutstring";
        public static final String FT3PercentageWITHOUTSTRING = "ft3percentagewithoutstring";
        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + DATE + " DATE , " + TSH + " REAL ," + FT4 + " REAL ," + FT4_UPPER_VALUE + " REAL ," + FT4_LOWER_VALUE + " REAL ," + FT3 + " REAL ," +
                FT3_UPPER_VALUE + " REAL ," + FT3_LOWER_VALUE + " REAL ," + CONSTITUTION + " TEXT ," + COMMENT + " TEXT ," + TSHWITHOUTSTRING + " REAL," +
                FT4Percentage + " REAL," + FT3Percentage + " REAL, " + FT4PercentageWITHOUTSTRING + " REAL, " + FT3PercentageWITHOUTSTRING + " REAL);";
        public static final int VERSION = 1;


        public DbHelper(Context context) {
            super(context, DB_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
            onCreate(db);
        }
    }

}
