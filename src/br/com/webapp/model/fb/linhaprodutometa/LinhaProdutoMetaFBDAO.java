package br.com.webapp.model.fb.linhaprodutometa;

import java.util.List;

import br.com.webapp.web.util.DAOException;

public interface LinhaProdutoMetaFBDAO {

	public void alterar(LinhaProdutoMetaFB linhaProdutoMetaFB) throws DAOException;
	public Integer insert(LinhaProdutoMetaFB linhaProdutoMetaFB) throws DAOException;
	public void rollback();
	public LinhaProdutoMetaFB salvar(LinhaProdutoMetaFB linhaProdutoMetaFB);
	void excluir(LinhaProdutoMetaFB linhaProdutoMetaFB) throws DAOException;
	public LinhaProdutoMetaFB carregar(Integer id);
	public List<LinhaProdutoMetaFB> listar(String anomes, Integer id_pessoa_emp);
	public List<LinhaProdutoMetaFB> listar(String anomes, Integer idPessoaEmp, Integer idLinhaProduto);
	public List<LinhaProdutoMetaFB> listar(String anomes, String anomesref, Integer empresa, Integer vendedor,Integer nivelLinhaProduto, boolean incluirDevolucao);
	public List<LinhaProdutoMetaFBDTO> listarMeta(String anoMes, Integer empresaId, Integer idVendedor, String idGestaoVendaMob, String visualizarPor, boolean incluirDevolucao);
	
}
