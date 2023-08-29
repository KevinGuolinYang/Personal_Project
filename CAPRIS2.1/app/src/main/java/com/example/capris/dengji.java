package com.example.capris;

import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

public class dengji extends AppCompatActivity {
    public static MyDataBaseHelper dbHelper;

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        //菜单
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bangdingxinxi:
                //点击绑定信息
                Intent intenttobangdingxinxi = new Intent(dengji.this, bangdingxinxi.class);
                startActivity(intenttobangdingxinxi);
                break;
            case R.id.shujutongji:
                //点击数据统计
                SQLiteDatabase db = dengji.dbHelper.getWritableDatabase();
                if(db.query("form", new String[]{"id,二维码ID,姓名,身份证号,电话,居住小区"}, "id=?", new String[]{String.valueOf(1)}, null, null, null).getCount()!=0){
                    Intent intenttoshujutongji = new Intent(dengji.this, shujutongji.class);
                    startActivity(intenttoshujutongji);
                }else{
                    Toast.makeText(this,"没有数据",Toast.LENGTH_LONG).show();
                }

                break;
            default:

        }
        return true;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dengji_layouyt);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        //创建数据库信息存储数据库
        dbHelper = new MyDataBaseHelper(this, "information.db", null, 1);
        dbHelper.getWritableDatabase();
        //handler
        Handler handler = new Handler();
        //创建输入框
        EditText dengjierweimashuru = (EditText) findViewById(R.id.dengjierweimaid);
        //一个自动监听文本框输入的线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){

                    while(dengjierweimashuru.getText().toString().isEmpty()){

                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    Log.d("文本",dengjierweimashuru.getText().toString() );
                    Log.d("自动执行", "开始");
                    //获取输入框二维码ID信息
                    String dengjierweimaid = dengjierweimashuru.getText().toString();
                    //创建数据库对象
                    SQLiteDatabase db = dengji.dbHelper.getWritableDatabase();
                    //在数据库中查找该二维码所对应的信息
                    Cursor cursor = db.query("form", new String[]{"二维码ID,姓名,身份证号,电话,居住小区"}, "二维码ID=?", new String[]{dengjierweimaid}, null, null, null);
                    if(cursor.moveToFirst()){
                        @SuppressLint("Range") String shujukuhuoquxingming = cursor.getString(cursor.getColumnIndex("姓名"));
                        @SuppressLint("Range") String shujukuhuoqushenfenzhenghao = cursor.getString(cursor.getColumnIndex("身份证号"));
                        @SuppressLint("Range") String shujukuhuoqudianhua = cursor.getString(cursor.getColumnIndex("电话"));
                        @SuppressLint("Range") String shujukuhuoqujuzhuxiaoqu = cursor.getString(cursor.getColumnIndex("居住小区"));
                        cursor.close();
                        //创建contentvalues 对象
                        ContentValues values_dengjierweimaid = new ContentValues();
                        //将二维码ID放入数据库中
                        values_dengjierweimaid.put("二维码ID", dengjierweimaid);
                        //获取当前日期和时间
                        Calendar calendar = Calendar.getInstance();//获取年月日
                        int year = calendar.get(Calendar.YEAR);

                        int month = calendar.get(Calendar.MONTH)+1;

                        int day = calendar.get(Calendar.DAY_OF_MONTH);

                        int hour = calendar.get(Calendar.HOUR_OF_DAY);

                        int minute = calendar.get(Calendar.MINUTE);
                        //组合数据
                        values_dengjierweimaid.put("年", year);
                        values_dengjierweimaid.put("月", month);
                        Log.d("月份", String.valueOf(month));
                        values_dengjierweimaid.put("日", day);
                        values_dengjierweimaid.put("小时", hour);
                        values_dengjierweimaid.put("分钟", minute);
                        db.insert("record", null, values_dengjierweimaid);
                        //intent到下一个activity
                        Intent intentTodengjihouxinxixianshi = new Intent(dengji.this, dengjihouxinxixianshi.class);
                        //将从数据库获取的信息传入intent
                        intentTodengjihouxinxixianshi.putExtra("姓名", shujukuhuoquxingming);
                        intentTodengjihouxinxixianshi.putExtra("身份证号", shujukuhuoqushenfenzhenghao);
                        intentTodengjihouxinxixianshi.putExtra("电话", shujukuhuoqudianhua);
                        intentTodengjihouxinxixianshi.putExtra("居住小区", shujukuhuoqujuzhuxiaoqu);

                        startActivity(intentTodengjihouxinxixianshi);
                        dengjierweimashuru.setText(null);


                    }else{
                        dengjierweimashuru.setText(null);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(dengji.this,"数据不存在",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        }).start();



    }
}

