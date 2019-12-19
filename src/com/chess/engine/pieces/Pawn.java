package com.chess.engine.pieces;

import com.chess.engine.Loyalty;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.board.Move.*;

public class Pawn extends Piece{
    private final static int[] CANDIDATE_MOVE_CORD = {8, 16};

    Pawn(final int piecePosition, final Loyalty pieceLoyalty) {
        super(piecePosition, pieceLoyalty);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for(final int candidateCordOffset : CANDIDATE_MOVE_CORD){
            //have our destination influenced by the loyalty of the piece
            int candidateDestinationCord = this.piecePosition + (this.getPieceLoyalty().getDirection() * candidateCordOffset);

            //if the square is not valid, skip it
            if(!BoardUtils.isValidSquareCord(candidateDestinationCord)){
                continue;
            }

            //if the pawn is moving forwards and the tile is NOT occupied
            if(candidateCordOffset == 8 && !board.getSquare(candidateDestinationCord).isSquareOccupied()){
                //TODO need to implement pawn movement
                legalMoves.add(new MajorMove(board, this, candidateDestinationCord));
            } else if(candidateCordOffset == 16 && this.isFirstMove()){

            }


        }

        return legalMoves;
    }
}
