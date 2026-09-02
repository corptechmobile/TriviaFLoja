package br.com.webapp.model.fb.relatorio.vendaforn;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.fornecedor.FornecedorFB;
import br.com.webapp.model.fb.tipovendedor.TipoVendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;

public interface VendaFornDTODAO {
	public List<VendaFornDTO> listarAutoServico(EmpresaFB empresa, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, String porFilter, Date dataFilter1, Date dataFilter2);
	public List<VendaFornDTO> listarPedido(EmpresaFB empresa, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, String porFilter, Date dataFilter1, Date dataFilter2);
	public List<VendaFornDTO> listarAmbos(EmpresaFB empresa, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, String porFilter, Date dataFilter1, Date dataFilter2);
	public List<VendaFornDTO> listarPedidoSemAutoServico(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, String porFilter, Date dataFilter1, Date dataFilter2);
	public List<VendaFornDTO> dashBoardPorMes(Integer empresaFilter, Integer vendedorFilter, TipoVendedorFB tipoVendedorFilter, Date dataFilter1, Date dataFilter2, Date cDtMesAnt, Date cDtMesAnt2, Date cDtAnoAnt, Date cDtAnoAnt2, String vendasPorFilter, String segmentoFilter);
	public List<VendaFornDTO> dashBoardPorMesAno(Integer empresaFilter, Integer vendedorFilter, TipoVendedorFB tipoVendedorFilter, Date dataFilter1, Date dataFilter2, Date time, Date time2, Date time3, Date time4, String vendasPorFilter, String segmentoFilter);
	public List<VendaFornDTO> listarPorSegmento(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, FornecedorFB fornecedorFB, String porFilter, Date dataFilter1, Date dataFilter2, String segmentoFilter, String vendasPorFilter);
}
