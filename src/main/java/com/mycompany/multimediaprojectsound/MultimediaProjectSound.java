
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.multimediaprojectsound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author User
 */
public class MultimediaProjectSound {
    //differentiates between wave and flac files
    public static String whichType(File file) throws FileNotFoundException, IOException{
        byte[] type=new byte[12];
        FileInputStream fis=new FileInputStream(file);
        fis.read(type);
        if(type[0]==0x52 && type[1]==0x49 && type[2]==0x46 && type[3]==0x46 && type[8]==0x57 && type[9]==0x41 && type[10]==0x56 && type[11]==0x45){
            return "This is Wave file";
        }
        else if(type[0]==0x66 && type[1]==0x4c && type[2]==0x61 && type[3]==0x43){
            return "This is Flac file";
        }
        else{
        return "File not supported";}
    }
    /*int chuncksize = ((header[7] & 0xff) << 24) |
                     ((header[6] & 0xff) << 16) |
                     ((header[5] & 0xff) << 8) |
                     (header[4] & 0xff);
    */
     private static int byteToInt(byte[] b, int offset, int length) {
        int value = 0;
        for (int i = 0; i < length; i++) {
            value |= (b[offset + i] & 0xFF) << (8 * i);
        }
        return value;
    }
     public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    
    public static void displayHeaderWave(File file) throws FileNotFoundException, IOException{
        byte[] header=new byte[44];
        FileInputStream fis=new FileInputStream(file);
        fis.read(header);
        String chunckID=new String(header,0,4);
        System.out.println("ChunkID: "+chunckID);
        System.out.println("Chuncksize: "+byteToInt(header,4,4));
        String format=new String(header,8,4);
        System.out.println("Format: "+format);
        String subchunck1ID=new String(header,12,4);
        System.out.println("Subchunck1ID: "+subchunck1ID);
        System.out.println("Subchunck1Size: "+byteToInt(header,16,4));
        System.out.println("AudioFormat: "+byteToInt(header,20,2));
        System.out.println("NumChannels: "+byteToInt(header,22,2));
        System.out.println("SampleRate: "+byteToInt(header,24,4));
        System.out.println("ByteRate: "+byteToInt(header,28,4));
        System.out.println("BlockAlign: "+byteToInt(header,32,2));
        System.out.println("BitsPerSample: "+byteToInt(header,34,2));
        String Subchunk2ID=new String(header,36,4);
        System.out.println("Subchunk2ID: "+Subchunk2ID);
        System.out.println("Subchunk2Size: "+byteToInt(header,40,4));

    }
    public static void displayHeaderFlac(File file) throws FileNotFoundException, IOException{
        FileInputStream fis=new FileInputStream(file);
        int header = fis.read();
        while (header != -1) {
                boolean isLast = (header & 0x80) != 0;
                int blockType = header & 0x7F;
                int length = fis.read() << 16 | fis.read() << 8 | fis.read();

                if (blockType == 0) { 

                    byte[] data = new byte[34];
                    fis.read(data);

                    int minBlockSize = (data[0] & 0xFF) << 8 | (data[1] & 0xFF);
                    int maxBlockSize = (data[2] & 0xFF) << 8 | (data[3] & 0xFF);
                    int minFrameSize = (data[4] & 0xFF) << 16 | (data[5] & 0xFF) << 8 | (data[6] & 0xFF);
                    int maxFrameSize = (data[7] & 0xFF) << 16 | (data[8] & 0xFF) << 8 | (data[9] & 0xFF);
                    int sampleRate = (data[10] & 0xFF) << 12 | (data[11] & 0xFF) << 4 | (data[12] & 0xF0) >> 4;
                    int channels = ((data[12] & 0x0F) >> 1) + 1;
                    int bitsPerSample = ((data[12] & 0x01) << 4) | ((data[13] & 0xF0) >> 4) + 1;
                    long totalSamples = ((long) (data[13] & 0x0F) << 32) | ((long) (data[14] & 0xFF) << 24) | ((long) (data[15] & 0xFF) << 16) | ((long) (data[16] & 0xFF) << 8) | (data[17] & 0xFF);
                    byte[] md5 = new byte[16];
                    System.arraycopy(data, 18, md5, 0, 16);

                    System.out.println("STREAMINFO block found");
                    System.out.println("Minimum block size: " + minBlockSize + " samples");
                    System.out.println("Maximum block size: " + maxBlockSize + " samples");
                    System.out.println("Minimum frame size: " + minFrameSize + " bytes");
                    System.out.println("Maximum frame size: " + maxFrameSize + " bytes");
                    System.out.println("Sample rate: " + sampleRate + " Hz");
                    System.out.println("Number of channels: " + channels);
                    System.out.println("Bits per sample: " + bitsPerSample);
                    System.out.println("Total samples: " + totalSamples);
                    System.out.println("MD5 signature: " + bytesToHex(md5));

                    break;
                } else {

                    fis.skip(length);
                    header = fis.read();
                }

                if (isLast) {
                    System.out.println("No STREAMINFO block found");
                    break;
                }
            }
    }

    public static void main(String[] args) throws IOException {
        File file=new File("D:\\downloads\\Symphony No.6 (1st movement).flac");
        System.out.println(whichType(file));
        if(whichType(file) == "This is Wave file"){
            System.out.println("The header of this file");
            displayHeaderWave(file);
        }
        else if(whichType(file)=="This is Flac file"){
            System.out.println("The header of this file");
            displayHeaderFlac( file);
        }
    }
}
