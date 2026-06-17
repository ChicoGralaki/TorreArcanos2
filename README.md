# A Torre dos Arcanos 🧙‍♂️🗼

**A Torre dos Arcanos** é um jogo roguelike de sobrevivência mágica por turnos com interface exclusivamente textual, desenvolvido em Java. 

Este projeto foi construído como Trabalho Final da disciplina de Programação Orientada a Objetos I (POO I) do curso de Ciência da Computação (Universidade Estadual do Centro-Oeste - UNICENTRO), sob orientação da Profa. Inali Wisniewski Soares.

Utilização dos conceitos de POO: Encapsulamento e
Ocultação de Informações, Herança, Interface, classe
abstrata, polimorfismo, agregação/composição, tratamento
de exceções, estrutura de dados e arquivos.

---

## 📜 Descrição do Jogo

No jogo, você assume o papel de um Mago que deve sobreviver a embates em sequência subindo os andares da temível Torre dos Arcanos, simples assim. O sistema de combate é **puramente estratégico e determinístico**: não há aleatoriedade ou rolagem de dados para calcular o dano. O inimigo sempre anuncia qual será o seu próximo movimento, forçando o jogador a planejar seus turnos.

Em vez de depender da sorte para um "acerto crítico", o jogo introduz o sistema de **Usos para Crítico**: cada feitiço tem um contador interno. Ao atingir o número X de usos daquela magia específica, o seu efeito (dano ou cura) é dobrado na rodada, recompensando o planejamento a longo prazo.

---

## 🚀 Como Executar

Para compilar e jogar "A Torre dos Arcanos", certifique-se de ter o [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/) instalado na sua máquina.

1. Clone este repositório ou baixe os arquivos fonte.
2. Abra o terminal (ou Prompt de Comando/PowerShell) e navegue até a pasta raiz do projeto.
3. Compile o código rodando o comando:
   
javac -d out (Get-ChildItem -Recurse -Filter *.java | % FullName); java -cp out torrearcanos.visao.MotorTextual
javac -encoding UTF-8 -d . torrearcanos/**/*.java
