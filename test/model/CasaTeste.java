// CasaTest.java

package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CasaTeste {

    private Casa casa1;
    private Casa casa2;

    @Before
    public void setUp() {

        casa1 = new Casa();
        casa2 = new Casa();

        casa1.setId(1);
        casa2.setId(2);
    }

    @Test
    public void deveDefinirId() {

        assertEquals(1, casa1.getId());
    }

    @Test
    public void deveAdicionarVizinho() {

        casa1.adicionarVizinho(casa2);

        assertEquals(1, casa1.getVizinhos().size());
    }

    @Test
    public void deveConterVizinhoCorreto() {

        casa1.adicionarVizinho(casa2);

        assertEquals(casa2, casa1.getVizinhos().get(0));
    }
}