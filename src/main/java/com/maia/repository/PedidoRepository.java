package com.maia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maia.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	@Query("DELETE FROM Pedido  WHERE cliente.id = ?1")
	void deleteByIdCliente(Long idCliente);

}
