package br.com.famel;

import br.com.famel.boardgame.Board;
import br.com.famel.boardgame.Position;
import br.com.famel.chess.ChessMatch;

import static java.lang.IO.println;

public class Main {
    static void main() {

        ChessMatch match = new ChessMatch();
        UI.printBoard(match.getPieces());
    }
}
