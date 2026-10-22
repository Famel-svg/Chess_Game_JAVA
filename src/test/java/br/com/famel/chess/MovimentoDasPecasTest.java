package br.com.famel.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

class MovimentoDasPecasTest {

    private static ChessPosition pos(String s) {
        return new ChessPosition(s.charAt(0), Integer.parseInt(s.substring(1)));
    }

    private static void jogar(ChessMatch match, String... lances) {
        for (String lance : lances) {
            String[] casas = lance.split("-");
            match.performChessMove(pos(casas[0]), pos(casas[1]));
        }
    }

    private static Set<String> destinos(ChessMatch match, String origem) {
        boolean[][] mat = match.PossibleMove(pos(origem));
        Set<String> casas = new TreeSet<>();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j]) {
                    casas.add("" + (char) ('A' + j) + (8 - i));
                }
            }
        }
        return casas;
    }

    private static String peca(ChessMatch match, String casa) {
        ChessPosition p = pos(casa);
        ChessPiece piece = match.getPieces()[8 - p.getRow()][p.getColumn() - 'A'];
        return piece == null ? null : piece.getColor() + " " + piece;
    }

    @Test
    void setupInicialTemPecasNasCasasPadrao() {
        ChessMatch match = new ChessMatch();
        String[] fila = {"R", "N", "B", "Q", "K", "B", "N", "R"};
        for (int c = 0; c < 8; c++) {
            String coluna = String.valueOf((char) ('A' + c));
            assertEquals("WHITE " + fila[c], peca(match, coluna + "1"));
            assertEquals("WHITE P", peca(match, coluna + "2"));
            assertEquals("BLACK P", peca(match, coluna + "7"));
            assertEquals("BLACK " + fila[c], peca(match, coluna + "8"));
        }
    }

    @Test
    void cavaloSaltaEmLPorCimaDosPeoes() {
        ChessMatch match = new ChessMatch();
        // D2 é peão próprio: o L para lá não vale; A3 e C3 sim, mesmo com peões no caminho
        assertEquals(Set.of("A3", "C3"), destinos(match, "B1"));
        assertEquals(Set.of("F3", "H3"), destinos(match, "G1"));
    }

    @Test
    void cavaloNoCentroTemOitoDestinosEmL() {
        ChessMatch match = new ChessMatch();
        jogar(match, "G1-F3", "A7-A6", "F3-E5", "A6-A5", "E5-D3", "A5-A4", "D3-F4", "H7-H6", "F4-D5", "H6-H5");
        // cavalo em D5: oito saltos em L, C7 e E7 são capturas de peões pretos
        assertEquals(Set.of("B4", "B6", "C3", "C7", "E3", "E7", "F4", "F6"), destinos(match, "D5"));
    }

    @Test
    void bispoBloqueadoPelosPropriosPeoesNaoTemLance() {
        ChessMatch match = new ChessMatch();
        assertEquals("WHITE B", peca(match, "C1"));
        ChessException e = assertThrows(ChessException.class, () -> match.PossibleMove(pos("C1")));
        assertEquals("There is no possible move in this position", e.getMessage());
    }

    @Test
    void bispoAndaNaDiagonalAteAPrimeiraPeca() {
        ChessMatch match = new ChessMatch();
        jogar(match, "D2-D4", "A7-A6");
        // B2 tem peão branco: a diagonal C1-A3 fica bloqueada; a outra vai até H6
        assertEquals(Set.of("D2", "E3", "F4", "G5", "H6"), destinos(match, "C1"));
    }

    @Test
    void bispoCapturaPecaAdversariaMasNaoPassaDela() {
        ChessMatch match = new ChessMatch();
        jogar(match, "E2-E4", "B7-B5");
        // B5 tem peão preto: captura permitida, A6 (atrás dele) não
        assertEquals(Set.of("E2", "D3", "C4", "B5"), destinos(match, "F1"));
    }

    @Test
    void rainhaCombinaLinhaEDiagonal() {
        ChessMatch match = new ChessMatch();
        jogar(match, "E2-E4", "E7-E5");
        assertEquals(Set.of("E2", "F3", "G4", "H5"), destinos(match, "D1"));

        jogar(match, "D1-H5", "A7-A6");
        // H5: coluna H até H7 (captura), fileira 5 até E5 (captura), diagonais até F7 (captura) e E2
        assertEquals(Set.of("H3", "H4", "H6", "H7", "G5", "F5", "E5", "G6", "F7", "G4", "F3", "E2", "D1"),
                destinos(match, "H5"));
    }
}
