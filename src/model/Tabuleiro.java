package model;

import java.util.HashMap;

class Tabuleiro {

    static final int LINHAS = 25;
    static final int COLUNAS = 24;

    private Casa[][] grade;
    
    // HashMap separado para os cômodos
    private HashMap<Integer, Casa> comodos;

    {
        grade = new Casa[LINHAS][COLUNAS];
        comodos = new HashMap<>();
    }

    public void adicionarCasa(Casa casa) {
        
        // Se for cômodo (ID >= 1000), guarda no HashMap
        if (casa.getId() >= 1000) {
            comodos.put(casa.getId(), casa);
            return;
        }
        
        // Casa normal — guarda na grade
        int lin = casa.getId() / COLUNAS;
        int col = casa.getId() % COLUNAS;
        grade[lin][col] = casa;
    }

    public Casa getCasa(int id) {
        
        // Se for cômodo, busca no HashMap
        if (id >= 1000) {
            return comodos.get(id);
        }
        
        // Casa normal — busca na grade
        int lin = id / COLUNAS;
        int col = id % COLUNAS;
        if (lin < 0 || lin >= LINHAS || col < 0 || col >= COLUNAS) {
            return null;
        }
        return grade[lin][col];
    }

    public Casa getCasa(int lin, int col) {
        if (lin < 0 || lin >= LINHAS || col < 0 || col >= COLUNAS) {
            return null;
        }
        return grade[lin][col];
    }

    public void conectarCasas(int id1, int id2) {
        Casa casa1 = getCasa(id1);
        Casa casa2 = getCasa(id2);

        if (casa1 == null || casa2 == null) {
            throw new IllegalArgumentException(
                String.format("Casa de id %d ou %d não existe na grade.", id1, id2)
            );
        }

        casa1.adicionarVizinho(casa2);
        casa2.adicionarVizinho(casa1);
    }

    public Casa[][] getGrade() { return grade; }

    public static int toId(int lin, int col) { return lin * COLUNAS + col; }
    public static int toLinha(int id) { return id / COLUNAS; }
    public static int toColuna(int id) { return id % COLUNAS; }
    
    
    
    public void setMatriz(Casa[][] matriz) {
    	this.grade = matriz;
    }
}