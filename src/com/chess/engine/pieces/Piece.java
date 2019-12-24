package com.chess.engine.pieces;

import com.chess.engine.Loyalty;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

  protected final int piecePosition;
  protected final Loyalty pieceLoyalty;
  protected final boolean isFirstMove;

  Piece(final int piecePosition, final Loyalty pieceLoyalty) {
    this.piecePosition = piecePosition;
    this.pieceLoyalty = pieceLoyalty;
    // TODO more work
    this.isFirstMove = false;
  }

  public Loyalty getPieceLoyalty() {
    return this.pieceLoyalty;
  }

  public boolean isFirstMove() {
    return this.isFirstMove;
  }

  public abstract Collection<Move> calculateLegalMoves(final Board board);
}
