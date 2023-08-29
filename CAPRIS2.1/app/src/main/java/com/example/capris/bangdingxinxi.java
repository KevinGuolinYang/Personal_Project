package com.example.capris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class bangdingxinxi extends AppCompatActivity {
    String juzhuxiaoqu;

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.confirm:
                //获取文本输入框信息
                //创建二维码ID输入框对象
                EditText erweimaidshuru=(EditText) findViewById(R.id.erweimaidshuru);
                //创建姓名输入框对象
                EditText xingmingshuru=(EditText) findViewById(R.id.xingmingshuru);
                //创建身份证号输入框对象
                EditText shenfenzhenghaoshuru = (EditText) findViewById(R.id.shenfenzhenghaoshuru);
                //创建电话输入框对象
                EditText dianhuashuru = (EditText) findViewById(R.id.dianhuashuru);
                //将以上数据添加进数据库
                //获取输入框信息
                String erweimaid= erweimaidshuru.getText().toString();
                String xingming=xingmingshuru.getText().toString();
                String shenfenzhenghao= shenfenzhenghaoshuru.getText().toString();
                String dianhua= dianhuashuru.getText().toString();
                //数据库对象
                SQLiteDatabase db=dengji.dbHelper.getWritableDatabase();

                if(erweimaid.isEmpty()
                        ||xingming.isEmpty()
                        ||shenfenzhenghao.isEmpty()
                        ||dianhua.isEmpty()){
                    Toast.makeText(this, "输入内容不能为空", Toast.LENGTH_SHORT).show();

                }else{
                    try {
                        //判断电话位数
                        long tmpdianhua = Long.parseLong(dianhua);
                        int count = 0;
                        while (tmpdianhua >= 1) {
                            tmpdianhua /= 10;
                            count++;//11

                        }
                        //判断身份证号位数
                        Long tmpshenfenzhenghao = Long.parseLong(shenfenzhenghao);
                        int count2 = 0;
                        while (tmpshenfenzhenghao >= 1) {
                            tmpshenfenzhenghao /= 10;
                            count2++;//18
                        }

                        if (count != 11 || count2 != 18) {
                            Toast.makeText(this, "请输入正确的电话号码或身份证号", Toast.LENGTH_SHORT).show();
                        } else {
                            //编辑加入数据库的信息
                            long erweimaid1 = Long.parseLong(erweimaid);
                            long shenfenzhenghao1 = Long.parseLong(shenfenzhenghao);
                            long dianhuahaoma1 = Long.parseLong(dianhua);
                            ContentValues values = new ContentValues();
                            values.put("二维码ID", erweimaid1);
                            values.put("姓名", xingming);
                            values.put("身份证号", shenfenzhenghao1);
                            values.put("电话", dianhuahaoma1);
                            values.put("居住小区", juzhuxiaoqu);
                            db.insert("form", null, values);
                            finish();//结束activity
                        }
                    } catch (Exception e) {
                        Log.e("报错", "报错");
                        e.printStackTrace();
                        Toast.makeText(this,"数据格式不正确",Toast.LENGTH_LONG).show();
                    }
                }

                break;
            default:
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bangdingxinxi_layout);
        //toolbar
        Toolbar toolbar=(Toolbar)findViewById(R.id.Toolbar2);
        setSupportActionBar(toolbar);
        //spinner
        Spinner spinner = (Spinner) findViewById(R.id.xuanzejuzhuxiaoqu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.content, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                juzhuxiaoqu=spinner.getSelectedItem().toString();
                Log.d("居住小区", juzhuxiaoqu);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}