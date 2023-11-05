import Bitstream.BitStream;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HexFormat;

public class BitStreamTest {

    @Test
    void writeBit() {
        BitStream bitStream = new BitStream();
        bitStream.setBit(true);Assert.assertEquals(bitStream.getBytes()[0], HexFormat.of().parseHex("80")[0]);
    }

    @Test
    void writeByte() {
        byte[] value = HexFormat.of().parseHex("C5");
        BitStream bitStream = new BitStream();
        bitStream.setByte(value[0]);
        Assert.assertEquals(bitStream.getBytes(), Arrays.copyOf(value,256));
    }

    @Test
    void writeBytes() {
        byte[] values = HexFormat.of().parseHex("ff5bab71d5800ae6");
        BitStream bitStream = new BitStream();
        bitStream.setBytes(values);
        byte[] result = bitStream.getBytes();
        Assert.assertEquals(result, Arrays.copyOf(values,256));
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

        byte[] expected = HexFormat.of().parseHex("C56BE0");
        byte[] bytes = bitStream.getBytes();
        Assert.assertEquals(bytes, Arrays.copyOf(expected,256));
    }

    @Test
    void writeMassiveByteArray(){
        byte[] values = HexFormat.of().parseHex("8627d1f0ffa864167bb58870837f93d5aafbcb608d5ca54095bcfff" +
                "3d43e1ed792babd937fd409f3da112720616b05629a48dcfbb79de8fff12a4864cf4a9b7d3ba3d47fb1eb1c2ebc5b" +
                "e3138dd916306d7c2b273e81256786877be4e21207a1badffde70561c3238b53b6816dba5f8a05f96ff30a6ef3b1f" +
                "c688ad1cef26eef4accaf66fb5ae8f9376ec154fd7bbec898a2648afa1b4eb25ab750bb3733c61e64569b07253b9f" +
                "805170253d72a2444f326a1c2f0d7ef2755acf4b12c15b8c68896dc35258a3bda159d04e4fddf71936dc668b34413" +
                "4c3b0a9bb5c5f3a2971b14738ce3ac61eba537d98c87b77bf73d93ac651b6f520c615cd26e46b3b1ca1e5f68d521f" +
                "db0157feb6b4c14757697d77d34555");
        BitStream bitStream = new BitStream();
        bitStream.setBytes(values);
        Assert.assertEquals(bitStream.getBytes(), Arrays.copyOf(values,512));
    }
}
