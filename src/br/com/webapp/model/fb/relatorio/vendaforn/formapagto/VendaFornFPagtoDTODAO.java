package br.com.webapp.model.fb.relatorio.vendaforn.formapagto;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.tipovendedor.TipoVendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;

public interface VendaFornFPagtoDTODAO {
	public VendaFornFPagtoDTO carregarBancaria(EmpresaFB empresaFilter, VendedorFB vendedorFB, Date dataFilter1, Date dataFilter2);
	
	public List<VendaFornFPagtoDTO> listar(EmpresaFB empresaFB, VendedorFB vendedorFB, Date dataFilter1, Date dataFilter2);
	
	public List<VendaFornFPagtoDTO> listarCondPagto(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, Date dataFilter1, Date dataFilter2);
	public List<VendaFornFPagtoDTO> listarCartoesGroupByParcela(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, Date dataFilter1, Date dataFilter2);
	public List<VendaFornFPagtoDTO> listarOutros(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, Date dataFilter1, Date dataFilter2);
	public List<VendaFornFPagtoDTO> listarPedidosAFaturar(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, Date dataFilter1, Date dataFilter2);
	public List<VendaFornFPagtoDTO> listarCartoes(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, Date dataFilter1, Date dataFilter2, Integer parcelas);
	
	
}
