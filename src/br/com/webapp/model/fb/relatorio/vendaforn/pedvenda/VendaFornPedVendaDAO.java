package br.com.webapp.model.fb.relatorio.vendaforn.pedvenda;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.relatorio.vendaforn.VendaFornDTO;
import br.com.webapp.model.fb.relatorio.vendaforn.formapagto.VendaFornFPagtoDTO;
import br.com.webapp.model.fb.vendasproduto.dto.VendasProdutoDTO;
import br.com.webapp.model.fb.vendedor.VendedorFB;

public interface VendaFornPedVendaDAO {
	public List<VendaFornPedVenda> listarPedVenda(VendaFornDTO vendaFornDTO, VendasProdutoDTO vendasProdutoDTO, VendaFornFPagtoDTO vendaFornFPagtoDTO, EmpresaFB empresaFilter, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2, String porFilter);
	public List<VendaFornPedVenda> listarPedVendaSemAutoServ(VendaFornDTO vendaFornDTO, VendasProdutoDTO vendasProdutoDTO, VendaFornFPagtoDTO vendaFornFPagtoDTO, EmpresaFB empresaFilter, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2, String porFilter);
	public List<VendaFornPedVenda> listarNotas(VendaFornDTO vendaFornDTO, VendasProdutoDTO vendasProdutoDTO, VendaFornFPagtoDTO vendaFornFPagtoDTO, EmpresaFB empresaFilter, VendedorFB vendedorFilter, Date dataFilter1, Date dataFilter2, String porFilter, String segmentoFilter);
	public List<VendaFornPedVenda> listarPedVendaFpgto(VendaFornDTO vendaFornDTO, VendasProdutoDTO vendasProdutoDTO, VendaFornFPagtoDTO vendaFornFPagtoDTO, EmpresaFB empresaFilter, VendedorFB vendedorFilter, Date dataFilter1,Date dataFilter2, String porFilter);
}
