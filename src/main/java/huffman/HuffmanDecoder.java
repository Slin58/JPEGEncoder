package huffman;

import bitstream.BitStream;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static bitstream.BitStream.BYTE_START_INDEX;

public class HuffmanDecoder<T extends Serializable> {

    private final int maxSize;
    List<HuffmanLookUpRow<T>> lookUpTable;
    private int currentGetBitIdx;
    private int currentGetByteIdx;
    private BitStream bitStream;

    public HuffmanDecoder(Map<T, HuffmanLookUpRow<T>> lookUpTable, BitStream bitStream) {
        this.maxSize = lookUpTable.values().stream().map(HuffmanLookUpRow::getBitSize).max(Integer::compareTo)
                .orElseThrow(() -> new RuntimeException(
                        "Something went wrong when trying to get maxSize of path on lookUpTable"));

        this.lookUpTable = lookUpTable.values().stream()
                .map(tHuffmanLookUpRow -> { //convert Map to list, fillUp Paths to same lengths and sort list by path
                    // in ASC
                    tHuffmanLookUpRow.setPath(
                            fillLookUpRow(tHuffmanLookUpRow.getPath(), this.maxSize, tHuffmanLookUpRow.getBitSize()));
                    return tHuffmanLookUpRow;
                }).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        this.currentGetBitIdx = BYTE_START_INDEX;
        this.currentGetByteIdx = 0;
        this.bitStream = bitStream;
    }

    public List<T> decodeAll() {
        List<T> result = new ArrayList<>();
        while (this.currentGetByteIdx < this.bitStream.getCurrentSetByteIdx() ||
               (this.currentGetByteIdx == this.bitStream.getCurrentSetByteIdx() &&
                this.currentGetBitIdx - 1 > this.bitStream.getCurrentSetBitIdx())) {
            result.add(decodeOne());
        }
        return result;
    }

    private T decodeOne() {
        HuffmanLookUpRow<T> temp = null;
        if (this.lookUpTable.isEmpty()) throw new RuntimeException("LookUpTable empty");
        int code = getBits(maxSize);
        for (HuffmanLookUpRow<T> elem : this.lookUpTable) {
            temp = elem;
            if (code <= temp.getPath()) {
                break;
            }
        }
        //zu viele gelesene Bits zurückgehen + ggf. Bytes zurückgehen:
        int tmp = this.maxSize - temp.getBitSize();
        this.currentGetBitIdx += tmp;
        this.currentGetByteIdx -= (this.currentGetBitIdx / 8);
        this.currentGetBitIdx %= 8;
        return temp.getValue();
    }

    private int fillLookUpRow(int key, int maxSize, int currentSize) {
        for (int i = maxSize - currentSize; i > 0; i--) {
            key = (key << 1) | 1;
        }
        return key;
    }

    public int getBits(int bitSize) {
        int value = 0;
        for (int i = bitSize - 1; i >= 0; i--) {
            if (this.currentGetByteIdx > this.bitStream.getCurrentSetByteIdx() ||
                (this.currentGetByteIdx == this.bitStream.getCurrentSetByteIdx() &&
                 this.currentGetBitIdx <= this.bitStream.getCurrentSetBitIdx())) {
                value = (value << 1) | 1;
            } else {
                value = (value << 1) |
                        ((this.bitStream.getBytes()[this.currentGetByteIdx] >> this.currentGetBitIdx) & 1);
            }
            this.currentGetBitIdx--;
            if (this.currentGetBitIdx < 0) {
                this.currentGetByteIdx++;
                this.currentGetBitIdx = BYTE_START_INDEX;
            }
        }
        return value;
    }


}
