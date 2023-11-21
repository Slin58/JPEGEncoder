package segments;

import bitstream.BitStream;
import huffman.HuffmanLookUpRow;
import huffman.HuffmanTree;

import java.io.Serializable;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class DHTSegment<T extends Serializable> {

    private final List<HuffmanLookUpRow<T>> huffmanLookUpRows;
    BitStream bitStream;
    HuffmanTree<T> huffmanTree;

    public DHTSegment(BitStream bitStream, HuffmanTree<T> huffmanTree) {
        this.bitStream = bitStream;
        this.huffmanTree = huffmanTree;
        this.huffmanLookUpRows =
                List.copyOf(this.huffmanTree.getLookUpTable().values()).stream().sorted().collect(toList());
    }

    public void writeSegmentToBitStream(int segmentNumber, boolean isDC) {
        this.bitStream.writeHexString("ffc4");
        int length = getLength() + 19;
        this.bitStream.setInt(length, 16);
        this.bitStream.setInt(0, 3);
        this.bitStream.setBit(isDC);
        this.bitStream.setInt(segmentNumber, 4);
        for (int i = 1; i <= 16; i++) {
            final int bitsize = i;
            int symbolsOfBitsize = (int) huffmanLookUpRows.stream()
                    .filter(tHuffmanLookUpRow -> tHuffmanLookUpRow.getBitSize() == bitsize).count();
            this.bitStream.setInt(symbolsOfBitsize, 8);
        }
        for (HuffmanLookUpRow<T> huffmanLookUpRow : huffmanLookUpRows) {
            this.bitStream.setByte(huffmanLookUpRow.encodedValue());
        }
    }

    private int getLength() {
        return this.huffmanTree.getLookUpTable().size();
    }
}
