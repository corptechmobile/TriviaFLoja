package br.com.webapp.model.fb.cliente;

import java.util.Date;
import java.util.List;

import br.com.webapp.web.util.DAOException;

public interface ClienteFBDAO {

	public ClienteFB carregar(Integer clienteId);
	public ClienteFB carregar(String cnpjCpf);
	public Integer insert(ClienteFB clienteFB) throws DAOException;
	public Integer update(ClienteFB clienteFB) throws DAOException;
	public List<ClienteFB> listar(String descricaoFilter);
	public List<ClienteNaoPositivadoFBDTO> listarClientesNaoPositivados(Date dataFilter1, Date dataFilter2, String clienteFilter, String cidadeFilter, String bairroFilter, String numeroFilter);
	public List<ClienteFB> listarClienteTransferencia(String descricao, Integer isTransferencia);
	public ClienteCreditoFBDTO verificarLimiteCredito(Integer clienteId, Integer pedvendaId);
	public void rollback(); 


}
