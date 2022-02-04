# Unit and Integration Testing Studies

## Anotações:

### Teste de repositório com JPA:
1. É <b>necessário</b> gerar um application.properties dentro do diretório de testes para que as configurações sejam usadas no momento de testar a aplicação.
2. É <b>necessário</b> adicionar a anotação <b>@DataJpaTest</b> em cada classe de teste para que o JPA gerencie as instanciações que se relacionam ao banco.
3. É <b>importante</b> que se tenha um método de tearDown() com anotação <b>@AfterEach</b> cuja função seja deletar todos os dados gerados após cada teste, para que não haja sobrecarga da aplicação e possíveis conflitos com outros testes.
4. Não é necessário testar consultas do próprio JPA, apenas as criadas pelo desenvolvedor. Isso porque as queries do JPA já são testadas pelo módulo automaticamente.