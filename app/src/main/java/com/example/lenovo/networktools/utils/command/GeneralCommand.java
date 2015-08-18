package com.example.lenovo.networktools.utils.command;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.lenovo.networktools.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by 10129302 on 15-2-6.
 */
public class GeneralCommand {

    private static String TCP_DUMP_NAME = "tcpdump";

    /**
     * 简单的请求root权限
     */
    public static void root() {
        CommandExecutor.root();
    }
    /**
     * 对于root的手机进行adb connect
     *
     * @return
     */
    public static void AdbConnect() {
        String[] commands = new String[3];
        commands[0] = "stop adbd";
        commands[1] = "setprop service.adb.tcp.port 5555";
        commands[2] = "start adbd";

        CommandExecutor.execCmd(commands);
    }

    public static void chmod(String dir) {
        String command =  "chmod 777 " + dir;
        CommandExecutor.execCmd(command);
    }
    /**
     * 手机抓包程序
     * @param context
     */
    public static void TcpDump(Context context) {
        InputStream is = null;
        OutputStream os = null;
        AssetManager am = context.getAssets();
        try {
            is = am.open(TCP_DUMP_NAME);
            File file = new File("data/local/", TCP_DUMP_NAME);
            os = new FileOutputStream(file);
            FileUtils.copyStream(is, os);

            FileUtils.closeSafely(is);
            FileUtils.closeSafely(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
