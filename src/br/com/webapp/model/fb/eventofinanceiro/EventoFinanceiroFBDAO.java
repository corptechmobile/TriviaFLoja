package br.com.webapp.model.fb.eventofinanceiro;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.grupofinanceiro.GrupoFinanceiroFB;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.web.util.DAOException;

public interface EventoFinanceiroFBDAO {

	public EventoFinanceiroFB carregar(Integer Id);
	public List<EventoFinanceiroFB> listar(String descricao);
	public List<EventoFinanceiroFB> listar();
	public void editar(EventoFinanceiroFB eventoFinanceiroFB) throws DAOException;
	public List<EventoFinanceiroFB> listarAssociados(String grupoFinanceiro, String descricaoFilter, Integer id);
	public List<EventoFinanceiroFB> listarDesassociados(String grupoFinanceiro, String descricaoFilter, Integer id);
	public List<EventoFinanceiroFB> listar(EmpresaFB empresaFilter, VendedorFB vendedorFilter, GrupoFinanceiroFB grupoFinanceiroFilter, Date dataFilter, Date dataFilter2);
	public List<EventoFinanceiroFB> listarDetalhe(EmpresaFB empresaFilter, VendedorFB vendedorFilter, Date dataFilter, Date dataFilter2, String grupoFinanceiroId, Integer eventoFinanceiroId);
}
