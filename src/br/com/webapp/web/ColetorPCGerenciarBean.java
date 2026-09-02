package br.com.webapp.web;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.TabCloseEvent;

import br.com.webapp.model.fb.coletorpc.ColetorDivergenciaFB;
import br.com.webapp.model.fb.coletorpc.ColetorDivergenciaFBRN;
import br.com.webapp.model.fb.coletorpc.ColetorPCDivergFB;
import br.com.webapp.model.fb.coletorpc.ColetorPCDivergFBRN;
import br.com.webapp.model.fb.coletorpc.ColetorPCFB;
import br.com.webapp.model.fb.coletorpc.ColetorPCFBRN;
import br.com.webapp.model.fb.coletorpc.ColetorPCItemFB;
import br.com.webapp.model.fb.coletorpc.ColetorPCItemFBRN;
import br.com.webapp.model.fb.coletorpc.contagem.ColetorPCFBContagem;
import br.com.webapp.model.fb.coletorpc.contagem.ColetorPCFBContagemRN;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.fornecedor.FornecedorFB;
import br.com.webapp.model.fb.fornecedor.FornecedorFBRN;
import br.com.webapp.model.fb.nfcompra.NFCompraFB;
import br.com.webapp.model.fb.nfcompra.NFCompraFBRN;
import br.com.webapp.model.fb.produtocb.ProdutoCBFB;
import br.com.webapp.model.fb.produtocb.ProdutoCBFBRN;
import br.com.webapp.model.fb.usuariocoletordiverg.UsuarioColetorDivergFB;
import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilData;
import br.com.webapp.web.util.UtilMessage;

@ManagedBean(name="coletorPCGerenciarBean")
@SessionScoped
public class ColetorPCGerenciarBean implements Serializable {

	private static final long serialVersionUID = -3590909713841298628L;

	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private String integracao;
	
	private boolean tabVisualizarLeituras;
	private boolean tabAssociarCodigoBarras;
	
	private boolean disableExcluirLeituras;
	private boolean renderedBtnFinalizar;
	
	private Integer selecionadaId;
	private ColetorPCFB selecionada;
	private EmpresaFB empresa;
	private FornecedorFB fornecedor;
	private ColetorPCItemFB  planilhaCegaItemMovDTO;
	private ColetorPCItemFB planilhaCegaItemMov;
	private ColetorPCFBContagem coletorPCFBContagem;
	//private CodigoBarraDTO codigoBarraDTO;
	private ProdutoCBFB produtoCB;
	private Integer produtoSelecionadoId;
	
	private Boolean controlalote; // assoc produto x codbarras
	private Date dtVencLot; // assoc produto x codbarras
	private String codigolote; // assoc produto x codbarras
	
	private List<NFCompraFB> listaNFCompra;
	private List<ColetorPCItemFB> itens;
	private List<ColetorPCFBContagem> itensMov;
	//private List<CodigoBarraDTO> listaCodBarras;
	
	private List<ColetorPCDivergFB> listaDivergencia;
	private boolean btnTransmitir;
	
	private Double totalQtdNF;
	private Double totalQtdLeituras;
	private Double totalQtdAvarias;
	private Double totalQtdDevolvida;
	
	
	
	@PostConstruct
	public void init(){ 
		//this.integracao = Funcoes.INTEGRACAO;
	}
	
	public void gerenciar(){
		tabVisualizarLeituras = false;
		tabAssociarCodigoBarras = false;
		
		//planilhaCegaItemMovDTO = null;
		//codigoBarraDTO = null;
		//listaCodBarras = null;
		selecionada = new ColetorPCFBRN().carregar(selecionadaId);
		empresa = new EmpresaFBRN().carregar(selecionada.getEmpresaId());
		fornecedor = new FornecedorFBRN().carregar(selecionada.getFornecedorId());
		
		itensMov = new ColetorPCFBContagemRN().listar(selecionadaId, false);
		listaNFCompra=null;
		itens = null;
		
		buscarDivergencias();
		
		totalQtdNF = 0.0;
		totalQtdLeituras = 0.0;
		totalQtdAvarias = 0.0;
		totalQtdDevolvida = 0.0;
		
	}
	
