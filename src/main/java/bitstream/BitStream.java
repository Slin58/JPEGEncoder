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

    public BitStream() {
        this.byteArray = new byte[256];
        this.currentSetBitIdx = BYTE_START_INDEX;
        this.currentSetByteIdx = 0;
    }

    public void setBit(boolean bit) {
        if (this.currentSetBitIdx < 0) {
            this.currentSetByteIdx++;
            this.currentSetBitIdx = BYTE_START_INDEX;
            if (currentSetByteIdx == byteArray.length - 1) {
                this.byteArray = Arrays.copyOf(this.byteArray, this.byteArray.length * 2);
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

    public void writeBitStreamToFile() {
        String fName = "bitstreamOutput.jpeg";
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
        if (currentSetBitIdx > 0) {
            int currByteIdx = currentSetByteIdx;
            while (currByteIdx == currentSetByteIdx) {
                setBit(false);
            }
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
