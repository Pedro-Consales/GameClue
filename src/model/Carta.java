package model; //Avisa que pertence ao pacote model
import java.util.Arrays;
import java.util.List;

class Carta {
	
	
	
	//Atributos:
	private String nome;
	private String tipo;
	
	
	//Listas de Nomes possíveis para carta
	private static final List<String> SUSPEITOS = Arrays.asList(
	    "Coronel Mustard", "Srta. Scarlet", "Professor Plum",
	    "Reverendo Green", "Sra. White", "Sra. Peacock"
	);

	private static final List<String> ARMAS = Arrays.asList(
	    "Corda", "Cano de Chumbo", "Faca",
	    "Chave Inglesa", "Castiçal", "Revólver"
	);

	private static final List<String> COMODOS = Arrays.asList(
	    "Cozinha", "Salão de Baile", "Conservatório",
	    "Sala de Bilhar", "Biblioteca", "Estudo",
	    "Salão", "Sala de Música", "Sala de Jantar"
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
	}
	
	
	//Métodos
	public String getNome() { return this.nome; }
	public String getTipo() { return this.tipo; }
	
	

}
