package me.mattsay.jzip;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Class that compress and decompress files
 *
 * @author Mattia Sicoli
 * @version 1.1
 */
public class Jzip {

    public static final int BUFFER = 4096;

    /**
     * Returns all decompressed files from a compressed file
     * @param outdir the path where the program decompresses the files
     * @param path the path of the file to convert
     * @throws IOException throws if the file does not exist
     */
    public static File[] decompressFiles(String outdir , String path) throws IOException {
        File[] file;
        int i = 0;
        FileInputStream fis = new FileInputStream(path);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
        ZipEntry entry;
        file = new File[zis.available()];
        while((entry = zis.getNextEntry()) != null){
            if(entry.isDirectory()){
                new File(outdir, entry.getName()).mkdir();
                continue;
            }
            file[i] = decompressFile(entry, zis, outdir);
            i++;
        }
        zis.closeEntry();
        zis.close();
        return file;
    }

    /**
     * Returns decompressed file
     * @param entry all file's informations
     * @param zis gets data from the compressed file
     * @param outdir path where the program decompress the file
     * @throws IOException throws if the file does not exist
     */
    private static File decompressFile(ZipEntry entry,ZipInputStream zis, String outdir) throws IOException{
        File file = new File(outdir, entry.getName());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file),BUFFER);
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
        return file;
    }

    /**
     * Returns all files compressed into a single file
     * @param files All files to be compressed
     * @param destination the path where the program compress the file
     * @throws IOException throws if the file does not exist
     */
    public static File compressFiles(File[] files, String destination) throws IOException{
        File file = new File(destination);
        FileOutputStream fos = new FileOutputStream(file);
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
        return file;
    }

    /**
     * Compress file
     * @param f File to compress
     * @param zos Writes all data
     * @throws IOException throws if the file does not exist
     */
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
