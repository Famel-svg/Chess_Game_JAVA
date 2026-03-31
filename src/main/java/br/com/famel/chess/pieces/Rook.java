package br.com.famel.chess.pieces;

import br.com.famel.boardgame.Board;
import br.com.famel.chess.ChessPiece;
import br.com.famel.chess.Color;

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }
}
