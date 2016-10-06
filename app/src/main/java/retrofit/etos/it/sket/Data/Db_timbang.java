package retrofit.etos.it.sket.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.etos.it.sket.Interface.Db_timbangInt;
import retrofit.etos.it.sket.Model.Timbang;

/**
 * Created by IT on 15/09/2016.
 *
 *
 */

public class Db_timbang extends SQLiteOpenHelper implements Db_timbangInt {
    public static final String DB_NAME = "timbanggan.db";
    public static final String TBL_NAME = "db_timbang";

    public static final int VERSI = 1;
    public static final String ID = "id_timbang";
    public static final String BERAT = "berat";
    public static final String ID_USER = "id_user";
    public static final String KODE_TIMBANG = "kode_timbang";
    public static final String NAMA_IKAN = "nama_ikan";
    public static final String STATUS_TIMBANG = "status_timbang";
    public static final String TANGGAL_TIMBANG = "tanggal_timbang";
    public static final String SATUAN = "satuan";
    public static final String ID_KAPAL = "id_kapal";
    public static final String ID_IKAN = "id_ikan";
    public static final String UPI = "upi";
    public static final String FAKTOR_B = "faktor_b";
    public static final String FAKTOR_A = "faktor_a";
    public static final String ID_TIMBANG_DETAIL = "id_timbang_detail";
    public static final String HARGA = "harga";
    public static final String KEY_UNIK = "keyUnik";
    public static final String SQL_CREATE = "CREATE TABLE " + TBL_NAME + "("
            +ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +BERAT+" TEXT, "
            +ID_USER+" TEXT, "
            +KODE_TIMBANG+" TEXT, "
            +NAMA_IKAN+" TEXT, "
            +STATUS_TIMBANG+" TEXT, "
            +TANGGAL_TIMBANG+" TEXT, "
            +SATUAN+" TEXT, "
            +ID_KAPAL+" TEXT, "
            +ID_IKAN+" TEXT, "
            +UPI+" TEXT, "
            +FAKTOR_A+" TEXT, "
            +FAKTOR_B+" TEXT, "
            +ID_TIMBANG_DETAIL+" TEXT, "
            +HARGA+" TEXT, "
            +KEY_UNIK+" TEXT "
            +")";


    public Db_timbang(Context context) {
        super(context, DB_NAME, null, VERSI);
    }


    @Override
    public void addTimbang(Timbang timbang) {
        SQLiteDatabase db = this.getWritableDatabase();
//
//        String SQL = "DROP TABLE IF EXISTS "+TBL_NAME;
//        db.execSQL(SQL);
//        this.onCreate(db);
        try {
            ContentValues values = new ContentValues();
            values.put(BERAT, timbang.getBerat());
            values.put(ID_USER, timbang.getId_user());
            values.put(KODE_TIMBANG, timbang.getKode_timbang());
            values.put(NAMA_IKAN, timbang.getNama_ikan());
            values.put(STATUS_TIMBANG, timbang.getStatus_timbang());
            values.put(TANGGAL_TIMBANG, timbang.getTanggal_timbang());
            values.put(SATUAN, timbang.getSatuan());
            values.put(ID_KAPAL, timbang.getId_kapal());
            values.put(ID_IKAN, timbang.getId_ikan());
            values.put(UPI, timbang.getUpi());
            values.put(FAKTOR_A, timbang.getFaktor_a());
            values.put(FAKTOR_B, timbang.getFaktor_b());
            values.put(ID_TIMBANG_DETAIL, timbang.getId_timbang_detail());
            values.put(HARGA, timbang.getHarga());
            values.put(KEY_UNIK, timbang.getKeyUnik());
            db.insert(TBL_NAME,null,values);
            Log.i("db_timbang", "berhasil menambah data!");

            db.close();
        }
        catch (Exception e)
        {
            Log.e("GAGAL","input" + e);
        }
    }

    @Override
    public ArrayList<Timbang> getAllTimbang(int id) {
        return null;
    }

    @Override
    public Timbang getTimbang(int id) {
        return null;
    }

    @Override
    public int getTimbangCount(int id) {
        return 0;
    }

