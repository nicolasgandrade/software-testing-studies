# Unit and Integration Testing Studies

## Anotações:

### Teste de repositório com JPA:
1. É <b>necessário</b> gerar um application.properties dentro do diretório de testes para que as configurações sejam usadas no momento de testar a aplicação.
2. É <b>necessário</b> adicionar a anotação <b>@DataJpaTest</b> em cada classe de teste para que o JPA gerencie as instanciações que se relacionam ao banco.
3. É <b>importante</b> que se tenha um método de tearDown() com anotação <b>@AfterEach</b> cuja função seja deletar todos os dados gerados após cada teste, para que não haja sobrecarga da aplicação e possíveis conflitos com outros testes.
4. Não é necessário testar consultas do próprio JPA, apenas as criadas pelo desenvolvedor. Isso porque as queries do JPA já são testadas pelo módulo automaticamente.


### Teste com Mockito (service):
1. A utilização do mockito serve para mockar dependências externas. Por exemplo: <br> Para se testar um **service**, seria extremamente lento e desnecessário gerar uma nova tabela, popular com dados de teste etc. <br> Para resolver isso, cria-se um **mock** do repositório, ou seja, o teste de service só vai conferir se métodos que atuam sobre o repositório foram invocados corretamente, não é uma instanciação real do repositório. Por isso se testa separadamente somente o repositório e depois o service (com mock).
2. Os ArgumentCaptor(s) servem para capturar objetos que foram passados para os métodos. <br> Assim, pode-se verificar se o objeto com o qual foi realizada a operação é o mesmo que o desenvolvedor desejava.
3. Lançamento de exceções também devem ser testados. Caso algo ocorra errado e uma exceção personalizada tenha que ser lançada, é **necessário** ter testes que cubram esse lançamento. Para isso, é comum se usar o assertThatThrownBy(() -> métodoASerChamado).