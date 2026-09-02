package br.com.webapp.model.fb.coletorpc;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import br.com.coletor.PlanilhaCegaIntegracao;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;


public class ColetorPCFBRN {

	private ColetorPCFBDAO coletorPCFBDAO;

	public ColetorPCFBRN(Session session) {
		this.coletorPCFBDAO = DAOFactoryFirebird.criarColetorPCFB(session);
	}
	
	public ColetorPCFBRN() {
		this.coletorPCFBDAO = DAOFactoryFirebird.criarColetorPCFB();
	}

	public ColetorPCFB carregar(Integer coletorPCId) {
		return this.coletorPCFBDAO.carregar(coletorPCId);
	}

	public List<ColetorPCFB> listar(Integer empresaId){
		return this.coletorPCFBDAO.listar(empresaId);
	}
	
	public List<ColetorPCFBDTO> listar(EmpresaFB empresaFilter, String fornecedorFilter, String planilhaCegaIdFilter, String notafiscalFilter, String planilhaCegaFilter, String produtoFilter, Date data1Filter, Date data2Filter, boolean concluidoFilter, Set<EmpresaFB> empresas){
		data1Filter = Funcoes.dataFilter1(data1Filter);
		data2Filter = Funcoes.dataFilter2(data2Filter);
		return this.coletorPCFBDAO.listar(empresaFilter, fornecedorFilter, planilhaCegaIdFilter, notafiscalFilter, planilhaCegaFilter, produtoFilter, data1Filter, data2Filter, concluidoFilter, empresas);
	}
	
	public List<Integer> listarPendentesProcessar(){
		return this.coletorPCFBDAO.listarPendentesProcessar();
	}

	public ColetorPCFB novo(UsuarioFB usuario) {
		ColetorPCFB model = new ColetorPCFB();
		model.setDtCriacao(new Date());
		model.setInformarLote(false);

		model.setStatus(ColetorPCFB.STATUS_EM_ABERTO);
		model.setUsuarioId(usuario.getId());

		return model;
	}
	
	public void excluir(Integer coletorPCFBId) throws DAOException{
		 this.coletorPCFBDAO.excluir(coletorPCFBId);
	}	

	public ColetorPCFB salvar(ColetorPCFB coletorPC, UsuarioFB usuarioFB) throws DAOException {
		if(coletorPC.getDtCriacao()==null){
			coletorPC.setDtCriacao(new Date());
		}

		if(coletorPC.getId()==null) {
			coletorPC = this.carregar(this.coletorPCFBDAO.insert(coletorPC));
		}else {
			this.coletorPCFBDAO.update(coletorPC);
			coletorPC = this.carregar(coletorPC.getId());
		}

		return coletorPC;
	}

	public void liberar(ColetorPCFB coletorPCFB) throws DAOException {
		coletorPCFB.setDtLiberacao(new Date());
		coletorPCFB.setStatus(ColetorPCFB.STATUS_LIBERADO);
		this.coletorPCFBDAO.update(coletorPCFB);
	}
	
	public void finalizar(ColetorPCFB coletorPCFB, UsuarioFB usuarioLogado, Session session) throws RNException {
		Double totalQtdNF = 0.0;
		Double totalQtdLeituras = 0.0;
		Double totalQtdAvarias = 0.0;
		Double totalQtdDevolvida = 0.0;
		boolean warnShelfLife = false;
		
		ColetorPCItemFBRN coletorPCItemFBRN = session != null ? new ColetorPCItemFBRN(session) : new ColetorPCItemFBRN();
		
		List<ColetorPCItemFB> itens = coletorPCItemFBRN.listar(coletorPCFB.getId());
		
		Set<ColetorPCDivergFB> divergencias = new HashSet<ColetorPCDivergFB>(0);
		
		try {
			
			totalQtdNF = 0.0;
			totalQtdLeituras = 0.0;
			totalQtdAvarias = 0.0;
			boolean warnItemQtd = false;
			for(ColetorPCItemFB rs : itens){
				
				totalQtdNF += rs.getQuantidade();
				totalQtdLeituras += rs.getQtdLeitura();
				totalQtdAvarias += rs.getQtdAvaria();
				totalQtdDevolvida += rs.getQtdDevolvida();
	
				if(rs.getProdutoPrazo() != null && rs.getTolShelfLife() != null && rs.getControlaLote() == 1){
					if(!(rs.getProdutoPrazo() > rs.getTolShelfLife())){
						warnShelfLife = true;
					}
				}
				
				if(!rs.getQuantidade().equals(rs.getTotLeituras())) {
					warnItemQtd = true;
				}
	
			}
			
			if((totalQtdLeituras+totalQtdAvarias+totalQtdDevolvida)>totalQtdNF) {
				throw new RNException(UtilMessage.mensagem("msg.erro.qtdleituras.maiorqueanota.contagem"));
			}
			
			// validar
			// 1. qtdNF x QTd Conf
			if(!totalQtdNF.equals(totalQtdLeituras) || warnItemQtd){
				ColetorPCDivergFB diverg1 = new ColetorPCDivergFBRN().novo(coletorPCFB.getId(), ColetorDivergenciaFB.QTDNF_QTDCONFERENCIA);
				if(diverg1.getDtAprovacao() == null){
					divergencias.add(diverg1);
				}
			}
			
			// 2. Avaria
			if(totalQtdAvarias>0){
				ColetorPCDivergFB diverg2 = new ColetorPCDivergFBRN().novo(coletorPCFB.getId(), ColetorDivergenciaFB.AVARIA);
				if(diverg2.getDtAprovacao() == null){
					divergencias.add(diverg2);
				}
			}
			
			// 3. Shelf Life
			if(warnShelfLife){
				ColetorPCDivergFB diverg3 = new ColetorPCDivergFBRN().novo(coletorPCFB.getId(), ColetorDivergenciaFB.SHEL_FLIFE);
				if(diverg3.getDtAprovacao() == null){
					divergencias.add(diverg3);
				}
			}
			
			// 4. Final do mes (da de entrada é 30 ou 31)
			Calendar c = Calendar.getInstance();
			c.setTime(coletorPCFB.getDtInicio());
			if(c.get(Calendar.DATE) == Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH) 
					|| c.get(Calendar.DATE) == (Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)-1)){
				ColetorPCDivergFB diverg4 = new ColetorPCDivergFBRN().novo(coletorPCFB.getId(), ColetorDivergenciaFB.FINAL_DE_MES);
				if(diverg4.getDtAprovacao() == null){
					divergencias.add(diverg4);
				}
			}
			
