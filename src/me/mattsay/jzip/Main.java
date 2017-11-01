package me.mattsay.jzip;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException{
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
                String filename = args[1];
                File[] files = new File[args.length-2];
                for(int i = 2; i < args.length; i++){
                    files[i-2] = new File(args[i]);
                    if(!files[i-2].exists()){
                        System.err.println(files[i-2] + " does not exist. Stopped compressing process");
                        return;
                    }
                }
                System.out.println("Compressing....");
                Jzip.compressFiles(files, filename,false);
                System.out.println(ChatColor.ANSI_GREEN + "Compressed all file successfully!");
                break;
            case "decompress":
                if(args.length == 1 || args[1] == null){
                    System.err.println("Arguments missing!");
                    break;
                }
                String file = args[1];
                if(!new File(file).exists()){
                    System.err.println(file + " does not exist. Stopped decompressing process");
                    break;
                }
                System.out.println("Decompressing....");
                Jzip.decompressFile(file,true);
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
