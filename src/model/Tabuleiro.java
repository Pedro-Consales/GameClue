package model;

import java.util.HashMap;

class Tabuleiro {

    private HashMap<Integer, Casa> casas;

    // Inicializador
    {
        casas = new HashMap<>();
    }

    // FUNÇÕES AUXILIARES

    public void adicionarCasa(Casa casa) {
        casas.put(casa.getId(), casa);
    }

    public Casa getCasa(int id) {
        return casas.get(id);
    }

    public void conectarCasas(int id1, int id2) {

        Casa casa1 = casas.get(id1);
        Casa casa2 = casas.get(id2);

        casa1.adicionarVizinho(casa2);
        casa2.adicionarVizinho(casa1);
    }

    // GETTERS

    public HashMap<Integer, Casa> getCasas() {
        return casas;
    }
}