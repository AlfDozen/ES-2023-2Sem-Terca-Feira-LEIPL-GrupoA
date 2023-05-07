# ES-2023-Sem2-Terca-Feira-LEIPL-GrupoA
Versão: GestaodeHorarios-1.0

Identificação do grupo no Moodle: Terça-Feira-LEIPL-GrupoA

Identificação do repositório: ES-2023-Sem2-Terca-Feira-LEIPL-GrupoA (https://github.com/AlfDozen/ES-2023-Sem2-Terca-Feira-LEIPL-GrupoA)

Identificação do sonarcloud: https://sonarcloud.io/project/overview?id=AlfDozen_ES-2023-Sem2-Terca-Feira-LEIPL-GrupoA

Identificação dos elementos do grupo: Pedro Almeida (Nº: 97960, username GitHub: pmaaa2-iscte, mail: pmaaa2@iscte-iul.pt); Diogo Cardoso (Nº: 98816, username GitHub: diogocardoso777, mail: daoco@iscte-iul.pt); Alexander Ferreira (Nº: 94481, username GitHub: afarl-iscteiul, mail: afarl@iscte-iul.pt); Cláudia Ferreira (Nº: 98184, username GitHub: carfa2-iscte, mail: alexandra_rocha@iscte-iul.pt); Salvador Silva (Nº: 98777, username: salvadoriscte, mail: smvsa3@iscte-iul.pt); Vitor Silva (Nº: 99149, username GitHub: vitorhugo-iscteiul, mail: vhcsa@iscte-iul.pt)

Sprint 1:

Identificação e descrição de erros: Não foram identificados erros.

Funcionalidade não implementadas ou incompletas: 1) do backlog, encontra-se por implementar (criação do horário de estudantes a partir dos dados do Fénix; criação de horário através da selecção de UC's de outro horário previamente carregado; visualização das aulas em sobreposição de um horário previamente carregado; visualização das aulas em sobrelotação de um horário previamente carregado); 2) a interface encontra-se incompleta, mas cumpre os objectivos os Sprint 1.

Nota Adicional: As classes da interface gráfica não foram revistas para fazer documentação e corrigir code smells uma vez que ainda representa um esboço.


Sprint 2:

Identificação e descrição de erros: Não foram identificados erros.

Funcionalidade não implementadas ou incompletas: Nada a referir.

Code smells não corrigidos: A classe Schedule tem várias constantes que levam à identificação do code smell X. Considera-se que não faz sentido dividir os métodos e funcionalidades da classe por outras classes e que a transferência de apenas os atributos em “excesso” para outra classe levaria à criação de uma data class.

JUnit Code Coverage: X% de cobertura segundo a métrica de complexidade ciclomática, relativamente às classes que não as de GUI;

Continuous Integration: Foi criado um workflow no github através das GitHub Actions para, aquando um commit ou pull request no branch main, seja feito automaticamente: o build, os testes JUnit, a validação do Javadoc e a integração no SonarCloud.

Bibliotecas Utilizadas:
- JavaFX: https://openjfx.io/
- CalendarFX: https://github.com/dlsc-software-consulting-gmbh/CalendarFX
- CSV e JSON: https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind, https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core, https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-csv
