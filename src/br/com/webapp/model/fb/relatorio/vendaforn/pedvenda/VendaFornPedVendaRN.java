package br.com.webapp.model.fb.relatorio.vendaforn.pedvenda;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.relatorio.vendaforn.VendaFornDTO;
import br.com.webapp.model.fb.relatorio.vendaforn.formapagto.VendaFornFPagtoDTO;
import br.com.webapp.model.fb.vendasproduto.dto.VendasProdutoDTO;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.Funcoes;

public class VendaFornPedVendaRN {

	private VendaFornPedVendaDAO vendaFornPedVendaDAO;
	
	public VendaFornPedVendaRN() {
		vendaFornPedVendaDAO = DAOFactoryFirebird.criarVendaFornPedVenda();
	}
	
	public List<VendaFornPedVenda> listarPedVenda(VendaFornDTO vendaFornDTO, VendasProdutoDTO vendasProdutoDTO, VendaFornFPagtoDTO vendaFornFPagtoDTO, EmpresaFB empresaFilter, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2, String porFilter){
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return vendaFornPedVendaDAO.listarPedVenda(vendaFornDTO, vendasProdutoDTO, vendaFornFPagtoDTO, empresaFilter, vendedorFilter, dataFilter1, dataFilter2, porFilter);
	}

	public List<VendaFornPedVenda> listarPedVendaSemAutoServ(VendaFornDTO vendaFornDTO, VendasProdutoDTO vendasProdutoDTO, VendaFornFPagtoDTO vendaFornFPagtoDTO, EmpresaFB empresaFilter, VendedorFB vendedorFilter,  Date dataFilter1, Date dataFilter2, String porFilter) {
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return vendaFornPedVendaDAO.listarPedVendaSemAutoServ(vendaFornDTO, vendasProdutoDTO, vendaFornFPagtoDTO, empresaFilter, vendedorFilter, dataFilter1, dataFilter2, porFilter);
	}

	public List<VendaFornPedVenda> listarNotas(VendaFornDTO vendaFornDTO, VendasProdutoDTO vendasProdutoDTO, VendaFornFPagtoDTO vendaFornFPagtoDTO, EmpresaFB empresaFilter, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2, String porFilter, String segmentoFilter) {
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return vendaFornPedVendaDAO.listarNotas(vendaFornDTO, vendasProdutoDTO, vendaFornFPagtoDTO, empresaFilter, vendedorFilter, dataFilter1, dataFilter2, porFilter, segmentoFilter);
	}

	public List<VendaFornPedVenda> listarPedVendaFpgto(VendaFornDTO vendaFornDTO, VendasProdutoDTO vendasProdutoDTO, VendaFornFPagtoDTO vendaFornFPagtoDTO, EmpresaFB empresaFilter, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2, String porFilter) {
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return vendaFornPedVendaDAO.listarPedVendaFpgto(vendaFornDTO, vendasProdutoDTO, vendaFornFPagtoDTO, empresaFilter, vendedorFilter, dataFilter1, dataFilter2, porFilter);
	}
}
