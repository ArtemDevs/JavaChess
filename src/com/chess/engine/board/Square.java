package com.chess.engine.board;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public abstract class Square {

    protected final int squareCord;

    private static final Map<Integer, EmptySquare> EMPTY_SQUARES = createAllPossibleEmptySquares();

    private static Map<Integer,EmptySquare> createAllPossibleEmptySquares() {
        final Map<Integer,EmptySquare> emptySquareMap = new HashMap<>();

        for(int i = 0; i < 64; i++){
            emptySquareMap.put(i, new EmptySquare(i));
        }

        return ImmutableMap.copyOf(emptySquareMap);
    }

    public static Square createSquare(final int squareCord, final Piece piece){
        return piece != null ? new OccupiedSquare(squareCord, piece) : EMPTY_SQUARES.get(squareCord);
    }

    private Square(int squareCord){
        this.squareCord = squareCord;
    }

    public abstract boolean isSquareOccupied();

    public abstract Piece getPiece();

    public static final class EmptySquare extends Square{

        EmptySquare(final int cord){
            super(cord);
        }

        @Override
        public boolean isSquareOccupied() {
            return false;
        }

        @Override
        public Piece getPiece(){
            return null;
        }
    }

    public static final class OccupiedSquare extends Square{

        private final Piece pieceOnSquare;

        OccupiedSquare(int squareCord, Piece pieceOnSquare){
            super(squareCord);
            this.pieceOnSquare = pieceOnSquare;
        }

        @Override
        public boolean isSquareOccupied(){
            return true;
        }

        @Override
        public Piece getPiece(){
            return this.pieceOnSquare;
        }
    }
}