	private void buscarDivergencias(){
		
		if(selecionada!=null) {
			btnTransmitir = false;
			boolean verDiverg = false;
			listaDivergencia = new ColetorPCDivergFBRN().listar(selecionadaId);
			if(listaDivergencia.size()>0){
				for(ColetorPCDivergFB rs : listaDivergencia){
					if(rs.getDtAprovacao()==null){
						verDiverg = true;
					}
				}
				
				if(verDiverg){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", UtilMessage.mensagem("msg.planilhacega.com.divergencia")));
					
				}
			}
			
			if(verDiverg==false && ColetorPCFB.STATUS_FINALIZADO.equals(selecionada.getStatus())){
				btnTransmitir = true;
			}
			
		}
		
	}
	
	public boolean verAprovarDivergencia(ColetorPCDivergFB divergencia){
		
		if(divergencia.getDtAprovacao()!=null){
			return true;
		}
		
		List<ColetorDivergenciaFB> listaDivergencias = new ColetorDivergenciaFBRN().listarPorUsuario(contextoBean.getUsuarioLogado().getId()); 
		
		for(ColetorDivergenciaFB rs : listaDivergencias){
			if(rs.getDivergenciaId().equals(divergencia.getDivergenciaId())){
				return false;
			}
		}
		
		return true;
		//return false;
		
	}
	
	public void aprovarDivergencia(ColetorPCDivergFB divergencia){
		try {
			
			ColetorPCDivergFBRN divergenciaRN = new ColetorPCDivergFBRN();
			divergenciaRN.aprovar(divergencia, contextoBean.getUsuarioLogado());
			
			gerenciar();
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		}
		
		RequestContext.getCurrentInstance().execute("PF('tabViewItensMov').select(0);");
	}
	
//	public void transferirLeiturasEntrePlanilhas(Integer planilhaCegaIdDestino) {
//		try {
//			
//			ColetorPCFBRN planilhaCegaRN = new ColetorPCFBRN();
//			planilhaCegaRN.transferirLeiturasEntrePlanilhas(selecionada.getId(), planilhaCegaIdDestino);
//			
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Leituras transferidas com sucesso!"));
//			
//		} catch (Exception e) {
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
//		}
//	}
	
	public void atualizar(){
		selecionada = null;
		gerenciar();
		RequestContext.getCurrentInstance().execute("PF('tabViewItensMov').select(0);");
	}
	
