package retrofit.etos.it.sket.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.etos.it.sket.Model.Kapal;

/**
 * Created by IT on 05/10/2016.
 */
public class Db_kapals extends SQLiteOpenHelper {

    public static final String TBL_NAME = "db_kapals";
    public static final String ID = "id";
    public static final String ID_KAPAL = "id_kapal";
    public static final String NO_INDUK = "no_induk";
    public static final String NO_IZIN = "no_izin";
    public static final String NAMA_KAPAL = "nama_kapal";
    public static final String PEMILIK = "pemilik";
    public static final String ALAT_TANGKAP = "alat_tangkap";
    public static final String STATUS_IZIN = "status_izin";
    public static final String KEY_UNIK = "key_unik";

    private static final String CREATE_SQL = "CREATE TABLE "+TBL_NAME+" ("
            +ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +ID_KAPAL+" TEXT, "
            +NO_INDUK+" TEXT, "
            +NO_IZIN+" TEXT, "
            +NAMA_KAPAL+" TEXT, "
            +PEMILIK+" TEXT, "
            +ALAT_TANGKAP+" TEXT, "
            +STATUS_IZIN+" TEXT, "
            +KEY_UNIK+" TEXT "
            +")";

    public Db_kapals(Context context) {
        super(context, Db_timbang.DB_NAME, null, Db_timbang.VERSI);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e("creted","tabel");
        sqLiteDatabase.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(sqLiteDatabase);
    }

    public void Reset() {
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "DROP TABLE IF EXISTS "+TBL_NAME;
        db.execSQL(SQL);
        onCreate(db);
    }


    public void clearTable()   {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_NAME, null,null);
    }

    public void addKapal(Kapal kapal) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put(ID_KAPAL, kapal.getIdKapal());
            values.put(NO_INDUK, kapal.getNoInduk());
            values.put(NO_IZIN, kapal.getNoIzin());
            values.put(ALAT_TANGKAP, kapal.getAlatTangkap());
            values.put(NAMA_KAPAL, kapal.getNamaKapal());
            values.put(PEMILIK, kapal.getPemilik());
            values.put(STATUS_IZIN, kapal.getStatusIzin());
            values.put(KEY_UNIK, kapal.getKeyUnik());

            db.insert(TBL_NAME,null,values);
            Log.i("input kapal","berhasil");

            db.close();
        }
        catch (Exception e)
        {
            Log.i("input kapal","Gagal" + e);
        }
    }

    public JSONObject toJSONallKapal() throws JSONException {

        SQLiteDatabase db =this.getReadableDatabase();
        String SQL = "SELECT * FROM " + TBL_NAME + " ORDER by " + ID + " DESC";
        Cursor cursor = db.rawQuery(SQL,null);

        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();

        if(!cursor.isLast())
        {

            while (cursor.moveToNext())
            {
                JSONObject obj = new JSONObject();
                if(cursor.getInt(0) < 1)
                {
                    obj.put("id_kapal", cursor.getInt(0));
                }
                else
                {
                    obj.put("id_kapal",cursor.getInt(1));
                }

                obj.put("no_induk", cursor.getInt(2));
                obj.put("no_izin", cursor.getString(3));
                obj.put("nama_kapal", cursor.getString(4));
                obj.put("pemilik", cursor.getString(5));
                obj.put("alat_tangkap", cursor.getString(6));
                obj.put("status_izin",cursor.getInt(7));
                obj.put("key_unik", cursor.getString(8));

                array.put(obj);

            }
        }
        db.close();
        Log.e("berhasil","data di load");
        return jsonObject.put("list_kapal", array);

    }

    public ArrayList<Kapal> getAllKapal() {
        SQLiteDatabase db =this.getReadableDatabase();

        ArrayList<Kapal> kapals = new ArrayList<Kapal>();
        String SQL = "SELECT * FROM " + TBL_NAME + " ORDER BY " + ID + " DESC";

        Cursor cursor = db.rawQuery(SQL,null);
        if(!cursor.isLast())
        {

            while (cursor.moveToNext())
            {
                Kapal kapal = new Kapal();
                if(cursor.getInt(0) < 1)
                {
                    kapal.setIdKapal(cursor.getInt(0));
                }
                else
                {
                    kapal.setIdKapal(cursor.getInt(1));
                }
                kapal.setNoInduk(cursor.getInt(2));
                kapal.setNoIzin(cursor.getString(3));
                kapal.setNamaKapal(cursor.getString(4));
                kapal.setPemilik(cursor.getString(5));
                kapal.setAlatTangkap(cursor.getString(6));
                kapal.setStatusIzin(cursor.getInt(7));
                kapal.setKeyUnik(cursor.getString(8));

                kapals.add(kapal);

            }
        }
        db.close();
        Log.e("berhasil","data di load");
        return kapals;
    }


    public Kapal getKapal(String keyUnik) {
        SQLiteDatabase db = this.getReadableDatabase();
        String SQL  = "SELECT * FROM " + TBL_NAME + " WHERE " + KEY_UNIK + " LIKE '"+  keyUnik +"' ";
        Cursor cursor = db.rawQuery(SQL,null);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }

        Kapal kapal =new Kapal();
        kapal.setNoInduk(cursor.getInt(cursor.getColumnIndex(NO_INDUK)));
        kapal.setNoIzin(cursor.getString(cursor.getColumnIndex(NO_IZIN)));
        kapal.setNamaKapal(cursor.getString(cursor.getColumnIndex(NAMA_KAPAL)));
        kapal.setPemilik(cursor.getString(cursor.getColumnIndex(PEMILIK)));
        kapal.setAlatTangkap(cursor.getString(cursor.getColumnIndex(ALAT_TANGKAP)));
        kapal.setStatusIzin(cursor.getInt(cursor.getColumnIndex(STATUS_IZIN)));
        kapal.setKeyUnik(cursor.getString(cursor.getColumnIndex(KEY_UNIK)));

        return kapal;
    }

    public int getKapalCount() {

        SQLiteDatabase db = this.getReadableDatabase();
        int NumRows = (int) DatabaseUtils.queryNumEntries(db,TBL_NAME);

        return NumRows;
    }
}
