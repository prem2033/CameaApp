package com.prem.cameaapp;

import android.os.Environment;

import java.io.File;

public class SetThePath {
     static File dir,filePath;
    public static File SetPathFroImage(){
        filePath = Environment.getExternalStorageDirectory();
        dir = new File(filePath.getAbsoluteFile() + "/DEMO/");
        dir.mkdir();
        return  dir;
    }
}
