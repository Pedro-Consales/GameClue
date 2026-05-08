package model;

class Comodo extends Casa {

    private String nome;

    public Comodo(int id, String nome) {
        if(nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Nome do cômodo inválido."
            );
        }
        this.nome = nome; 
        setId(id);
    }
    public String getNome() {
        return nome;
    }
  
    @Override
    public String toString() {
        return "Comodo: " + nome;
    }
}
