package com.example.raul.prg_proyecto06;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;


public class Formulario extends Activity {

    private TextView labelId;
    private EditText nombre;
    private Switch hate_switch;

    //Identificador de la base de datos
    private DataBaseHelper mDbHelper;
    private SQLiteDatabase db;
    private long id ;
    private int love_hate;

    //
    // Modo del formulario
    //
    private int modo ;

    //Botones
    private Button boton_guardar;
    private Button boton_cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        hate_switch = (Switch)findViewById(R.id.love_hate_switch);
        hate_switch.setTextOn("love");
        hate_switch.setTextOff("hate");


        //CApturamos los datos enviados
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();

        if (extra == null) return;

        // Consultamos la base de datos
        mDbHelper = new DataBaseHelper(this);
        db = mDbHelper.getWritableDatabase();

        //
        // Obtenemos los elementos de la vista
        //
        labelId = (TextView) findViewById(R.id.label_id);
        nombre  = (EditText) findViewById(R.id.nombre);

        //
        // Obtenemos el identificador del registro si viene indicado
        //
        if (extra.containsKey(DataBaseHelper.ID))
        {
            id = extra.getLong(DataBaseHelper.ID);
            try {
                consultar(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (extra.containsKey("tipo")){

            love_hate = extra.getInt("tipo");
        }

        if(love_hate==1){
            hate_switch.setChecked(true);

        }else if(love_hate==0){
            hate_switch.setChecked(false);

        }


        hate_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    love_hate = 1;
                }else {
                    love_hate = 0;
                }

            }
        });
        //Botones de guardado y cancelar
        boton_guardar = (Button) findViewById(R.id.boton_guardar);
        boton_cancelar = (Button) findViewById(R.id.boton_cancelar);

        //
        // Establecemos el modo del formulario
        //
        establecerModo(extra.getInt(mDbHelper.C_MODO));

        //
        // Definimos las acciones para los dos botones
        //
        boton_guardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                guardar();
            }
        });

        boton_cancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                cancelar();
            }
        });
    }

    private void consultar(long id) throws SQLException {
        //
        // Consultamos el centro por el identificador
        //
        Cursor cursor = mDbHelper.getRegistro(id);
        labelId.setText(labelId.getText()+cursor.getString(cursor.getColumnIndex(DataBaseHelper.ID)));
        nombre.setText(cursor.getString(cursor.getColumnIndex(DataBaseHelper.ITEM_NAME)));
    }

    private void establecerModo(int m)
    {
        this.modo = m ;

        if (modo == mDbHelper.C_VISUALIZAR)
        {
            this.nombre.setEnabled(false);
            this.boton_guardar.setEnabled(false);
            this.hate_switch.setClickable(false);
        }else if ((modo == mDbHelper.C_CREAR)||(modo == mDbHelper.C_EDITAR)){
            this.setTitle(R.string.add_item);
            this.nombre.setEnabled(true);
            this.boton_guardar.setEnabled(true);
            this.hate_switch.setClickable(true);
        }
    }

    private void guardar()
    {
        //
        // Obtenemos los datos del formulario
        //
        ContentValues reg = new ContentValues();
        if (modo == mDbHelper.C_EDITAR) reg.put(mDbHelper.ID, id);
        reg.put(mDbHelper.ITEM_NAME, nombre.getText().toString());
        reg.put(mDbHelper.LOVE_HATE, love_hate);

        if (modo == mDbHelper.C_CREAR)
        {
            mDbHelper.insert(reg);
            Toast.makeText(Formulario.this, "Artista creado", Toast.LENGTH_SHORT).show();
        }   else if (modo == mDbHelper.C_EDITAR){
            Toast.makeText(Formulario.this, "Artista modificado", Toast.LENGTH_SHORT).show();
            mDbHelper.update(reg);
        }

        //
        // Devolvemos el control
        //
        setResult(RESULT_OK);
        finish();
    }
    private void cancelar()
    {
        setResult(RESULT_CANCELED, null);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
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


}