package br.com.webapp.model.fb.relatorio.devvenda;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.cliente.ClienteFB;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.produto.ProdutoFB;
import br.com.webapp.model.fb.produtolinha.ProdutoLinhaFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.Funcoes;

public class DevVendaDTORN {

	private DevVendaDTODAO devVendaDTODAO;
	
	public DevVendaDTORN() {
		this.devVendaDTODAO = DAOFactoryFirebird.criarDevVendaDTODAO();
	}
	
	public List<DevVendaDTO> listar(String boletim, EmpresaFB empresaFilter, VendedorFB vendedorFilter, ClienteFB clienteFilter, Integer fornecedorFilter, ProdutoFB produtoFilter, ProdutoLinhaFB linhaProdutoFilter, Date dataFilter1, Date dataFilter2, String agruparPor){
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return devVendaDTODAO.listar(boletim, empresaFilter, vendedorFilter, clienteFilter, fornecedorFilter, produtoFilter, linhaProdutoFilter, dataFilter1, dataFilter2, agruparPor);
	}

	public List<DevVendaDTO> listarBoletim(String boletim, EmpresaFB empresaFilter, VendedorFB vendedorFilter, ClienteFB clienteFilter, ProdutoFB produtoFilter, Date dataFilter1, Date dataFilter2) {
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return devVendaDTODAO.listarBoletim(boletim, empresaFilter, vendedorFilter, clienteFilter, produtoFilter, dataFilter1, dataFilter2);
	}

}
