package model;

import java.io.*;
import java.util.*;

public class SalvarJogo {

    private static final String ARQUIVO = "savegame.dat";

    // =========================================
    // SALVAR
    // =========================================
    public static void salvar(ClueModel model, 
                               List<String> nomesJogadores,
                               Map<String, int[]> posicoesVisuais) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {

            // Jogadores e posições no model
            Map<String, Integer> posicoes = model.getPosicoesJogadores();
            pw.println("JOGADORES=" + String.join(",", nomesJogadores));

            for (String nome : nomesJogadores) {
                pw.println("POS_MODEL_" + nome + "=" + posicoes.get(nome));
                int[] vis = posicoesVisuais.get(nome);
                pw.println("POS_VISUAL_" + nome + "=" + vis[0] + "," + vis[1]);
            }

            // Jogador atual
            pw.println("JOGADOR_ATUAL=" + model.getNomeJogadorAtual());

            // Cartas de cada jogador no bloco de notas
            for (String nome : nomesJogadores) {
                List<String> marcadas = model.getCartasMarcadasJogador(nome);
                pw.println("BLOCO_" + nome + "=" + String.join("|", marcadas));
            }

            for (String nome : nomesJogadores) {
                List<String> mao = model.getNomesMaoJogador(nome);
                pw.println("MAO_" + nome + "=" + String.join("|", mao));
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    // =========================================
    // VERIFICAR SE EXISTE SAVE
    // =========================================
    public static boolean existeSave() {
        return new File(ARQUIVO).exists();
    }

    // =========================================
    // CARREGAR
    // =========================================
    public static Map<String, String> carregar() {
        Map<String, String> dados = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                int idx = linha.indexOf('=');
                if (idx > 0) {
                    dados.put(linha.substring(0, idx), linha.substring(idx + 1));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar: " + e.getMessage());
        }
        return dados;
    }
}