    public void clearTable()   {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBL_NAME, null,null);
    }

    @Override
    public boolean updateData(String keyUnik) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATUS_TIMBANG,1);
        db.update(TBL_NAME,values,"" + KEY_UNIK + "=? and " + STATUS_TIMBANG + "=0",new String[]{keyUnik.toString()});
        Log.i("update","berhasil");
        return true;
    }

    @Override
    public  ArrayList<Timbang> ambilData(String keyUnik) {
        Log.e("keyUnik",keyUnik);
        SQLiteDatabase db = this.getReadableDatabase();
        String SQL = "Select * from " + TBL_NAME + " where " + KEY_UNIK + " = ? ";
        Cursor cursor = db.rawQuery(SQL,new String[] {keyUnik.toString()}); //

        ArrayList<Timbang> timbangs = new ArrayList<Timbang>();
        Log.e("jumlah data","" + cursor.getCount());

        if(!cursor.isLast())
        {
            while (cursor.moveToNext())
            {
                Timbang timbang = new Timbang();
                timbang.setKeyUnik(cursor.getString(cursor.getColumnIndex(KEY_UNIK)));
                timbang.setKode_timbang(cursor.getString(cursor.getColumnIndex(KODE_TIMBANG)));
                timbang.setStatus_timbang(cursor.getInt(cursor.getColumnIndex(STATUS_TIMBANG)));
                timbang.setFaktor_a(cursor.getString(cursor.getColumnIndex(FAKTOR_A)));
                timbang.setFaktor_b(cursor.getString(cursor.getColumnIndex(FAKTOR_B)));
                timbang.setUpi(cursor.getString(cursor.getColumnIndex(UPI)));
                timbang.setSatuan(cursor.getString(cursor.getColumnIndex(SATUAN)));
                timbang.setId_user(cursor.getInt(cursor.getColumnIndex(ID_USER)));
                timbang.setBerat(cursor.getDouble(cursor.getColumnIndex(BERAT)));
                timbang.setHarga(cursor.getInt(cursor.getColumnIndex(HARGA)));
                timbang.setId_ikan(cursor.getInt(cursor.getColumnIndex(ID_IKAN)));
                timbang.setNama_ikan(cursor.getString(cursor.getColumnIndex(NAMA_IKAN)));
                timbang.setTanggal_timbang(cursor.getString(cursor.getColumnIndex(TANGGAL_TIMBANG)));

                timbangs.add(timbang);
                //bug ---> tujuan ikan
            }

            while (cursor.isAfterLast() == false)
            {
                Log.e("Loop 2",cursor.getString(cursor.getColumnIndex(KEY_UNIK)));
            }

        }

        cursor.close();
        db.close();
        return timbangs;

    }

    @Override
    public ArrayList<HashMap<String, String>> countTimbang(String keyUnik) {

        ArrayList<HashMap<String, String>> list= new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //jumlah Timbang
        String SQL = "Select " + NAMA_IKAN + " , " + ID_IKAN + " , " + KEY_UNIK + " , " +
                        "sum( CAST("+ BERAT +"  as INTEGER)) as jml_berat, " +
                        " count("+ ID +") as jml_timbang " +
                        "  from " + TBL_NAME + " " +
                        "where " + KEY_UNIK + " = ? " +
                        " group by " + ID_IKAN;
        Cursor cursor = db.rawQuery(SQL,new String[] {keyUnik.toString()}); //

        if(!cursor.isLast())
        {
            int no =0;
            while (cursor.moveToNext())
            {
                HashMap<String,String> array = new HashMap<String,String>();
                array.put("jml_timbang",cursor.getString(cursor.getColumnIndex("jml_timbang")));
                array.put("jml_berat",cursor.getString(cursor.getColumnIndex("jml_berat")));
                array.put("nama_ikan",cursor.getString(cursor.getColumnIndex(NAMA_IKAN)));
                array.put("id_ikan",cursor.getString(cursor.getColumnIndex(ID_IKAN)));
                array.put("keyUnik",cursor.getString(cursor.getColumnIndex(KEY_UNIK)));

                no = no + 1;
                array.put("no",Integer.toString(no) );

                list.add(array);
            }
        }

        return list;
    }

    @Override
    public JSONObject toJSONallTimbang() throws JSONException {
        SQLiteDatabase db =this.getReadableDatabase();
        String SQL = "Select * from " + TBL_NAME + " ORDER by " + ID + " DESC";
        Cursor cursor = db.rawQuery(SQL,null);

        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();

        if(!cursor.isLast())
        {

            while (cursor.moveToNext())
            {
                JSONObject obj = new JSONObject();


                obj.put("berat",cursor.getString(cursor.getColumnIndex(BERAT)));
                obj.put("id_user",cursor.getString(cursor.getColumnIndex(ID_USER)));
                obj.put("kode_timbang",cursor.getString(cursor.getColumnIndex(KODE_TIMBANG)));
                obj.put("nama_ikan",cursor.getString(cursor.getColumnIndex(NAMA_IKAN)));
                obj.put("status_timbang",cursor.getString(cursor.getColumnIndex(STATUS_TIMBANG)));
                obj.put("tanggal_timbang",cursor.getString(cursor.getColumnIndex(TANGGAL_TIMBANG)));
                obj.put("satuan",cursor.getString(cursor.getColumnIndex(SATUAN)));
                obj.put("id_kapal",cursor.getString(cursor.getColumnIndex(ID_KAPAL)));
                obj.put("id_ikan",cursor.getString(cursor.getColumnIndex(ID_IKAN)));
                obj.put("id_timbang","0");
                obj.put("upi",cursor.getString(cursor.getColumnIndex(UPI)));
                obj.put("faktor_b",cursor.getString(cursor.getColumnIndex(FAKTOR_B)));
                obj.put("faktor_a",cursor.getString(cursor.getColumnIndex(FAKTOR_A)));
                obj.put("id_timbang_detail",cursor.getString(cursor.getColumnIndex(ID)));
                obj.put("harga",cursor.getString(cursor.getColumnIndex(HARGA)));
                obj.put("keyUnik",cursor.getString(cursor.getColumnIndex(KEY_UNIK)));


                array.put(obj);

            }
        }
        db.close();
        Log.e("berhasil","data di load");
        return jsonObject.put("list_timbang", array);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String SQL = "DROP TABLE IF EXISTS "+TBL_NAME;
        db.execSQL(SQL);
        onCreate(db);
    }

    public void Reset() {
        SQLiteDatabase db = this.getWritableDatabase();
        String SQL = "DROP TABLE IF EXISTS "+TBL_NAME;
        db.execSQL(SQL);
        onCreate(db);
    }

}
