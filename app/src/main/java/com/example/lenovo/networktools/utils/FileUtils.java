package com.example.lenovo.networktools.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by 10129302 on 15-2-27.
 * 文件操作
 */
public class FileUtils {

    public static File copy(File inFile, String dir) {
        InputStream in;
        OutputStream ou;
        try {
            in = new FileInputStream(inFile);
            ou = new FileOutputStream(dir, false);
            copyStream(in, ou);
            closeSafely(in);
            closeSafely(ou);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new File(dir);
    }

    public static void copyStream(InputStream is, OutputStream os) {
        final int BUFFER_SIZE = 1024;
        try {
            byte[] bytes = new byte[BUFFER_SIZE];
            for (; ; ) {
                int count = is.read(bytes, 0, BUFFER_SIZE);
                if (count == -1) {
                    break;
                }
                os.write(bytes, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭输入输出流
     *
     * @param is
     */
    public static void closeSafely(Closeable is) {
        try {
            if (null != is) {
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mkDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            deleteFile(file);
        }
    }

    public static void deleteFile(File oldPath) {
        if (oldPath.isDirectory()) {
            File[] files = oldPath.listFiles();
            for (File file : files) {
                deleteFile(file);
                file.delete();
            }
        }
    }

}
