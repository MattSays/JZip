# JZip - Quick Start Guide

A Simple library to comopress or decompress files.
Compressing and Decompressing files is very simple with this library! Only one line to decompress your files!
Debug informations included!

<a href="https://koding.com/"> <img src="https://koding-cdn.s3.amazonaws.com/badges/made-with-koding/v1/koding_badge_RectangleColor.png" srcset="https://koding-cdn.s3.amazonaws.com/badges/made-with-koding/v1/koding_badge_RectangleColor.png 1x, https://koding-cdn.s3.amazonaws.com/badges/made-with-koding/v1/koding_badge_RectangleColor@2x.png 2x" alt="Made with Koding" /> </a>

## Decompressing Example

To decompress files you only need the output path and the zip file path

```java
  package test;
  
  import me.mattsay.jzip.Jzip;
  
  import java.io.IOException;
  
  public class DecompressingExample {
  
    public static void main(String... args) {
  
      String path = "/Example.zip"; // Setting zip file location
      String outpath = "/"; //Setting Output path location
      
      Jzip.decompressFiles(outpath,path); //Decompressing files
    }
  }
```
## Compressing Example

To compress files you only need the output path and an array that contains all to be compressed files!

```java
  package test;
  
  import me.mattsay.jzip.Jzip;
  
  import java.io.IOException;
  
  public class CompressingExample {
  
    public static void main(String... args) {
  
      File[] files = {new File("/file1.txt"), new File("/file2.txt")};// Setting example array
      String outpath = "/"; //Setting Output path location
      
      Jzip.compressFiles(files,outpath); //Compressing files
    }
  }
```
