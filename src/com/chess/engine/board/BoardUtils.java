package com.chess.engine.board;

public class BoardUtils {
    public static final boolean[] FIRST_COLUMN = initCol(0);
    public static final boolean[] SECOND_COLUMN = initCol(1);
    public static final boolean[] SEVENTH_COLUMN = initCol(6);
    public static final boolean[] EIGHTH_COLUMN = initCol(7);

    public static final int SQUARE_COUNT = 64;
    public static final int SQUARE_COUNT_PER_ROW = 8;

    //set the squares in the board equal to true depending on which column number is passed
    private static boolean[] initCol(int colNumber){
        final boolean[] col = new boolean[SQUARE_COUNT];

        do{
            col[colNumber] = true;
            colNumber += SQUARE_COUNT_PER_ROW;
        } while(colNumber < SQUARE_COUNT);
        return col;
    }

    private BoardUtils() {
        throw new RuntimeException("Cannot run the BoardUtils class");
    }

    public static boolean isValidSquareCord(final int cord) {
        return cord >= 0 && cord < 64;
    }
}
