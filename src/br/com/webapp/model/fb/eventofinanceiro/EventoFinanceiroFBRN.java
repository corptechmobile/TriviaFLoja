package br.com.webapp.model.fb.eventofinanceiro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

import javax.faces.model.SelectItem;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.grupofinanceiro.GrupoFinanceiroFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;

public class EventoFinanceiroFBRN {

	private EventoFinanceiroFBDAO eventoFinanceiroFBDAO;
	
	public EventoFinanceiroFBRN() {
		this.eventoFinanceiroFBDAO = DAOFactoryFirebird.criarEventoFinanceiroFBDAO();
	}
	
	public EventoFinanceiroFB carregar(Integer eventoFinanceiroId) {
		return this.eventoFinanceiroFBDAO.carregar(eventoFinanceiroId);
	}
	
	public List<EventoFinanceiroFB> listar(String descricao) {
		return this.eventoFinanceiroFBDAO.listar(descricao);
	}

	public List<EventoFinanceiroFB> listar() {
		return this.eventoFinanceiroFBDAO.listar();
	}
	
	public List<SelectItem> montaDadosSelect(List<EventoFinanceiroFB> eventosFinanceiros, String string) {
		List<SelectItem> select = new ArrayList<SelectItem>();
		SelectItem item = null;
		if (eventosFinanceiros != null) {
			for (EventoFinanceiroFB eventoFinanceiroFB : eventosFinanceiros) {
				item = new SelectItem(eventoFinanceiroFB, eventoFinanceiroFB.getDescEventoFinanceiro());
				item.setEscape(false);
				select.add(item);
			}
		}
		 
		return select;
		
	}

	public void editar(EventoFinanceiroFB eventoFinanceiroFB) throws DAOException {
		this.eventoFinanceiroFBDAO.editar(eventoFinanceiroFB);
	}

	public List<EventoFinanceiroFB> listarAssociados(String grupoFinanceiro, String descricaoFilter, Integer id) {
		return this.eventoFinanceiroFBDAO.listarAssociados(grupoFinanceiro, descricaoFilter, id);
	}

	public List<EventoFinanceiroFB> listarDesassociados(String grupoFinanceiro, String descricaoFilter, Integer id) {
		return this.eventoFinanceiroFBDAO.listarDesassociados(grupoFinanceiro, descricaoFilter, id);
	}

	public List<EventoFinanceiroFB> listar(EmpresaFB empresaFilter, VendedorFB vendedorFilter, GrupoFinanceiroFB grupoFinanceiroFilter, Date dataFilter, Date dataFilter2) {
		if(dataFilter!=null){
			Calendar c = Calendar.getInstance();
			c.setTime(dataFilter);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			
			dataFilter = c.getTime();
		}
		
		if(dataFilter2!=null){
			Calendar c = Calendar.getInstance();
			c.setTime(dataFilter2);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			
			dataFilter2 = c.getTime();
		}			
		
		return this.eventoFinanceiroFBDAO.listar(empresaFilter, vendedorFilter, grupoFinanceiroFilter, dataFilter, dataFilter2);
	}

	public List<EventoFinanceiroFB> listarDetalhe(EmpresaFB empresaFilter, VendedorFB vendedorFilter, Date dataFilter, Date dataFilter2, String grupoFinanceiroId, Integer eventoFinanceiroId) {
		if(dataFilter!=null){
			Calendar c = Calendar.getInstance();
			c.setTime(dataFilter);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			
			dataFilter = c.getTime();
		}
		
		if(dataFilter2!=null){
			Calendar c = Calendar.getInstance();
			c.setTime(dataFilter2);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			
			dataFilter2 = c.getTime();
		}	
		
		return this.eventoFinanceiroFBDAO.listarDetalhe(empresaFilter, vendedorFilter, dataFilter, dataFilter2, grupoFinanceiroId, eventoFinanceiroId);
		
	}
	
}
	