	public void finalizar(){
		
		try {

			ColetorPCFBRN planilhaCegaRN = new ColetorPCFBRN();
			planilhaCegaRN.finalizar(selecionada, contextoBean.getUsuarioLogado(), null);
			
			gerenciar();
			
			if(btnTransmitir){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Planilha Cega finalizada!"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		}
		
		RequestContext.getCurrentInstance().execute("PF('tabViewItensMov').select(0);");
		
	}
	
	public void transmitir() {
		try {
			ColetorPCFBRN planilhaCegaRN = new ColetorPCFBRN();
			planilhaCegaRN.finalizar(selecionada, contextoBean.getUsuarioLogado(), null);
			
			gerenciar();
			
			if(btnTransmitir){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Planilha Cega transmitida!"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		}
		
		RequestContext.getCurrentInstance().execute("PF('tabViewItensMov').select(0);");
	}
	
	public void reAbrirConferencia(){
		try {
			
			ColetorPCFBRN coletorPCFBRN = new ColetorPCFBRN();
			coletorPCFBRN.reAbrirConferencia(selecionada.getId(), contextoBean.getUsuarioLogado());
			
			selecionada = null;
			gerenciar();
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, ResourceBundle.getBundle(FacesContext.getCurrentInstance().getApplication().getMessageBundle()).getString("msg.liberada.planilhacega")));
			//RNException
		} catch (RNException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
			e.printStackTrace();
		}
		
		RequestContext.getCurrentInstance().execute("PF('tabViewItensMov').select(0);");
		
	}
	
	public void visualizarLeituras(){
		itensMov = null;
		tabVisualizarLeituras = true;
		tabAssociarCodigoBarras = false;
	}
	
	public void associarCodigoBarras(){
		produtoCB = null;
		tabVisualizarLeituras = false;
		tabAssociarCodigoBarras = true;
		//codigoBarraDTO = null;
		//listaCodBarras = null;
		
		codigolote = null;
		dtVencLot = null;
		controlalote = false;
	}
	
	public void confirmarAssocProduto(){
		
		try {
			ProdutoCBFBRN produtoCBRN = new ProdutoCBFBRN();
			//produtoCBRN.associarInColetorPCFB(selecionada, produtoCB, codigolote, dtVencLot);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Código de Barras Associado!"));
			
			tabAssociarCodigoBarras = false;
			itens = null;
			//listaCodBarras = null;
			
			codigolote = null;
			dtVencLot = null;
			controlalote = false;
			
			RequestContext.getCurrentInstance().execute("PF('tabViewItensMov').select(0);");
			RequestContext.getCurrentInstance().execute("PF('tabViewItensMov').remove(1);");
			
		} catch (Exception e) {
			
			selectCodigoBarras();
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
			e.printStackTrace();
		}
		
	}
	
	public Integer produtoPrazo(Date dtVencLot){
		return UtilData.daysBetweenDates(selecionada.getDtLiberacao(), dtVencLot);
	}
	
	public Date produtoDtShelflite(Integer produtoPrazo) {
		if(selecionada.getDtLiberacao() != null && produtoPrazo != null && produtoPrazo > 0) {
			Calendar c = Calendar.getInstance();
			c.setTime(selecionada.getDtLiberacao());
			c.add(Calendar.DATE, produtoPrazo);
			return c.getTime();
		}
		return null;
	}
	
	public Double percProdPrazo(Date dtVencLot){
		if(planilhaCegaItemMovDTO != null && dtVencLot != null){
			Integer produtoPrazo = this.produtoPrazo(dtVencLot);
			if(produtoPrazo != null && produtoPrazo > 0){
				return Funcoes.percentual(planilhaCegaItemMovDTO.getTolShelfLife(), produtoPrazo.doubleValue());
			}
		}
		return null;
	}
	
	public void onTabCloseAssocCodBarras(TabCloseEvent event) {
		tabAssociarCodigoBarras = false;
		tabVisualizarLeituras = false;
	}
	
	public void selectCodigoBarras(){
		
//		Produto produto = new ProdutoRN().carregar(planilhaCegaItemMovDTO.getProdutoId());
//		controlalote = produto.getControlalote();
//		
//		produtoCB = new ProdutoCBRN().novo(contextoBean.getUsuarioLogado(), planilhaCegaItemMovDTO.getProdutoId(), codigoBarraDTO.getCodigobarras());
//		produtoCB.setProduto(produto);
//		produtoCB.setQtd(1);
	}
	
	public void excluirTodasLeituras(){
		try {
			ColetorPCFBContagemRN planilhaCegaItemMovRN = new ColetorPCFBContagemRN();
			planilhaCegaItemMovRN.excluirTodasLeituras(selecionada.getId());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", UtilMessage.mensagem("msg.coletorpccontagem.excluido.leituras")));
			
			coletorPCFBContagem = null;
			itensMov=null;
			itens = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
			e.printStackTrace();
		}
	}
	
	public void excluirLeitura(){
		try {
			ColetorPCFBContagemRN planilhaCegaItemMovRN = new ColetorPCFBContagemRN();
			planilhaCegaItemMovRN.excluirLeitura(coletorPCFBContagem);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", UtilMessage.mensagem("msg.excluido.leituras")));
			
			coletorPCFBContagem = null;
			itensMov=null;
			itens = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
			e.printStackTrace();
		}
	}
	
	public void imprimirPallet(Long ordArmVolId){
//		if(ordArmVolId != null) {
//			new OrdArmVolRN().imprimirEtiquetas(ordArmVolId);
//		}
	}

	public Integer getSelecionadaId() {
		return selecionadaId;
	}

	public void setSelecionadaId(Integer selecionadaId) {
		this.selecionadaId = selecionadaId;
	}

	public ColetorPCFB getSelecionada() {
		if(selecionada==null && selecionadaId != null){
			ColetorPCFBRN planilhaCegaRN = new ColetorPCFBRN();
			selecionada = planilhaCegaRN.carregar(selecionadaId);
			
			buscarDivergencias();
		}
		return selecionada;
	}

	public void setSelecionada(ColetorPCFB selecionada) {
		this.selecionada = selecionada;
	}
	
	public EmpresaFB getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaFB empresa) {
		this.empresa = empresa;
	}

	public FornecedorFB getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(FornecedorFB fornecedor) {
		this.fornecedor = fornecedor;
	}

	public ColetorPCItemFB getColetorPCFBItemMov() {
		return planilhaCegaItemMov;
	}

	public void setColetorPCFBItemMov(ColetorPCItemFB planilhaCegaItemMov) {
		this.planilhaCegaItemMov = planilhaCegaItemMov;
	}
	
	public List<NFCompraFB> getListaNFCompra() {
		if(listaNFCompra==null){
			listaNFCompra = new NFCompraFBRN().listarPorPlanilhaCegaEFornecedor(selecionada);	
		}
		return listaNFCompra;
	}

	public void setListaNFCompra(List<NFCompraFB> listaNFCompra) {
		this.listaNFCompra = listaNFCompra;
	}

	public List<ColetorPCItemFB> getItens() {
		if(itens==null){
			ColetorPCItemFBRN coletorPCItemFBRN = new ColetorPCItemFBRN();
			itens = coletorPCItemFBRN.listar(selecionadaId);
			
			totalQtdNF = 0.0;
			totalQtdLeituras = 0.0;
			totalQtdAvarias = 0.0;
			totalQtdDevolvida = 0.0;
			for(ColetorPCItemFB rs : itens){
				totalQtdNF += rs.getQuantidade();
				totalQtdLeituras += rs.getQtdLeitura();
				totalQtdAvarias += rs.getQtdAvaria();
				totalQtdDevolvida += rs.getQtdDevolvida();
			}
			
			
		}
		return itens;
	}

	public void setItens(List<ColetorPCItemFB> itens) {
		this.itens = itens;
	}
	
	public List<ColetorPCFBContagem> getItensMov() {
		if(itensMov==null){
			if(planilhaCegaItemMov!=null){
				itensMov = new ColetorPCFBContagemRN().listarLeiturasProduto(selecionadaId, planilhaCegaItemMov.getProdutoId());
			}
		}
		return itensMov;
	}

	public void setItensMov(List<ColetorPCFBContagem> itensMov) {
		this.itensMov = itensMov;
	}
	
	public List<ColetorPCDivergFB> getListaDivergencia() {
		return listaDivergencia;
	}

	public void setListaDivergencia(List<ColetorPCDivergFB> listaDivergencia) {
		this.listaDivergencia = listaDivergencia;
	}

	public ColetorPCItemFB getPlanilhaCegaItemMovDTO() {
		return planilhaCegaItemMovDTO;
	}

	public void setPlanilhaCegaItemMovDTO(ColetorPCItemFB planilhaCegaItemMovDTO) {
		this.planilhaCegaItemMovDTO = planilhaCegaItemMovDTO;
	}

	public ColetorPCItemFB getPlanilhaCegaItemMov() {
		return planilhaCegaItemMov;
	}

	public void setPlanilhaCegaItemMov(ColetorPCItemFB planilhaCegaItemMov) {
		this.planilhaCegaItemMov = planilhaCegaItemMov;
	}

	public ColetorPCFBContagem getColetorPCFBContagem() {
		return coletorPCFBContagem;
	}

	public void setColetorPCFBContagem(ColetorPCFBContagem coletorPCFBContagem) {
		this.coletorPCFBContagem = coletorPCFBContagem;
	}

	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}
	
