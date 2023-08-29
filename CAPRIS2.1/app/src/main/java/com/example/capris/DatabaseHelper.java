package com.example.capris;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class DatabaseHelper {

    public static void copyDatabaseToPublicDirectory(Context context, String dbName) {
        File privateDbPath = context.getDatabasePath(dbName);
        File publicDbPath = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), dbName);
        Log.d("download dic", String.valueOf(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS)));

        try {
            FileChannel src = new FileInputStream(privateDbPath).getChannel();
            FileChannel dst = new FileOutputStream(publicDbPath).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

