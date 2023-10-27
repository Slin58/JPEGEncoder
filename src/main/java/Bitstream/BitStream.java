package Bitstream;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.BitSet;

public class BitStream {
    //"Die zu Grunde liegende Repräsentation muss weiterhin auf Byte-Folgen basieren" --> wtf does this mean?? werden bytes reingeschrieben oder muss der datentyp im bitstream bytes sein?
    //todo: wuerde es erst mal mit BitSet probieren. Sieht gut optimiert aus
    private int maxSize;
    private int lastSetBitIdx;
    private BitSet bitSet; //todo: koennen nach Test mal versuchen auf 20 000 000 oder mehr zu gehen

    public BitStream(int lastSetBitIdx , int maxSize) {
        this.maxSize = maxSize;
        this.bitSet = new BitSet(maxSize);
        this.lastSetBitIdx = 0;
    }

    public void setBit(boolean bit) {
        if(lastSetBitIdx >= this.maxSize-1) {
            writeBitStreamToFile();
        }
        this.bitSet.set(lastSetBitIdx);
        lastSetBitIdx++;
    }
    public boolean getNewestBit() {
        this.lastSetBitIdx--;
        return this.bitSet.get(lastSetBitIdx+1);

        //alternativ:
        /*boolean fuckYou = this.bitSet.get(lastSetBitIdx);
        lastSetBitIdx--;
        return fuckYou; */
    }

    //todo: check if performance is dogshit; sponsored by ChatGPT
    public void writeBitStreamToFile() {
        try (FileOutputStream fileOutputStream = new FileOutputStream("bitstream\\bitstreamOutput.txt");
             DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {

            byte[] byteArray = bitSet.toByteArray();
            dataOutputStream.write(byteArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
