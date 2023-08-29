package com.example.capris;

public class formmodel {
    private int 二维码ID;//二维码ID
    private String 姓名;//姓名
    private int 身份证号;//身份证号
    private int 电话;//电话
    private String 居住小区;//居住小区

    public formmodel(int erweimaid, String xingming, int shenfenzhenghao, int dianhua, String juzhuxiaoqu) {
        this.二维码ID = erweimaid;
        this.姓名 = xingming;
        this.身份证号 = shenfenzhenghao;
        this.电话 = dianhua;
        this.居住小区 = juzhuxiaoqu;
    }

    public int get二维码ID() {
        return 二维码ID;
    }

    public void set二维码ID(int 二维码ID) {
        this.二维码ID = 二维码ID;
    }

    public String get姓名() {
        return 姓名;
    }

    public void set姓名(String 姓名) {
        this.姓名 = 姓名;
    }

    public int get身份证号() {
        return 身份证号;
    }

    public void set身份证号(int 身份证号) {
        this.身份证号 = 身份证号;
    }

    public int get电话() {
        return 电话;
    }

    public void set电话(int 电话) {
        this.电话 = 电话;
    }

    public String get居住小区() {
        return 居住小区;
    }

    public void set居住小区(String 居住小区) {
        this.居住小区 = 居住小区;
    }

    @Override
    public String toString() {
        return "二维码ID:" + 二维码ID +
                ", 姓名:" + 姓名 +
                ", 身份证号:" + 身份证号 +
                ", 电话:" + 电话 +
                ", 居住小区:" + 居住小区  ;
    }
}