	public boolean isTabVisualizarLeituras() {
		return tabVisualizarLeituras;
	}

	public void setTabVisualizarLeituras(boolean tabVisualizarLeituras) {
		this.tabVisualizarLeituras = tabVisualizarLeituras;
	}

	public boolean isTabAssociarCodigoBarras() {
		return tabAssociarCodigoBarras;
	}

	public void setTabAssociarCodigoBarras(boolean tabAssociarCodigoBarras) {
		this.tabAssociarCodigoBarras = tabAssociarCodigoBarras;
	}
	
//	public List<CodigoBarraDTO> getListaCodBarras() {
//		if(listaCodBarras==null){
//			ColetorPCFBItemMovRN planilhaCegaItemMovRN = new ColetorPCFBItemMovRN();
//			listaCodBarras = planilhaCegaItemMovRN.listaCodBarrasToAssoc(selecionada);
//		}
//		return listaCodBarras;
//	}
//
//	public void setListaCodBarras(List<CodigoBarraDTO> listaCodBarras) {
//		this.listaCodBarras = listaCodBarras;
//	}
	
//	public List<ColetorPCDivergFB> getListaDivergencia() {
//		return listaDivergencia;
//	}
//
//	public void setListaDivergencia(List<ColetorPCDivergFB> listaDivergencia) {
//		this.listaDivergencia = listaDivergencia;
//	}
//	
//	public CodigoBarraDTO getCodigoBarraDTO() {
//		return codigoBarraDTO;
//	}
//
//	public void setCodigoBarraDTO(CodigoBarraDTO codigoBarraDTO) {
//		this.codigoBarraDTO = codigoBarraDTO;
//	}
	
