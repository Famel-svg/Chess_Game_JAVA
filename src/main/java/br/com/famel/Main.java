package br.com.famel;

import br.com.famel.chess.ChessException;
import br.com.famel.chess.ChessMatch;
import br.com.famel.chess.ChessPiece;
import br.com.famel.chess.ChessPosition;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ChessMatch match = new ChessMatch();

        while (true){
            try {
                UI.printBoard(match.getPieces());
                System.out.println();
                System.out.println("Source: ");
                ChessPosition source = UI.readChessPosition(new Scanner(sc.nextLine().toUpperCase()));

                boolean[][] possibleMoves = match.PossibleMove(source);
                UI.clearScreen();
                UI.printBoard(match.getPieces(), possibleMoves);

                System.out.println();
                System.out.println("Target: ");
                ChessPosition target = UI.readChessPosition(new Scanner(sc.nextLine().toUpperCase()));

                ChessPiece capturedPiece = match.performChessMove(source, target);
            }
            catch (ChessException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }

    }
}