			if(divergencias.size()>0){
				coletorPCFB.setStatus(ColetorPCFB.STATUS_EM_CONFERENCIA);
				coletorPCFB.setDtTermino(new Date());
				this.salvar(coletorPCFB, usuarioLogado);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			rollBack();
			
			if(e instanceof RNException) {
				throw new RNException(e.getMessage());
			}else {
				throw new RNException(UtilMessage.mensagem("msg.erro.finalizar.planilhacega"));
			}
			
		}
		
		if(divergencias.size()==0){
			
			if(coletorPCFB.getIdErp()!=null) {
				throw new RNException(UtilMessage.mensagem("msg.erro.transmitir.planilhacega.integracao.firebird"));
			}
			
			PlanilhaCegaIntegracao integracao = new PlanilhaCegaIntegracao();
			try {
				Integer idErp = integracao.finalizarPlanilhaCega(coletorPCFB.getId(), coletorPCFB.getEmpresaId());
				
				coletorPCFB.setStatus(ColetorPCFB.STATUS_FINALIZADO);
				coletorPCFB.setDtTermino(new Date());
				coletorPCFB.setIdErp(idErp);
				coletorPCFB.setIntegrada(true);
				
				this.salvar(coletorPCFB, usuarioLogado);
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
				rollBack();
				
				//integracao.rollBack();
				
				if(e instanceof RNException) {
					throw new RNException(e.getMessage());
				}else {
					throw new RNException(UtilMessage.mensagem("msg.erro.finalizar.planilhacega"));
				}
				
			}
		}
	}
	
	public void reAbrirConferencia(Integer coletorId, UsuarioFB usuarioLogado) throws RNException {
		PlanilhaCegaIntegracao integracao = new PlanilhaCegaIntegracao();
		try {
			
			ColetorPCFB planilhaCega = this.carregar(coletorId);
			
			integracao.reAbrirConferencia(planilhaCega.getIdErp(), usuarioLogado);
			
			ColetorDivergenciaFBRN divRN = new ColetorDivergenciaFBRN();
			divRN.excluir(planilhaCega.getId());
			
			planilhaCega.setStatus(ColetorPCFB.STATUS_EM_CONFERENCIA);
			planilhaCega.setIntegrada(false);
			planilhaCega.setDtTermino(null);
			this.salvar(planilhaCega, usuarioLogado);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			rollBack();
			
			//integracao.rollBack();
			
			if(e instanceof RNException) {
				throw new RNException(e.getMessage());
			}else {
				throw new RNException(UtilMessage.mensagem("msg.erro.reabrir.planilhacega.integracao"));
			}
		}		
	}	

	public Integer integracao(Integer planilhaCegaId, EmpresaFB empresa) {
		// TODO Auto-generated method stub
		return null;
	}

	public void liberar(ColetorPCFB selecionada, UsuarioFB usuarioLogado) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean verificarFinalizacaoAutomatica(Integer coletorPCFBId) {
		return this.coletorPCFBDAO.verificarFinalizacaoAutomatica(coletorPCFBId) == 1 ? true : false;
	}
	
	public void rollBack() {
		this.coletorPCFBDAO.rollBack();
	}

}
