package br.com.famel.chess;

/**
 * Converte o texto digitado pelo jogador (ex.: "e2", "E2", " e2 ") em {@link ChessPosition}.
 * Não faz I/O: só interpreta a string, para poder ser testado isoladamente.
 */
public final class ChessPositionParser {

    private static final String INVALID = "Invalid position, position must be between A1 and H8";

    private ChessPositionParser() {
    }

    public static ChessPosition parse(String input) {
        if (input == null) {
            throw new ChessException(INVALID);
        }
        String s = input.trim().toUpperCase();
        if (s.length() != 2) {
            throw new ChessException(INVALID);
        }
        char column = s.charAt(0);
        char rank = s.charAt(1);
        if (column < 'A' || column > 'H' || rank < '1' || rank > '8') {
            throw new ChessException(INVALID);
        }
        return new ChessPosition(column, rank - '0');
    }
}
