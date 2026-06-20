package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class BlocoDeNotas {

    // Cartas que o próprio jogador recebeu no início da partida (chave = nome)
    private Map<String, Carta> cartasProprias;

    // Cartas reveladas por outros jogadores durante palpites (chave = nome)
    private Map<String, Carta> cartasReveladas;

    public BlocoDeNotas() {
        this.cartasProprias  = new LinkedHashMap<>();
        this.cartasReveladas = new LinkedHashMap<>();
    }

    // Adiciona as carta que o jogador possui
    public void adicionarCartaPropria(Carta carta) {
        cartasProprias.put(carta.getNome(), carta);
    }

    // Adiciona uma carta que foi revelada por outros jogadores
    public void marcarCartaRevelada(Carta carta) {
        if (!cartasProprias.containsKey(carta.getNome())) {
            cartasReveladas.put(carta.getNome(), carta);
        }
    }

    // Desmarca uma carta revelada
    public void desmarcarCartaRevelada(Carta carta) {
        cartasReveladas.remove(carta.getNome());
    }

    public boolean estaMarcada(Carta carta) {
        return cartasProprias.containsKey(carta.getNome())
            || cartasReveladas.containsKey(carta.getNome());
    }

    public boolean ehCartaPropria(Carta carta) {
        return cartasProprias.containsKey(carta.getNome());
    }

    public boolean ehCartaRevelada(Carta carta) {
        return cartasReveladas.containsKey(carta.getNome());
    }

    public ArrayList<Carta> getCartasProprias() {
        return new ArrayList<>(cartasProprias.values());
    }

    public ArrayList<Carta> getCartasReveladas() {
        return new ArrayList<>(cartasReveladas.values());
    }

    // Todas as cartas marcadas (próprias + reveladas)
    public ArrayList<Carta> getTodasCartasMarcadas() {
        ArrayList<Carta> todas = new ArrayList<>();
        todas.addAll(cartasProprias.values());
        todas.addAll(cartasReveladas.values());
        return todas;
    }
}
