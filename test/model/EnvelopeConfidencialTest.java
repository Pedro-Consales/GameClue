package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class EnvelopeConfidencialTest {

    // ✅ Teste 1 — Verifica se o suspeito é guardado corretamente
    @Test
    public void testGetSuspeito() {
        Carta suspeito = new Carta("Coronel Mustard", "Suspeito");
        Carta arma = new Carta("Faca", "Arma");
        Carta comodo = new Carta("Cozinha", "Cômodo");

        EnvelopeConfidencial envelope = new EnvelopeConfidencial(suspeito, arma, comodo);

        assertEquals("Coronel Mustard", envelope.getSuspeito().getNome());
    }

    // ✅ Teste 2 — Verifica se a arma é guardada corretamente
    @Test
    public void testGetArma() {
        Carta suspeito = new Carta("Coronel Mustard", "Suspeito");
        Carta arma = new Carta("Faca", "Arma");
        Carta comodo = new Carta("Cozinha", "Cômodo");

        EnvelopeConfidencial envelope = new EnvelopeConfidencial(suspeito, arma, comodo);

        assertEquals("Faca", envelope.getArma().getNome());
    }

    // ✅ Teste 3 — Verifica se o cômodo é guardado corretamente
    @Test
    public void testGetComodo() {
        Carta suspeito = new Carta("Coronel Mustard", "Suspeito");
        Carta arma = new Carta("Faca", "Arma");
        Carta comodo = new Carta("Cozinha", "Cômodo");

        EnvelopeConfidencial envelope = new EnvelopeConfidencial(suspeito, arma, comodo);

        assertEquals("Cozinha", envelope.getComodo().getNome());
    }
}