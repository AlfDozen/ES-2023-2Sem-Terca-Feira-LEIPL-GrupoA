# ES-2023-Sem2-Terca-Feira-LEIPL-GrupoA
Versão: GestaodeHorarios-1.0

Identificação do grupo no Moodle: Terça-Feira-LEIPL-GrupoA

Identificação do repositório: ES-2023-Sem2-Terca-Feira-LEIPL-GrupoA (https://github.com/AlfDozen/ES-2023-Sem2-Terca-Feira-LEIPL-GrupoA)

Identificação do sonarcloud: https://sonarcloud.io/project/overview?id=AlfDozen_ES-2023-Sem2-Terca-Feira-LEIPL-GrupoA

Identificação dos elementos do grupo: Pedro Almeida (Nº: 97960, username GitHub: pmaaa2-iscte, mail: pmaaa2@iscte-iul.pt); Diogo Cardoso (Nº: 98816, username GitHub: diogocardoso777, mail: daoco@iscte-iul.pt); Alexander Ferreira (Nº: 94481, username GitHub: afarl-iscteiul, mail: afarl@iscte-iul.pt); Cláudia Ferreira (Nº: 98184, username GitHub: carfa2-iscte, mail: alexandra_rocha@iscte-iul.pt); Salvador Silva (Nº: 98777, username: salvadoriscte, mail: smvsa3@iscte-iul.pt); Vitor Silva (Nº: 99149, username GitHub: vitorhugo-iscteiul, mail: vhcsa@iscte-iul.pt)

Na pasta Docs estão os Javadocs.

Na pasta Entregaveis estão os printscreen do JUnit Code Coverage, passagem nos testes e SonarCloud.

Sprint 1:

Identificação e descrição de erros: Não foram identificados erros.

Funcionalidades não implementadas ou incompletas: 1) do backlog, encontra-se por implementar (criação do horário de estudantes a partir dos dados do Fénix; criação de horário através da selecção de UC's de outro horário previamente carregado; visualização das aulas em sobreposição de um horário previamente carregado; visualização das aulas em sobrelotação de um horário previamente carregado); 2) a interface encontra-se incompleta, mas cumpre os objectivos os Sprint 1.

Nota Adicional: As classes da interface gráfica não foram revistas para fazer documentação e corrigir code smells uma vez que ainda representa um esboço.


Sprint 2:

Identificação e descrição de erros: Não foram identificados erros.

Funcionalidade não implementadas ou incompletas: Nada a referir.

Code smells não corrigidos: O SonarCloud identifica 7 critical code smells, relacionados com a utilização de atributos estáticos em métodos não estáticos. Estes code smells são identificados nas classes de GUI e não se considera que têm impacto na aplicação. Identificou ainda 4 major code smells que estão relacionados com duas exceções que são capturadas e ignoradas (levando a um catch vazio que é necessário) e a sugestão de utilização de um import diferente para garantir que as mensagens quando ocorre erro ao fazer delete a um ficheiro são mais adequadas (estas mensagens não são utilizadas). Por fim, identificou 4 minor code smells que estão relacionadas com a questão do static referida anteriormente, com o facto de não considerarmos um atributo com referência estática como final (este atributo é afectivamente alterado e não faria sentido ser final) e de não implementarmos uma nova função equals quando aplicamos a interface comparable a duas classes (não foi considerado relevante o uso do equals para o presente caso).

É também importante referir que o SonarCloud identificou 3 minor bugs relacionados com o facto de não tratarmos o boolean returnado na criação e delete de ficheiros temporários num método. No nosso entender, o código está a funcionar como pretendido e não há problema associado a esta questão. 

Apesar do SonarCloud não identificar este code smells, a classe Schedule tem várias constantes que levam à identificação no SonarLint de um excesso de parametros. Considera-se que não faz sentido dividir os métodos e funcionalidades da classe por outras classes, uma vez que estão todos relacionados com a criação e manipulação de objectos Schedule e que a transferência de apenas os atributos em “excesso” para outra classe levaria à criação de uma data class. Desta forma, não foi reduzida a classe Schedule. 

JUnit Code Coverage: 64% de cobertura segundo a métrica de complexidade ciclomática, relativamente a todas as classes do src/main/java. No entanto, esta percentagem inclui as classes da GUI. As classes que não envolvem a GUI têm todas mais de 90% de cobertura segundo a métrica de complexida ciclomática.

Continuous Integration: Foi criado um workflow no github através das GitHub Actions para, aquando um commit ou pull request no branch main, seja feito automaticamente: o build, os testes JUnit, a validação do Javadoc e a integração no SonarCloud.

Bibliotecas Utilizadas:
- JavaFX: https://openjfx.io/
- CalendarFX: https://github.com/dlsc-software-consulting-gmbh/CalendarFX
- CSV e JSON: https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind, https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core, https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-csv
