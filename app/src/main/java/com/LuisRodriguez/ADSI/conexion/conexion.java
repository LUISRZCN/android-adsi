package com.LuisRodriguez.ADSI.conexion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class conexion extends SQLiteOpenHelper {
    //atributos - propiedades - caracteristicas
    static String bdNombre = "usuarios";
    static int bdVersion = 1;
    //metodos - acciones - funciones

    public conexion(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //creamos la base de datos cuando se ejecute la aplicacion
        String cadenaSql = "create table usuarios(id interger primary key autoincrement, nombre text,descripcion text)";
        sqLiteDatabase.execSQL(cadenaSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //para actualizar la base de datos
        String cadenaSql = "drop table if exists usuarios";
        sqLiteDatabase.execSQL(cadenaSql);

        onCreate(sqLiteDatabase);

    }
    //creamos los metodos de consulta

    //consulta sin retorno
    public static void consultaSinRetorno(Context context,String cadenaSql){
        conexion miConector = new conexion(context,bdNombre,null,bdVersion);
        SQLiteDatabase miDB = miConector.getWritableDatabase();

        //para cerrar las conexiones de la base de datos y el conector
        miDB.close();
        miConector.close();
    }
    //consulta con retorno
    public  static  String[][] consultaConRetorno(Context context, String cadenaSql){
        //select
        String [][] datos = null;
        conexion miConector = new conexion(context,bdNombre,null,bdVersion);
        SQLiteDatabase miDB = miConector.getReadableDatabase();
        Cursor miCursor = miDB.rawQuery(cadenaSql,null);
        //contar filas y columnas
        int filas = miCursor.getCount();
        int columnas = miCursor.getColumnCount();
        //doy la longitud a la matriz
        datos = new String[filas][columnas];

        int contadorFila = 0;
        while (miCursor.moveToNext()){
            //verdadero por lomenos una fila
            //hay que recorrer las columnas de cada fila
            for (int contadorCol=0; contadorCol < columnas;contadorCol++){

                //asignamos el valor de la tabla a cada posicion
                datos [contadorFila][contadorCol] = miCursor.getString(contadorCol);
            }
            contadorFila++;
        }
        return datos;
    }
}