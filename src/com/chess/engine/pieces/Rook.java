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

public class Rook extends Piece {
    private final static int[] CANDIDATE_MOVE_VECTOR_CORDS = {-8, -1, 1, 8};

    Rook(int piecePosition, Loyalty pieceLoyalty) {
        super(piecePosition, pieceLoyalty);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        //for each of the possible vectors
        for(final int candidateCordOffset : CANDIDATE_MOVE_VECTOR_CORDS){
            //set our position as the starting candidate position, which is valid
            int candidateDestinationCord = this.piecePosition;

            //consider if the current move is valid
            while (BoardUtils.isValidSquareCord(candidateDestinationCord)){
                //if at the edge case, then we don't want to calculate the legal moves
                if(isFirstColumnEdgeCase(this.piecePosition, candidateCordOffset) ||
                        isEighthColumnEdgeCase(this.piecePosition, candidateCordOffset)){
                    break;
                }

                //we can apply the offset
                candidateDestinationCord += candidateCordOffset;

                //with the offset applied, is the move still valid
                if(BoardUtils.isValidSquareCord(candidateDestinationCord)){
                    //get the square
                    final Square candidateDestinationSquare = board.getSquare(candidateDestinationCord);

                    //if the square is not occupied then we add a legal move to the list, and we can loop again
                    if(!candidateDestinationSquare.isSquareOccupied()){
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCord));
                    } else {
                        final Piece pieceAtDestinationCord = candidateDestinationSquare.getPiece();
                        final Loyalty pieceLoyalty = pieceAtDestinationCord.getPieceLoyalty();

                        //if the loyalty is the opposite, we can add an attack move to the legal moves
                        if(this.pieceLoyalty != pieceLoyalty){
                            legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCord, pieceAtDestinationCord));
                        }
                        //if there is a friendly piece that is blocking the rest of the vector, we shouldn't consider any
                        //more of the vector as legal coordinate candidates
                        break;
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColumnEdgeCase(final int currentPos, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPos] && (candidateOffset == -1);
    }

    private static boolean isEighthColumnEdgeCase(final int currentPos, final int candidateOffset){
        return BoardUtils.FIRST_COLUMN[currentPos] && (candidateOffset == 1);
    }
}
