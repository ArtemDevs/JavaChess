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

public class Knight extends Piece{


    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(int piecePosition, Loyalty pieceLoyalty) {
        super(piecePosition, pieceLoyalty);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board){
        List<Move> legalMoves = new ArrayList<>();

        for(final int  currentCandidate : CANDIDATE_MOVE_COORDINATES){
            final int candidateDestinationCord = this.piecePosition + currentCandidate;

            if(BoardUtils.isValidSquareCord(candidateDestinationCord)){
                if(isFirstColumnEdgeCase(this.piecePosition, currentCandidate) ||
                        isSecondColumnEdgeCase(this.piecePosition, currentCandidate) ||
                        isSeventhColumnEdgeCase(this.piecePosition, currentCandidate) ||
                        isEighthColumnEdgeCase(this.piecePosition, currentCandidate)){
                    continue;
                }

                final Square candidateDestinationSquare = board.getSquare(candidateDestinationCord);

                if(!candidateDestinationSquare.isSquareOccupied()){
                    legalMoves.add(new Move());
                } else {
                    final Piece pieceAtDestination = candidateDestinationSquare.getPiece();
                    final Loyalty pieceLoyalty = pieceAtDestination.getPieceLoyalty();

                    if(this.pieceLoyalty != pieceLoyalty){
                        legalMoves.add(new Move());
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    //Edge cases for the Knight, for when near the board edge and the offset will be incorrect
    public static boolean isFirstColumnEdgeCase(final int currentPos, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPos] && (candidateOffset == -17 || candidateOffset == -10 ||
                candidateOffset == 6 || candidateOffset == 15);
    }

    public static boolean isSecondColumnEdgeCase(final int currentPos, final int candidateOffset){
        return BoardUtils.SECOND_COLUMN[currentPos] && (candidateOffset == -10 || candidateOffset == 6);
    }

    public static boolean isSeventhColumnEdgeCase(final int currentPos, final int candidateOffset){
        return BoardUtils.SEVENTH_COLUMN[currentPos] && (candidateOffset == -6 || candidateOffset == 10);
    }

    public static boolean isEighthColumnEdgeCase(final int currentPos, final int candidateOffset){
        return BoardUtils.EIGHTH_COLUMN[currentPos] && (candidateOffset == -15 || candidateOffset == -6 ||
                candidateOffset == 10 || candidateOffset == 17);
    }
}
