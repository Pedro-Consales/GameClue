# GameClue

Uma implementação em Java do clássico jogo de tabuleiro **Clue / Cluedo**, com interface gráfica em Swing. Desenvolvido como projeto acadêmico (Eclipse).

## Sobre o jogo

Os jogadores se movem pelo tabuleiro, visitam cômodos, fazem **palpites** e **acusações** para tentar descobrir o suspeito, a arma e o cômodo do crime guardados no **envelope confidencial**. O jogo conta com bloco de notas para anotar pistas, sistema de salvamento de partida e tabuleiro visual completo.

## Estrutura do projeto

```
GameClue/
├── src/
│   ├── model/      # Lógica do jogo (tabuleiro, cartas, jogadores, regras, salvamento)
│   └── view/       # Telas Swing (tela inicial, tabuleiro, palpites, cartas, bloco de notas)
├── test/
│   └── model/      # Testes unitários (JUnit) das classes de model
├── Imagens/         # Assets visuais (armas, cômodos, suspeitos, tabuleiros)
├── savegame.dat     # Arquivo de estado salvo (gerado durante o jogo)
└── leiame.txt       # Documentação original do projeto (em português)
```

### Principais classes

**Model**
- `ClueModel` — classe central (singleton) com o estado e as regras da partida
- `Tabuleiro` / `TabuleiroBuilder` — construção e representação do tabuleiro
- `Jogador`, `Carta`, `Comodo`, `Casa`, `Dado` — entidades do jogo
- `EnvelopeConfidencial` — solução secreta do crime
- `ResultadoPalpite` / `ResultadoAcusacao` — resultado de palpites e acusações
- `SalvarJogo` — leitura/escrita do arquivo `savegame.dat`
- `BlocoDeNotas` — anotações de pistas do jogador

**View**
- `TelaInicial` — tela inicial (Novo Jogo / Continuar)
- `TelaTabuleiro` — tabuleiro principal e movimentação
- `TelaSelecao`, `TelaCartas`, `TelaPalpite`, `TelaBlocoDeNotas`, `PainelSidebar` — demais telas do jogo

## Requisitos

- Java (JDK compatível com projeto Eclipse, com suporte a módulos)
- JUnit 4 (para rodar os testes em `test/`)

## Como executar

1. Importe o projeto no Eclipse (ou configure `src` e `test` como source roots em outra IDE).
2. Execute a classe `view.TelaInicial` (contém o método `main`).
3. Na tela inicial, escolha **Novo Jogo** para começar uma partida ou **Continuar** para carregar o `savegame.dat` existente.

## Salvamento de partida

O estado da partida é salvo em texto simples (UTF-8) no arquivo `savegame.dat`, na raiz do projeto, no formato `CHAVE=VALOR` (uma informação por linha). As chaves incluem lista de jogadores, posições no model e na tela, jogador atual, cartas na mão e cartas marcadas no bloco de notas de cada jogador. A leitura e escrita desse arquivo são feitas pela classe `model.SalvarJogo`. Detalhes completos do formato estão em `leiame.txt`.

## Testes

Os testes unitários (JUnit) das classes de `model` estão em `test/model` e cobrem `Carta`, `Casa`, `ClueModel`, `Comodo`, `Dado`, `EnvelopeConfidencial`, `Jogador` e `Tabuleiro`.

## Autores

- Pedro Consales Margaronis
- Bruno Kubudi Cardeman
- Felipe Vanzin dos Santos Rocha
