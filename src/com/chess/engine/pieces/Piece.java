package com.chess.engine.pieces;

import com.chess.engine.Loyalty;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final int piecePosition;
    protected final Loyalty pieceLoyalty;

    Piece(final int piecePosition, final Loyalty pieceLoyalty){
        this.piecePosition = piecePosition;
        this.pieceLoyalty = pieceLoyalty;
    }

    public Loyalty getPieceLoyalty(){
        return this.pieceLoyalty;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);
}
