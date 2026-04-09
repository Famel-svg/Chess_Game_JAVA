package br.com.famel;

import br.com.famel.boardgame.Board;
import br.com.famel.boardgame.Position;
import br.com.famel.chess.ChessMatch;
import br.com.famel.chess.ChessPiece;
import br.com.famel.chess.ChessPosition;

import java.util.Scanner;

import static java.lang.IO.println;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ChessMatch match = new ChessMatch();

        while (true){
            UI.printBoard(match.getPieces());
            System.out.println();
            System.out.println("Source: ");
            ChessPosition source = UI.readChessPosition(new Scanner(sc.nextLine().toUpperCase()));

            System.out.println();
            System.out.println("Target: ");
            ChessPosition target = UI.readChessPosition(new Scanner(sc.nextLine().toUpperCase()));

            ChessPiece capturedPiece = match.performChessMove(source, target);
        }

    }
}
