package retrofit.etos.it.sket.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by IT on 27/09/2016.
 */
public class Db_config extends SQLiteOpenHelper{

    private static final  String TBL_NAME = "db_config";
    private static final  String CONFIG = "config";
    private static final  String VALUE = "value";
    public static final String ID = "_id";

    private static final String CREATE_SQL = "CREATE TABLE "+TBL_NAME+" ("
            +ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +CONFIG+" TEXT, "
            +VALUE+" TEXT "
            +")";
    private static final String SQL_CREATE = "DROP TABLE IF EXISTS "+TBL_NAME;


    public Db_config(Context context) {
        super(context, Db_timbang.DB_NAME, null, Db_timbang.VERSI);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String SQL = "DROP TABLE IF EXISTS "+TBL_NAME;
        db.execSQL(SQL);
        onCreate(db);
    }



    public String getConfig(String key) {
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL ="Select * from " + TBL_NAME + " where " + CONFIG + " = ?";
        Cursor cursor = db.rawQuery(SQL,new String[] {key});

        String value = "";
        try{
            if(cursor.getCount() > 0)
            {
                cursor.moveToFirst();
                value = cursor.getString(cursor.getColumnIndex(VALUE)).toString();
            }
            else
            {
                this.addConfig(key,value);
            }

            return value;
        }finally {
           cursor.close();
        }

    }

    public void addConfig(String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put(CONFIG, key);
            values.put(VALUE, value);

            db.insert(TBL_NAME,null,values);

            db.close();
        }
        catch (Exception e)
        {
            Log.i("input config","Gagal" + e);
        }
    }

    public void editConfig(String key, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put(VALUE, value);

            db.update(TBL_NAME,values,"" + CONFIG + " = ? ", new String[] {key.toString()});

            db.close();
        }
        catch (Exception e)
        {
            Log.i("Update config","Gagal" + e);
        }
    }

}
