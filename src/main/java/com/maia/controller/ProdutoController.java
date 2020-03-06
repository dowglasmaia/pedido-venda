package com.maia.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.maia.domain.Produto;
import com.maia.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@GetMapping("/{id}")
	public ResponseEntity<Produto> getById(@PathVariable Long id) {
		Produto produto = produtoService.retornaProdutoPorId(id);
		return ResponseEntity.ok().body(produto);
	}

	@GetMapping
	public ResponseEntity<List<Produto>> getAll() {
		List<Produto> result = produtoService.retornaListaDeProdutos();
		return ResponseEntity.ok().body(result);
	}

	@PostMapping()
	public ResponseEntity<Void> insert(@Valid @RequestBody Produto produto) {
		produto = produtoService.inserirNovoProduto(produto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(produto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody Produto produto) {
		produto.setId(id);
		produto = produtoService.editProduto(produto);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		produtoService.excluirProduto(id);
		return ResponseEntity.noContent().build();
	}

}
