package com.example.raul.prg_proyecto06;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * Created by raulgarciaruiz on 18/2/15.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    //Datos de la BD
    final private static String NAME = "proyecto_seis";
    final static String TABLE_NAME = "love_hate";
    final static String ID = "_id";
    final static String ITEM_NAME = "name";
    final static String LOVE_HATE = "my_status";


    //Modos edicion
    public static final String C_MODO  = "modo" ;
    public static final int C_VISUALIZAR = 551 ;
    public static final int C_CREAR = 552 ;
    public static final int C_EDITAR = 553 ;


    // Comandos
    final static String[] db_columns = {ID, ITEM_NAME,LOVE_HATE};
    final private static String CREATE =
        "CREATE TABLE " + TABLE_NAME + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ITEM_NAME + " TEXT NOT NULL,"
            + LOVE_HATE + " INTEGER DEFAULT 1)";


    final private static Integer VERSION = 1;
    final private Context mContext;



    public DataBaseHelper(Context context) {
        super(context, NAME, null, VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE);

        ContentValues values = new ContentValues();

        values.put(DataBaseHelper.ITEM_NAME, "ColdPlay");
        values.put(DataBaseHelper.LOVE_HATE, 1);
        db.insert(DataBaseHelper.TABLE_NAME,null, values);
        values.clear();

        values.put(DataBaseHelper.ITEM_NAME, "Metallica");
        values.put(DataBaseHelper.LOVE_HATE, 1);
        db.insert(DataBaseHelper.TABLE_NAME,null, values);
        values.clear();

        values.put(DataBaseHelper.ITEM_NAME, "Pitbull");
        values.put(DataBaseHelper.LOVE_HATE, 0);
        db.insert(DataBaseHelper.TABLE_NAME,null, values);
        values.clear();

        values.put(DataBaseHelper.ITEM_NAME, "Meghan Reinor");
        values.put(DataBaseHelper.LOVE_HATE, 1);
        db.insert(DataBaseHelper.TABLE_NAME,null, values);
        values.clear();

        values.put(DataBaseHelper.ITEM_NAME, "Isabel Pantoja");
        values.put(DataBaseHelper.LOVE_HATE, 0);
        db.insert(DataBaseHelper.TABLE_NAME,null, values);
        values.clear();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor leerBaseDeDatos(SQLiteDatabase db, Integer l){
        String consulta = "";
        if (l == 0){
            consulta = " my_status = 0 ";
        }else if(l == 1){
            consulta = " my_status = 1 ";
        }
        return db.query(TABLE_NAME, db_columns, consulta, new String[] {}, null, null, null );
    }

    public Cursor getRegistro(long id) throws SQLException{
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(true, TABLE_NAME, db_columns, ID + " = " + id, null, null, null, null,null);

        if (c!= null){
            c.moveToFirst();
        }
        return c;
    }

    public long insert(ContentValues reg){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(TABLE_NAME, null, reg);
    }


    public long update(ContentValues reg){
        SQLiteDatabase db = this.getWritableDatabase();
        if (reg.containsKey(ID)){
            long id = reg.getAsLong(ID);
            reg.remove(ID);
            return db.update(TABLE_NAME, reg, "_id = " + id, null);
        }

        return db.insert(TABLE_NAME, null, reg);
    }
}
