package br.com.webapp.model.fb.relatorio.vendaforn.resumo;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.fornecedor.FornecedorFB;
import br.com.webapp.model.fb.tipovendedor.TipoVendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;

public interface VendaFornResumoDAO {

	public VendaFornResumo carregar(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, FornecedorFB fornecedorFilter, Date dataFilter1, Date dataFilter2, Date dataAnt1, Date dataAnt2, String vendasPorFilter, String segmentoFilter);

	public VendaFornResumo carregarSemAutoServico(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, FornecedorFB fornecedorFilter, Date dataFilter1, Date dataFilter2);
	
}
