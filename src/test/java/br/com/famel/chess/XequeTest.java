package br.com.famel.chess;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class XequeTest {

    private static ChessPosition pos(String s) {
        return new ChessPosition(s.charAt(0), Integer.parseInt(s.substring(1)));
    }

    private static void jogar(ChessMatch match, String... lances) {
        for (String lance : lances) {
            String[] casas = lance.split("-");
            match.performChessMove(pos(casas[0]), pos(casas[1]));
        }
    }

    @Test
    void lancesSimuladosNaoTiramOAvancoDuploDoPeao() {
        ChessMatch match = new ChessMatch();
        // 2.Qh5+ deixa as pretas em xeque: a verificação de mate simula (e desfaz) a7-a6 e a7-a5
        jogar(match, "E2-E4", "F7-F5", "D1-H5");
        assertTrue(match.getCheck());
        jogar(match, "G7-G6", "A2-A3");

        // a7 nunca se moveu de verdade, então o avanço de duas casas continua válido
        assertDoesNotThrow(() -> match.performChessMove(pos("A7"), pos("A5")));
    }
}
