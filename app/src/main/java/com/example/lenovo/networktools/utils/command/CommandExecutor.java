package com.example.lenovo.networktools.utils.command;


import java.io.DataOutputStream;
import java.io.IOException;

import android.text.TextUtils;

import com.example.lenovo.networktools.utils.Logger;


/**
 * Created by 10129302 on 15-2-6.
 */
public class CommandExecutor
{

    public static Process execCmd(String command)
    {
        return execCmd(new String[]{command}, true);
    }

    public static Process root()
    {
        return execCmd(null, false);
    }

    public static Process execCmd(String[] commands)
    {
        return execCmd(commands, false);
    }

    public static Process execCmd(String[] commands, boolean waitFor)
    {
        Process suProcess = null;
        try
        {
            suProcess = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());
            if (commands != null)
            {
                for (String cmd : commands)
                {
                    if (!TextUtils.isEmpty(cmd))
                    {
                        Logger.i("cmd命令 = " + cmd);
                        os.writeBytes(cmd + "\n");
                    }
                }
            }
            os.flush();
            os.writeBytes("exit\n");
            os.flush();
            if (!waitFor)
            {
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if (waitFor)
        {
            try
            {
                if (255 != suProcess.waitFor())
                {
                }
                else
                {
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        return suProcess;
    }

}
