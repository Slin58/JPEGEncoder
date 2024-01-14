package segments;

import bitstream.BitStream;

public class APP0JFIFSegment {
    BitStream bitStream;

    public APP0JFIFSegment(BitStream bitStream) {
        this.bitStream = bitStream;
    }

    public void writeSegmentToBitStream(int xDensity, int yDensity) {
        this.bitStream.writeHexString("ffe0");          //marker
        this.bitStream.writeHexString("0010");          //segment length
        this.bitStream.writeHexString("4a46494600");    //JFIF String
        this.bitStream.writeHexString("01");            //major revision number
        this.bitStream.writeHexString("01");            //minor revision number
        this.bitStream.writeHexString("00");            //pixel size
        this.bitStream.setInt(xDensity, 16);          //x-density
        this.bitStream.setInt(yDensity, 16);          //y-density
        this.bitStream.writeHexString("00");            //size thumbnail x
        this.bitStream.writeHexString("00");            //size thumbnail y
    }

}
