package me.mattsay.jzip;

import java.io.File;
import java.io.IOException;

/**
 * Main Class
 *
 * @author Mattia Sicoli
 * @version 1.0
 */

public class Main {



    public static void main(String[] args) throws Exception{
        String localpath = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        if(args.length == 0){
            System.err.println("Unknown command. Please enter help for more information");
            return;
        }
        switch (args[0]){
            case "compress":
                if(args.length == 1 ||args[1] == null || args[2] == null){
                    System.err.println("Arguments missing!");
                    break;
                }
                String destination = args[1];
                File[] files = new File[args.length-2];
                for(int i = 2; i < args.length; i++){
                    files[i-2] = new File(args[i]);
                    if(!files[i-2].exists()){
                        System.err.println(files[i-2] + " does not exist. Stopped compressing process");
                        return;
                    }
                }
                System.out.println("Compressing....");
                Jzip.compressFiles(files, destination);
                System.out.println(ChatColor.ANSI_GREEN + "Compressed all file successfully!");
                break;
            case "decompress":
                if(args.length == 1 || args[1] == null){
                    System.err.println("Arguments missing!");
                    break;
                }
                String file = args[2];
                if(!new File(file).exists()){
                    System.err.println(file + " does not exist. Stopped decompressing process");
                    break;
                }
                String outpath = args[1];
                System.out.println("Decompressing....");
                Jzip.decompressFiles(outpath ,file);
                System.out.println(ChatColor.ANSI_GREEN + "Decompressed all files  successfully!");
                break;
            case "help":
                System.out.println("Command List:\n- Compress [archive file name] [files]\n- Decompress [archive file name]");
                break;
            default:
                System.err.println("Unknown command. Please enter help for more information");
                break;
        }
    }
}
