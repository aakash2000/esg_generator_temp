package com.example.testwithsoot;


import java.nio.charset.StandardCharsets;
public class DataflowTestFuzzer {
    public static int x = 1;
    public static int y = 2;
    public static int counter = 0;

    private static class TreasureFoundException extends RuntimeException {
        TreasureFoundException(byte[] input) {
            super(renderPath(input));
        }
    }

    public static void fuzzerTestOneInput(byte[] input) {
        switch (input[0]) {
            case 'D':
                x++;
                counter++;
                break;
            case 'U':
                x--;
                counter++;
                break;
            case 'L':
                y++;
                counter++;
                break;
            case 'R':
                y--;
                counter++;
                break;
            case 'G':
                counter++;
                throw new TreasureFoundException(input);
            default:
                counter++;
                break;
        }
    }

    private static String renderPath(byte[] input) {
        return "x: " + x + ", y: " + y + ",count: " + counter + ",input: " + new String(input, StandardCharsets.UTF_8);
    }

    /*public static void main(String[] args) {
        byte[] input = new byte[]{'D', 'U', 'L', 'R', 'G'};
        fuzzerTestOneInput(input);
    }*/
}