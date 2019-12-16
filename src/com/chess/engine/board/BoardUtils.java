package com.chess.engine.board;

public class BoardUtils {
    public static final boolean[] FIRST_COLUMN = null;

    private BoardUtils() {
        throw new RuntimeException("Cannot run the BoardUtils class");
    }

    public static boolean isValidSquareCord(int cord) {
        return cord >= 0 && cord < 64;
    }
}
