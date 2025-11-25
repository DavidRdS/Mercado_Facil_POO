// Função para formatar o preço como moeda brasileira (mantida)
const formatarPreco = (preco) => {
    return preco.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
};

// Função para renderizar os produtos na página (mantida)
function renderizarProdutos(listaProdutos) {
    const container = document.getElementById('lista-produtos');
    container.innerHTML = ''; // Limpa o conteúdo existente

    if (!listaProdutos || listaProdutos.length === 0) {
        container.innerHTML = '<p style="text-align: center;">Nenhum produto encontrado no momento.</p>';
        return;
    }

    listaProdutos.forEach(produto => {
        const card = document.createElement('div');
        card.classList.add('produto-card');
        
        // **IMPORTANTE:** O nome das propriedades (nome, preco, id) deve 
        // ser exatamente o mesmo usado pelo seu Backend.
        
        card.innerHTML = `
            <h3>${produto.nome}</h3>
             
            <p class="preco">${formatarPreco(produto.preco)}</p>
            <button class="btn-comprar" data-id="${produto.id}">Comprar Agora</button>
        `;
        
        container.appendChild(card);
    });

    // ... (Listener para o botão Comprar - Mantido)
    document.querySelectorAll('.btn-comprar').forEach(button => {
        button.addEventListener('click', (event) => {
            const produtoId = event.target.getAttribute('data-id');
            console.log(`Produto ID ${produtoId} para compra.`);
            alert(`Produto ID ${produtoId} adicionado ao carrinho! (Simulação)`);
        });
    });
}

// 🌐 Função principal para buscar dados do Backend
document.addEventListener('DOMContentLoaded', async () => {
    // ⚠️ SUBSTITUA PELA SUA URL REAL DO BACKEND
    // Exemplo: Se o backend estiver rodando na porta 3000 com o endpoint /api/produtos
    const API_URL = 'http://localhost:3000/api/produtos'; 

    try {
        console.log(`Buscando dados em: ${API_URL}`);
        const response = await fetch(API_URL);
        
        if (!response.ok) {
            // Lança um erro se o status HTTP não for 2xx
            throw new Error(`Erro ao buscar dados. Status: ${response.status}`);
        }
        
        // Converte a resposta para JSON
        const data = await response.json(); 
        
        // Renderiza os produtos
        renderizarProdutos(data); 
        
    } catch (error) {
        console.error('❌ ERRO FATAL na requisição:', error);
        const container = document.getElementById('lista-produtos');
        container.innerHTML = '<p style="text-align: center; color: red; padding: 20px;">Houve um problema ao carregar os produtos. Verifique se o Backend está ativo e a URL correta.</p>';
    }
});