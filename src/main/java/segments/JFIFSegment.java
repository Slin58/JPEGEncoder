package segments;

import bitstream.BitStream;

public class JFIFSegment {
    BitStream bitStream;

    public JFIFSegment(BitStream bitStream) {
        this.bitStream = bitStream;
    }

    public void writeSegmentToBitStream() {
        this.bitStream.writeHexString("ffe0");          //marker
        this.bitStream.writeHexString("0010");            //segment length
        this.bitStream.writeHexString("4a46494600");    //JFIF String
        this.bitStream.writeHexString("01");            //major revision number
        this.bitStream.writeHexString("01");            //minor revision number
        this.bitStream.writeHexString("00");            //pixel size
        this.bitStream.writeHexString("0048");          //x-density
        this.bitStream.writeHexString("0048");          //y-density
        this.bitStream.writeHexString("00");            //size thumbnail x
        this.bitStream.writeHexString("00");            //size thumbnail y
        this.bitStream.writeHexString("ffff");            //size thumbnail y
    }

}
