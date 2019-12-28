package com.chess.engine.pieces;

import com.chess.engine.Loyalty;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Square;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece {

  private static final int[] CANDIDATE_MOVE_CORD = {-9, -8, -7, -1, 1, 7, 8, 9};

  public King(final int piecePosition, final Loyalty pieceLoyalty) {
    super(piecePosition, pieceLoyalty);
  }

  @Override
  public Collection<Move> calculateLegalMoves(Board board) {
    final List<Move> legalMoves = new ArrayList<>();

    for (final int currentCandidateOffset : CANDIDATE_MOVE_CORD) {
      final int candidateDestinationCord = this.piecePosition + currentCandidateOffset;

      // edge cases where we want to skip
      if (isFirstColumnEdgeCase(this.piecePosition, currentCandidateOffset)
          || isEighthColumnEdgeCase(this.piecePosition, currentCandidateOffset)) {
        continue;
      }

      // get the square
      final Square candidateDestinationSquare = board.getSquare(candidateDestinationCord);

      // if the square is not occupied then we add a legal move to the list, and we can loop again
      if (!candidateDestinationSquare.isSquareOccupied()) {
        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCord));
      } else {
        final Piece pieceAtDestinationCord = candidateDestinationSquare.getPiece();
        final Loyalty pieceLoyalty = pieceAtDestinationCord.getPieceLoyalty();

        // if the loyalty is the opposite, we can add an attack move to the legal moves
        if (this.pieceLoyalty != pieceLoyalty) {
          legalMoves.add(
              new Move.AttackMove(board, this, candidateDestinationCord, pieceAtDestinationCord));
        }
      }
    }
    return ImmutableList.copyOf(legalMoves);
  }

  @Override
  public String toString() {
    return PieceType.KING.toString();
  }

  // Edge cases for the Knight, for when near the board edge and the offset will be incorrect
  private static boolean isFirstColumnEdgeCase(final int currentPos, final int candidateOffset) {
    return BoardUtils.FIRST_COLUMN[currentPos]
        && (candidateOffset == -9 || candidateOffset == -1 || candidateOffset == 7);
  }

  private static boolean isEighthColumnEdgeCase(final int currentPos, final int candidateOffset) {
    return BoardUtils.EIGHTH_COLUMN[currentPos]
        && (candidateOffset == -7 || candidateOffset == 1 || candidateOffset == 9);
  }
}
