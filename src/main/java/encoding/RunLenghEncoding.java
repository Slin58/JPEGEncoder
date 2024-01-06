package encoding;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

public class RunLenghEncoding {

    private final short bits;
    private final byte category;

    public RunLenghEncoding(short bits, byte category) {
        this.bits = bits;
        this.category = category;
    }

    public static <T extends Serializable> RunLenghEncoding runLenghtEncoding(T number) {
        byte[] serialize = SerializationUtils.serialize(number);
        short bits = (short) ((serialize[serialize.length - 2] << 8) | (serialize[serialize.length - 1] & 0xff));

        short abs = (short) Math.abs(bits);
        if (bits == 0) {
            return new RunLenghEncoding((short) 0, (byte) 0);
        }

        byte category = 0;
        while (abs > 0) {
            abs = (short) (abs >> 1);
            category++;
        }

        if (bits < 0) {
            bits--;
        }

        return new RunLenghEncoding(bits, category);
    }

    public short getBits() {
        return bits;
    }

    public byte getCategory() {
        return category;
    }
}
