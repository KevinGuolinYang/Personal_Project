package com.example.capris;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String CREATE_FORM = "create table form( "
            +"id integer primary key autoincrement,"
            +"二维码ID integer ,"
            +"姓名 text,"
            +"身份证号 integer,"
            +"电话 integer,"
            +"居住小区 text)";
    public static final String CREATE_RECORD = "create table record("
            +"id integer primary key autoincrement,"
            +"二维码ID integer,"
            +"年 text,"
            +"月 text,"
            +"日 text,"
            +"小时 text,"
            +"分钟 text)";

    public MyDataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_FORM);
        sqLiteDatabase.execSQL(CREATE_RECORD);
        Toast.makeText(this.context, "数据库创建成功", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<formmodel> getAll(){
        List<formmodel> list= new ArrayList<>();
        SQLiteDatabase db= dengji.dbHelper.getReadableDatabase();
        Cursor cursor= db.query("form",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                @SuppressLint("Range") formmodel formmodel=new formmodel(
                        cursor.getInt(cursor.getColumnIndex("二维码ID")),
                        cursor.getString(cursor.getColumnIndex("姓名")),
                        cursor.getInt(cursor.getColumnIndex("身份证号")),
                        cursor.getInt(cursor.getColumnIndex("电话")),
                        cursor.getString(cursor.getColumnIndex("居住小区")));

                list.add(formmodel);
            } while(cursor.moveToNext());
        }


        cursor.close();
        return list;
    }
}
