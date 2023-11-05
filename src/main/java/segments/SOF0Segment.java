package segments;

import bitstream.BitStream;

import java.util.HexFormat;

public class SOF0Segment {
    BitStream bitStream;
    int xMax;
    int yMax;

    int accuracy;
    Component[] components;

    public SOF0Segment(BitStream bitStream, int accuracy, int xMax, int yMax, Component[] components) {
        this.bitStream = bitStream;
        this.xMax = xMax;
        this.yMax = yMax;
        this.accuracy = accuracy;
        this.components = components;
    }

    public SOF0Segment(BitStream bitStream, int xMax, int yMax, Component[] components) {
        this(bitStream, 8, xMax, yMax, components);
    }

    public void writeSegmentToBitStream() {
        this.bitStream.writeHexString("FFC0");
        int length = 8 + this.components.length * 3;
        this.bitStream.setBytes(new byte[]{(byte) (length >> 8), (byte) length});
        this.bitStream.setByte((byte) this.accuracy);
        this.bitStream.setBytes(new byte[]{(byte) (this.yMax >> 8), (byte) this.yMax});
        this.bitStream.setBytes(new byte[]{(byte) (this.xMax >> 8), (byte) this.xMax});
        this.bitStream.setByte((byte) this.components.length);
        for (Component component : this.components) {
            this.bitStream.setBytes(component.getComponentAsBytes());
        }
    }

    public static class Component {
        int id;
        String samplingFactor;
        int quantId;        //todo: Quantisierungstabelle?

        public Component(int id, String samplingFactor, int quantId) {
            this.id = id;
            this.samplingFactor = samplingFactor;
            this.quantId = quantId;
        }

        public byte[] getComponentAsBytes() {
            return new byte[]{(byte) this.id, HexFormat.of().parseHex(this.samplingFactor)[0], (byte) this.quantId};
        }
    }

}
