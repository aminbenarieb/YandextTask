package com.aminbenarieb.yandextask.Services.Storage;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by aminbenarieb on 4/16/17.
 */

public class ABStorage extends Service implements Storage {

    public void saveFile(String params, String mJsonResponse) throws IOException {

        FileWriter file = new FileWriter("/data/data/"
                + getPackageName() + "/" + params);
        file.write(mJsonResponse);
        file.flush();
        file.close();
    }

    public String getFile(String params) throws IOException {
        File f = new File("/data/data/" + getPackageName() + "/" + params);
        FileInputStream fileInputStream = new FileInputStream(f);
        int size = fileInputStream.available();
        byte[] buffer = new byte[size];
        fileInputStream.read(buffer);
        fileInputStream.close();

        return new String(buffer);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
