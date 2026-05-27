package model;
import java.util.ArrayList;
import java.util.List;

class Jogador{

    private int id;
    private String nome;
    private int posicao;
    private List<Carta> cartas;

    //Inicializador
    {
        cartas = new ArrayList<>();
    }

    //FUNÇÕES AUXILIARES

    //Add Cartas
    public void adicionarCarta(Carta carta) {
        cartas.add(carta);
    }
    
    public void removerCarta(Carta carta) {
        cartas.remove(carta);
    
    }
    //Quantidade de Cartas
    public int quantidadeCartas() {
        return cartas.size();
    }
    
    //Mover Posição
    public void mover(int novaPosicao) {
        this.posicao = novaPosicao;
    }

    // GETTERS

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getPosicao() {
        return posicao;
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}