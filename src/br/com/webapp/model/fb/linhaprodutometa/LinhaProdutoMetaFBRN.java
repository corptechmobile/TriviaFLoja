package br.com.webapp.model.fb.linhaprodutometa;

import java.util.List;

import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.RNException;

public class LinhaProdutoMetaFBRN {

	private LinhaProdutoMetaFBDAO linhaProdutoMetaFBDAO;

	public LinhaProdutoMetaFBRN() {
		this.linhaProdutoMetaFBDAO = DAOFactoryFirebird.criarLinhaProdutoMetaFBDAO();
	}

	public List<LinhaProdutoMetaFB> listar(String anomes, Integer id_pessoa_emp) {
		return this.linhaProdutoMetaFBDAO.listar(anomes, id_pessoa_emp);
	}
 
	public Integer inserir(LinhaProdutoMetaFB linhaProdutoMetaFB) throws DAOException, RNException {
			return this.linhaProdutoMetaFBDAO.insert(linhaProdutoMetaFB);
	}

	public void alterar(LinhaProdutoMetaFB linhaProdutoMetaFB) throws DAOException, RNException {
			this.linhaProdutoMetaFBDAO.alterar(linhaProdutoMetaFB);

	}

	public void excluir(LinhaProdutoMetaFB linhaProdutoMetaFB) throws DAOException {
		this.linhaProdutoMetaFBDAO.excluir(linhaProdutoMetaFB);
	}

	public List<LinhaProdutoMetaFB> listar(String anoMes, String anoMesRef, Integer empresaId, Integer vendedorId, Integer nivelLinhaProduto, boolean incluirDevolucao){
		return this.linhaProdutoMetaFBDAO.listar(anoMes, anoMesRef, empresaId, vendedorId, nivelLinhaProduto, incluirDevolucao);
	}

	public List<LinhaProdutoMetaFBDTO> listarMeta(String anoMes, Integer empresaId, Integer idVendedor, String idGestaoVendaMob, String visualizarPor, boolean incluirDevolucao) {
		return this.linhaProdutoMetaFBDAO.listarMeta(anoMes, empresaId, idVendedor, idGestaoVendaMob, visualizarPor, incluirDevolucao);
	}

}
