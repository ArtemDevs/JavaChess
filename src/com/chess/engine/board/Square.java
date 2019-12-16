package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Square {

    int squareCord;

    Square(int squareCord){
        this.squareCord = squareCord;
    }

    public abstract boolean isSquareOccupied();

    public abstract Piece getPiece();

    public static final class EmptySquare extends Square{

        EmptySquare(int cord){
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

        Piece pieceOnSquare;

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
