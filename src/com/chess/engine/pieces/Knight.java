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

import static com.chess.engine.board.Move.*;

public class Knight extends Piece {

  private static final int[] CANDIDATE_MOVE_CORDS = {-17, -15, -10, -6, 6, 10, 15, 17};

  public Knight(final int piecePosition, final Loyalty pieceLoyalty) {
    super(piecePosition, pieceLoyalty);
  }

  @Override
  public Collection<Move> calculateLegalMoves(final Board board) {
    final List<Move> legalMoves = new ArrayList<>();

    // for each of the possible moves
    for (final int currentCandidate : CANDIDATE_MOVE_CORDS) {
      // apply the first move offset
      final int candidateDestinationCord = this.piecePosition + currentCandidate;

      // consider if the current move is valid
      if (BoardUtils.isValidSquareCord(candidateDestinationCord)) {
        // if at the edge case, then we don't want to calculate the legal moves
        if (isFirstColumnEdgeCase(this.piecePosition, currentCandidate)
            || isSecondColumnEdgeCase(this.piecePosition, currentCandidate)
            || isSeventhColumnEdgeCase(this.piecePosition, currentCandidate)
            || isEighthColumnEdgeCase(this.piecePosition, currentCandidate)) {
          continue;
        }

        // get the square
        final Square candidateDestinationSquare = board.getSquare(candidateDestinationCord);

        // if the square is not occupied then we add a legal move to the list, and we can loop again
        if (!candidateDestinationSquare.isSquareOccupied()) {
          legalMoves.add(new MajorMove(board, this, candidateDestinationCord));
        } else {
          final Piece pieceAtDestinationCord = candidateDestinationSquare.getPiece();
          final Loyalty pieceLoyalty = pieceAtDestinationCord.getPieceLoyalty();

          // if the loyalty is the opposite, we can add an attack move to the legal moves
          if (this.pieceLoyalty != pieceLoyalty) {
            legalMoves.add(
                new AttackMove(board, this, candidateDestinationCord, pieceAtDestinationCord));
          }
        }
      }
    }
    return ImmutableList.copyOf(legalMoves);
  }

  @Override
  public String toString() {
    return PieceType.KNIGHT.toString();
  }

  // Edge cases for the Knight, for when near the board edge and the offset will be incorrect
  private static boolean isFirstColumnEdgeCase(final int currentPos, final int candidateOffset) {
    return BoardUtils.FIRST_COLUMN[currentPos]
        && (candidateOffset == -17
            || candidateOffset == -10
            || candidateOffset == 6
            || candidateOffset == 15);
  }

  private static boolean isSecondColumnEdgeCase(final int currentPos, final int candidateOffset) {
    return BoardUtils.SECOND_COLUMN[currentPos] && (candidateOffset == -10 || candidateOffset == 6);
  }

  private static boolean isSeventhColumnEdgeCase(final int currentPos, final int candidateOffset) {
    return BoardUtils.SEVENTH_COLUMN[currentPos]
        && (candidateOffset == -6 || candidateOffset == 10);
  }

  private static boolean isEighthColumnEdgeCase(final int currentPos, final int candidateOffset) {
    return BoardUtils.EIGHTH_COLUMN[currentPos]
        && (candidateOffset == -15
            || candidateOffset == -6
            || candidateOffset == 10
            || candidateOffset == 17);
  }
}
