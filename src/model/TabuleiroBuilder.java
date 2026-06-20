package model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TabuleiroBuilder {

    // Código da matriz -> nome do cômodo
    private static final Map<Integer, String> NOMES_COMODOS;

    static {

        Map<Integer, String> nomes = new HashMap<>();

        nomes.put(2, "Cozinha");
        nomes.put(3, "Sala de Música");
        nomes.put(4, "Jardim de Inverno");
        nomes.put(5, "Sala de Jantar");
        nomes.put(6, "Salão de Jogos");
        nomes.put(7, "Biblioteca");
        nomes.put(8, "Sala de Estar");
        nomes.put(9, "Entrada");
        nomes.put(10, "Escritório");

        NOMES_COMODOS =
            Collections.unmodifiableMap(nomes);
    }


    // =========================================
    // LEGENDA
    // =========================================
    // 0  = parede
    // 1  = caminho
    // 2-10 = cômodos
    // 11 = centro
    // 12 = porta
    // =========================================

//    private static final int[][] GRADE = { --> Antiga
//
//        {2,2,2,2,2,1,1,3,3,3,3,3,3,3,3,1,1,4,4,4,4,4,4,0},
//        {2,2,2,2,2,1,1,3,3,3,3,3,3,3,3,1,1,4,4,4,4,4,4,0},
//        {2,2,2,2,2,1,1,3,3,3,3,3,3,3,3,1,0,4,4,4,4,4,4,0},
//        {2,2,2,2,2,1,1,3,3,3,3,3,3,3,3,1,0,4,4,4,4,4,4,0},
//        {2,2,2,2,2,1,1,3,3,3,3,3,3,3,3,1,1,4,4,4,4,4,4,1},
//        {2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
//        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
//        {5,5,5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,6,6,6,6,6,6,0},
//        {5,5,5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,6,6,6,6,6,6,0},
//        {5,5,5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,6,6,6,6,6,6,0},
//        {5,5,5,5,5,5,1,1,1,1,0,0,1,1,1,1,1,6,6,6,6,6,6,0},
//        {5,5,5,5,5,5,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1},
//        {5,5,5,5,5,5,1,1,1,1,0,0,1,1,1,1,1,7,7,7,7,7,7,0},
//        {1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,7,7,7,7,7,7,0},
//        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,7,7,7,7,7,0},
//        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,7,7,7,7,7,0},
//        {8,8,8,8,8,8,1,1,1,1,1,1,1,1,1,1,1,7,7,7,7,7,7,1},
//        {8,8,8,8,8,8,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
//        {8,8,8,8,8,8,1,1,1,1,9,9,9,9,1,1,1,10,10,10,10,10,10,0},
//        {8,8,8,8,8,8,1,1,1,1,9,9,9,9,1,1,1,10,10,10,10,10,10,0},
//        {8,8,8,8,8,8,1,1,1,1,9,9,9,9,1,1,1,10,10,10,10,10,10,0},
//        {8,8,8,8,8,8,1,1,1,1,9,9,9,9,1,1,1,10,10,10,10,10,10,0},
//        {0,1,1,1,1,1,1,1,1,1,9,9,9,9,1,1,1,10,10,10,10,10,10,0},
//        {0,0,0,0,0,1,1,1,1,1,9,9,9,9,1,1,1,1,1,1,1,1,0,0},
//        {0,0,0,0,0,0,1,1,1,1,9,9,9,9,1,1,0,0,0,0,0,0,0,0}
//    };
	
	public static final int[][] GRADE = { //O final restringe que a matriz seja mudada ao longo do código
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{2, 2, 2, 2, 2, 2, 0, 1, 1, 1, 3, 3, 3, 3, 1, 1, 1, 0, 4, 4, 4, 4, 4, 4},
			{2, 2, 2, 2, 2, 2, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 4, 4, 4, 4, 4, 4},
			{2, 2, 2, 2, 2, 2, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 4, 4, 4, 4, 4, 4},
			{2, 2, 2, 2, 2, 2, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 4, 4, 4, 4, 4, 4},
			{2, 2, 2, 2, 2, 2, 1, 1, 12, 3, 3, 3, 3, 3, 3, 12, 1, 1, 1, 12, 4, 4, 4, 0},
			{2, 2, 2, 2, 12, 2, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 3, 12, 3, 3, 3, 3, 12, 3, 1, 1, 1, 1, 1, 1, 1, 0},
			{0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 6, 6, 6, 6, 6},
			{5, 5, 5, 5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 12, 6, 6, 6, 6, 6},
			{5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 12, 11, 11, 11, 11, 1, 1, 1, 6, 6, 6, 6, 6, 6},
			{5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 11, 11, 11, 11, 11, 1, 1, 1, 6, 6, 6, 6, 6, 6},
			{5, 5, 5, 5, 5, 5, 5, 12, 1, 1, 11, 11, 11, 11, 11, 1, 1, 1, 6, 6, 6, 6, 12, 6},
			{5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 11, 11, 11, 11, 12, 1, 1, 1, 1, 1, 1, 1, 1, 0},
			{5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 11, 11, 11, 11, 11, 1, 1, 1, 7, 7, 12, 7, 7, 7},
			{5, 5, 5, 5, 5, 5, 12, 5, 1, 1, 11, 11, 11, 11, 11, 1, 1, 7, 7, 7, 7, 7, 7, 7},
			{0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 12, 11, 11, 11, 11, 1, 1, 12, 7, 7, 7, 7, 7, 7},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7, 7, 7, 7, 7, 7, 7},
			{0, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 12, 12, 9, 9, 1, 1, 1, 7, 7, 7, 7, 7, 7},
			{8, 8, 8, 8, 8, 8, 12, 1, 1, 9, 9, 9, 9, 9, 9, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{8, 8, 8, 8, 8, 8, 8, 1, 1, 9, 9, 9, 9, 9, 12, 1, 1, 1, 1, 1, 1, 1, 1, 0},
			{8, 8, 8, 8, 8, 8, 8, 1, 1, 9, 9, 9, 9, 9, 9, 1, 1, 12, 10, 10, 10, 10, 10, 10},
			{8, 8, 8, 8, 8, 8, 8, 1, 1, 9, 9, 9, 9, 9, 9, 1, 1, 10, 10, 10, 10, 10, 10, 10},
			{8, 8, 8, 8, 8, 8, 8, 1, 1, 9, 9, 9, 9, 9, 9, 1, 1, 10, 10, 10, 10, 10, 10, 10},
			{8, 8, 8, 8, 8, 8, 0, 1, 0, 9, 9, 9, 9, 9, 9, 0, 1, 0, 10, 10, 10, 10, 10, 10}, 
	};

    public static void popular(Tabuleiro tabuleiro) {

        int linhas = GRADE.length;
        int colunas = GRADE[0].length;

        Casa[][] matriz =
            new Casa[linhas][colunas];

        // =========================================
        // PASSO 1 — CRIAR TODAS AS CASAS
        // =========================================

        for (int lin = 0; lin < linhas; lin++) {

            for (int col = 0; col < colunas; col++) {

                int valor = GRADE[lin][col];

                TipoCasa tipo =
                    converterTipo(valor);

                String nomeComodo =
                    NOMES_COMODOS.get(valor);

                Casa casa = new Casa(
                    lin * colunas + col,
                    lin,
                    col,
                    tipo,
                    nomeComodo
                );

                matriz[lin][col] = casa;
            }
        }

        // =========================================
        // PASSO 2 — CONECTAR VIZINHOS
        // =========================================

        int[] dlin = {-1, 1, 0, 0};
        int[] dcol = {0, 0, -1, 1};

        for (int lin = 0; lin < linhas; lin++) {

            for (int col = 0; col < colunas; col++) {

                Casa atual = matriz[lin][col];

                if (atual.isParede()) {
                    continue;
                }

                for (int i = 0; i < 4; i++) {

                    int novaLin =
                        lin + dlin[i];

                    int novaCol =
                        col + dcol[i];

                    if (novaLin < 0
                            || novaLin >= linhas
                            || novaCol < 0
                            || novaCol >= colunas) {

                        continue;
                    }

                    Casa vizinho =
                        matriz[novaLin][novaCol];

                    if (vizinho.isParede()) {
                        continue;
                    }

                    atual.adicionarVizinho(vizinho);
                }
            }
        }

        tabuleiro.setMatriz(matriz);
    }

    public static String getNomeComodoPorCodigo(
            int codigo) {

        return NOMES_COMODOS.get(codigo);
    }

    private static TipoCasa converterTipo(
            int valor) {

        if (valor == 0) {
            return TipoCasa.PAREDE;
        }

        if (valor == 1) {
            return TipoCasa.CAMINHO;
        }

        if (valor == 11) {
            return TipoCasa.CENTRO;
        }

        if (valor == 12) {
            return TipoCasa.PORTA;
        }

        return TipoCasa.COMODO;
    }
}