import Bitstream.BitStream;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HexFormat;

public class BitStreamTest {

    @Test
    void writeBit() {
        BitStream bitStream = new BitStream();
        bitStream.setBit(true);
        System.out.print(bitStream);
        Assert.assertEquals(bitStream.getBytes()[0], Byte.decode("0x1"));
    }

    @Test
    void writeByte() {
        byte[] value = HexFormat.of().parseHex("C5");
        BitStream bitStream = new BitStream();
        bitStream.setByte(value[0]);
        System.out.println(bitStream);
        Assert.assertEquals(bitStream.getBytes(), value);
    }

    @Test
    void writeBytes() {
        byte[] values = HexFormat.of().parseHex("ff5bab71d5800ae6");
        BitStream bitStream = new BitStream();
        bitStream.setBytes(values);
        byte[] result = bitStream.getBytes();
        System.out.println(bitStream);
        Assert.assertEquals(result, values);
    }


    @Test
    void writeBitAndByte() {
        byte value1 = HexFormat.of().parseHex("C5")[0];//1100 0101
        byte value2 = HexFormat.of().parseHex("AF")[0];//1010 1111
        BitStream bitStream = new BitStream();
        bitStream.setByte(value1);//1100 0101
        bitStream.setBit(false);//1100 0101 0
        bitStream.setBit(true);//1100 0101 01
        bitStream.setByte(value2);//1100 0101 0110 1011 11
        bitStream.setBit(true);//1100 0101 0110 1011 1110 0000

        byte[] expected = HexFormat.of().parseHex("C57D60");
        byte[] bytes = bitStream.getBytes();
        for (byte aByte : bytes) {
            System.out.println(aByte);
        }
        Assert.assertEquals(bytes, expected);
    }
}
