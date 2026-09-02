package br.com.webapp.model.fb.romaneio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import br.com.coletor.PlanilhaCegaIntegracao;
import br.com.coletor.dao.DAOColetorOrdSep;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;


public class RomaneioFBRN {

	private RomaneioFBDAO romaneioFBDAO;

	public RomaneioFBRN() {
		this.romaneioFBDAO = DAOFactoryFirebird.criarRomaneioFB();
	}

	public RomaneioFB carregar(Integer romaneioId) {
		return this.romaneioFBDAO.carregar(romaneioId);
	}

	public void excluir(Integer romaneioFBId) throws DAOException{
		 this.romaneioFBDAO.excluir(romaneioFBId);
		
	}	
	
	public void cancelar(RomaneioFB romaneio, UsuarioFB usuarioLogado) throws DAOException {
		romaneio.setMomentoCanc(new Date());
		this.romaneioFBDAO.cancelar(romaneio, usuarioLogado);
		
	}
	
	public List<RomaneioFB> listar(String numProcTranspFilter, String numRomaneioFilter, String usuarioFilter, String produtoFilter, Date data1Filter, Date data2Filter, List<Integer> situacaoFilter){
		data1Filter = Funcoes.dataFilter1(data1Filter);
		data2Filter = Funcoes.dataFilter2(data2Filter);
		return this.romaneioFBDAO.listar(numProcTranspFilter, numRomaneioFilter, usuarioFilter, produtoFilter, data1Filter, data2Filter, situacaoFilter);
	}


	public RomaneioFB salvar(RomaneioFB romaneio, UsuarioFB usuarioFB) throws DAOException {
		this.romaneioFBDAO.update(romaneio);
		romaneio = this.carregar(romaneio.getRomaneioId());

		return romaneio;
	}

	public void finalizar(RomaneioFB romaneioFB, UsuarioFB usuarioLogado) throws RNException {
		
	}
	
	public void integracaoRomaneio(Integer romaneioId) throws DAOException {
		List<RomaneioIntegracaoDTOFB> listRomaneioIntegracaoDTOFB = listarParaIntegracao(romaneioId);
		
		for(RomaneioIntegracaoDTOFB rs : listRomaneioIntegracaoDTOFB){
			Integer seqId = this.romaneioFBDAO.integracao(rs.getOrdemcarregId());
			new RomaneioItemFBRN().integracao(seqId, romaneioId, rs.getOrdemcarregId());
		}
	}

	private List<RomaneioIntegracaoDTOFB> listarParaIntegracao(Integer romaneioId) {
		return this.romaneioFBDAO.listarParaIntegracao(romaneioId);
	}

	public void liberar(RomaneioFB selecionada, UsuarioFB usuarioLogado) {
		// TODO Auto-generated method stub
		
	}

	public void atualizarStatus(Integer romaneioId, String statusEmConferencia) throws DAOException {
		this.romaneioFBDAO.atualizarStatus(romaneioId, statusEmConferencia);
	}

	@SuppressWarnings("unused")
	public
	 void rollBack() {
		this.romaneioFBDAO.rollBack();
	}

	

	


}
