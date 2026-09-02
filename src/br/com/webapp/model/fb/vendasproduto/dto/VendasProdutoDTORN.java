package br.com.webapp.model.fb.vendasproduto.dto;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.fornecedor.FornecedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.Funcoes;

public class VendasProdutoDTORN {

	private VendasProdutoDTODAO vendasProdutoDTODAO;
	
	public VendasProdutoDTORN() {
		this.vendasProdutoDTODAO = DAOFactoryFirebird.criarVendasProdutoDTO();
	}
	
	public List<VendasProdutoDTO> listarProdutosAutoServico(EmpresaFB empresaFB, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2, Integer id, String porFilter){
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return vendasProdutoDTODAO.listarAutoServico(empresaFB, vendedorFilter, dataFilter1, dataFilter2, id, porFilter);
	}
	
	public List<VendasProdutoDTO> listarProdutosPedido(EmpresaFB empresaFB, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2, Integer id, String porFilter){
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return vendasProdutoDTODAO.listarPedido(empresaFB, vendedorFilter, dataFilter1, dataFilter2, id, porFilter);
	}
	
	public List<VendasProdutoDTO> listarProdutosAmbos(EmpresaFB empresaFB, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2, Integer id, String porFilter){
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return vendasProdutoDTODAO.listarAmbos(empresaFB, vendedorFilter, dataFilter1, dataFilter2, id, porFilter);
	}

	public List<VendasProdutoDTO> listarPorSegmento(EmpresaFB empresaFilter, VendedorFB vendedorFilter, FornecedorFB fornecedorFilter, String porFilter, Date dataFilter1, Date dataFilter2, String segmentoFilter, String vendasPorFilter, Integer id) {
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return vendasProdutoDTODAO.listarPorSegmento(empresaFilter, vendedorFilter, fornecedorFilter, porFilter, dataFilter1, dataFilter2, segmentoFilter, vendasPorFilter, id);
	}
}
