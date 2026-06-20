package model; //Avisa que pertence ao pacote model
import java.util.Arrays;
import java.util.List;

class Carta {



	//Atributos:
	private String nome;
	private String tipo;
	private int id;


	//Listas de Nomes possíveis para carta
	static final List<String> SUSPEITOS = Arrays.asList(
	    "Coronel Mustard", "Srta. Scarlet", "Professor Plum",
	    "Reverendo Green", "Sra. White", "Sra. Peacock"
	);

	static final List<String> ARMAS = Arrays.asList(
	    "Corda", "Cano de Chumbo", "Faca",
	    "Chave Inglesa", "Castiçal", "Revólver"
	);

	// Nomes batem com o tabuleiro e com as imagens em Imagens/Comodos
	static final List<String> COMODOS = Arrays.asList(
	    "Cozinha", "Sala de Música", "Salão de Jogos",
	    "Biblioteca", "Sala de Jantar", "Sala de Estar",
	    "Entrada", "Escritório", "Jardim de Inverno"
	);

	private static final List<String> TIPOS = Arrays.asList(
	    "Suspeito", "Arma", "Cômodo"
	);




	//Construtor
	public Carta(String nome, String tipo) {


		if(!TIPOS.contains(tipo)) { //Verifica se o tipo digitado é correto
			throw new IllegalArgumentException(
			    String.format("O tipo '%s' não existe.\nTipos válidos: %s", tipo, TIPOS.toString())
			);
		}

		if(!SUSPEITOS.contains(nome) && !ARMAS.contains(nome) && !COMODOS.contains(nome)) { //Verifica se o nome digitado é correto
			throw new IllegalArgumentException(
			    String.format("O nome %s não existe nas regras do jogo", nome)
			);
		}


		//Verificações se o tipo bate com o nome
		if(tipo.equals("Suspeito") && !SUSPEITOS.contains(nome) ) {

			throw new IllegalArgumentException(
			    String.format("O nome %s não é compatível com o tipo %s", nome, tipo)
			);
		}

		if(tipo.equals("Arma") && !ARMAS.contains(nome) ) {
			throw new IllegalArgumentException(

			    String.format("O nome %s não é compatível com o tipo %s", nome, tipo)
			);
		}

		if(tipo.equals("Cômodo") && !COMODOS.contains(nome) ) {
			throw new IllegalArgumentException(

			    String.format("O nome %s não é compatível com o tipo %s", nome, tipo)
			);
		}


		this.nome = nome;
		this.tipo = tipo;
		this.id = calcularId(nome);
	}


	// Identificador constante (0-20) na ordem canônica suspeitos + armas + cômodos.
	// Permite à View recuperar a imagem em tempo constante.
	private static int calcularId(String nome) {
		int pos = SUSPEITOS.indexOf(nome);
		if (pos >= 0) return pos;
		pos = ARMAS.indexOf(nome);
		if (pos >= 0) return SUSPEITOS.size() + pos;
		pos = COMODOS.indexOf(nome);
		return SUSPEITOS.size() + ARMAS.size() + pos;
	}


	//Métodos
	public String getNome() { return this.nome; }
	public String getTipo() { return this.tipo; }
	public int getId() { return this.id; }


	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Carta)) return false;
		return this.id == ((Carta) obj).id;
	}

	@Override
	public int hashCode() {
		return id;
	}

}
