# Mercado Fácil POO: PDV e Servidor de Sincronização

## 👥 1. Integrantes do Projeto

* **David Roberto da Silva Sousa - Matricula 01765638
* 

---

## 🛠️ 2. Como Compilar e Executar o Servidor (`mercadofacil-server`)

O servidor é a camada de API e persistência (banco de dados).

### Pré-requisitos
* **Java Development Kit (JDK) 17** ou superior.
* **Maven** (gerenciador de dependências).

### Passos de Execução

1.  **Navegar até a pasta do Servidor:**
    ```bash
    cd mercadofacil-server
    ```

2.  **Compilar o Projeto:**
    Utilize o Maven para baixar as dependências e gerar o pacote `.jar` (ou `.war`):
    ```bash
    mvn clean install
    ```

3.  **Executar o Servidor:**
    Execute o arquivo `.jar` gerado na pasta `target`:
    ```bash
    java -jar target/mercadofacil-server-1.0.0.jar
    ```
    O servidor será inicializado e estará acessível em `http://localhost:8080` (porta padrão do Spring Boot).

---

## 🖥️ 3. Como Compilar e Executar o Cliente PDV (`mercadofacil-pdv`)

O cliente PDV (Ponto de Venda) é uma aplicação Java ou React (dependendo da sua estrutura) que opera de forma independente para garantir vendas ininterruptas.

**Atenção:** Se o projeto PDV for uma aplicação **Maven/Java**, use os mesmos pré-requisitos e passos de compilação/execução do servidor (ajustando o nome do arquivo JAR na execução).

* *Se o projeto PDV for React/JavaScript, ajuste os pré-requisitos (Node.js/NPM) e os comandos para `npm install` e `npm start`.*

### Passos de Execução (Exemplo - Assumindo que é um projeto Java/Maven)

1.  **Navegar até a pasta do Cliente:**
    ```bash
    cd mercadofacil-pdv
    ```

2.  **Compilar o Projeto:**
    ```bash
    mvn clean install
    ```

3.  **Executar o Cliente:**
    Execute o arquivo `.jar` gerado na pasta `target`:
    ```bash
    java -jar target/mercadofacil-pdv-1.0.0.jar
    ```

---

## 🌐 4. Arquitetura Offline-First

O projeto Mercado Fácil utiliza uma arquitetura **Offline-First**, que prioriza a funcionalidade do cliente (PDV) mesmo na ausência de conexão com a internet.

### JSON como Mecanismo de Sincronização

A sincronização de dados ocorre em dois sentidos usando arquivos **JSON** locais:

1.  **Sincronização de Entrada (Produto):**
    * No início do dia ou sempre que a conexão estiver disponível, o PDV baixa a lista completa de produtos do servidor.
    * Esses dados são armazenados localmente em um arquivo, como `catalogo.json`, garantindo que o PDV sempre tenha preços e estoque atualizados para consulta durante as vendas.

2.  **Sincronização de Saída (Venda):**
    * Todas as vendas realizadas pelo PDV são inicialmente registradas localmente em um arquivo de pendências, como `vendas_pendentes.json`.
    * Quando a conexão com a internet é restabelecida, um serviço de sincronização do PDV envia o conteúdo de `vendas_pendentes.json` para o servidor (API) em lote.
    * Após a confirmação do servidor, o arquivo local de pendências é limpo.

Essa abordagem garante que as operações de venda cruciais nunca sejam interrompidas por falhas de rede.
