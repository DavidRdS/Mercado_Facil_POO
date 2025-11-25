📦 Mercado Fácil — PDV + Servidor Node.js
🔄 Arquitetura Offline-First com Sincronização JSON

👥 1. Integrante do Projeto
David Roberto da Silva Sousa — Matrícula 01765638

🛠️ 2. Como Executar o Servidor (mercadofacil-server - Node.js)
O servidor funciona como uma API REST, responsável por produtos, vendas e sincronização entre PDV ↔ servidor.
📌 Pré-requisitos


Node.js (versão 18 ou superior)


NPM (já vem com o Node)


Visual Studio Code



▶️ Passo a Passo para Rodar o Servidor no VSCode
1️⃣ Abra o projeto no VSCode
mercadofacil-server/
 ├── package.json
 ├── server.js
 ├── src/
 └── ...

2️⃣ Instale as dependências
No terminal integrado (CTRL + `):
npm install

3️⃣ Inicie o servidor
npm start

Ou, se quiser rodar em modo desenvolvedor:
npm run dev

4️⃣ Acesse no navegador
http://localhost:3000

📌 Estrutura mínima do servidor (Node.js + Express)
Exemplo simples do arquivo server.js:
const express = require("express");
const cors = require("cors");
const fs = require("fs");
const app = express();

app.use(cors());
app.use(express.json());

// Carregar catálogo (produtos)
app.get("/api/produtos", (req, res) => {
    const produtos = JSON.parse(fs.readFileSync("./data/catalogo.json"));
    res.json(produtos);
});

// Receber vendas do PDV
app.post("/api/sincronizar/vendas", (req, res) => {
    const vendas = req.body;

    fs.writeFileSync("./data/vendas_recebidas.json", JSON.stringify(vendas, null, 2));

    res.json({ status: "OK", recebidas: vendas.length });
});

app.listen(3000, () => console.log("🚀 API MercadoFácil rodando em http://localhost:3000"));


🖥️ 3. Como Executar o PDV (mercadofacil-pdv)
📌 O PDV agora é um cliente Node.js também.
Esse cliente funciona offline, lendo e salvando JSON localmente.
▶️ Passos:
1️⃣ Abra a pasta do PDV
cd mercadofacil-pdv

2️⃣ Instale dependências
npm install

3️⃣ Execute o PDV
npm start


🌐 4. Arquitetura Offline-First (Com JSON Local)
O Mercado Fácil implementa uma arquitetura Offline-First, essencial para PDVs que precisam funcionar mesmo sem internet.

🔄 Sincronização de Entrada (Produtos)


O PDV chama:


GET /api/produtos



O servidor retorna catalogo.json


O PDV salva localmente:


data/catalogo_local.json

✔️ Assim, consultas de preço e estoque funcionam mesmo offline.

🔄 Sincronização de Saída (Vendas)


PDV salva vendas localmente em:


data/vendas_pendentes.json



Quando a internet voltar:


POST /api/sincronizar/vendas



Servidor recebe e confirma.


PDV apaga o arquivo local de pendências.


✔️ Nenhuma venda é perdida se a conexão cair.

🗂️ Estrutura de Pastas Recomendada
Servidor Node.js
mercadofacil-server/
 ├── server.js
 ├── data/
 │    ├── catalogo.json
 │    └── vendas_recebidas.json
 ├── package.json
 └── README.md

PDV Node.js
mercadofacil-pdv/
 ├── app.js
 ├── data/
 │    ├── catalogo_local.json
 │    └── vendas_pendentes.json
 ├── package.json
 └── README.md


✔️ Se quiser, posso gerar TODA a estrutura do projeto para você:
🔧 Opções:


Gerar automaticamente os dois package.json


Criar estrutura completa do servidor


Criar o PDV completo


Criar rotas de sincronização prontas


Criar versão com banco SQLite ao invés de JSON


👉 O que você deseja que eu gere agora?
