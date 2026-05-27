package model;

class Comodo extends Casa {

    private String nome;

    public Comodo(int id, int lin, int col, String nome) {
        super(id, lin, col, TipoCasa.COMODO);
        if(nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cômodo inválido.");
        }
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }
    
  
    @Override
    public String toString() {
        return "Comodo: " + nome;
    }
}
