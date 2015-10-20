Krakatoa

Prof. José de Oliveira Guimarães.
UFSCar
Segundo semestre de 2015

This project is a Compiler that transforms texts from the Object Oriented Language Krakatoa to the language C.

Descrição do trabalho: a partir do analisador sintático da linguagem Krakatoa, 
faça um compilador que constrói a ASA, faz a análise semântica  e gera código idêntico ao original, 
exceto que sem os comentários. Podem existir pequenas diferenças entre o código gerado e o original, 
como uma declaração  "int a, b, c;"  ser desdobrada em "int a; int b; int c;". O código gerado deve 
estar corretamente indentado.
 
Devem existir métodos “genKra” (ou algo assim) nas classes da ASA Program, Statement, subclasses 
de Statement etc. Estes métodos devem gerar código em Krakatoa. O seu compilador deve passar nos 
testes léxicos, sintáticos e semânticos fornecidos na página Material de Aula.  Toda a análise sintática 
e léxica já está feita, exceto para métodos e variáveis estáticos e classes e métodos finais.
