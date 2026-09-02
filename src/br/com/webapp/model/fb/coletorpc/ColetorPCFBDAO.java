package br.com.webapp.model.fb.coletorpc;

import java.util.Date;
import java.util.List;
import java.util.Set;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.web.util.DAOException;

public interface ColetorPCFBDAO {
	public ColetorPCFB carregar(Integer id);
	public List<ColetorPCFB> listar(Integer empresaId);
	public List<ColetorPCFB> listar(Integer empresaId, Integer fornecedorId);
	public Integer insert(ColetorPCFB coletorPC) throws DAOException;
	public void update(ColetorPCFB coletorPC) throws DAOException;	
	public List<ColetorPCFBDTO> listar(EmpresaFB empresaFilter, String fornecedorFilter, String planilhaCegaIdFilter, String notafiscalFilter, String planilhaCegaFilter, String produtoFilter, Date data1Filter, Date data2Filter, boolean concluidoFilter, Set<EmpresaFB> empresas);
	public void rollBack();
	public void excluir(Integer coletorPCFBId) throws DAOException;
	public Integer verificarFinalizacaoAutomatica(Integer coletorPCFBId);
	public List<Integer> listarPendentesProcessar();
}
