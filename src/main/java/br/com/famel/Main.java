package br.com.famel;

import br.com.famel.chess.ChessException;
import br.com.famel.chess.ChessMatch;
import br.com.famel.chess.ChessPosition;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ChessMatch match = new ChessMatch();

        while (!match.getCheckMate()){
            try {
                UI.clearScreen();
                UI.printMatch(match);
                System.out.println();
                System.out.println("Source: ");
                ChessPosition source = UI.readChessPosition(sc);

                boolean[][] possibleMoves = match.PossibleMove(source);
                UI.clearScreen();
                UI.printBoard(match.getPieces(), possibleMoves);
                System.out.println();
                System.out.println("Target: ");
                ChessPosition target = UI.readChessPosition(sc);

                match.performChessMove(source, target);
            }
            catch (ChessException e){
                System.out.println(e.getMessage());
                System.out.println("Press Enter to continue...");
                if (!sc.hasNextLine()) {
                    return;
                }
                sc.nextLine();
            }
            catch (NoSuchElementException e){
                // entrada encerrada (Ctrl+D ou fim do pipe)
                return;
            }
        }
        UI.clearScreen();
        UI.printMatch(match);
    }
}
