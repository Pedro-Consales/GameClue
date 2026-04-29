package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class CartaTest {

    // Teste 1 — Verifica se o nome é retornado corretamente
    @Test
    public void testGetNome() {
        // Arrange — monta o cenário
        Carta carta = new Carta("Faca", "Arma");
        
        // Act — executa o que quer testar
        String resultado = carta.getNome();
        
        // Assert — verifica se está certo
        assertEquals("Faca", resultado);
    }

    //  Teste 2 — Verifica se o tipo é retornado corretamente
    @Test
    public void testGetTipo() {
        Carta carta = new Carta("Faca", "Arma");
        
        String resultado = carta.getTipo();
        
        assertEquals("Arma", resultado);
    }
    
    //Teste 3 - Verifica se de fato da pra criar de forma consistente
    @Test
    public void criaUmaCartaCorret() {
    	
    	String nomeCartaParam = "Coronel Mustard";
    	String tipoCartaParam = "Suspeito";
    	
    	Carta carta = new Carta(nomeCartaParam, tipoCartaParam);
    	
		String nomeCarta = carta.getNome();
		String tipoCarta = carta.getTipo();
		        
		assertEquals(nomeCartaParam, nomeCarta);
		assertEquals(tipoCartaParam, tipoCarta);
    }

    // Teste 3 — Verifica se lança exceção com tipo inválido
    @Test(expected = IllegalArgumentException.class)
    public void testTipoInvalido() {
        // Não precisa de Assert — o (expected) já faz isso
        // O teste passa SE a exceção for lançada
        new Carta("Faca", "TipoQueNaoExiste");
    }

    // Teste 4 — Verifica se lança exceção com nome incompatível
    @Test(expected = IllegalArgumentException.class)
    public void testNomeIncompativelComTipo() {
        // Faca é Arma, não Suspeito — deve lançar exceção
        new Carta("Faca", "Suspeito");
    }

    // Teste 5 — Verifica se lança exceção com nome inexistente
    @Test(expected = IllegalArgumentException.class)
    public void testNomeInexistente() {
        new Carta("NomeQueNaoExiste", "Arma");
    }
    
}