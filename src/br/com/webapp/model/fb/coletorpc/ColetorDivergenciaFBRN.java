package br.com.webapp.model.fb.coletorpc;

import java.util.Date;
import java.util.List;

import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

public class ColetorDivergenciaFBRN {

	private ColetorDivergenciaFBDAO coletorPCDivergenciaFBDAO; 
	
	public ColetorDivergenciaFBRN() {
		this.coletorPCDivergenciaFBDAO = DAOFactoryFirebird.criarDivergenciaPCFB();
	}
	
	public ColetorDivergenciaFB carregar(){
		return this.coletorPCDivergenciaFBDAO.carregar();
	}
	
	
	public ColetorDivergenciaFB salvar(ColetorDivergenciaFB coletorPCDivergenciaFB) throws RNException{
		try {
			return this.coletorPCDivergenciaFBDAO.salvar(coletorPCDivergenciaFB);
		} catch (Exception e) {
			e.printStackTrace();
			
			rollBack();
			
			if(e instanceof RNException) {
				throw new RNException(e.getMessage());
			}else {
				throw new RNException(UtilMessage.mensagem("msg.erro.reabrir.planilhacega.integracao"));
			}
		}
	}
	
	public void excluir(ColetorDivergenciaFB coletorPCDivergenciaFB) throws RNException{
		try {
			this.coletorPCDivergenciaFBDAO.excluir(coletorPCDivergenciaFB);
		} catch (Exception e) {
			e.printStackTrace();
			
			rollBack();
			
			if(e instanceof RNException) {
				throw new RNException(e.getMessage());
			}else {
				throw new RNException(UtilMessage.mensagem("msg.erro.reabrir.planilhacega.integracao"));
			}
		}		
		
	}
	
	public void excluir(Integer divergenciaId) throws DAOException, RNException {
		try {
			this.coletorPCDivergenciaFBDAO.excluir(divergenciaId);
		} catch (Exception e) {
			e.printStackTrace();
			
			rollBack();
			
			if(e instanceof RNException) {
				throw new RNException(e.getMessage());
			}else {
				throw new RNException(UtilMessage.mensagem("msg.erro.reabrir.planilhacega.integracao"));
			}
		}		

		
	}
	
	public List<ColetorDivergenciaFB> listar(){
		return this.coletorPCDivergenciaFBDAO.listar();
	}
	
	public List<ColetorDivergenciaFB> listar(Integer divergenciaId){
		return this.coletorPCDivergenciaFBDAO.listar(divergenciaId);
	}

	public List<ColetorDivergenciaFB> listarPorUsuario(Integer usuarioId) {
		return this.coletorPCDivergenciaFBDAO.listarPorUsuario(usuarioId);
	}
	
	@SuppressWarnings("unused")
	public
	 void rollBack() {
		this.coletorPCDivergenciaFBDAO.rollBack();
	}

	
	
}
