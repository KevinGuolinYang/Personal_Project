package com.example.capris;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class dengjihouxinxixianshi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dengjihouxinxixianshi_layout);
        //接受intent的信息
        Intent intent=getIntent();
        String shujukuhuoquxingming=intent.getStringExtra("姓名");
        String shujukuhuoqushenfenzhenghao=intent.getStringExtra("身份证号");
        String shujukuhuoqudianhua=intent.getStringExtra("电话");
        String shujukuhuoqujuzhuxiaoqu=intent.getStringExtra("居住小区");

        //创建文本框对象
        TextView xingmingtishiwenben=(TextView) findViewById(R.id.xianshixingming);
        TextView shenfenzhenghaotishixingwenben=(TextView) findViewById(R.id.xianshishenfenzhenghao);
        TextView dianhuatishixingwenben=(TextView) findViewById(R.id.xianshidianhua);
        TextView juzhuxiaoqutishixingwenben=(TextView) findViewById(R.id.xianshijuzhuxiaoqu);
        //设置文本框信息
        xingmingtishiwenben.setText(shujukuhuoquxingming);
        shenfenzhenghaotishixingwenben.setText(shujukuhuoqushenfenzhenghao);
        dianhuatishixingwenben.setText(shujukuhuoqudianhua);
        juzhuxiaoqutishixingwenben.setText(shujukuhuoqujuzhuxiaoqu);
        //创建按钮对象
        Button queren=(Button) findViewById(R.id.queren);
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}