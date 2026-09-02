package br.com.webapp.model.fb.orcamentometa;

import java.util.List;

import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

public class OrcamentoMetaFBRN {

	private OrcamentoMetaFBDAO orcamentoMetaFBDAO;

	public OrcamentoMetaFBRN() {
		this.orcamentoMetaFBDAO = DAOFactoryFirebird.criarOrcamentoMetaFBDAO();
	}

	public OrcamentoMetaFB carregar(String anoMes, Integer id_pessoa_emp) throws RNException {
		
		if(anoMes != null && !"".equals(anoMes)){
			if(Integer.parseInt(anoMes.substring(4, 6))>12 || Integer.parseInt(anoMes.substring(4, 6))<1) { 
				throw new RNException(UtilMessage.mensagem("msg.erromesinvalido.orcamentometa"));
			}	
		}
		
		return this.orcamentoMetaFBDAO.carregar(anoMes, id_pessoa_emp);
	}

	public List<OrcamentoMetaFB> listar(String ano) {
		return this.orcamentoMetaFBDAO.listar(ano);
	}

	public Integer inserir(OrcamentoMetaFB orcamentoMetaFB) throws DAOException, RNException {
		//OrcamentoMetaFB existeFaixaCadastrada = orcamentoMetaFBDAO.validarFaixa(orcamentoMetaFB);
		//if(existeFaixaCadastrada == null) {
			return this.orcamentoMetaFBDAO.insert(orcamentoMetaFB);
		//}else{
			//throw new RNException(UtilMessage.mensagem("msg.erro.faixainvalida.comissaofaixa"));
		//}
	}

	public void alterar(OrcamentoMetaFB orcamentoMetaFB) throws DAOException, RNException {
		//OrcamentoMetaFB existeFaixaCadastrada = orcamentoMetaFBDAO.validarFaixa(orcamentoMetaFB);
		//if(existeFaixaCadastrada == null) {
			this.orcamentoMetaFBDAO.alterar(orcamentoMetaFB);
		//}else{
			//throw new RNException(UtilMessage.mensagem("msg.erro.faixainvalida.comissaofaixa"));
		//}

	}

	public void excluir(OrcamentoMetaFB orcamentoMetaFB) throws DAOException {
		this.orcamentoMetaFBDAO.excluir(orcamentoMetaFB);
	}

	public List<OrcamentoMetaFB> listar(String anoMes, Integer empresaId) {
		return this.orcamentoMetaFBDAO.listar(anoMes, empresaId);
	}

}
