package com.maia.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = " O campo Valor Total da Compra do Pedido é obrigatório")
	private Double totalDaCompra;

	@NotNull(message = " O campo Data da Compra do Pedido é obrigatório")
	private LocalDateTime dataDaCompra;

	@Valid
	@ManyToOne
	private Cliente cliente;

	@Valid
	@ManyToOne
	private Produto produto;

	public Pedido() {

	}

	public Pedido(Long id, Double totalDaCompra, LocalDateTime dataDaCompra, Cliente cliente, Produto produto) {		
		this.id = id;
		this.totalDaCompra = totalDaCompra;
		this.dataDaCompra = dataDaCompra;
		this.cliente = cliente;
		this.produto = produto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getTotalDaCompra() {
		return totalDaCompra;
	}

	public void setTotalDaCompra(Double totalDaCompra) {
		this.totalDaCompra = totalDaCompra;
	}

	public LocalDateTime getDataDaCompra() {
		return dataDaCompra;
	}

	public void setDataDaCompra(LocalDateTime dataDaCompra) {
		this.dataDaCompra = dataDaCompra;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
