package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComodoTeste {
    private Comodo comodo;

    @Before
    public void setUp() {
        comodo = new Comodo(1, "Cozinha");
    }

    @Test
    public void deveDefinirId() {
        assertEquals(1, comodo.getId());
    }

    @Test
    public void deveDefinirNome() {
        assertEquals("Cozinha", comodo.getNome());
    }

    @Test
    public void deveSerUmaCasa() {
        assertTrue(comodo instanceof Casa);
    }

    @Test
    public void deveAdicionarVizinho() {
        Casa casaVizinha = new Casa();
        casaVizinha.setId(2);
        comodo.adicionarVizinho(casaVizinha);
        assertEquals(1, comodo.getVizinhos().size());
    }

    @Test
    public void deveConterVizinhoCorreto() {
        Casa casaVizinha = new Casa();
        casaVizinha.setId(2);
        comodo.adicionarVizinho(casaVizinha);
        assertEquals(casaVizinha, comodo.getVizinhos().get(0));
    }

    @Test
    public void deveRetornarToStringCorretamente() {
        assertEquals("Comodo: Cozinha", comodo.toString());
    }
}
