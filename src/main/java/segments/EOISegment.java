package segments;

import bitstream.BitStream;

public class EOISegment {
    BitStream bitStream;

    public EOISegment(BitStream bitStream) {
        this.bitStream = bitStream;
    }

    public void writeSegmentToBitStream() {
        this.bitStream.writeHexString("ffd9");          //marker 1111111110110010
        //1111111111011001
    }
}
