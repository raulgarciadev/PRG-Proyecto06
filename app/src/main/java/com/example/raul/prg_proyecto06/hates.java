package com.example.raul.prg_proyecto06;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class Hates extends ListActivity {

    private DataBaseHelper mDbHelper;
    private SQLiteDatabase db;
    private SimpleCursorAdapter mAdapter;
    private Cursor c;


    public static final String C_MODO  = "modo" ;
    public static final int C_VISUALIZAR = 551 ;
    public static final int C_CREAR = 552 ;
    public static final int C_EDITAR = 553 ;
    public static final String C_TIPO = "tipo";
    public static final int love_hate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hates);

        mDbHelper = new DataBaseHelper(this);
        db = mDbHelper.getWritableDatabase();

        c = mDbHelper.leerBaseDeDatos(db, love_hate);

        mAdapter = new SimpleCursorAdapter(this, R.layout.activity_list,c, mDbHelper.db_columns, new int[] {R.id._id, R.id.nombre},0 );

        setListAdapter(mAdapter);

        final Button boton=(Button) findViewById(R.id.button);
        boton.setOnClickListener(new Button.OnClickListener(){
                                     @Override
                                     public void onClick(View v) {
                                         Intent intent = new Intent(Hates.this, Formulario.class);
                                         intent.putExtra(C_MODO, C_CREAR);
                                         intent.putExtra(C_TIPO, love_hate);
                                         startActivityForResult(intent, C_CREAR);
                                     }
                                 }
        );
    }


    public void editHandler(View v) {
        //get the row the clicked button is in
        LinearLayout vwParentRow = (LinearLayout)v.getParent();
        TextView id =(TextView) vwParentRow.findViewById(R.id._id);
        Intent intent = new Intent(Hates.this, Formulario.class);

        intent.putExtra(C_MODO, C_EDITAR);
        intent.putExtra(C_TIPO, love_hate);
        intent.putExtra(mDbHelper.ID, Long.valueOf((String)id.getText()));


        this.startActivityForResult(intent, C_EDITAR);
    }

    public void viewHandler(View v) {
        //get the row the clicked button is in
        LinearLayout vwParentRow = (LinearLayout)v.getParent();
        TextView id =(TextView) vwParentRow.findViewById(R.id._id);
        Intent intent = new Intent(Hates.this, Formulario.class);

        intent.putExtra(C_MODO, C_VISUALIZAR);
        intent.putExtra(mDbHelper.ID, Long.valueOf((String)id.getText()));


        this.startActivityForResult(intent, C_VISUALIZAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hates, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case C_CREAR:
                if (resultCode == RESULT_OK){
                    c=mAdapter.getCursor();
                    c=mDbHelper.leerBaseDeDatos(db,love_hate);
                    mAdapter.changeCursor(c);
                    mAdapter.notifyDataSetChanged();

                }
            case C_EDITAR:
                if (resultCode == RESULT_OK){
                    c=mAdapter.getCursor();
                    c=mDbHelper.leerBaseDeDatos(db,love_hate);
                    mAdapter.changeCursor(c);
                    mAdapter.notifyDataSetChanged();

                }
            default:
                super.onActivityResult(requestCode, resultCode, data);

        }

    }
}