	public ProdutoCBFB getProdutoCB() {
		return produtoCB;
	}

	public void setProdutoCB(ProdutoCBFB produtoCB) {
		this.produtoCB = produtoCB;
	}
	
	public Integer getProdutoSelecionadoId() {
		return produtoSelecionadoId;
	}

	public void setProdutoSelecionadoId(Integer produtoSelecionadoId) {
		this.produtoSelecionadoId = produtoSelecionadoId;
	}

	public Date getDtVencLot() {
		return dtVencLot;
	}

	public void setDtVencLot(Date dtVencLot) {
		this.dtVencLot = dtVencLot;
	}

	public String getCodigolote() {
		return codigolote;
	}

	public void setCodigolote(String codigolote) {
		this.codigolote = codigolote;
	}
	
	public Boolean getControlalote() {
		return controlalote;
	}

	public void setControlalote(Boolean controlalote) {
		this.controlalote = controlalote;
	}
	
	public Double getTotalQtdNF() {
		return totalQtdNF;
	}

	public void setTotalQtdNF(Double totalQtdNF) {
		this.totalQtdNF = totalQtdNF;
	}

	public Double getTotalQtdLeituras() {
		return totalQtdLeituras;
	}

	public void setTotalQtdLeituras(Double totalQtdLeituras) {
		this.totalQtdLeituras = totalQtdLeituras;
	}

	public Double getTotalQtdAvarias() {
		return totalQtdAvarias;
	}

	public void setTotalQtdAvarias(Double totalQtdAvarias) {
		this.totalQtdAvarias = totalQtdAvarias;
	}

	public Double getTotalQtdDevolvida() {
		return totalQtdDevolvida;
	}

	public void setTotalQtdDevolvida(Double totalQtdDevolvida) {
		this.totalQtdDevolvida = totalQtdDevolvida;
	}

	public boolean isBtnTransmitir() {
		return btnTransmitir;
	}

	public void setBtnTransmitir(boolean btnTransmitir) {
		this.btnTransmitir = btnTransmitir;
	}
	
	public boolean isDisableExcluirLeituras() {
		disableExcluirLeituras = false;
		if(selecionada.getStatus() == ColetorPCFB.STATUS_FINALIZADO
				|| selecionada.getStatus() == ColetorPCFB.STATUS_EXCLUIDO) {
			disableExcluirLeituras = true;
		}
		return disableExcluirLeituras;
	}

	public void setDisableExcluirLeituras(boolean disableExcluirLeituras) {
		this.disableExcluirLeituras = disableExcluirLeituras;
	}
	
	public boolean isRenderedBtnFinalizar() {
		renderedBtnFinalizar = false;
		if(!ColetorPCFB.STATUS_FINALIZADO.equals(selecionada.getStatus())
				&& !ColetorPCFB.STATUS_EXCLUIDO.equals(selecionada.getStatus()) && (itensMov != null && itensMov.size()>0)) {
			renderedBtnFinalizar = true;
		}
		
		return renderedBtnFinalizar;
	}

	public void setRenderedBtnFinalizar(boolean renderedBtnFinalizar) {
		this.renderedBtnFinalizar = renderedBtnFinalizar;
	}

	public String getIntegracao() {
		return integracao;
	}

	public void setIntegracao(String integracao) {
		this.integracao = integracao;
	}

	public void clearSession() {
		  HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		  if(request.getParameter("id")!=null){
			  HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		      session.removeAttribute("planilhaCegaGerenciarBean");
		  }
	}

}
