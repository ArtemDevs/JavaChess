package com.chess.engine.board;

import com.chess.engine.Loyalty;
import com.chess.engine.pieces.*;
import com.google.common.collect.ImmutableList;

import java.util.*;

public class Board {

  // using list for our collection of squares (0 - 63) over an array because we can have an
  // immutable list
  private final List<Square> gameBoard;
  private final Collection<Piece> whitePieces;
  private final Collection<Piece> blackPieces;

  private Board(Builder builder) {
    this.gameBoard = createGameBoard(builder);
    this.whitePieces = calculateActivePieces(this.gameBoard, Loyalty.WHITE);
    this.blackPieces = calculateActivePieces(this.gameBoard, Loyalty.BLACK);

    //final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
    //final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);
  }

  // print the board in ASCII text so we can quickly interpret the board
  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < BoardUtils.SQUARE_COUNT; i++) {
      final String squareText = this.gameBoard.get(i).toString();
      builder.append(String.format("%3s", squareText));
      if ((i + 1) % BoardUtils.SQUARE_COUNT_PER_ROW == 0) {
        builder.append("\n");
      }
    }
    return builder.toString();
  }

  private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) {
    final List<Move> legalMoves = new ArrayList<>();

    for (final Piece piece : pieces) {
      legalMoves.addAll(piece.calculateLegalMoves(this));
    }

    return ImmutableList.copyOf(legalMoves);
  }

  private static Collection<Piece> calculateActivePieces(
      final List<Square> gameBoard, Loyalty loyalty) {
    final List<Piece> activePieces = new ArrayList<>();

    for (final Square square : gameBoard) {
      if (square.isSquareOccupied()) {
        final Piece piece = square.getPiece();
        if (piece.getPieceLoyalty() == loyalty) {
          activePieces.add(piece);
        }
      }
    }
    return ImmutableList.copyOf(activePieces);
  }

  public Square getSquare(final int squareCord) {
    return gameBoard.get(squareCord);
  }

  private static List<Square> createGameBoard(final Builder builder) {
    final Square[] squares = new Square[BoardUtils.SQUARE_COUNT];
    for (int i = 0; i < BoardUtils.SQUARE_COUNT; i++) {
      squares[i] = Square.createSquare(i, builder.boardConfig.get(i));
    }
    return ImmutableList.copyOf(squares);
  }

  // using the builder we can create the standard board of pieces
  public static Board createStandardBoard() {
    final Builder builder = new Builder();
    // layout for the Black Pieces
    builder.setPiece(new Rook(0, Loyalty.BLACK));
    builder.setPiece(new Knight(1, Loyalty.BLACK));
    builder.setPiece(new Bishop(2, Loyalty.BLACK));
    builder.setPiece(new Queen(3, Loyalty.BLACK));
    builder.setPiece(new King(4, Loyalty.BLACK));
    builder.setPiece(new Bishop(5, Loyalty.BLACK));
    builder.setPiece(new Knight(6, Loyalty.BLACK));
    builder.setPiece(new Rook(7, Loyalty.BLACK));
    for (int i = 0; i < BoardUtils.SQUARE_COUNT_PER_ROW; i++) {
      builder.setPiece(new Pawn(i + 8, Loyalty.BLACK));
    }

    // layout for the White Pieces
    for (int i = 0; i < BoardUtils.SQUARE_COUNT_PER_ROW; i++) {
      builder.setPiece(new Pawn(i + 48, Loyalty.BLACK));
    }
    builder.setPiece(new Rook(56, Loyalty.WHITE));
    builder.setPiece(new Knight(57, Loyalty.WHITE));
    builder.setPiece(new Bishop(58, Loyalty.WHITE));
    builder.setPiece(new Queen(59, Loyalty.WHITE));
    builder.setPiece(new King(60, Loyalty.WHITE));
    builder.setPiece(new Bishop(61, Loyalty.WHITE));
    builder.setPiece(new Knight(62, Loyalty.WHITE));
    builder.setPiece(new Rook(63, Loyalty.WHITE));

    return builder.build();
  }

  // builder
  public static class Builder {

    Map<Integer, Piece> boardConfig;
    Loyalty currentPlayer;

    public Builder() {
      this.boardConfig = new HashMap<>();
    }

    public Builder setPiece(final Piece piece) {
      this.boardConfig.put(piece.getPiecePos(), piece);
      return this;
    }

    public Builder setCurrentPlayer(final Loyalty currentPlayer) {
      this.currentPlayer = currentPlayer;
      return this;
    }

    public Board build() {
      return new Board(this);
    }
  }
}
