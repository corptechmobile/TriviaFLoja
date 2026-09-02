package br.com.webapp.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.webapp.model.fb.cliente.ClienteFB;
import br.com.webapp.model.fb.cliente.ClienteFBRN;
import br.com.webapp.model.fb.fretetipo.FreteTipoFB;
import br.com.webapp.model.fb.fretetipo.FreteTipoFBRN;
import br.com.webapp.model.fb.pedvenda.PedVendaFB;
import br.com.webapp.model.fb.pedvenda.PedVendaFBRN;
import br.com.webapp.model.fb.pedvenda.diverg.PedVendaDivergFBRN;
import br.com.webapp.model.fb.pedvenda.diverg.dto.PedVendaDivergFBDTO;
import br.com.webapp.model.fb.pedvendaitem.dto.PedVendaItemFBDTO;
import br.com.webapp.model.fb.pedvendaitem.dto.PedVendaItemFBDTORN;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFBRN;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

@ManagedBean(name = "pedVendaLiberarBean")
@SessionScoped
public class PedVendaLiberarBean implements Serializable{

	private static final long serialVersionUID = 8335564064925633460L;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private Integer selecionadaId;
	private PedVendaFB selecionada;
	private List<PedVendaItemFBDTO> listaItens;
	private List<PedVendaDivergFBDTO> divergenciasSelected;
	private List<PedVendaDivergFBDTO> listaDivergencias;
	private ClienteFB cliente;
	private Double valorTotalPedido;
	private String obsDivergencia;
	
	private String tituloAdd = "";
	private boolean editando = false;
	private boolean btnLiberar = false;
	private boolean btnNaoLiberar = false;
	
	private VendedorFB vendedor;
	
	@PostConstruct
	public void init(){}
	
	public void gerenciar() {
		editando = false;
		divergenciasSelected = null;
		selecionada = null;
		listaDivergencias = null;
		cliente = null;
		vendedor = null;
		selecionada = getSelecionada();
	}
	
	public void liberar(){
		tituloAdd = UtilMessage.mensagem("txt.liberar");
		editando = true;
		btnLiberar = true;
		btnNaoLiberar = false;
		obsDivergencia = "";
	}
	
	public void naoLiberar(){
		tituloAdd = UtilMessage.mensagem("txt.nao.liberar");
		editando = true;
		btnLiberar = false;
		btnNaoLiberar = true;
		obsDivergencia = "";
	}
	
	public void confirmarLiberacao() {
		try {
			
			PedVendaDivergFBRN pedVendaDivergFBRN = new PedVendaDivergFBRN();
			pedVendaDivergFBRN.liberar(selecionadaId, divergenciasSelected, obsDivergencia, contextoBean.getUsuarioLogado(), selecionada.getCondPagtoId());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.liberar.salvo.pedvendaliberacao")));
			
			divergenciasSelected = null;
			listaDivergencias = null;
			editando = false;
			
		}catch (RNException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
			e.printStackTrace();
		}
	}
	
	public void confirmarNaoLiberacao() {
		try {
			
			PedVendaDivergFBRN pedVendaDivergFBRN = new PedVendaDivergFBRN();
			pedVendaDivergFBRN.naoLiberar(selecionadaId, divergenciasSelected, obsDivergencia, contextoBean.getUsuarioLogado());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, UtilMessage.mensagem("msg.naoliberar.salvo.pedvendaliberacao")));
			
			divergenciasSelected = null;
			listaDivergencias = null;
			editando = false;
			
		} catch (RNException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
		}
		
	}
	
	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

	public Integer getSelecionadaId() {
		return selecionadaId;
	}

	public void setSelecionadaId(Integer selecionadaId) {
		this.selecionadaId = selecionadaId;
	}

	public PedVendaFB getSelecionada() {
		if (selecionada == null) {
			PedVendaFBRN pedVendaFBRN = new PedVendaFBRN();
			selecionada = pedVendaFBRN.carregar(selecionadaId);
			vendedor = new VendedorFBRN().carregar(selecionada.getVendedorId());
			cliente = new ClienteFBRN().carregar(selecionada.getClienteId());
			
			selecionada.setFreteTipo(new FreteTipoFBRN().carregar(selecionada.getFreteTipoId()));
			
			listaItens = null;
		}
		return selecionada;
	}

	public void setSelecionada(PedVendaFB selecionada) {
		this.selecionada = selecionada;
	}

	public List<PedVendaItemFBDTO> getListaItens() {
		if (listaItens == null) {
			PedVendaItemFBDTORN pedVendaItemFBDTORN = new PedVendaItemFBDTORN();
			listaItens = pedVendaItemFBDTORN.listar(selecionadaId); 
		}
		return listaItens;
	}

	public void setListaItens(List<PedVendaItemFBDTO> listaItens) {
		this.listaItens = listaItens;
	}

	public List<PedVendaDivergFBDTO> getDivergenciasSelected() {
		return divergenciasSelected;
	}

	public void setDivergenciasSelected(List<PedVendaDivergFBDTO> divergenciasSelected) {
		this.divergenciasSelected = divergenciasSelected;
	}

	public List<PedVendaDivergFBDTO> getListaDivergencias() {
		if (listaDivergencias == null) {
			listaDivergencias = new PedVendaDivergFBRN().listarDTO(selecionadaId);
		}
		return listaDivergencias;
	}

	public void setListaDivergencias(List<PedVendaDivergFBDTO> listaDivergencias) {
		this.listaDivergencias = listaDivergencias;
	}

	public ClienteFB getCliente() {
		return cliente;
	}

	public void setCliente(ClienteFB cliente) {
		this.cliente = cliente;
	}

	public Double getValorTotalPedido() {
		return valorTotalPedido;
	}

	public void setValorTotalPedido(Double valorTotalPedido) {
		this.valorTotalPedido = valorTotalPedido;
	}

	public String getObsDivergencia() {
		return obsDivergencia;
	}

	public void setObsDivergencia(String obsDivergencia) {
		this.obsDivergencia = obsDivergencia;
	}

	public String getTituloAdd() {
		return tituloAdd;
	}

	public void setTituloAdd(String tituloAdd) {
		this.tituloAdd = tituloAdd;
	}

	public boolean isEditando() {
		return editando;
	}

	public void setEditando(boolean editando) {
		this.editando = editando;
	}

	public boolean isBtnLiberar() {
		return btnLiberar;
	}

	public void setBtnLiberar(boolean btnLiberar) {
		this.btnLiberar = btnLiberar;
	}

	public boolean isBtnNaoLiberar() {
		return btnNaoLiberar;
	}

	public void setBtnNaoLiberar(boolean btnNaoLiberar) {
		this.btnNaoLiberar = btnNaoLiberar;
	}

	public VendedorFB getVendedor() {
		return vendedor;
	}

	public void setVendedor(VendedorFB vendedor) {
		this.vendedor = vendedor;
	}
	
	public void listenerSelect() {
		editando = false;
		btnLiberar = false;
		btnNaoLiberar = false;
	}

	public void clearSession() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		    session.removeAttribute("pedVendaLiberarBean");
		}
	}

}
