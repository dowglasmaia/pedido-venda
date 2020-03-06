package com.maia.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.maia.domain.Cliente;
import com.maia.services.ClienteService;


@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping("/{id}")
	public ResponseEntity<Cliente> getById(@PathVariable Long id) {
		Cliente cliente = clienteService.retornaClientePorId(id);
		return ResponseEntity.ok().body(cliente);
	}

	@GetMapping
	public ResponseEntity<List<Cliente>> getAll() {
		List<Cliente> result = clienteService.retornaListaDeClientes();
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping("/lista/nome")
	public ResponseEntity<List<Cliente>> getAllByNome(@RequestParam(value = "nome" )String nome) {
		List<Cliente> result = clienteService.retornaClientePorNome(nome);
		return ResponseEntity.ok().body(result);
	}

	@PostMapping()
	public ResponseEntity<Void> insert(@Valid @RequestBody Cliente cliente) {
		cliente = clienteService.inserirNovoCliente(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
		cliente.setId(id);
		cliente = clienteService.editCliente(cliente);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		clienteService.excluirCliente(id);
		return ResponseEntity.noContent().build();
	}

}
