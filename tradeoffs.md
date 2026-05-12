## Este documento tem o objetivo de documentar os tradeoffs feitos neste exercício

### Centralizar a configuração do sistema
**Responsável:** Hitallo Azevedo

### **Modificação das classes `ConfiguracaoService`, `ExportacaoService` e `RelatorioService` para utilizarem injeção de dependência**

#### Por quê?

Eu, particularmente, quando estou trabalhando com um objeto de configuração que centraliza as configurações do sistema, gosto de injetar essa dependência (configurações), dessa forma deixando explicito que determinado objeto depende de determinada configuração, aumentando consciência do contexto (contextual awareness).

### **Dados de configuração hardcoded na definição da classe ao invés de método inicializador**

#### Por quê?

Há casos e casos, comumente e com o que eu trabalhei nos últimos anos, configuração não é algo que muda em runtime, por isso, os dados são passados diretamente na definição da classe, não disponibilizando um método de inicialização. Isso poderia evoluir para extrair os dados de alguma váriavel de ambiente, banco de dados ou fonte externa... Em todos os casos, essa alimentação de dados, externos ou não, seria feita na inicialização do objeto de configuração, sendo uma etapa síncrona e bloqueante, enquanto o objeto de configuração não estiver completo e inicializado, o sistema não inicia os seus fluxos de execução.

Portanto, não existe método inicalizador. E se tratando de singleton, isso também garante que a inicialização ocorra apenas uma única vez, evitando que um método inicializador seja chamado em lugares diferentes sobrescrevendo a configuração inicial.

### **Setters na classe de configuração**

Seguindo a orientação de não alterar o comportamento existente, eu não removi os métodos setters, porém, eu não gosto dessa abordagem e não aplicaria isso em um sistema.

Eu não gosto da ideia de alterar configurações em runtime, na minha visão o objeto de configuração deve ser the source of truth, imutável e construído na inicialização do sistema. A construção do objeto de configuração falhou? Encerra o processo com código de erro.

### **Singleton com instância de DEV e PROD**

Também é um formato que eu não adotaria enquando desenvolvedor. Eu utilizaria algo que carregue os valores distintos na fase de inicialização, ao invés de delegar para o código qual ambiente escolher, resumindo, eu controlaria através de variáveis de ambiente ao invés de duas instâncias, mas, foi uma forma razoável que eu encontrei para não alterar tanto o comportamento do sistema e os testes.

### **Reset singleton nos testes**

Foi necessário uma pequena modificação nos testes adicionado um método que redefine as instâncias do singleton a fim de evitar comportamentos inesperados.

---