package model;


public class TabuleiroBuilder {

    // =========================================
    // LEGENDA
    // =========================================
    // 0  = parede
    // 1  = caminho
    // 2-10 = cômodos
    // 11 = centro
    // 12 = porta
    // =========================================

    private static final int[][] GRADE = {

        {2,2,2,2,2,1,1,3,3,3,3,3,3,3,3,1,1,4,4,4,4,4,4,0},
        {2,2,2,2,2,1,1,3,3,3,3,3,3,3,3,1,1,4,4,4,4,4,4,0},
        {2,2,2,2,2,1,1,3,3,3,3,3,3,3,3,1,0,4,4,4,4,4,4,0},
        {2,2,2,2,2,1,1,3,3,3,3,3,3,3,3,1,0,4,4,4,4,4,4,0},
        {2,2,2,2,2,1,1,3,3,3,3,3,3,3,3,1,1,4,4,4,4,4,4,1},
        {2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {5,5,5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,6,6,6,6,6,6,0},
        {5,5,5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,6,6,6,6,6,6,0},
        {5,5,5,5,5,5,1,1,1,1,1,1,1,1,1,1,1,6,6,6,6,6,6,0},
        {5,5,5,5,5,5,1,1,1,1,0,0,1,1,1,1,1,6,6,6,6,6,6,0},
        {5,5,5,5,5,5,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1},
        {5,5,5,5,5,5,1,1,1,1,0,0,1,1,1,1,1,7,7,7,7,7,7,0},
        {1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,7,7,7,7,7,7,0},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,7,7,7,7,7,0},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,7,7,7,7,7,7,0},
        {8,8,8,8,8,8,1,1,1,1,1,1,1,1,1,1,1,7,7,7,7,7,7,1},
        {8,8,8,8,8,8,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {8,8,8,8,8,8,1,1,1,1,9,9,9,9,1,1,1,10,10,10,10,10,10,0},
        {8,8,8,8,8,8,1,1,1,1,9,9,9,9,1,1,1,10,10,10,10,10,10,0},
        {8,8,8,8,8,8,1,1,1,1,9,9,9,9,1,1,1,10,10,10,10,10,10,0},
        {8,8,8,8,8,8,1,1,1,1,9,9,9,9,1,1,1,10,10,10,10,10,10,0},
        {0,1,1,1,1,1,1,1,1,1,9,9,9,9,1,1,1,10,10,10,10,10,10,0},
        {0,0,0,0,0,1,1,1,1,1,9,9,9,9,1,1,1,1,1,1,1,1,0,0},
        {0,0,0,0,0,0,1,1,1,1,9,9,9,9,1,1,0,0,0,0,0,0,0,0}
    };

    public static void popular(Tabuleiro tabuleiro) {

        int linhas = GRADE.length;
        int colunas = GRADE[0].length;

        Casa[][] matriz = new Casa[linhas][colunas];


        // =========================================
        // PASSO 1 — CRIAR TODAS AS CASAS
        // =========================================

        for (int lin = 0; lin < linhas; lin++) {

            for (int col = 0; col < colunas; col++) {

                int valor = GRADE[lin][col];

                TipoCasa tipo = converterTipo(valor);

                Casa casa = new Casa(lin * colunas + col, lin, col, tipo);

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

                // parede não conecta
                if (atual.isParede()) {
                    continue;
                }

                for (int i = 0; i < 4; i++) {

                    int novaLin = lin + dlin[i];
                    int novaCol = col + dcol[i];

                    // fora do tabuleiro
                    if (
                        novaLin < 0 ||
                        novaLin >= linhas ||
                        novaCol < 0 ||
                        novaCol >= colunas
                    ) {
                        continue;
                    }

                    Casa vizinho = matriz[novaLin][novaCol];

                    // não conecta parede
                    if (vizinho.isParede()) {
                        continue;
                    }

                    atual.adicionarVizinho(vizinho);
                }
            }
        }

        // =========================================
        // PASSO 3 — SALVAR MATRIZ NO TABULEIRO
        // =========================================

        tabuleiro.setMatriz(matriz);
    }

    // =========================================
    // CONVERSOR DE TIPO
    // =========================================

    private static TipoCasa converterTipo(int valor) {

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