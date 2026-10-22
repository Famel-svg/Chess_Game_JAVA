package br.com.famel.chess;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private static String peca(ChessMatch match, String casa) {
        ChessPosition p = pos(casa);
        ChessPiece piece = match.getPieces()[8 - p.getRow()][p.getColumn() - 'A'];
        return piece == null ? null : piece.getColor() + " " + piece;
    }

    private static void assertLanceRejeitadoPorXeque(ChessMatch match, String origem, String destino) {
        int turno = match.getTurn();
        Color jogador = match.getCurrentPlayer();
        String pecaOrigem = peca(match, origem);
        String pecaDestino = peca(match, destino);

        ChessException e = assertThrows(ChessException.class,
                () -> match.performChessMove(pos(origem), pos(destino)));
        assertEquals("You cant put yourself in check", e.getMessage());

        // o lance foi desfeito: tabuleiro, turno e vez continuam iguais
        assertEquals(pecaOrigem, peca(match, origem));
        assertEquals(pecaDestino, peca(match, destino));
        assertEquals(turno, match.getTurn());
        assertEquals(jogador, match.getCurrentPlayer());
    }

    @Test
    void xequeDoBispoEhDetectadoESemMate() {
        ChessMatch match = new ChessMatch();
        jogar(match, "E2-E4", "D7-D6", "F1-B5");
        assertTrue(match.getCheck());
        assertFalse(match.getCheckMate());
        assertEquals(Color.BLACK, match.getCurrentPlayer());
    }

    @Test
    void lanceQueNaoTiraOReiDoXequeEhRejeitado() {
        ChessMatch match = new ChessMatch();
        jogar(match, "E2-E4", "D7-D6", "F1-B5");
        assertLanceRejeitadoPorXeque(match, "A7", "A6");

        // bloquear o xeque é aceito
        jogar(match, "C7-C6");
        assertFalse(match.getCheck());
    }

    @Test
    void pecaCravadaNaoPodeExporORei() {
        ChessMatch match = new ChessMatch();
        // c6 fica cravado pelo bispo de b5 contra o rei em e8
        jogar(match, "E2-E4", "D7-D6", "F1-B5", "C7-C6", "A2-A3");
        assertLanceRejeitadoPorXeque(match, "C6", "C5");
    }

    @Test
    void reiNaoPodeEntrarEmCasaAtacada() {
        ChessMatch match = new ChessMatch();
        // bispo em c4 ataca f7
        jogar(match, "E2-E4", "F7-F6", "F1-C4");
        assertLanceRejeitadoPorXeque(match, "E8", "F7");
    }

    @Test
    void mateDoPastorEncerraAPartida() {
        ChessMatch match = new ChessMatch();
        jogar(match, "E2-E4", "E7-E5", "F1-C4", "B8-C6", "D1-H5", "G8-F6");
        assertFalse(match.getCheckMate());

        ChessPiece capturada = match.performChessMove(pos("H5"), pos("F7"));

        assertEquals("BLACK P", capturada.getColor() + " " + capturada);
        assertTrue(match.getCheck());
        assertTrue(match.getCheckMate());
        // no mate a vez não passa: o jogador atual é o vencedor
        assertEquals(Color.WHITE, match.getCurrentPlayer());
        assertEquals(7, match.getTurn());
    }

    @Test
    void xequeComDefesaNaoEhMate() {
        ChessMatch match = new ChessMatch();
        // mesmo ataque em f7, mas sem o bispo em c4: o rei captura a dama
        jogar(match, "E2-E4", "E7-E5", "D1-H5", "B8-C6", "H5-F7");
        assertTrue(match.getCheck());
        assertFalse(match.getCheckMate());

        ChessPiece capturada = match.performChessMove(pos("E8"), pos("F7"));
        assertEquals("WHITE Q", capturada.getColor() + " " + capturada);
        assertFalse(match.getCheck());
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
