o arquivo tripwise_ddl.sql contém as tabelas para criação do DB. Para a conexão funcionar você deverá criar a base de dados tripwise no workbench, ao executar o arquivo .sql

Depois deverá executar o arquivo main.java

Se tiver algum problema, talvez possa ser da biblioteca commoms-langs3:
Verifique o Build Path e Configure o Classpath para Execução:

No Eclipse (ou outra IDE), vá para as configurações de execução do seu projeto.

Certifique-se de que a biblioteca commons-lang3 está incluída no classpath de execução e não apenas no build path.

Para fazer isso no Eclipse: vá em Run > Run Configurations..., selecione sua configuração de execução, vá para a aba Classpath e confirme que o commons-lang3.jar está listado lá.   