package com.edutech.app.others;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by vedant on 5/9/17.
 */

public class EncryptDecrypt {


   public static File getDecryptVideo(File file){
       if(!file.isDirectory()){
           String remoteLocation = "Location";
           String decryptedVideoName = "system.dll";
           byte[] b = readBytesFromFile(file.getAbsolutePath());
           File decryptFile = new File(new File(remoteLocation),decryptedVideoName);
           for(int i=0;i<10;i++){
               b[i] = (byte)(b[i]-10);
           }
           writeBytesToFileClassic(b,decryptFile.getAbsolutePath());
           return decryptFile;
       }else{
           return null;
       }
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



}
