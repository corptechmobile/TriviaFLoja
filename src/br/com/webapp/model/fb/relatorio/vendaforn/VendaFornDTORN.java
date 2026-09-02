package br.com.webapp.model.fb.relatorio.vendaforn;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.fornecedor.FornecedorFB;
import br.com.webapp.model.fb.tipovendedor.TipoVendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.Funcoes;

public class VendaFornDTORN {

	private VendaFornDTODAO vendaFornDTODAO;
	
	public VendaFornDTORN() {
		this.vendaFornDTODAO = DAOFactoryFirebird.criarVendaFornDTO();
	}
	
	public List<VendaFornDTO> listarAutoServico(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, String porFilter, Date dataFilter1, Date dataFilter2){
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return vendaFornDTODAO.listarAutoServico(empresaFilter, vendedorFilter, tipoVendedorFilter, porFilter, dataFilter1, dataFilter2);
	}
	
	public List<VendaFornDTO> listarPedido(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, String porFilter, Date dataFilter1, Date dataFilter2){
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return vendaFornDTODAO.listarPedido(empresaFilter, vendedorFilter, tipoVendedorFilter, porFilter, dataFilter1, dataFilter2);
	}
	
	public List<VendaFornDTO> listarAmbos(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, String porFilter, Date dataFilter1, Date dataFilter2){
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return vendaFornDTODAO.listarAmbos(empresaFilter, vendedorFilter, tipoVendedorFilter, porFilter,dataFilter1, dataFilter2);
	}

	public List<VendaFornDTO> listarPedidoSemAutoServico(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, String porFilter, Date dataFilter1,Date dataFilter2) {
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return vendaFornDTODAO.listarPedidoSemAutoServico(empresaFilter, vendedorFilter, tipoVendedorFilter, porFilter, dataFilter1, dataFilter2);
	}

	public List<VendaFornDTO> dashBoardPorMes(Integer empresaFilter, Integer vendedorFilter, TipoVendedorFB tipoVendedorFilter, Date dataFilter1, Date dataFilter2, String vendasPorFilter, String segmentoFilter) {
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		Calendar cDtMesAnt = Calendar.getInstance();
		cDtMesAnt.setTime(dataFilter1);
		cDtMesAnt.set(Calendar.DAY_OF_MONTH, 1);
		cDtMesAnt.set(Calendar.HOUR_OF_DAY, 0);
		cDtMesAnt.set(Calendar.MINUTE, 0);
		cDtMesAnt.set(Calendar.SECOND, 0);
		cDtMesAnt.add(Calendar.MONTH, -1);
		
		Calendar cDtMesAnt2 = Calendar.getInstance();
		cDtMesAnt2.setTime(dataFilter2);
		cDtMesAnt2.set(Calendar.HOUR_OF_DAY, 23);
		cDtMesAnt2.set(Calendar.MINUTE, 59); 
		cDtMesAnt2.set(Calendar.SECOND, 59);
		cDtMesAnt2.add(Calendar.MONTH, -1);	

		Calendar cDtAnoAnt = Calendar.getInstance();
		cDtAnoAnt.setTime(dataFilter1);
		cDtAnoAnt.set(Calendar.DAY_OF_MONTH, 1);
		cDtAnoAnt.set(Calendar.HOUR_OF_DAY, 0);
		cDtAnoAnt.set(Calendar.MINUTE, 0);
		cDtAnoAnt.set(Calendar.SECOND, 0);
		cDtAnoAnt.add(Calendar.YEAR, -1);
		
		Calendar cDtAnoAnt2 = Calendar.getInstance();
		cDtAnoAnt2.setTime(dataFilter2);
		cDtAnoAnt2.set(Calendar.HOUR_OF_DAY, 23);
		cDtAnoAnt2.set(Calendar.MINUTE, 59); 
		cDtAnoAnt2.set(Calendar.SECOND, 59);
		cDtAnoAnt2.add(Calendar.YEAR, -1);	

		return vendaFornDTODAO.dashBoardPorMes(empresaFilter, vendedorFilter, tipoVendedorFilter, dataFilter1, dataFilter2, cDtMesAnt.getTime(), cDtMesAnt2.getTime(), cDtAnoAnt.getTime(), cDtAnoAnt2.getTime(), vendasPorFilter, segmentoFilter);
	}
	
