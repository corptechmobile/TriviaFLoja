package br.com.webapp.model.fb.vendasproduto.dto;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.fornecedor.FornecedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;

public interface VendasProdutoDTODAO {
	public List<VendasProdutoDTO> listarAutoServico(EmpresaFB empresaFB, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2, Integer id, String porFilter);
	public List<VendasProdutoDTO> listarPedido(EmpresaFB empresaFB, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2, Integer id, String porFilter);
	public List<VendasProdutoDTO> listarAmbos(EmpresaFB empresaFB, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2, Integer id, String porFilter);
	public List<VendasProdutoDTO> listarPorSegmento(EmpresaFB empresaFilter, VendedorFB vendedorFilter, FornecedorFB fornecedorFilter, String porFilter, Date dataFilter1, Date dataFilter2, String segmentoFilter, String vendasPorFilter, Integer id);
}
