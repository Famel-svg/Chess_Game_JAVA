package br.com.famel.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ChessMatchTest {

    @Test
    void estadoInicialTem22PecasEVezDasBrancas() {
        ChessMatch match = new ChessMatch();
        int pecas = 0;
        for (ChessPiece[] linha : match.getPieces()) {
            for (ChessPiece p : linha) {
                if (p != null) pecas++;
            }
        }
        // setup atual: torres, reis e peões; cavalos, bispos e damas não são criados
        assertEquals(22, pecas);
        assertEquals(1, match.getTurn());
        assertEquals(Color.WHITE, match.getCurrentPlayer());
        assertFalse(match.getCheck());
    }

    @Test
    void peaoDaFrenteAndaDuasCasasNoPrimeiroLance() {
        ChessMatch match = new ChessMatch();
        ChessPiece capturada = match.performChessMove(new ChessPosition('E', 2), new ChessPosition('E', 4));

        assertNull(capturada);
        assertEquals(2, match.getTurn());
        assertEquals(Color.BLACK, match.getCurrentPlayer());
    }

    @Test
    void peaoNoPrimeiroLanceTemDoisMovimentosPossiveis() {
        ChessMatch match = new ChessMatch();
        boolean[][] possiveis = match.PossibleMove(new ChessPosition('E', 2));

        int total = 0;
        for (boolean[] linha : possiveis) {
            for (boolean b : linha) {
                if (b) total++;
            }
        }
        assertEquals(2, total);
    }

    @Test
    void lanceDeCasaVaziaEhRejeitado() {
        ChessMatch match = new ChessMatch();
        assertThrows(ChessException.class,
                () -> match.performChessMove(new ChessPosition('E', 4), new ChessPosition('E', 5)));
        assertEquals(1, match.getTurn());
    }

    @Test
    void jogadorNaoMoveDuasVezesSeguidas() {
        ChessMatch match = new ChessMatch();
        match.performChessMove(new ChessPosition('E', 2), new ChessPosition('E', 4));
        assertThrows(ChessException.class,
                () -> match.performChessMove(new ChessPosition('D', 2), new ChessPosition('D', 4)));
    }
}
