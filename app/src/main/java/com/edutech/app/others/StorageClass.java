package com.edutech.app.others;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by vedant on 17/9/17.
 */

public class StorageClass {
    public static String sdCardPath;

    public static String getSdCardPath() {
        return sdCardPath;
    }

    public static List<String> listFile = new ArrayList<>();

    public static void setSdCardPath(String sdCardPath1) {
        sdCardPath = sdCardPath1;
    }

    public static List<String> giveAllFolderPresent(File file, String folderName) {

        for (File f : file.listFiles()) {
            if (f.getName().contains(folderName) && f.isDirectory()) {
                File file1 = new File(f.getAbsolutePath());
                listFile.add(file1.getAbsolutePath());

            } else {
                if (f.isDirectory()) {
                    giveAllFolderPresent(f, folderName);
                }
            }

        }
        return listFile;

    }

    public static List<String> giveInsideSDOrInternal(File file, String folderName) {
        List<String> list = new ArrayList<>();
        list.clear();
        for (File f : file.listFiles()) {
            if ((f.isDirectory()) && (f.getName().contains(folderName))) {
                File file1 = new File(f.getAbsolutePath());
                listFile.add(file1.getAbsolutePath());
            }
        }

        return list;
    }

    public static boolean sdPresent() {
        boolean bool = false;
        String strCardPath = System.getenv("SECONDARY_STORAGE");
        if ((strCardPath == null) || (strCardPath.length() == 0)) {
            strCardPath = System.getenv("EXTERNAL_SDCARD_STORAGE");
        }

        if (strCardPath != null) {
            if (strCardPath.contains(":")) {
                strCardPath = strCardPath.substring(0, strCardPath.indexOf(":"));
            }
            File externalFilePath = new File(strCardPath);
            if (externalFilePath.exists() && externalFilePath.canWrite()) {
                setSdCardPath(externalFilePath.getAbsolutePath());
                bool = true;
            }
        }

        return bool;
    }

    public static String sdCardLocation() {
        if (getSd2Location() != null) {
            Log.d("some", "98");

            return getSd2Location();
        }

        if (sdPresent() && getSdCardPath() != null) {
            Log.d("some", "94");
            return getSdCardPath();
        }
        if (getSdSamsung() != null) {
            Log.d("some", "103");

            return getSdSamsung();
        }
        if (getAsus() != null) {
            Log.d("some", "108");

            return getAsus();
        }
        if (getOthersSd() != null) {
            Log.d("some", "113");

            return getOthersSd();
        } else {
            return getOthersSd();
        }


        /*if (sdPresent()) {
            return getSdCardPath();
        } else {
            if (getSd2Location() == null) {
                if (getSdSamsung() != null) {
                    return getSdSamsung();
                } else {
                    if (getAsus() != null) {
                        return getAsus();
                    } else {
                        getOthersSd();
                    }
                }
            } else {
                return getSd2Location();

            }
        }*/
    }


    public static String readFile(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    public static String decryptVideo(File file) {
        File location = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/media");
        File decryptFile = new File(location, "system_os.dll");
        if (!decryptFile.exists()) {
            try {
                decryptFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] b = readBytesFromFile(file.getAbsolutePath());
        for (int i = 0; i < 10; i++) {
            b[i] = (byte) (b[i] - 10);
        }
        writeBytesToFileClassic(b, decryptFile.getAbsolutePath());

        return decryptFile.getAbsolutePath();
    }


    private static byte[] readBytesFromFile(String filePath) {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {

            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return bytesArray;

    }

    //write bytes to a file
    private static void writeBytesToFileClassic(byte[] bFile, String fileDest) {

        FileOutputStream fileOuputStream = null;

        try {
            fileOuputStream = new FileOutputStream(fileDest);
            fileOuputStream.write(bFile);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOuputStream != null) {
                try {
                    fileOuputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public static String getImei(Context context) {
        final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static String getDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }


    private static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getSd2Location() {
        String removableStoragePath = null;
        File fileList[] = new File("/storage/").listFiles();
        for (File file : fileList) {
            if (file.getAbsolutePath().contains("-") && file.canRead() && file.isDirectory()) {
                removableStoragePath = file.getAbsolutePath();
                return file.getAbsolutePath();
            }
        }


        return removableStoragePath;
        //If there is an SD Card, removableStoragePath will have it's path. If there isn't it will be an em
    }

    public static String getSdSamsung() {
        String removableStoragePath = null;
        File fileList[] = new File("/mnt/").listFiles();

        for (File file : fileList) {
            if (!file.getAbsolutePath().equalsIgnoreCase(Environment.getExternalStorageDirectory().getAbsolutePath()) && file.isDirectory() && file.canRead())
                removableStoragePath = file.getAbsolutePath();
        }

        if (removableStoragePath == null) {
            File fileList1[] = new File("/mnt/sdcard").listFiles();

            for (File file : fileList1) {
                if (!file.getAbsolutePath().equalsIgnoreCase(Environment.getExternalStorageDirectory().getAbsolutePath()) && file.isDirectory() && file.canRead()) {
                    removableStoragePath = file.getAbsolutePath();
                    return removableStoragePath;
                }

            }

        }

        return removableStoragePath;


    }

    public static String getAsus() {
        String removableStoragePath = null;
        File fileList[] = new File("/removable/").listFiles();

        for (File file : fileList) {
            if (!file.getAbsolutePath().equalsIgnoreCase(Environment.getExternalStorageDirectory().getAbsolutePath()) && file.isDirectory() && file.canRead())
                removableStoragePath = file.getAbsolutePath();
        }


        return removableStoragePath;

    }

    public static String getOthersSd() {
        String removableStoragePath = null;
        File fileList[] = new File("/data/").listFiles();
        File fileList1[] = new File("/storage/removable").listFiles();

        for (File file : fileList) {
            if (!file.getAbsolutePath().equalsIgnoreCase(Environment.getExternalStorageDirectory().getAbsolutePath()) && file.isDirectory() && file.canRead())
                removableStoragePath = file.getAbsolutePath();
        }
        if (removableStoragePath == null) {

            for (File file : fileList1) {
                if (!file.getAbsolutePath().equalsIgnoreCase(Environment.getExternalStorageDirectory().getAbsolutePath()) && file.isDirectory() && file.canRead())
                    removableStoragePath = file.getAbsolutePath();
            }


        }

        return removableStoragePath;

    }

}