	public List<VendaFornDTO> dashBoardPorMesAno(Integer empresaFilter, Integer vendedorFilter, TipoVendedorFB tipoVendedorFilter, Date dataFilter1, Date dataFilter2, String vendasPorFilter, String segmentoFilter) {
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		Calendar cDtMesAnt = Calendar.getInstance();
		cDtMesAnt.setTime(dataFilter1);
		cDtMesAnt.set(Calendar.DAY_OF_MONTH, 1);
		cDtMesAnt.set(Calendar.HOUR_OF_DAY, 0);
		cDtMesAnt.set(Calendar.MINUTE, 0);
		cDtMesAnt.set(Calendar.SECOND, 0);
		cDtMesAnt.add(Calendar.MONTH, -1);
		
		Calendar cDtMesAnt2 = Calendar.getInstance();
		cDtMesAnt2.setTime(cDtMesAnt.getTime());
		cDtMesAnt2.add(Calendar.MONTH, 1);	
		cDtMesAnt2.add(Calendar.DAY_OF_MONTH, -1);	
		cDtMesAnt2.set(Calendar.HOUR_OF_DAY, 23);
		cDtMesAnt2.set(Calendar.MINUTE, 59); 
		cDtMesAnt2.set(Calendar.SECOND, 59);
		System.out.println("cDtMesAnt2: "+cDtMesAnt2.getTime());
		
		Calendar cDtAnoAnt = Calendar.getInstance();
		cDtAnoAnt.setTime(dataFilter1);
		cDtAnoAnt.set(Calendar.DAY_OF_MONTH, 1);
		cDtAnoAnt.set(Calendar.HOUR_OF_DAY, 0);
		cDtAnoAnt.set(Calendar.MINUTE, 0);
		cDtAnoAnt.set(Calendar.SECOND, 0);
		cDtAnoAnt.add(Calendar.YEAR, -1);
		
		Calendar cDtAnoAnt2 = Calendar.getInstance();
		cDtAnoAnt2.setTime(cDtAnoAnt.getTime());
		cDtAnoAnt2.add(Calendar.MONTH, 1);
		cDtAnoAnt2.add(Calendar.DAY_OF_MONTH, -1);
		cDtAnoAnt2.set(Calendar.HOUR_OF_DAY, 23);
		cDtAnoAnt2.set(Calendar.MINUTE, 59); 
		cDtAnoAnt2.set(Calendar.SECOND, 59);
		System.out.println("cDtMesAnt2: "+cDtAnoAnt2.getTime());

		return vendaFornDTODAO.dashBoardPorMesAno(empresaFilter, vendedorFilter, tipoVendedorFilter, dataFilter1, dataFilter2, cDtMesAnt.getTime(), cDtMesAnt2.getTime(), cDtAnoAnt.getTime(), cDtAnoAnt2.getTime(), vendasPorFilter, segmentoFilter);
	}

	public List<VendaFornDTO> listarPorSegmento(EmpresaFB empresaFilter, VendedorFB vendedorFilter, TipoVendedorFB tipoVendedorFilter, FornecedorFB fornecedorFilter, String porFilter, Date dataFilter1, Date dataFilter2, String segmentoFilter, String vendasPorFilter) {
		dataFilter1 = Funcoes.dataFilter1(dataFilter1);
		dataFilter2 = Funcoes.dataFilter2(dataFilter2);
		return vendaFornDTODAO.listarPorSegmento(empresaFilter, vendedorFilter, tipoVendedorFilter, fornecedorFilter, porFilter, dataFilter1, dataFilter2, segmentoFilter, vendasPorFilter);
	}
	

}
