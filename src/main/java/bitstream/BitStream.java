package bitstream;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HexFormat;

/**
 * Class represents little endian. last byte may be flipped i.e for last byte containing 011 -> results in int 6
 * Because of this we may need to manually change the last byte when writing.
 */
public class BitStream {
    public final static int BYTE_START_INDEX = 7;
    private int currentSetBitIdx;
    private int currentSetByteIdx;
    private byte[] byteArray;
    private int ones = 0;
    private boolean sos = false;

    public BitStream() {
        this.byteArray = new byte[256];
        this.currentSetBitIdx = BYTE_START_INDEX;
        this.currentSetByteIdx = 0;
    }

    public boolean isSos() {
        return sos;
    }

    public void setSos(boolean sos) {
        this.sos = sos;
    }

    public void setBit(boolean bit) {
        if (this.currentSetBitIdx < 0) {
            this.currentSetByteIdx++;
            this.currentSetBitIdx = BYTE_START_INDEX;
            if (currentSetByteIdx >= byteArray.length - 1) {
                this.byteArray = Arrays.copyOf(this.byteArray, this.byteArray.length * 2);
            }
            if (isSos()) {
                if (this.currentSetBitIdx == BYTE_START_INDEX) {
                    if (this.byteArray[this.currentSetByteIdx - 1] == (byte) 0b11111111) {
                        this.currentSetByteIdx++;
                    }
                }
            }
        }
        this.byteArray[this.currentSetByteIdx] |= (bit ? 1 : 0) << this.currentSetBitIdx;

        this.currentSetBitIdx--;
    }

    public void setByte(byte value) {
        for (int i = Byte.SIZE - 1; i >= 0; i--) {
            this.setBit(((value >> i) & 1) == 1);
        }
    }

    public void setInt(int value, int bits) {
        for (int i = bits - 1; i >= 0; i--) {
            this.setBit(((value >> i) & 1) == 1);
        }
    }

    public void writeBitStreamToFile(String fName) {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fName))) {
            byte[] bytes = this.byteArray;
            bufferedOutputStream.write(bytes, 0, currentSetByteIdx + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeBits(boolean... bits) {
        for (boolean bit : bits) {
            this.setBit(bit);
        }
    }

    public byte[] getBytes() {
        return this.byteArray;
    }

    public void setBytes(byte[] bytes) {
        for (byte aByte : bytes) {
            this.setByte(aByte);
        }
    }

    public void writeHexString(String string) {
        if (string.length() % 2 != 0) {
            throw new RuntimeException("String should contain only whole bytes");
        } else {
            this.setBytes(HexFormat.of().parseHex(string));
        }
    }

    public void fillByteWithZeroes() {
        if (currentSetBitIdx >= 0) {
            this.currentSetByteIdx++;
            this.currentSetBitIdx = BYTE_START_INDEX;
        }
    }

    public void fillByteWithOnes() {
        while (currentSetBitIdx >= 0) {
            setBit(true);
        }
    }


    public int getCurrentSetBitIdx() {
        return currentSetBitIdx;
    }

    public int getCurrentSetByteIdx() {
        return currentSetByteIdx;
    }

    public String getHexString() {
        String result = "";
        for (byte b : byteArray) {
            result += String.format("%02X", b);
        }
        return result;
    }

}
