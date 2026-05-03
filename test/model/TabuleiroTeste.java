// TabuleiroTest.java

package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TabuleiroTeste {

    private Tabuleiro tabuleiro;

    @Before
    public void setUp() {
        tabuleiro = new Tabuleiro();
    }

    @Test
    public void deveAdicionarCasa() {

        Casa casa = new Casa();
        casa.setId(1);

        tabuleiro.adicionarCasa(casa);

        assertEquals(casa, tabuleiro.getCasa(1));
    }

    @Test
    public void deveConectarCasas() {

        Casa casa1 = new Casa();
        Casa casa2 = new Casa();

        casa1.setId(1);
        casa2.setId(2);

        tabuleiro.adicionarCasa(casa1);
        tabuleiro.adicionarCasa(casa2);

        tabuleiro.conectarCasas(1, 2);

        assertTrue(casa1.getVizinhos().contains(casa2));
        assertTrue(casa2.getVizinhos().contains(casa1));
    }
}