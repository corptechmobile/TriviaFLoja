package br.com.webapp.model.fb.comissaofaixadesc;

import java.util.List;

import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

public class ComissaoFaixaDescFBRN {

	private ComissaoFaixaDescFBDAO comissaoFaixaDescFBDAO;
	
	public ComissaoFaixaDescFBRN() {
		this.comissaoFaixaDescFBDAO = DAOFactoryFirebird.criarComissaoFaixaDescFBDAO();
	}
	
	public ComissaoFaixaDescFB carregar(Integer comissaoFaixaDescId) {
		return this.comissaoFaixaDescFBDAO.carregar(comissaoFaixaDescId);
	}
	
	public List<ComissaoFaixaDescFB> listar(String descLinhaProd, Double descfaixa1, Double descfaixa2) {
		return this.comissaoFaixaDescFBDAO.listar(descLinhaProd, descfaixa1, descfaixa2);
	}
	
	public Integer inserir(ComissaoFaixaDescFB comissaoFaixaDescFB) throws DAOException, RNException {
		ComissaoFaixaDescFB existeFaixaCadastrada = comissaoFaixaDescFBDAO.validarFaixa(comissaoFaixaDescFB);
		if(existeFaixaCadastrada == null) {
			return this.comissaoFaixaDescFBDAO.insert(comissaoFaixaDescFB);
		}else{
			throw new RNException(UtilMessage.mensagem("msg.erro.faixainvalida.comissaofaixa")); 
		}
	}
	
	public void alterar(ComissaoFaixaDescFB comissaoFaixaDescFB) throws DAOException, RNException {
		ComissaoFaixaDescFB existeFaixaCadastrada = comissaoFaixaDescFBDAO.validarFaixa(comissaoFaixaDescFB);
		if(existeFaixaCadastrada.getId().equals(comissaoFaixaDescFB.getId())) {
			this.comissaoFaixaDescFBDAO.alterar(comissaoFaixaDescFB);
		}else{
			throw new RNException(UtilMessage.mensagem("msg.erro.faixainvalida.comissaofaixa")); 
		}

		
	}
	
	public void excluir(Integer comissaoFaixaDescId) throws DAOException {
		this.comissaoFaixaDescFBDAO.excluir(comissaoFaixaDescId);
	}
	
}
