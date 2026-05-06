package model;

import java.util.ArrayList;
import java.util.List;

class Casa {

    private int id;
    private List<Casa> vizinhos;

    // Inicializador
    {
        vizinhos = new ArrayList<>();
    }

    // FUNÇÕES AUXILIARES

    public void adicionarVizinho(Casa casa) {
        vizinhos.add(casa);
    }

    // GETTERS

    public int getId() {
        return id;
    }

    public List<Casa> getVizinhos() {
        return vizinhos;
    }

    // SETTERS

    public void setId(int id) {
        this.id = id;
    }
}