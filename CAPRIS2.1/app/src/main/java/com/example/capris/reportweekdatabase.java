package com.example.capris;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class reportweekdatabase extends SQLiteOpenHelper {
    private Context context;
    public static final String CREATE_WEEKREPORT="create table weekreport("
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

    public reportweekdatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_WEEKREPORT);
        Toast.makeText(this.context, "周报创建于download目录", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
