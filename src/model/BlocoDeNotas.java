package model;

import java.util.ArrayList;

public class BlocoDeNotas {

    // Cartas que o próprio jogador recebeu no início da partida
    private ArrayList<Carta> cartasProprias;

    // Cartas reveladas por outros jogadores durante palpites
    private ArrayList<Carta> cartasReveladas;

    public BlocoDeNotas() {
        this.cartasProprias  = new ArrayList<>();
        this.cartasReveladas = new ArrayList<>();
    }

    // Adiciona as carta que o jogador possui 
    public void adicionarCartaPropria(Carta carta) {
        if (!cartasProprias.contains(carta)) {
            cartasProprias.add(carta);
        }
    }

    // Adiciona uma carta que foi revelada por outros jogadores
    public void marcarCartaRevelada(Carta carta) {
        if (!cartasReveladas.contains(carta) && !cartasProprias.contains(carta)) {
            cartasReveladas.add(carta);
        }
    }

    public boolean estaMarcada(Carta carta) {
        return cartasProprias.contains(carta) || cartasReveladas.contains(carta);
    }

    public boolean ehCartaPropria(Carta carta) {
        return cartasProprias.contains(carta);
    }

    public boolean ehCartaRevelada(Carta carta) {
        return cartasReveladas.contains(carta);
    }

    public ArrayList<Carta> getCartasProprias() {
        return cartasProprias;
    }

    public ArrayList<Carta> getCartasReveladas() {
        return cartasReveladas;
    }

    // Todas as cartas marcadas (próprias + reveladas)
    public ArrayList<Carta> getTodasCartasMarcadas() {
        ArrayList<Carta> todas = new ArrayList<>();
        todas.addAll(cartasProprias);
        todas.addAll(cartasReveladas);
        return todas;
    }
}
