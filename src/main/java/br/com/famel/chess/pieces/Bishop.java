package br.com.famel.chess.pieces;

import br.com.famel.boardgame.Board;
import br.com.famel.boardgame.Position;
import br.com.famel.chess.ChessPiece;
import br.com.famel.chess.Color;

public class Bishop extends ChessPiece {

    private static final int[][] DIRECTIONS = {
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);
        // diagonais: anda até a borda ou até a primeira peça (captura se for adversária)
        for (int[] d : DIRECTIONS) {
            p.setValues(position.getRow() + d[0], position.getColumn() + d[1]);
            while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
                p.setValues(p.getRow() + d[0], p.getColumn() + d[1]);
            }
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
        }

        return mat;
    }
}
