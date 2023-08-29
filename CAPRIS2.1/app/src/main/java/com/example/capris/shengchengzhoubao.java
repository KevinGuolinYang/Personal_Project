package com.example.capris;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class shengchengzhoubao extends AppCompatActivity {
    public  static reportweekdatabase weekreport;
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1;


    public int[] minusDay(int year, int month, int day, int minusDay) {
        int ryear = 0;
        int rmonth = 0;
        int rday = 0;
        if (day > minusDay) {
            ryear = year;
            rmonth = month;
            rday = day - minusDay;
        } else {
            if (month - 1 == 1 || month - 1 == 3 || month - 1 == 5 || month - 1 == 7 || month - 1 == 8 || month - 1 == 10) {
                ryear = year;
                rmonth = month - 1;
                int tmp = minusDay - day;
                rday = 31 - tmp + 1;
            } else if (month - 1 == 4 || month - 1 == 6 || month - 1 == 9 || month - 1 == 11) {
                ryear = year;
                rmonth = month - 1;
                int tmp = minusDay - day;
                rday = 30 - tmp + 1;
            } else if (month - 1 == 2) {
                if (year % 4 == 0) {
                    ryear = year;
                    rmonth = month - 1;
                    int tmp = minusDay - day;
                    rday = 29 - tmp + 1;
                } else {
                    ryear = year;
                    rmonth = month - 1;
                    int tmp = minusDay - day;
                    rday = 28 - tmp + 1;
                }
            } else if (month == 1) {
                ryear = year - 1;
                rmonth = 12;
                int tmp = minusDay - day;
                rday = 31 - tmp + 1;
            }
        }
        return new int[]{ryear, rmonth, rday};
    }
    public int[] addDay(int year, int month, int day, int addDay) {//addDay<=31
        int ryear = 0;
        int rmonth = 0;
        int rday = 0;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10) {
            if (31 - day >= addDay) {
                ryear = year;
                rmonth = month;
                rday = day + addDay;
            } else {
                ryear = year;
                rmonth = month + 1;
                rday = addDay - (31 - day);
            }
        } else if (month == 12) {
            if (31 - day >= addDay) {
                ryear = year;
                rmonth = month;
                rday = day + addDay;
            } else {
                ryear = year + 1;
                rmonth = 1;
                rday = addDay - (31 - day);
            }

        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (30 - day >= addDay) {
                ryear = year;
                rmonth = month;
                rday = day + addDay;
            } else {
                ryear = year;
                rmonth = month + 1;
                rday = addDay - (30 - day);
            }

        } else if (month == 2) {
            if (year % 4 == 0) {//闰年
                if (28 - day >= addDay) {
                    ryear = year;
                    rmonth = month;
                    rday = day + addDay;
                } else {
                    ryear = year;
                    rmonth = month + 1;
                    rday = addDay - (28 - day);
                }
            } else {
                if (29 - day >= addDay) {
                    ryear = year;
                    rmonth = month;
                    rday = day + addDay;
                } else {
                    ryear = year;
                    rmonth = month + 1;
                    rday = addDay - (29 - day);
                }

            }
        }
        return new int[]{ryear, rmonth, rday};
    }

    @SuppressLint("Range")
    public int findID(int beginyear, int beginmonth, int beginday, int nowyear, int nowmonth, int nowday) {
        int benzhourenshu = 0;
        int id = 0;
        int id_2 = 0;
        SQLiteDatabase db = dengji.dbHelper.getWritableDatabase();
        Cursor cursor = db.query("record", new String[]{"id,年,月,日"}, "年=? AND 月=? AND 日=?", new String[]{String.valueOf(beginyear), String.valueOf(beginmonth), String.valueOf(beginday)}, null, null, null);

        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
            Log.d("ID", String.valueOf(id));
            Cursor cursor_2 = db.query("record", new String[]{"id,年,月,日"}, "年=? AND 月=? AND 日=?", new String[]{String.valueOf(nowyear), String.valueOf(nowmonth), String.valueOf(nowday)}, null, null, null);
            if (cursor_2.moveToFirst()) {

                do {
                    id_2 = cursor_2.getInt(cursor_2.getColumnIndex("id"));
                    Log.d("ID_2", String.valueOf(id_2));

                } while (cursor_2.moveToNext());
                benzhourenshu = id_2 - id + 1;

                return benzhourenshu;
            } else {
                int datelist_2[] = minusDay(nowyear, nowmonth, nowday, 1);
                nowyear = datelist_2[0];
                nowmonth = datelist_2[1];
                nowday = datelist_2[2];
                benzhourenshu = findID(beginyear, beginmonth, beginday, nowyear, nowmonth, nowday);
            }
            } else{
                int datelist[] = addDay(beginyear, beginmonth, beginday, 1);
                beginyear = datelist[0];
                beginmonth = datelist[1];
                beginday = datelist[2];
                Log.d("beginday", String.valueOf(beginday));
                benzhourenshu = findID(beginyear, beginmonth, beginday, nowyear, nowmonth, nowday);
            }


            return benzhourenshu;
        }
    public int[] findTwoID ( int beginyear, int beginmonth, int beginday, int nowyear, int nowmonth, int nowday){
            int benzhourenshu = 0;
            int id = 0;
            int id_2 = 0;
            SQLiteDatabase db = dengji.dbHelper.getWritableDatabase();
            int list[] = new int[2];
            Cursor cursor = db.query("record", new String[]{"id,年,月,日"}, "年=? AND 月=? AND 日=?", new String[]{String.valueOf(beginyear), String.valueOf(beginmonth), String.valueOf(beginday)}, null, null, null);

            if (cursor.moveToFirst()) {
                id = cursor.getInt(cursor.getColumnIndex("id"));
                Log.d("ID", String.valueOf(id));
                Cursor cursor_2 = db.query("record", new String[]{"id,年,月,日"}, "年=? AND 月=? AND 日=?", new String[]{String.valueOf(nowyear), String.valueOf(nowmonth), String.valueOf(nowday)}, null, null, null);
                if (cursor_2.moveToFirst()) {

                    do {
                        id_2 = cursor_2.getInt(cursor_2.getColumnIndex("id"));
                        Log.d("ID_2", String.valueOf(id_2));

                    } while (cursor_2.moveToNext());
                    list = new int[]{id, id_2};


                }else{
                    int datelist_2[] = minusDay(nowyear, nowmonth, nowday, 1);
                    nowyear = datelist_2[0];
                    nowmonth = datelist_2[1];
                    nowday = datelist_2[2];
                    list = findTwoID(beginyear, beginmonth, beginday, nowyear, nowmonth, nowday);
                }

            } else {
                int datelist[] = addDay(beginyear, beginmonth, beginday, 1);
                beginyear = datelist[0];
                beginmonth = datelist[1];
                beginday = datelist[2];
                Log.d("beginday", String.valueOf(beginday));
                list = findTwoID(beginyear, beginmonth, beginday, nowyear, nowmonth, nowday);
            }


            return list;
        }

    @SuppressLint("Range")
    public List<String> getJuzhuxiaoqu (int list[],SQLiteDatabase wrdb){
            List<String> juzhuxiaoqu = new ArrayList<>();
            SQLiteDatabase db = dengji.dbHelper.getWritableDatabase();
            //获取result,_int

            Calendar calendar = Calendar.getInstance();//获取年月日
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            int year_int = calendar.get(Calendar.YEAR);

            String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
            int month_int = calendar.get(Calendar.MONTH)+1;

            String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            int day_int = calendar.get(Calendar.DAY_OF_MONTH);
            int resultday;
            int resultmonth;
            int resultyear;
            if (day_int >= 7) {
                resultday = day_int - 7 + 1;
                resultmonth = month_int;
                resultyear = year_int;
            } else {
                if (month_int - 1 == 3 || month_int - 1 == 5 || month_int - 1 == 7 || month_int - 1 == 8 || month_int - 1 == 10 || month_int - 1 == 12) {
                    int tmp = 7 - day_int;
                    resultday = 31 - tmp + 1;
                    resultmonth = month_int - 1;
                    resultyear = year_int;

                } else if (month_int - 1 == 4 || month_int - 1 == 6 || month_int - 1 == 9 || month_int - 1 == 11) {
                    int tmp = 7 - day_int;
                    resultday = 30 - tmp + 1;
                    resultmonth = month_int - 1;
                    resultyear = year_int;
                } else {//剩下的情况，月=2
                    if (year_int % 4 == 0) {//闰年
                        int tmp = 7 - day_int;
                        resultday = 29 - tmp + 1;
                        resultmonth = month_int - 1;
                        resultyear = year_int;
                    } else {
                        int tmp = 7 - day_int;
                        resultday = 28 - tmp + 1;
                        resultmonth = month_int - 1;
                        resultyear = year_int;
                    }

                }
            }
            list = findTwoID(resultyear, resultmonth, resultday, year_int, month_int, day_int);
            int id_first = list[0];
            int id_last = list[1];

            while (id_first <= id_last) {
                Cursor cursor = db.query("record", new String[]{"id","二维码ID","年","月","日","小时","分钟"}, "id=?", new String[]{String.valueOf(id_first)}, null, null, null);
                int tmp = 0;
                String nian = null;
                String yue=null;
                String ri=null;
                String xiaoshi=null;
                String fenzhong=null;
                ContentValues v=new ContentValues();
                if (cursor.moveToFirst()) {
                    tmp = cursor.getInt(cursor.getColumnIndex("二维码ID"));
                    nian=cursor.getString(cursor.getColumnIndex("年"));
                    yue=cursor.getString(cursor.getColumnIndex("月"));
                    ri=cursor.getString(cursor.getColumnIndex("日"));
                    xiaoshi=cursor.getString(cursor.getColumnIndex("小时"));
                    fenzhong=cursor.getString(cursor.getColumnIndex("分钟"));

                    v.put("二维码ID",tmp);
                    v.put("年",nian);
                    v.put("月",yue);
                    v.put("日",ri);
                    v.put("小时",xiaoshi);
                    v.put("分钟",fenzhong);

                    Log.d("tmp", String.valueOf(tmp));
                }


                Cursor cursor_2 = db.query("form", new String[]{"二维码ID","居住小区","姓名","身份证号","电话"}, "二维码ID=?", new String[]{String.valueOf(tmp)}, null, null, null);
                String tmp_2 = null;
                String xingming = null;
                String shenfenzhenghao=null;
                String dianhua=null;
                Log.d("cursor_2", String.valueOf(cursor_2.getCount()));//log:OK
                if (cursor_2.moveToFirst()) {
                    Log.d("getColumnindex", String.valueOf(cursor_2.getColumnIndex("居住小区")));
                    tmp_2 = cursor_2.getString(cursor_2.getColumnIndex("居住小区"));
                    xingming=cursor_2.getString(cursor_2.getColumnIndex("姓名"));
                    shenfenzhenghao=cursor_2.getString(cursor_2.getColumnIndex("身份证号"));
                    dianhua=cursor_2.getString(cursor_2.getColumnIndex("电话"));
                    v.put("姓名",xingming);
                    v.put("电话",dianhua);
                    v.put("身份证号",shenfenzhenghao);
                    v.put("小区",tmp_2);
                    wrdb.insert("weekreport",null,v);
                    Log.d("tmp2", tmp_2);
                }
                String general="二维码ID："+tmp+","
                                +"姓名"+xingming+","
                        +"身份证号"+shenfenzhenghao+","
                        +"电话"+dianhua+","
                        +"居住小区"+tmp_2+","
                        +"时间"+nian+"年"+yue+"月"+ri+"日"+xiaoshi+"时"+fenzhong+"分";


                juzhuxiaoqu.add(general);
                id_first++;
            }


            return juzhuxiaoqu;
        }

        @SuppressLint("Range")
        @Override
        protected void onCreate (Bundle savedInstanceState){

            super.onCreate(savedInstanceState);
            setContentView(R.layout.shengchengzhoubao_layout);
            //获取当前日期用作文件名
            Calendar calendar = Calendar.getInstance();//获取年月日
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            int year_int = calendar.get(Calendar.YEAR);

            String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
            int month_int = calendar.get(Calendar.MONTH)+1;
            Log.d("month_int的值", String.valueOf(month_int));

            String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            int day_int = calendar.get(Calendar.DAY_OF_MONTH);

            String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));

            String minute = String.valueOf(calendar.get(Calendar.MINUTE));
            String name = year + month + day + hour + minute + "_weekly" + ".db";
            Log.d("周报", name);
            weekreport = new reportweekdatabase(this, name, null, 1);
            SQLiteDatabase wrdb=weekreport.getWritableDatabase();
            //获取本周人数
            SQLiteDatabase db = dengji.dbHelper.getWritableDatabase();
            /**
             * 以下内容是为了获取本周人数，首先要获取时间范围，即多少年多少月多少日到多少年多少月多少日，随后使用cursor获取具体数据，进行计数
             */
            //获取时间范围的算法
            int resultday;
            int resultmonth;
            int resultyear;
            if (day_int >= 7) {
                resultday = day_int - 7 + 1;
                resultmonth = month_int;
                resultyear = year_int;
            } else {
                if (month_int - 1 == 3 || month_int - 1 == 5 || month_int - 1 == 7 || month_int - 1 == 8 || month_int - 1 == 10 || month_int - 1 == 12) {
                    int tmp = 7 - day_int;
                    resultday = 31 - tmp + 1;
                    resultmonth = month_int - 1;
                    resultyear = year_int;

                } else if (month_int - 1 == 4 || month_int - 1 == 6 || month_int - 1 == 9 || month_int - 1 == 11) {
                    int tmp = 7 - day_int;
                    resultday = 30 - tmp + 1;
                    resultmonth = month_int - 1;
                    resultyear = year_int;
                } else {//剩下的情况，月=2
                    if (year_int % 4 == 0) {//闰年
                        int tmp = 7 - day_int;
                        resultday = 29 - tmp + 1;
                        resultmonth = month_int - 1;
                        resultyear = year_int;
                    } else {
                        int tmp = 7 - day_int;
                        resultday = 28 - tmp + 1;
                        resultmonth = month_int - 1;
                        resultyear = year_int;
                    }

                }
            }
            Log.d("时间范围", year_int + "年" + month_int + "月" + day_int + "日-" + resultyear + "年" + resultmonth + "月" + resultday + "日");

            int benzhourenshu = findID(resultyear, resultmonth, resultday, year_int, month_int, day_int);
            TextView xianshibenzhourenshu = (TextView) findViewById(R.id.xianshibenzhourenshu);
            Log.d("本周人数", String.valueOf(benzhourenshu));
            xianshibenzhourenshu.setText(String.valueOf(benzhourenshu));//OK

            int[] tmplist = findTwoID(resultyear, resultmonth, resultday, year_int, month_int, day_int);
            List<String> zhoubao = getJuzhuxiaoqu(tmplist,wrdb);
            Log.d("周报", String.valueOf(zhoubao));
            zhoubao.removeAll(Collections.singleton(null));//除去null值
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(shengchengzhoubao.this, android.R.layout.simple_list_item_1, zhoubao);
            ListView listView_1 = (ListView) findViewById(R.id.zhoubao);
            listView_1.setAdapter(adapter);


            //导出
            if (ContextCompat.checkSelfPermission(shengchengzhoubao.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(shengchengzhoubao.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(shengchengzhoubao.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_REQUEST_CODE);
            } else {
                // 权限已经被授予，执行文件操作
                Log.d("perform week report", "OK");
                DatabaseHelper.copyDatabaseToPublicDirectory(shengchengzhoubao.this, name);
            }



        }
    }
