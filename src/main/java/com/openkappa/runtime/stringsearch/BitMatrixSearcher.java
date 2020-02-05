package com.openkappa.runtime.stringsearch;

public class BitMatrixSearcher implements Searcher {

    public static void main(String... args) {
        BitMatrixSearcher search = new BitMatrixSearcher("A12f".getBytes());
        System.out.println("found it at " + search.find("ashhdipiqwhciqwipbqciwbecpiqwbecA12fFKJAsflgqweiffbibdlasbflagiofbwcdp".getBytes()));
        System.out.println("found it at " + search.find("ashhdipiqwhciqwipbqciwbecpiqwbecFKJAsflgqweiffbibdlasbflagiofbwcdp".getBytes()));
        System.out.println("found it at " + search.find("A12ashhdipiqwhciqwipbqciwbecpiqwbecFKJAsflgqweiffbibdlasbflagiofbwcdp".getBytes()));
    }

    private final long[] masks = new long[256];
    private final long success;

    public BitMatrixSearcher(byte[] searchString) {
        if (searchString.length > 64) {
            throw new IllegalArgumentException("Too many bytes");
        }
        long word = 1L;
        for (byte key : searchString) {
            masks[key & 0xFF] |= word;
            word <<= 1;
        }
        this.success = 1L << (searchString.length - 1);
    }

    public int find(byte[] data) {
        long current = 0L;
        for (int i = 0; i < data.length; ++i) {
            current = ((current << 1) | 1) & masks[data[i] & 0xFF];
            if ((current & success) == success) {
                return i - Long.numberOfTrailingZeros(success);
            }
        }
        return -1;
    }
}
