package com.android.lmc.tool;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RootHelper {
    private static RootHelper mInstance;
    private Process process;
    private final String Tag = "RootHelper";

    public static RootHelper getInstance(){
        if(mInstance == null){
            synchronized (RootHelper.class){
                mInstance = new RootHelper();
            }
        }
        return mInstance;
    }

    /**
     * 结束进程,执行操作调用即可
     */
    public String doCmd(String cmd) {
        initProcess();
        if(process == null) return "not rooted";
        DataOutputStream dos = new DataOutputStream(process.getOutputStream());
        DataInputStream dis = new DataInputStream(process.getInputStream());
        String result = "";
        LogHelper.d(Tag, cmd, false);
        try {
            dos.writeBytes(cmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            String line = null;
            while ((line = dis.readLine()) != null) {
                result += line;
            }
            process.waitFor();
            LogHelper.d(Tag, result, false);
        }catch (Exception e){
            e.printStackTrace();
            LogHelper.d(Tag, e.getMessage(), false);
        }
        close();
        return result;
    }

    /**
     * 初始化进程
     */
    private void initProcess() {
        if (process == null)
            try {
                process = Runtime.getRuntime().exec("su");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }


    /**
     * 关闭输出流
     */
    private void close() {
        if (process != null)
            try {
                process.getOutputStream().close();
                process.getInputStream().close();
                process = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static boolean isRoot(){
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("exit\n");
            os.flush();
            int exitValue = process.waitFor();
            if (exitValue == 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if(process != null) {
                    process.destroy();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
