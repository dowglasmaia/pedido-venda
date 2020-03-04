package com.maia.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.maia.domain.Pedido;
import com.maia.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@GetMapping("/{id}")
	public ResponseEntity<Pedido> findCargoPorId(@PathVariable Long id) {
		Pedido pedido = pedidoService.retornaPedidoPorId(id);
		return ResponseEntity.ok().body(pedido);
	}

	@GetMapping
	public ResponseEntity<List<Pedido>> findAll() {
		List<Pedido> result = pedidoService.retornaListaDePedidos();
		return ResponseEntity.ok().body(result);
	}

	@PostMapping("/{idCliente}")
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido, @PathVariable Long idCliente) {
		pedido = pedidoService.inserirNovoPedido(pedido, idCliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

}
