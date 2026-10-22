package br.com.famel.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ChessPositionParserTest {

    @ParameterizedTest
    @ValueSource(strings = {"e2", "E2", " e2 ", "e2\t"})
    void aceitaCasaValidaEmQualquerCaixaEComEspacos(String entrada) {
        ChessPosition p = ChessPositionParser.parse(entrada);
        assertEquals('E', p.getColumn());
        assertEquals(2, p.getRow());
    }

    @Test
    void aceitaOsCantosDoTabuleiro() {
        assertEquals("A1", ChessPositionParser.parse("a1").toString());
        assertEquals("H8", ChessPositionParser.parse("h8").toString());
        assertEquals("A8", ChessPositionParser.parse("A8").toString());
        assertEquals("H1", ChessPositionParser.parse("H1").toString());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   ", "e", "2", "e22", "e10", "i1", "e9", "e0", "ee", "22", "e2x", "-1", "e-1", "é2"})
    void rejeitaEntradaInvalidaComChessException(String entrada) {
        ChessException e = assertThrows(ChessException.class, () -> ChessPositionParser.parse(entrada));
        assertEquals("Invalid position, position must be between A1 and H8", e.getMessage());
    }
}
