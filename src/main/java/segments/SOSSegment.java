package segments;

import bitstream.BitStream;
import encoding.ImageEncoding;
import image.JPEGEncoderImage;

public class SOSSegment {
    BitStream bitStream;
    ImageEncoding imageEncoding;

    public SOSSegment(BitStream bitStream, ImageEncoding imageEncoding) {
        this.bitStream = bitStream;
        this.imageEncoding = imageEncoding;
    }

    public void writeSegmentToBitStream(JPEGEncoderImage image) {

        this.bitStream.writeHexString("ffda");          //marker
        this.bitStream.setByte((byte) 0);
        this.bitStream.setByte((byte) 12);
        this.bitStream.setByte((byte) 3);
        this.bitStream.writeHexString("0110");
        this.bitStream.writeHexString("0232");
        this.bitStream.writeHexString("0332");
        this.bitStream.writeHexString("003f00");

        this.bitStream.setSos(true);
        this.imageEncoding.writeBlocks(image, bitStream);
        this.bitStream.setSos(false);
    }
}
