package com.example.capris;

import static java.lang.Integer.valueOf;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.ValueIterator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class shujutongji extends AppCompatActivity {

    public static reportmonthdatabase monthreport;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.shujutongji_layout);


        //获取年月日
        Calendar calendar = Calendar.getInstance();//获取年月日

        String year = String.valueOf(calendar.get(Calendar.YEAR));

        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);

        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        //当日人数
        SQLiteDatabase db = dengji.dbHelper.getWritableDatabase();
        Cursor cursor = db.query("record", new String[]{"id,年,月,日"}, "年=? AND 月=? AND 日=?", new String[]{year, month, day}, null, null, null);

        int daypeoplenum;//当日人数
        List<Integer> list = new ArrayList<Integer>();
        if (cursor.moveToFirst()) {
            Log.d("当日人数已进入", "OK");
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                list.add(id);
            } while (cursor.moveToNext());
        }


        daypeoplenum = list.size();
        Log.d("当日人数", String.valueOf(daypeoplenum));

        //文本框
        TextView dangrirenshu = (TextView) findViewById(R.id.xianshidangrirenshu);
        dangrirenshu.setText(String.valueOf(daypeoplenum));
        //关闭curcor
        cursor.close();
        //创建按钮
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button shengchengzhoubao = (Button) findViewById(R.id.shengchengzhoubao);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button shengchengyuebao = (Button) findViewById(R.id.shengchengyuebao);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button daochu = (Button) findViewById(R.id.daochu);
        shengchengzhoubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent
                Intent intenttoshengchengzhoubao = new Intent(shujutongji.this, shengchengzhoubao.class);
                startActivity(intenttoshengchengzhoubao);


            }
        });
        daochu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(shujutongji.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(shujutongji.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(shujutongji.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            STORAGE_PERMISSION_REQUEST_CODE);
                } else {
                    // 权限已经被授予，执行文件操作

                    DatabaseHelper.copyDatabaseToPublicDirectory(shujutongji.this, "information.db");
                    Log.d("performfileinformation", "OK");
                    Toast.makeText(shujutongji.this,"information.db已保存到Downloads目录",Toast.LENGTH_LONG).show();
                }
            }


        });
        shengchengyuebao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenttoshengchengyuebao = new Intent(shujutongji.this, shengchengyuebao.class);
                startActivity(intenttoshengchengyuebao);
            }
        });


        //ListView
        //创建listview
        ListView listview = (ListView) findViewById(R.id.renyuanzhucebiao);


        List<String> item = new ArrayList<>();
        int i_int = 1;

        Cursor cursor2;
        if( db.query("form", new String[]{"id,二维码ID,姓名,身份证号,电话,居住小区"}, "id=?", new String[]{String.valueOf(i_int)}, null, null, null).getCount()!=0){
            do {
                cursor2 = db.query("form", new String[]{"id,二维码ID,姓名,身份证号,电话,居住小区"}, "id=?", new String[]{String.valueOf(i_int)}, null, null, null);
                cursor2.moveToFirst();
                i_int++;
                Log.d("cursor2是否为空",String.valueOf(cursor2.getCount()));

                @SuppressLint("Range")long erweimaid = cursor2.getLong(cursor2.getColumnIndex("二维码ID"));

                @SuppressLint("Range")String name = cursor2.getString(cursor2.getColumnIndex("姓名"));
                @SuppressLint("Range")Long shenfenzhenghao = cursor2.getLong(cursor2.getColumnIndex("身份证号"));
                Log.d("身份证号", String.valueOf(shenfenzhenghao));
                @SuppressLint("Range")Long dianhua = cursor2.getLong(cursor2.getColumnIndex("电话"));
                @SuppressLint("Range")String juzhuxiaoqu = cursor2.getString(cursor2.getColumnIndex("居住小区"));
                String content = "二维码ID：" + erweimaid + ",姓名：" + name + "，身份证号：" + shenfenzhenghao + "，电话：" + dianhua + "，居住小区：" + juzhuxiaoqu;

                item.add(content);
                Log.d("下一个是否有", String.valueOf(db.query("form", new String[]{"id,二维码ID,姓名,身份证号,电话,居住小区"}, "id=?", new String[]{String.valueOf(i_int)}, null, null, null).getCount()));


            } while (db.query("form", new String[]{"id,二维码ID,姓名,身份证号,电话,居住小区"}, "id=?", new String[]{String.valueOf(i_int)}, null, null, null).getCount() !=0);
        }else{
            //null
        }



        item.removeAll(Collections.singleton(null));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(shujutongji.this, android.R.layout.simple_list_item_1, item);
        listview.setAdapter(adapter);
    }
}