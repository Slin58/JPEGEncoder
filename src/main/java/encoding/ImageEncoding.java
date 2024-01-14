package encoding;

import bitstream.BitStream;
import dctImplementation.AraiDCT;
import dctImplementation.DCT;
import huffman.HuffmanTree;
import image.JPEGEncoderImage;
import quantization.Quantization;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static utils.Utils.*;

public class ImageEncoding {

    DCT dct = new AraiDCT();
    HuffmanTree<Byte> acYHuffmantree;
    HuffmanTree<Byte> dcYHuffmantree;
    HuffmanTree<Byte> acCbCrHuffmantree;
    HuffmanTree<Byte> dcCbCrHuffmantree;

    public void encodeImage(JPEGEncoderImage image) {
        dct.calculatePictureDataWithDCT(image);
        dct.calculateDateOnArrays(image.getData1(), Quantization::luminanceQuantistation);
        dct.calculateDateOnArrays(image.getData2(), Quantization::chrominanceQuantistation);
        dct.calculateDateOnArrays(image.getData3(), Quantization::chrominanceQuantistation);
        buildHuffmanTrees(image);
    }

    public void writeBlocks(JPEGEncoderImage image, BitStream bitStream) {
        calculateOnBlocks(image, bitStream);
    }

    public void calculateOnBlocks(JPEGEncoderImage image, BitStream bitStream) {
        double[][] y = image.getData1();
        Values yValues = new Values(bitStream, acYHuffmantree, dcYHuffmantree);
        double[][] cb = image.getData2();
        double[][] cr = image.getData3();
        Values cbValues = new Values(bitStream, acCbCrHuffmantree, dcCbCrHuffmantree);
        Values crValues = new Values(bitStream, acCbCrHuffmantree, dcCbCrHuffmantree);
        for (int i = 0; i < y.length; i += 16) {
            for (int j = 0; j < y[i].length; j += 16) {
                calculateOnArray(y, i, j, yValues::getAllEncodedValues);
                calculateOnArray(y, i, j + 8, yValues::getAllEncodedValues);
                calculateOnArray(y, i + 8, j, yValues::getAllEncodedValues);
                calculateOnArray(y, i + 8, j + 8, yValues::getAllEncodedValues);
                //                calculateOnArray(cb, i, j, cbValues::getAllEncodedValues);
                //                calculateOnArray(cr, i, j, crValues::getAllEncodedValues);
                calculateOnArray(cb, i / 2, j / 2, cbValues::getAllEncodedValues);
                calculateOnArray(cr, i / 2, j / 2, crValues::getAllEncodedValues);
            }
        }
    }


    private void buildHuffmanTrees(JPEGEncoderImage image) {    //todo in zigzag Reihenfolge, wie bei Quantisierung
        HuffmanValues yHuffmanValues = new HuffmanValues();
        calculateOnArraysInBlocksWithoutModification(image.getData1(), yHuffmanValues::getAllEncodedValues);
        acYHuffmantree = new HuffmanTree.Builder<Byte>().add(yHuffmanValues.acValues).build();
        dcYHuffmantree = new HuffmanTree.Builder<Byte>().add(
                yHuffmanValues.encodedDcValues.stream().map(RunLenghEncoding::getCategory).toList()).build();

        HuffmanValues cbHuffmanValues = new HuffmanValues();
        HuffmanValues crHuffmanValues = new HuffmanValues();

        calculateOnArraysWithoutModification(image.getData2(), cbHuffmanValues::getAllEncodedValues);
        calculateOnArraysWithoutModification(image.getData3(), crHuffmanValues::getAllEncodedValues);

        acCbCrHuffmantree = new HuffmanTree.Builder<Byte>().add(
                Stream.concat(crHuffmanValues.acValues.stream(), cbHuffmanValues.acValues.stream()).toList()).build();

        dcCbCrHuffmantree = new HuffmanTree.Builder<Byte>().add(
                        Stream.concat(cbHuffmanValues.encodedDcValues.stream().map(RunLenghEncoding::getCategory),
                                      crHuffmanValues.encodedDcValues.stream().map(RunLenghEncoding::getCategory)).toList())
                .build();

        acYHuffmantree.createLookUpTable();
        dcYHuffmantree.createLookUpTable();
        acCbCrHuffmantree.createLookUpTable();
        dcCbCrHuffmantree.createLookUpTable();
    }

