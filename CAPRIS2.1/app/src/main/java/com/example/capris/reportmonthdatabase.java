package com.example.capris;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class reportmonthdatabase extends SQLiteOpenHelper {
    public static final String CREATE_MONTHREPORT="create table monthreport("
            +"二维码ID integer,"
            +"姓名 text,"
            +"身份证号 integer,"
            +"电话 integer,"
            +"小区 text,"
            +"年 text,"
            +"月 text,"
            +"日 text,"
            +"小时 text,"
            +"分钟 text)";
    private Context context;
    public reportmonthdatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        sqLiteDatabase.execSQL(CREATE_MONTHREPORT);
        Toast.makeText(this.context, "月报创建于download目录", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
