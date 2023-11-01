package Bitstream;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.BitSet;

public class BitStream {
    //"Die zu Grunde liegende Repräsentation muss weiterhin auf Byte-Folgen basieren" --> wtf does this mean?? werden bytes reingeschrieben oder muss der datentyp im bitstream bytes sein?
    //todo: wuerde es erst mal mit BitSet probieren. Sieht gut optimiert aus
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
        if (bit) {
            this.bitSet.set(lastSetBitIdx, true);
        } else {
            this.bitSet.set(lastSetBitIdx, false);
        }
    }

    public int getNewestBit() {
        if(this.lastSetBitIdx == -1) {
            throw new RuntimeException("BitStream empty");
        }
        this.lastSetBitIdx--;
       // System.out.println(this.lastSetBitIdx + "; " + this.bitSet.get(lastSetBitIdx+1));
        return (this.bitSet.get(lastSetBitIdx+1)) ? 1 : 0;

        //alternativ:
        /*boolean fuckYou = this.bitSet.get(lastSetBitIdx);
        lastSetBitIdx--;
        return fuckYou; */
    }

    //todo: check if performance is dogshit; sponsored by ChatGPT
    public void writeBitStreamToFile()  {
        try (Writer wr = new FileWriter("bitstream\\bitstreamOutput.txt");) {
            //byte[] byteArray = bitSet.toByteArray();
            int[] arr = new int[this.maxSize];
            for (int i = 0; i < this.maxSize; i++) {
                arr[i] = getNewestBit();
            }
            wr.write(Arrays.toString(arr));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