    public HuffmanTree<Byte> getAcYHuffmantree() {
        return acYHuffmantree;
    }

    public HuffmanTree<Byte> getDcYHuffmantree() {
        return dcYHuffmantree;
    }

    public HuffmanTree<Byte> getAcCbCrHuffmantree() {
        return acCbCrHuffmantree;
    }

    public HuffmanTree<Byte> getDcCbCrHuffmantree() {
        return dcCbCrHuffmantree;
    }

    static class HuffmanValues {
        List<Byte> acValues = new ArrayList<>();
        List<RunLenghEncoding> encodedDcValues = new ArrayList<>();
        int lastDcValue = 0;

        double[][] getAllEncodedValues(double[][] values) {
            double[] valuesInZigzag = getValuesInZigzag(values);

            encodedDcValues.add(RunLenghEncoding.runLenghtEncoding((int) valuesInZigzag[0] - lastDcValue));
            lastDcValue = (int) valuesInZigzag[0];

            byte zeroes = 0;
            int zeroblocks = 0;
            for (int i = 1; i < valuesInZigzag.length; i++) {
                if (valuesInZigzag[i] == 0 && zeroes < 15) {
                    zeroes++;
                } else if (valuesInZigzag[i] == 0) {
                    zeroblocks++;
                    zeroes = 0;
                } else {
                    if (zeroblocks > 0) {
                        for (int z = 0; z < zeroblocks; z++) {
                            acValues.add((byte) 0xf0);
                        }
                        zeroblocks = 0;
                    }
                    byte huffmanValues = (byte) (zeroes << 4 |
                                                 RunLenghEncoding.runLenghtEncoding((int) valuesInZigzag[i])
                                                         .getCategory() & 0x0f);
                    acValues.add(huffmanValues);
                    zeroes = 0;
                }
            }
            if ((int) valuesInZigzag[valuesInZigzag.length - 1] == 0) acValues.add((byte) 0x0);
            return new double[0][0];
        }
    }

    static class Values {
        private final BitStream bitStream;
        private final HuffmanTree<Byte> acHuffmantree;
        private final HuffmanTree<Byte> dcHuffmantree;
        int lastDcValue = 0;

        Values(BitStream bitStream, HuffmanTree<Byte> acHuffmantree, HuffmanTree<Byte> dcHuffmantree) {
            this.bitStream = bitStream;
            this.acHuffmantree = acHuffmantree;
            this.dcHuffmantree = dcHuffmantree;
        }

        double[] getAllEncodedValues(double[][] values) {
            double[] valuesInZigzag = getValuesInZigzag(values);

            RunLenghEncoding dcEncoding = RunLenghEncoding.runLenghtEncoding((int) valuesInZigzag[0] - lastDcValue);
            dcHuffmantree.encodeSingle(dcEncoding.getCategory(), bitStream);
            bitStream.setInt(dcEncoding.getBits(), dcEncoding.getCategory());
            lastDcValue = (int) valuesInZigzag[0];

            byte zeroes = 0;
            int zeroblocks = 0;
            for (int i = 1; i < valuesInZigzag.length; i++) {
                if (valuesInZigzag[i] == 0 && zeroes < 15) {
                    zeroes++;
                } else if (valuesInZigzag[i] == 0) {
                    zeroblocks++;
                    zeroes = 0;
                } else {
                    if (zeroblocks > 0) {
                        for (int z = 0; z < zeroblocks; z++) {
                            acHuffmantree.encodeSingle((byte) 0xF0, bitStream);
                        }
                        zeroblocks = 0;
                    }
                    RunLenghEncoding acEncoding = RunLenghEncoding.runLenghtEncoding((int) valuesInZigzag[i]);
                    byte huffmanValues = (byte) (zeroes << 4 | acEncoding.getCategory() & 0x0f);
                    acHuffmantree.encodeSingle(huffmanValues, bitStream);
                    bitStream.setInt(acEncoding.getBits(), acEncoding.getCategory());
                    zeroes = 0;
                }
            }
            if ((int) valuesInZigzag[valuesInZigzag.length - 1] == 0) acHuffmantree.encodeSingle((byte) 0x0, bitStream);
            return new double[0];
        }
    }
}
