package huffman;

import bitstream.BitStream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static bitstream.BitStream.BYTE_START_INDEX;

public class HuffmanDecoder<T> {

    List<HuffmanLookUpRow<T>> lookUpTable;
    private int currentGetBitIdx;

    private int currentGetByteIdx;
    private BitStream bitStream;
    private int maxSize;

    public HuffmanDecoder(Map<T, HuffmanLookUpRow<T>> lookUpTable, BitStream bitStream) {
        this.maxSize = lookUpTable.values().stream().map(HuffmanLookUpRow::getCounter).max(Integer::compareTo)
                .orElseThrow(() -> new RuntimeException(
                        "Something went wrong when trying to get maxSize of path on lookUpTable"));

        this.lookUpTable = lookUpTable.values().stream()
                .map(tHuffmanLookUpRow -> { //convert Map to list, fillUp Paths to same lengths and sort list by path
                    // in ASC
                    tHuffmanLookUpRow.setPath(fillLookUpRow(tHuffmanLookUpRow.getPath(), this.maxSize));
                    return tHuffmanLookUpRow;
                }).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        this.currentGetBitIdx = BYTE_START_INDEX;
        this.currentGetByteIdx = 0;
        this.bitStream = bitStream;
    }

    private List<T> decodeAll() {
        List<T> result = new ArrayList<>();
        while (this.currentGetByteIdx >= this.bitStream.getCurrentSetByteIdx() &&
               this.currentGetBitIdx <= this.bitStream.getCurrentSetBitIdx()) {
            result.add(decodeOne());
        }
        return result;
    }

    private T decodeOne() {
        HuffmanLookUpRow<T> temp = null;
        if (this.lookUpTable.isEmpty()) throw new RuntimeException("LookUpTable empty");
        int code = getBits(maxSize);
        for (HuffmanLookUpRow<T> elem : this.lookUpTable) {
            if (temp != null) {
                if (code >= elem.getPath()) {
                    break;
                }
            }
            temp = elem;
        }
        return temp.getValue();
    }

    private int fillLookUpRow(int key, int maxSize) {
        for (int i = maxSize - this.lookUpTable.get(key).getCounter(); i >= 0; i--) {
            key = (key << 1) | 1;
        }
        return key;
    }

    public int getBits(int bitSize) {
        int value = 0;
        for (int i = bitSize - 1; i >= 0; i--) {
            if (this.currentGetByteIdx >= this.bitStream.getCurrentSetByteIdx() &&
                this.currentGetBitIdx <= this.bitStream.getCurrentSetBitIdx()) {
                value = (value << 1) | 1;
                continue;
            }
            value = (value << 1) | ((this.bitStream.getBytes()[this.currentGetByteIdx] >> this.currentGetBitIdx) & 1);
            this.currentGetBitIdx--;
            if (this.currentGetBitIdx < 0) {
                this.currentGetByteIdx++;
                this.currentGetBitIdx = BYTE_START_INDEX;
            }
        }
        return value;
    }


}
