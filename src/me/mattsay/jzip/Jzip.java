package me.mattsay.jzip;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Jzip {

    public static final int BUFFER = 4096;

    public static void decompressFiles(String outdir , String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
        ZipEntry entry;
        while((entry = zis.getNextEntry()) != null){
            if(entry.isDirectory()){
                new File(outdir, entry.getName()).mkdir();
                continue;
            }
            decompressFile(entry, zis, outdir);

        }
        zis.closeEntry();
        zis.close();

    }

    private static void decompressFile(ZipEntry entry,ZipInputStream zis, String outdir) throws IOException{
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(outdir, entry.getName())),BUFFER);
        byte[] data = new byte[BUFFER];
        int count = -1;
        double progress = 0,changedpercent = 0,percent = 0;

        while ((count = zis.read(data)) != -1){
            bos.write(data, 0 , count);
            progress+=count;
            percent = ((100 * progress) / entry.getSize());
            percent = Math.round(percent);
            if(percent != changedpercent)System.out.println(ChatColor.ANSI_CYAN + "Decompressing Progress: " + (int)percent + "%");
            changedpercent = percent;
        }
        System.out.println(Character.MIN_VALUE);

        System.out.println(ChatColor.ANSI_CYAN + "Decompressed: \n  File: " + entry.getName() + "\n  Size: " + (entry.getSize()<1024 ? entry.getSize() + " B" : entry.getSize()/1024 + " KB"));
        bos.close();
    }


    public static void compressFiles(File[] files, String destination) throws IOException{
        FileOutputStream fos = new FileOutputStream(destination);
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));
        for(File f : files){
            if(f.isDirectory()){
                f.mkdir();
                continue;
            }
            compressFile(f,zos);
        }
        zos.close();
        fos.close();

    }
    private static void compressFile(File f, ZipOutputStream zos) throws IOException{

        FileInputStream fis = new FileInputStream(f.getPath());
        ZipEntry entry = new ZipEntry(f.getName());
        zos.putNextEntry(entry);
        int count;
        byte[] data = new byte[BUFFER];
        double progress = 0,changedpercent = 0,percent = 0;

        while ((count = fis.read(data)) != -1){
            zos.write(data, 0 , count);
            progress+=count;
            percent = ((100 * progress) / f.length());
            percent = Math.round(percent);
            if(percent != changedpercent)System.out.println(ChatColor.ANSI_CYAN + "Compressing Progress: " + (int)percent + "%");
            changedpercent = percent;
        }
        String size = (f.length()<1024 ? f.length() + " B" : f.length()/1024 + " KB");
        String estimated_size = (f.length()<1024 ? (double)(f.length()*0.5)+ " B"   : (double)((f.length()/1024)*0.5) + " KB");
        System.out.println("\n" + ChatColor.ANSI_CYAN + "Compressed: \n  File: " + f.getName() + "\n  Size: " + size + "\n  Estimated Compress Size: " + estimated_size);
        fis.close();
    }

}
