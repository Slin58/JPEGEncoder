package Bitstream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class represents little endian. last byte may be flipped i.e for last byte containing 011 -> results in int 6
 * Because of this we may need to manually change the last byte when writing.
 */
public class BitStream {
    //"Die zu Grunde liegende Repräsentation muss weiterhin auf Byte-Folgen basieren" --> wtf does this mean??
    // werden bytes reingeschrieben oder muss der datentyp im bitstream bytes sein?
    //todo: wuerde es erst mal mit BitSet probieren. Sieht gut optimiert aus
    private String storageDir = "bitstream";
    private int lastSetBitIdx;
    private int currentByteIdx;
    private byte[] byteArray; //todo: koennen nach Test mal versuchen auf 20 000 000 oder mehr zu gehen
    private byte tempByte;

    public BitStream() {
        this.byteArray = new byte[256];
        this.lastSetBitIdx = 7;
        this.currentByteIdx = 0;
        this.tempByte = 0;
    }

    public void setBit(boolean bit) {
        if (lastSetBitIdx < 0) {
            this.byteArray[this.currentByteIdx++] = this.tempByte;
            this.tempByte = (byte) 0;
            this.lastSetBitIdx = 7;
            if (currentByteIdx == byteArray.length - 1) {
                this.byteArray = Arrays.copyOf(this.byteArray, this.byteArray.length * 2);
            }
        }
        this.tempByte |= bit ? 1 : 0 << this.lastSetBitIdx;
        this.lastSetBitIdx--;
    }

    public void setByte(byte value) {
        for (int i =  Byte.SIZE; i > 0; i--) {
            this.setBit(((value >> i) & 1) == 1);
        }
    }
    public void setBytes(byte[] bytes) {
        for (byte aByte : bytes) {
            this.setByte(aByte);
        }
    }

    public void writeBitStreamToFile() {
        try {
            String fName = "bitstreamOutput.jpeg";
            FileOutputStream fileOutputStream = new FileOutputStream(fName);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            byte[] bytes = this.byteArray;
            bufferedOutputStream.write(bytes);

            bufferedOutputStream.close();
            fileOutputStream.close();

            System.out.println("Byte array has been written to " + this.storageDir + "\\bitstreamOutput.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getBytes() {
        return this.byteArray;
    }

}
