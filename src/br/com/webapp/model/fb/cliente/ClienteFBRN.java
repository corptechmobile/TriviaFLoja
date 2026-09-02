package br.com.webapp.model.fb.cliente;

import java.util.Date;
import java.util.List;

import br.com.webapp.model.fb.cobrtipo.CobrTipoFBRN;
import br.com.webapp.model.fb.condpagto.CondPagtoFBRN;
import br.com.webapp.model.fb.formapagto.FormaPagtoFB;
import br.com.webapp.model.fb.formapagto.FormaPagtoFBRN;
import br.com.webapp.model.fb.fretetipo.FreteTipoFBRN;
import br.com.webapp.model.fb.movfisctipo.MovFiscTipoFBRN;
import br.com.webapp.model.fb.parametro.ParametroFB;
import br.com.webapp.model.fb.parametro.ParametroFBRN;
import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

public class ClienteFBRN {
	
	private ClienteFBDAO clienteFBDAO;
	
	public ClienteFBRN(){
		this.clienteFBDAO = DAOFactoryFirebird.criarClienteFBDAO();
	}
	
	public ClienteFB carregar(Integer clienteId) {
		ClienteFB clienteFB = this.clienteFBDAO.carregar(clienteId);
		if(clienteFB!=null) {
			clienteFB.setFreteTipo(new FreteTipoFBRN().carregar(clienteFB.getFreteTipoId()));
			clienteFB.setMovFiscTipo(new MovFiscTipoFBRN().carregar(clienteFB.getMovFiscTipoId()));
			//clienteFB.setFormaPagto(new FormaPagtoFBRN().carregar(FormaPagtoFB.FORMAPAGTO_AVISTA));
			clienteFB.setFormaPagto(new FormaPagtoFBRN().carregar(clienteFB.getFormaPagtoId()));
			clienteFB.setCondPagto(new CondPagtoFBRN().carregar(clienteFB.getCondPagtoId()));
			clienteFB.setCobrTipo(new CobrTipoFBRN().carregar(clienteFB.getCobrTipoId()));
		}
		return clienteFB;

	}

	public ClienteFB carregar(String cnpjCpf) {
		ClienteFB clienteFB = this.clienteFBDAO.carregar(cnpjCpf);
		if(this.clienteFBDAO.carregar(cnpjCpf)!=null) {
			clienteFB.setFreteTipo(new FreteTipoFBRN().carregar(clienteFB.getFreteTipoId()));
			clienteFB.setMovFiscTipo(new MovFiscTipoFBRN().carregar(clienteFB.getMovFiscTipoId()));
			//clienteFB.setFormaPagto(new FormaPagtoFBRN().carregar(FormaPagtoFB.FORMAPAGTO_AVISTA));
			clienteFB.setFormaPagto(new FormaPagtoFBRN().carregar(clienteFB.getFormaPagtoId()));
			clienteFB.setCondPagto(new CondPagtoFBRN().carregar(clienteFB.getCondPagtoId()));
			clienteFB.setCobrTipo(new CobrTipoFBRN().carregar(clienteFB.getCobrTipoId()));
		}
		return clienteFB;
	}

	public List<ClienteFB> listar(String descricaoFilter) {
		return this.clienteFBDAO.listar(descricaoFilter);
	}

	public Integer salvar(ClienteFB clienteFB) throws RNException {
		try {
			
			Integer clienteFBId = null;
			
			ClienteFB verCliente = this.carregar(clienteFB.getCnpjCpf());
			if(verCliente==null) {
				clienteFBId = this.insert(clienteFB);
			}else {
				
				if(clienteFB.getId()==null) {
					clienteFB.setId(verCliente.getId());
				}
				
				if(clienteFB.getEnderecoPrincipalId()==null) {
					clienteFB.setEnderecoPrincipalId(verCliente.getEnderecoPrincipalId());
				}
				
				if(clienteFB.getTelefonePrincipalId()==null) {
					clienteFB.setTelefonePrincipalId(verCliente.getTelefonePrincipalId());
				}
				
				clienteFBId = this.update(clienteFB);
			}
			
			return clienteFBId;
		
		} catch (Exception e) {
			e.printStackTrace();
			clienteFBDAO.rollback();
			throw new RNException(e.getMessage());
		}
	}

	private Integer insert(ClienteFB clienteFB) throws DAOException {
		return this.clienteFBDAO.insert(clienteFB);
	}
	
	private Integer update(ClienteFB clienteFB) throws DAOException {
		return this.clienteFBDAO.update(clienteFB);
	}

	public List<ClienteNaoPositivadoFBDTO> listarClientesNaoPositivados(Date dataFilter1, Date dataFilter2, String clienteFilter, String cidadeFilter, String bairroFilter, String numeroFilter) {
		return this.clienteFBDAO.listarClientesNaoPositivados(dataFilter1, dataFilter2, clienteFilter, cidadeFilter, bairroFilter, numeroFilter);
	}

	public List<ClienteFB> listarClienteTransferencia(String descricao, Integer isTransferencia) {
		return this.clienteFBDAO.listarClienteTransferencia(descricao, isTransferencia);
	}

	public void verificarLimiteCredito(ClienteFB cliente, PedVendaFB pedido) throws RNException {
			ClienteCreditoFBDTO clienteCredito = this.clienteFBDAO.verificarLimiteCredito(cliente.getId(), pedido.getId());
			if(clienteCredito != null && (clienteCredito.getLimiteCredito() <= 0d || clienteCredito.getSaldoDisponivel() <= 0d)) {
				throw UtilMessage.exceptionMensagem("msg.aviso.pedvenda.cliente.bloqueiocredito", null);
			}
    }

	public boolean validarCredito(ClienteFB cliente, PedVendaFB pedido) throws RNException {
		boolean temBloqueioCredito = false;

		ParametroFBRN parametroFBRN = new ParametroFBRN();
		ParametroFB param_IDCLIENTEPEDIDOAVULSO = parametroFBRN.carregar("IDCLIENTEPEDIDOAVULSO");
		//ParametroFB param_DupVencHojeBloqCredPV = parametroFBRN.carregar("DupVencHojeBloqCredPV");
		ParametroFB param_FDL_ID_PESSOA_CLI = parametroFBRN.carregar("FDL_ID_PESSOA_CLI");
		
		if((pedido.getMovFiscTipo() != null && pedido.getMovFiscTipo().getLancaCarCap() == 1) && 
				(param_IDCLIENTEPEDIDOAVULSO != null && !pedido.getClienteId().toString().equals(param_IDCLIENTEPEDIDOAVULSO.getValor())) && 
				  (param_FDL_ID_PESSOA_CLI != null && !pedido.getClienteId().toString().equals(param_FDL_ID_PESSOA_CLI.getValor())) && 
				    (pedido.getCondPagto() != null && pedido.getCondPagto().getDispContrCred() == 0)) {
			
			ClienteCreditoFBDTO clienteCredito = this.clienteFBDAO.verificarLimiteCredito(cliente.getId(), pedido.getId());
			if(clienteCredito != null && (clienteCredito.getPossuiChequeDev() == 1 || clienteCredito.getPossuiDupVenc() == 1)) {
				temBloqueioCredito = true;				
			}
			
			if(clienteCredito != null && ( clienteCredito.getLimiteCredito() == 0d || (clienteCredito.getLimiteCredito() > 0d && pedido.getValPedido() > clienteCredito.getSaldoDisponivel()))) {
				temBloqueioCredito = true;
			}
			
		}

		return temBloqueioCredito;	
		
	}


}
