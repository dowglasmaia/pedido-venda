package com.maia.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.maia.domain.Pedido;
import com.maia.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@GetMapping("/{id}")
	public ResponseEntity<Pedido> findPedidoPorId(@PathVariable Long id) {
		Pedido pedido = pedidoService.retornaPedidoPorId(id);
		return ResponseEntity.ok().body(pedido);
	}

	@GetMapping
	public ResponseEntity<List<Pedido>> findAllPedido() {
		List<Pedido> result = pedidoService.retornaListaDePedidos();
		return ResponseEntity.ok().body(result);
	}

	@PostMapping("/cliente/{idCliente}")
	public ResponseEntity<Void> insertPedido(@Valid @RequestBody Pedido pedido, @PathVariable Long idCliente) {
		pedido = pedidoService.inserirNovoPedido(pedido, idCliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{id}/cliente/{idCliente}")
	public ResponseEntity<Void> updatePedido(@PathVariable Long id,@PathVariable Long idCliente, @Valid @RequestBody Pedido pedido) {
		pedido.setId(id);
		pedido = pedidoService.editPedido(pedido, idCliente);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
		pedidoService.excluirPedido(id);
		return ResponseEntity.noContent().build();
	}

}
