package com.maia.utils.testIntegrations;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maia.domain.Cliente;
import com.maia.domain.Produto;
import com.maia.repository.ClienteRepository;
import com.maia.repository.ProdutoRepository;

@Service
public class TestsIntegrationsDB {
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Autowired
	private ProdutoRepository produtoRepo;	

	public void instanciateTestDatabase() throws ParseException {
		
		/*Cliente*/
		Cliente c1 = new Cliente(null, "Dowglas Maia", "644.875.570-54", LocalDate.parse("1980-12-25"));
		Cliente c2 = new Cliente(null, "Kayron Maia", "580.357.690-57", LocalDate.parse("1980-12-25"));
		Cliente c3 = new Cliente(null, "Luiz Lima", "854.491.590-64", LocalDate.parse("1980-12-25"));
		Cliente c4 = new Cliente(null, "Kamylli Braga", "127.901.300-19", LocalDate.parse("1980-12-25"));
		clienteRepo.saveAll(Arrays.asList(c1,c2,c3,c4));
		
		
		/* Produto*/
		Produto p1 = new Produto(null, "PK-220", "Teclado Gamer T2020", "Teclado Gamer Mecânico com LEDs", 1200.00, 10);
		Produto p2 = new Produto(null, "PK-2022", "Fone Gamer ", "Fone Gamer Mecânico com LEDs", 1000.00, 10);
		Produto p3 = new Produto(null, "KJ-0020", "Case Start T2", "", 200.00, 10);
		Produto p4 = new Produto(null, "OP-2088", "Monitor Curvo 4K", "", 11250.98, 10);
		produtoRepo.saveAll(Arrays.asList(p1,p2,p3,p4));
				
	}

}
