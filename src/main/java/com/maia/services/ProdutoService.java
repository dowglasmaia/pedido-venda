package com.maia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maia.domain.Produto;
import com.maia.repository.ProdutoRepository;
import com.maia.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;

	@Transactional(readOnly = true)
	public Produto retornaProdutoPorId(Long id) {
		Optional<Produto> produto = repository.findById(id);
		return produto.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado para o Código: " + id));
	}

	@Transactional
	public Produto inserirNovoProduto(Produto produto) {
		try {
			produto.setId(null);
			return repository.save(produto);			
		} catch (Exception e) {
			throw new RuntimeException("Falha na requisição de salvar um novo Produto ");
		}
	}

	@Transactional
	public Produto editProduto(Produto produto) {
		Produto newProduto = retornaProdutoPorId(produto.getId());
		try {
			updateData(newProduto, produto);
			return repository.save(newProduto);			
		} catch (Exception e) {
			throw new RuntimeException("Falha ao tentar editar o Produto "+ produto.getNome());
		}
	}

	@Transactional(readOnly = true)
	public List<Produto> retornaListaDeProdutos() {
		return repository.findAll();
	}
	
	@Transactional
	public void excluirProduto(Long id) {
		retornaProdutoPorId(id);
		try {
			repository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Falha ao Excluir Produto de Código:" +id);
		}
	}
	


	/* ##### METODOS PRIVADOS ###### */
	private void updateData(Produto newProduto, Produto produto) {
		newProduto.setDescricao(produto.getDescricao());
		newProduto.setNome(produto.getNome());
		newProduto.setPreco(produto.getPreco());
		newProduto.setQuantidade(produto.getQuantidade());
		newProduto.setSku(produto.getSku());
		
	
	}

}
