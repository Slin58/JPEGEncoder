package Bitstream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.BitSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BitStream {
    //"Die zu Grunde liegende Repräsentation muss weiterhin auf Byte-Folgen basieren" --> wtf does this mean?? werden bytes reingeschrieben oder muss der datentyp im bitstream bytes sein?
    //todo: wuerde es erst mal mit BitSet probieren. Sieht gut optimiert aus
    private String storageDir = "bitstream";
    private int maxSize;
    private int lastSetBitIdx;
    private BitSet bitSet; //todo: koennen nach Test mal versuchen auf 20 000 000 oder mehr zu gehen

    public BitStream(int maxSize) {
        this.maxSize = maxSize;
        this.bitSet = new BitSet(maxSize);
        this.lastSetBitIdx = -1;
    }

    public void setBit(boolean bit) {
        if(lastSetBitIdx >= this.maxSize-1) {
            writeBitStreamToFile();
            this.bitSet = new BitSet(maxSize);
            this.lastSetBitIdx = -1;
        }
        this.lastSetBitIdx++;
        this.bitSet.set(lastSetBitIdx, bit);
    }

    public int getNewestBit() {
        if(this.lastSetBitIdx == -1) {
            if(checkForStoredByteStream() == 0)
            throw new RuntimeException("BitStream empty");
            else {
            //todo: read Bytes from latest storaed BitStream File and convert to BitStream and return newest Bit and set lastSetBitIdx appropiately
            }
        }
        this.lastSetBitIdx--;
       // System.out.println(this.lastSetBitIdx + "; " + this.bitSet.get(lastSetBitIdx+1));
        return (this.bitSet.get(lastSetBitIdx+1)) ? 1 : 0;

        //alternativ:
        /*boolean fuckYou = this.bitSet.get(lastSetBitIdx);
        lastSetBitIdx--;
        return fuckYou; */
    }

    //todo: check performance
    public void writeHumanReadableBitStreamToFile()  {
        String fName = "bitstreamOutput.txt" + numberOfFilesInDir(Path.of(this.storageDir));
        try (Writer wr = new FileWriter(this.storageDir + fName)) {
            int[] arr = new int[this.maxSize];
            for (int i = 0; i < this.maxSize; i++) {
                arr[i] = getNewestBit();
            }
            wr.write(Arrays.toString(arr));
            System.out.println("wrote stream to file");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeBitStreamToFile() {
        try {
            String fName = "bitstreamOutput.txt" + numberOfFilesInDir(Path.of(this.storageDir));
            FileOutputStream fileOutputStream = new FileOutputStream(this.storageDir + "\\fName");
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

            byte[] bytes = this.bitSet.toByteArray();
            bufferedOutputStream.write(bytes);

            bufferedOutputStream.close();
            fileOutputStream.close();

            System.out.println("Byte array has been written to " + this.storageDir + "\\bitstreamOutput.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        private int checkForStoredByteStream() {
        return numberOfFilesInDir(Path.of(this.storageDir));
    }

    public int numberOfFilesInDir(Path path) {
        if (Files.isDirectory(path)) {
            try (Stream<Path> entries = Files.list(path)) {
                return entries.collect(Collectors.toList()).size();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return this.bitSet.toString();
    }

}
