package br.com.webapp.model.fb.coletor;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.web.util.DAOException;

public interface ColetorInvFBDAO {
	public ColetorInvFB carregar(Integer id);
	public List<ColetorInvFB> listar();
	public ColetorInvFB verificarInvAbertoEmpresa(Integer empresaId);
	public ColetorInvFB salvar(ColetorInvFB inventario);
	public Integer insert(ColetorInvFB inventario) throws DAOException;
	public void update(ColetorInvFB inventario) throws DAOException;
	public void excluir(Integer inventarioId) throws DAOException;
	public List<ColetorInvFB> listar(EmpresaFB empresaFilter, Date data1Filter, Date data2Filter, boolean concluidoFilter);
	public Integer criarInventario(ColetorInvFB coletorInvFB) throws DAOException;
	public void incluirItensInventario(Integer inventarioId, Integer coletorInvId) throws DAOException;
	public void inserirPosicaoEstoque(Integer inventarioId, Integer coletorInvId) throws DAOException;
	public void atualizarProdutoLocalidade(Integer inventarioId) throws DAOException;
	public void inserirContagens(Integer inventarioId) throws DAOException;
	public void finalizarInventario(Integer coletorInvId) throws DAOException;
	public void atualizarEmbFechVenda(Integer coletorInvId) throws DAOException;
	public void rollBack();

}
