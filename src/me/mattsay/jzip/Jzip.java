package me.mattsay.jzip;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Jzip {

    public static final int BUFFER = 1024;

    public static void decompressFile(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
        ZipEntry entry;
        while((entry = zis.getNextEntry()) != null){
            System.out.println(
                    "File: " + entry.getName() + "\n" + "Size: " + entry.getSize()
            );

            FileOutputStream fos = new FileOutputStream(entry.getName());
            BufferedOutputStream bos = new BufferedOutputStream(fos,BUFFER);
            int count;
            byte[] data = new byte[BUFFER];
            while ((count = zis.read(data)) > 0){
                bos.write(data, 0 , count);
            }
            bos.flush();
            bos.close();
            entry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();

    }

}
