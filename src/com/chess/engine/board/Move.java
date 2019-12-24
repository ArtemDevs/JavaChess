package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public class Move {

  final Board board;
  final Piece movedPiece;
  final int destinationCord;

  private Move(final Board board, final Piece movedPiece, final int destinationCord) {
    this.board = board;
    this.movedPiece = movedPiece;
    this.destinationCord = destinationCord;
  }

  public static final class MajorMove extends Move {
    public MajorMove(final Board board, final Piece movedPiece, final int destinationCord) {
      super(board, movedPiece, destinationCord);
    }
  }

  public static final class AttackMove extends Move {
    final Piece attackedPiece;

    public AttackMove(
        final Board board,
        final Piece movedPiece,
        final int destinationCord,
        final Piece attackedPiece) {
      super(board, movedPiece, destinationCord);
      this.attackedPiece = attackedPiece;
    }
  }
}
