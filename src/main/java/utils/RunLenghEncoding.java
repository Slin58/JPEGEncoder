package utils;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.Arrays;

public class RunLenghEncoding {
    private final short bits;
    private final short category;

    public RunLenghEncoding(short bits, short category) {
        this.bits = bits;
        this.category = category;
    }

    public static <T extends Serializable> RunLenghEncoding runLenghtEncoding(T number) {
        byte[] serialize = SerializationUtils.serialize(number);
        System.out.println(Arrays.toString(serialize));
        short bits = (short) ((serialize[serialize.length - 2] << 8) | (serialize[serialize.length - 1] & 0xff));
        System.out.println(bits);

        short abs = (short) Math.abs(bits);
        if (bits == 0) {
            return new RunLenghEncoding((short) 0, (short) 0);
        }

        short category = 0;
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

    public short getCategory() {
        return category;
    }
}
