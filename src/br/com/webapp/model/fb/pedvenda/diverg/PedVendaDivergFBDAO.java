package br.com.webapp.model.fb.pedvenda.diverg;

import java.util.List;

import br.com.webapp.model.fb.pedvenda.diverg.dto.PedVendaDivergFBDTO;
import br.com.webapp.web.util.DAOException;

public interface PedVendaDivergFBDAO {
	public Integer insert(PedVendaDivergFB pedVendaDivergFB) throws DAOException;
	public void update(PedVendaDivergFB pedVendaDivergFB) throws DAOException;
	public void updateLiberacao(PedVendaDivergFBDTO pedVendaDivergFB) throws DAOException;
	public void excluir(Integer pedVendaFBId, Integer pedVendaItemFBId) throws DAOException;
	public void excluir(Integer pedVendaFBId, Integer pedVendaItemFBId, int situacaoDiverg, int tipoDiverg) throws DAOException;
	public PedVendaDivergFB existDescontoLiberado(Integer pedVendaFBId, Integer pedVendaItemFBId, Integer condPagtoId, Double desconto);
	public PedVendaDivergFB existLoteLiberado(Integer pedVendaFBId, Integer pedVendaItemFBId);
	public PedVendaDivergFB existVendaSemEstoqueDispLiberado(Integer pedVendaFBId, Integer pedVendaItemFBId);
	public PedVendaDivergFB carregar(Integer id);
	public List<PedVendaDivergFB> listar();
//	public void liberar(Integer selecionadaId, List<PedVendaDivergFB> divergenciasSelected, String obsDivergencia, UsuarioFB usuarioLogado) throws RNException, DAOException;
//	public void naoliberar(Integer selecionadaId, List<PedVendaDivergFB> divergenciasSelected, String obsDivergencia, UsuarioFB usuarioLogado) throws RNException, DAOException;
	public List<PedVendaDivergFB> listar(Integer pedVendaFBId);
	public List<PedVendaDivergFBDTO> listarDTO(Integer pedVendaFBId);
	public List<PedVendaDivergFBDTO> listarToLiberar(Integer pedVendaFBId);
	public void rollback();
}
