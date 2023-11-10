package huffman;

import bitstream.BitStream;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HuffmanDecoder<T> {

    Map<Integer, HuffmanLookUpRow<T>> lookUpTable;

    public HuffmanDecoder(Map<T, HuffmanLookUpRow<T>> lookUpTable) {

        this.lookUpTable = lookUpTable.entrySet().stream().collect(Collectors.toMap(tHuffmanLookUpRowEntry -> tHuffmanLookUpRowEntry.getValue().getPath(), Map.Entry::getValue));
    }


    private void decode(BitStream bitStream) {

    }
}
