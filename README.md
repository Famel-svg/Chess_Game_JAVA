# Chess_Game_JAVA

Jogo de xadrez para dois jogadores no terminal, em Java puro (Maven, pacote `br.com.famel`).
O tabuleiro é desenhado com cores ANSI; cada lance é digitado como casa de origem e casa de
destino (`e2`, depois `e4`).

## Requisitos

- JDK 21
- Maven 3.9+

## Compilar e jogar

```bash
mvn -B -q compile
java -cp target/classes br.com.famel.Main
```

Peças: `R` torre, `N` cavalo, `B` bispo, `Q` dama, `K` rei, `P` peão. Brancas em branco,
pretas em amarelo. Ao escolher a origem, as casas de destino possíveis ficam destacadas.

## Testes

```bash
mvn -B test      # só os testes
mvn -B verify    # o mesmo que o CI roda
```

## Regras implementadas

- Setup inicial completo (32 peças)
- Movimento de rei, dama, torre, bispo, cavalo e peão (incluindo avanço duplo no primeiro lance
  e captura na diagonal)
- Alternância de turnos; só o dono da peça pode movê-la
- Xeque: lance que deixa o próprio rei em xeque é rejeitado e desfeito (inclui peça cravada e rei
  entrando em casa atacada)
- Xeque-mate encerra a partida

## O que ainda falta

- Roque
- En passant
- Promoção de peão
- Afogamento (stalemate): sem lances legais e sem xeque, a partida não termina
- Empates por regra de 50 lances, repetição tripla e material insuficiente
- Desistência / oferta de empate
- O destaque de casas possíveis mostra lances pseudo-legais; um lance que deixaria o rei em xeque
  só é recusado ao ser jogado
