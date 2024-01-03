package segments;

import bitstream.BitStream;

public class SOISegment {
    BitStream bitStream;

    public SOISegment(BitStream bitStream) {
        this.bitStream = bitStream;
    }

    public void writeSegmentToBitStream() {
        this.bitStream.writeHexString("ffd8");          //marker
    }
}
