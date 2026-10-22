package br.com.famel.chess.pieces;

import br.com.famel.boardgame.Board;
import br.com.famel.boardgame.Position;
import br.com.famel.chess.ChessPiece;
import br.com.famel.chess.Color;

public class Knight extends ChessPiece {

    private static final int[][] JUMPS = {
            {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
            {1, -2}, {1, 2}, {2, -1}, {2, 1}
    };

    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "N";
    }

    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p == null || p.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);
        // movimento em L: salta por cima de qualquer peça
        for (int[] jump : JUMPS) {
            p.setValues(position.getRow() + jump[0], position.getColumn() + jump[1]);
            if (getBoard().positionExists(p) && canMove(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
        }

        return mat;
    }
}
