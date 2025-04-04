# Relatório do Sistema de Reservas de Voos

## 1. Estrutura do Código

O sistema foi desenvolvido seguindo os princípios da Programação Orientada a Objetos e utilizando o padrão de projeto Command. A estrutura do projeto está organizada da seguinte forma:

```
src/
├── api/        # Interfaces (Command, ManagerInterface)
├── model/      # Entidades (Flight, Passenger)
├── repository/ # Persistência em memória
├── service/    # Regras de negócio (FlightService)
├── command/    # Implementações do padrão Command
├── util/       # Utilitários (UIHelper, InputReader)
└── Main.java   # Ponto de entrada da aplicação
```

## 2. Interação entre Classes

### 2.1 Padrão Command
O sistema utiliza o padrão Command para encapsular as diferentes operações do sistema. A interface `Command` define o contrato básico:

```java
public interface Command {
    void execute();
}
```

Três comandos principais implementam esta interface:
- `FlightCommand`: Gerencia operações de voos (cadastro, listagem, remoção)
- `BookingCommand`: Gerencia operações de reservas (criar, listar, cancelar)
- `SearchCommand`: Realiza consultas de voos (por origem, destino, data)

### 2.2 Fluxo de Execução
1. O `Main.java` é o ponto de entrada, apresentando um menu principal
2. Com base na escolha do usuário, um comando específico é instanciado
3. O comando executado interage com o `FlightService` para realizar operações
4. O `FlightService` gerencia a lógica de negócio e interage com os modelos
5. Os utilitários (`UIHelper`, `InputReader`) fornecem suporte para interface e entrada de dados

## 3. Exemplos de Execução

### 3.1 Cadastro de Voo
```
Sistema de Reservas de Voos
╔════════════════════════════════════════════════════════════════════════════════╗
║ 1. Gerenciar Voos                                                             ║
║ 2. Gerenciar Reservas                                                        ║
║ 3. Consultar Voos                                                            ║
║ 4. Sair                                                                      ║
╚════════════════════════════════════════════════════════════════════════════════╝

Escolha uma opção: 1

Gerenciamento de Voos
╔════════════════════════════════════════════════════════════════════════════════╗
║ 1. Cadastrar Voo                                                             ║
║ 2. Listar Voos                                                               ║
║ 3. Remover Voo                                                               ║
║ 4. Voltar                                                                    ║
╚════════════════════════════════════════════════════════════════════════════════╝

Número do voo: 101
Data do voo (dd/MM/yyyy): 25/04/2024
Hora do voo (HH:mm): 14:30
Origem: São Paulo
Destino: Rio de Janeiro
Número de assentos: 100

✓ Voo cadastrado com sucesso!
```

### 3.2 Realização de Reserva
```
Sistema de Reservas de Voos
╔════════════════════════════════════════════════════════════════════════════════╗
║ 1. Gerenciar Voos                                                             ║
║ 2. Gerenciar Reservas                                                        ║
║ 3. Consultar Voos                                                            ║
║ 4. Sair                                                                      ║
╚════════════════════════════════════════════════════════════════════════════════╝

Escolha uma opção: 2

Gerenciamento de Reservas
╔════════════════════════════════════════════════════════════════════════════════╗
║ 1. Fazer Reserva                                                             ║
║ 2. Listar Minhas Reservas                                                   ║
║ 3. Cancelar Reserva                                                         ║
║ 4. Voltar                                                                   ║
╚════════════════════════════════════════════════════════════════════════════════╝

Número do voo desejado: 101
Número do assento desejado: 15
ID do passageiro: 1001
Nome: João Silva
Documento: 123.456.789-00
Telefone: (11) 98765-4321

✓ Reserva realizada com sucesso!
```

## 4. Características do Sistema

- **Interface CLI**: Interface de linha de comando interativa com menus formatados
- **Validações**: Implementação robusta de validações de entrada
- **Persistência em Memória**: Dados mantidos durante a execução do programa
- **Documentação**: Código documentado com JavaDoc
- **Modularidade**: Separação clara de responsabilidades entre as classes
- **Extensibilidade**: Fácil adição de novos comandos através do padrão Command