// JogadorTest.java

package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JogadorTest {

    private Jogador jogador;

    @Before
    public void setUp() {
        jogador = new Jogador();
    }

    @Test
    public void deveDefinirId() {
        jogador.setId(1);

        assertEquals(1, jogador.getId());
    }

    @Test
    public void deveDefinirNome() {
        jogador.setNome("Bruno");

        assertEquals("Bruno", jogador.getNome());
    }

    @Test
    public void deveMoverJogador() {
        jogador.mover(5);

        assertEquals(5, jogador.getPosicao());
    }

    @Test
    public void deveAdicionarCarta() {
        Carta carta = new Carta("Corda", "Arma");

        jogador.adicionarCarta(carta);

        assertEquals(1, jogador.quantidadeCartas());
    }

    @Test
    public void deveRemoverCarta() {
        Carta carta = new Carta("Corda", "Arma");

        jogador.adicionarCarta(carta);
        jogador.removerCarta(carta);

        assertEquals(0, jogador.quantidadeCartas());
    }
}