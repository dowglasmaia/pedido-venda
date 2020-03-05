package com.maia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maia.domain.Cliente;
import com.maia.repository.ClienteRepository;
import com.maia.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Transactional(readOnly = true)
	public Cliente retornaClientePorId(Long id) {
		Optional<Cliente> cliente = repository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado para o Código: " + id));
	}

	@Transactional
	public Cliente inserirNovoCliente(Cliente cliente) {
		try {
			cliente.setId(null);
			return repository.save(cliente);			
		} catch (Exception e) {
			throw new RuntimeException("Falha na requisição de salvar um novo Cliente ");
		}
	}

	@Transactional
	public Cliente editCliente(Cliente cliente) {
		Cliente newCliente = retornaClientePorId(cliente.getId());
		try {
			updateData(newCliente, cliente);
			return repository.save(newCliente);			
		} catch (Exception e) {
			throw new RuntimeException("Falha ao tentar editar o Cliente "+ cliente.getNome());
		}
	}

	@Transactional(readOnly = true)
	public List<Cliente> retornaListaDeClientes() {
		return repository.findAll();
	}
	
	@Transactional
	public void excluirCliente(Long id) {
		retornaClientePorId(id);
		try {
			repository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Falha ao Excluir Cliente de Código:" +id);
		}
	}

	/* ##### METODOS PRIVADOS ###### */
	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setCpf(cliente.getCpf());
		newCliente.setDataNascimento(cliente.getDataNascimento());
		newCliente.setNome(cliente.getNome());
	}

}
