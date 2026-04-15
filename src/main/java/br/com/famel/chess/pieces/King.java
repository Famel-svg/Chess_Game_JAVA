package br.com.famel.chess.pieces;

import br.com.famel.boardgame.Board;
import br.com.famel.chess.ChessPiece;
import br.com.famel.chess.Color;

public class King extends ChessPiece {
    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        return mat;
    }
}
