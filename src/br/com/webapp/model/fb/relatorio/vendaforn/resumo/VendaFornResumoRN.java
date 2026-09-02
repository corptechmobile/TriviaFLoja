package br.com.webapp.model.fb.relatorio.vendaforn.resumo;

import java.util.Calendar;
import java.util.Date;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.fornecedor.FornecedorFB;
import br.com.webapp.model.fb.tipovendedor.TipoVendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.Funcoes;

public class VendaFornResumoRN {

	private VendaFornResumoDAO vendaFornResumoDAO;
	
	public VendaFornResumoRN() {
		vendaFornResumoDAO = DAOFactoryFirebird.criarVendaFornRerumoDAO();
	}
	
	public VendaFornResumo carregar(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, FornecedorFB fornecedorFilter, Date dataFilter1, Date dataFilter2, String vendasPorFilter, String segmentoFilter){
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		
		Calendar cDt1Ant = Calendar.getInstance();
		cDt1Ant.setTime(dataFilter1);
		cDt1Ant.set(Calendar.DAY_OF_MONTH, 1);
		cDt1Ant.set(Calendar.HOUR_OF_DAY, 0);
		cDt1Ant.set(Calendar.MINUTE, 0);
		cDt1Ant.set(Calendar.SECOND, 0);
		cDt1Ant.add(Calendar.MONTH, -1);
		
		Calendar cDt2Ant = Calendar.getInstance();
		cDt2Ant.setTime(cDt1Ant.getTime());
		cDt2Ant.add(Calendar.MONTH, 1);
		cDt2Ant.add(Calendar.DATE, -1);
		cDt2Ant.set(Calendar.HOUR_OF_DAY, 23);
		cDt2Ant.set(Calendar.MINUTE, 59);
		cDt2Ant.set(Calendar.SECOND, 59);	
		
		return vendaFornResumoDAO.carregar(empresaFilter, vendedorFilter, tipoVendedorFilter, fornecedorFilter, dataFilter1, dataFilter2, cDt1Ant.getTime(), cDt2Ant.getTime(), vendasPorFilter, segmentoFilter);
	}

	public VendaFornResumo carregarMes(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, FornecedorFB fornecedorFilter, Date dtReferencia, String vendasPorFilter, String segmentoFilter) {
		Calendar cDt1 = Calendar.getInstance();
		cDt1.setTime(dtReferencia);
		cDt1.set(Calendar.DAY_OF_MONTH, 1);
		cDt1.set(Calendar.HOUR_OF_DAY, 0);
		cDt1.set(Calendar.MINUTE, 0);
		cDt1.set(Calendar.SECOND, 0);
		
		Calendar cDt2 = Calendar.getInstance();
		cDt2.setTime(dtReferencia);
		cDt2.set(Calendar.HOUR_OF_DAY, 23);
		cDt2.set(Calendar.MINUTE, 59); 
		cDt2.set(Calendar.SECOND, 59);
		
		Calendar cDt1Ant = Calendar.getInstance();
		cDt1Ant.setTime(dtReferencia);
		cDt1Ant.set(Calendar.DAY_OF_MONTH, 1);
		cDt1Ant.set(Calendar.HOUR_OF_DAY, 0);
		cDt1Ant.set(Calendar.MINUTE, 0);
		cDt1Ant.set(Calendar.SECOND, 0);
		cDt1Ant.add(Calendar.MONTH, -1);
		
		Calendar cDt2Ant = Calendar.getInstance();
		cDt2Ant.setTime(cDt1Ant.getTime());
		cDt2Ant.add(Calendar.MONTH, 1);
		cDt2Ant.add(Calendar.DATE, -1);
		cDt2Ant.set(Calendar.HOUR_OF_DAY, 23);
		cDt2Ant.set(Calendar.MINUTE, 59);
		cDt2Ant.set(Calendar.SECOND, 59);			

		
		return vendaFornResumoDAO.carregar(empresaFilter, vendedorFilter, tipoVendedorFilter, fornecedorFilter, cDt1.getTime(), cDt2.getTime(), cDt1Ant.getTime(), cDt2Ant.getTime(), vendasPorFilter, segmentoFilter);
	}
	
	public VendaFornResumo carregarMesSemAutoServico(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, FornecedorFB fornecedorFilter, Date dtReferencia) {
		Calendar cDt1 = Calendar.getInstance();
		cDt1.setTime(dtReferencia);
		cDt1.set(Calendar.DAY_OF_MONTH, 1);
		cDt1.set(Calendar.HOUR_OF_DAY, 0);
		cDt1.set(Calendar.MINUTE, 0);
		cDt1.set(Calendar.SECOND, 0);
		
		Calendar cDt2 = Calendar.getInstance();
		cDt2.setTime(dtReferencia);
		cDt2.set(Calendar.HOUR_OF_DAY, 23);
		cDt2.set(Calendar.MINUTE, 59);
		cDt2.set(Calendar.SECOND, 59);
		
		return vendaFornResumoDAO.carregarSemAutoServico(empresaFilter, vendedorFilter, tipoVendedorFilter, fornecedorFilter, cDt1.getTime(), cDt2.getTime());
	}

	public VendaFornResumo carregarSemAutoServico(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, FornecedorFB fornecedorFilter, Date dataFilter1, Date dataFilter2) {
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return vendaFornResumoDAO.carregarSemAutoServico(empresaFilter, vendedorFilter, tipoVendedorFilter, fornecedorFilter, dataFilter1, dataFilter2);
	}
	
	
}
