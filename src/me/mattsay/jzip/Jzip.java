package me.mattsay.jzip;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Jzip {

    public static final int BUFFER = 1024;

    public static void decompressFile(String path, boolean debug) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
        ZipEntry entry;
        while((entry = zis.getNextEntry()) != null){
            FileOutputStream fos = new FileOutputStream(entry.getName());
            BufferedOutputStream bos = new BufferedOutputStream(fos,BUFFER);
            int count;
            byte[] data = new byte[BUFFER];
            while ((count = zis.read(data)) > -1) bos.write(data, 0 , count);
            if(debug)System.out.println(
                   ChatColor.ANSI_CYAN +
                           "Decompressing: \n  File: " + entry.getName() +
                           "\n  Size: " + entry.getSize()/1024 + " KB"
            );
            bos.flush();
            bos.close();
        }
        zis.closeEntry();
        zis.close();

    }


    public static void compressFiles(File[] files, String fileName, boolean debug) throws IOException{
        FileOutputStream fos = new FileOutputStream(fileName);
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));
        for(File f : files){
            if(debug)System.out.println(
                    ChatColor.ANSI_CYAN +
                            "Compressing: \n  File: " + f.getName() +
                                 "\n  Size: " + f.length()/1024 + " KB" +
                                 "\n  Estimated Compress Size: " + (f.length()/1024)*0.5 + " KB"
            );
            FileInputStream fis = new FileInputStream(f.getPath());
            ZipEntry entry = new ZipEntry(f.getName());
            zos.putNextEntry(entry);
            int count;
            byte[] data = new byte[BUFFER];
            while((count = fis.read(data)) > -1){
                zos.write(data,0,count);
            }
            fis.close();
        }
        zos.close();
        fos.close();

    }

}
