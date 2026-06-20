package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TabuleiroTeste {

    private Tabuleiro tabuleiro;

    @Before
    public void setUp() {
        tabuleiro = new Tabuleiro();

        Casa[][] matriz = new Casa[Tabuleiro.LINHAS][Tabuleiro.COLUNAS];
        for (int lin = 0; lin < Tabuleiro.LINHAS; lin++) {
            for (int col = 0; col < Tabuleiro.COLUNAS; col++) {
                int id = lin * Tabuleiro.COLUNAS + col;
                matriz[lin][col] = new Casa(id, lin, col, TipoCasa.CAMINHO);
            }
        }
        tabuleiro.setMatriz(matriz);
    }

    @Test
    public void deveBuscarCasaPorId() {
        Casa casa = tabuleiro.getCasa(27); // lin 1, col 3

        assertEquals(27, casa.getId());
        assertEquals(1, casa.getLinha());
        assertEquals(3, casa.getColuna());
    }

    @Test
    public void deveBuscarCasaPorLinhaColuna() {
        Casa casa = tabuleiro.getCasa(2, 5);

        assertEquals(2, casa.getLinha());
        assertEquals(5, casa.getColuna());
    }

    @Test
    public void deveRetornarNuloForaDaGrade() {
        assertNull(tabuleiro.getCasa(-1, 0));
        assertNull(tabuleiro.getCasa(0, Tabuleiro.COLUNAS));
    }

    @Test
    public void deveConverterLinhaColunaParaId() {
        assertEquals(27, Tabuleiro.toId(1, 3));
        assertEquals(1, Tabuleiro.toLinha(27));
        assertEquals(3, Tabuleiro.toColuna(27));
    }
}
