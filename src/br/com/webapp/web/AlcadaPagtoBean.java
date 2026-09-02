package br.com.webapp.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.webapp.model.fb.alcadacondpagto.dto.AlcadaCondPagtoFBDTO;
import br.com.webapp.model.fb.alcadacondpagto.dto.AlcadaCondPagtoFBDTORN;
import br.com.webapp.model.fb.condpagto.CondPagtoFB;
import br.com.webapp.model.fb.condpagto.CondPagtoFBRN;
import br.com.webapp.model.fb.gestaovenda.GestaoVendaFB;
import br.com.webapp.model.fb.gestaovenda.GestaoVendaFBRN;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.UtilMessage;

@ManagedBean(name = "alcadaPagtoBean")
@SessionScoped
public class AlcadaPagtoBean implements Serializable, IMenuAcesso{

	private static final long serialVersionUID = -7174324207513516422L;
	
		// Menu Acesso
		private Integer menu = MenuAcessoController.ALCADA_PAGTO_BEAN;
		
		@ManagedProperty(value="#{contextoBean}")
		private ContextoBean contextoBean;
		
		private AlcadaCondPagtoFBDTO selecionada = new AlcadaCondPagtoFBDTO();
		private GestaoVendaFB gestaoFilter = new GestaoVendaFB();
		private CondPagtoFB condPagtoFilter = new CondPagtoFB();
		private Double percAplicacaoMulti;
		private boolean renderedEdicao = false;
		private boolean renderedMultiAlcadas = false;
		private String tituloAdd = "CONSULTA";
		private Double alcadaMulti;
		
		private List<GestaoVendaFB> listaGestoes;
		private List<AlcadaCondPagtoFBDTO> lista;
		private List<AlcadaCondPagtoFBDTO> listaMultiAlcadas;
		private List<SelectItem> gestoesSelected;
		private List<SelectItem> condsSelected;
		
		@PostConstruct
		public void init(){
			System.out.println("[AlcadaPagtoBean][init]");
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			if(request.getParameter("id")!=null){
				if(contextoBean.verificaPermissao(menu) == false){
					try {
						FacesContext.getCurrentInstance().getExternalContext().redirect("../page404.jsf");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					addRecentes();
				}
			}
		}
		
		public void buscar() {
			this.lista = null;
		}
		
		public void limpar() {
			this.lista = null;
			gestaoFilter = new GestaoVendaFB();
			condPagtoFilter = new CondPagtoFB();
		}
		
		public void editar() {
			this.renderedMultiAlcadas = false;
			this.renderedEdicao = true;
		}
		
		public void novoMulti() {
			this.renderedMultiAlcadas = true;
			this.renderedEdicao = false;
			this.setTituloAdd("CADASTRAR ALÇADAS");
			listaMultiAlcadas = new ArrayList<AlcadaCondPagtoFBDTO>();
		}
		
		public void gerarMulti() {
			try {
				listaMultiAlcadas = new AlcadaCondPagtoFBDTORN().gerarMultiAlcadas(gestaoFilter, alcadaMulti);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void salvarMulti() {
			AlcadaCondPagtoFBDTORN alcadaCondPagtoFBDTORN = new AlcadaCondPagtoFBDTORN();
			try {
				alcadaCondPagtoFBDTORN.salvar(listaMultiAlcadas);
				this.renderedMultiAlcadas = false;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", UtilMessage.mensagem("msg.salvo.multialcadapagto")));
				limpar();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
			}
		}
		
		public void salvar() {
			try {
				AlcadaCondPagtoFBDTORN alcadaCondPagtoFBDTORN = new AlcadaCondPagtoFBDTORN();
				alcadaCondPagtoFBDTORN.update(selecionada);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", UtilMessage.mensagem("msg.editado.alcadapagto")));
				this.renderedEdicao = false;
				this.lista = null;
			}catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
			}
		}
		
		public void excluir() throws DAOException {
			AlcadaCondPagtoFBDTORN alcadaCondPagtoFBDTORN = new AlcadaCondPagtoFBDTORN();
			alcadaCondPagtoFBDTORN.delete(selecionada);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", UtilMessage.mensagem("msg.excluido.alcadapagto")));
			this.renderedEdicao = false;
			this.renderedMultiAlcadas = false;
			this.listaMultiAlcadas = null;
			this.selecionada = null;
			limpar();
		}
		
		public ContextoBean getContextoBean() {
			return contextoBean;
		}

		public void setContextoBean(ContextoBean contextoBean) {
			this.contextoBean = contextoBean;
		}

		public AlcadaCondPagtoFBDTO getSelecionada() {
			return selecionada;
		}

		public void setSelecionada(AlcadaCondPagtoFBDTO selecionada) {
			this.selecionada = selecionada;
		}

		public GestaoVendaFB getGestaoFilter() {
			return gestaoFilter;
		}

		public void setGestaoFilter(GestaoVendaFB gestaoFilter) {
			this.gestaoFilter = gestaoFilter;
		}

		public CondPagtoFB getCondPagtoFilter() {
			return condPagtoFilter;
		}

		public void setCondPagtoFilter(CondPagtoFB condPagtoFilter) {
			this.condPagtoFilter = condPagtoFilter;
		}

		public Double getPercAplicacaoMulti() {
			return percAplicacaoMulti;
		}

		public void setPercAplicacaoMulti(Double percAplicacaoMulti) {
			this.percAplicacaoMulti = percAplicacaoMulti;
		}

		public boolean isRenderedEdicao() {
			return renderedEdicao;
		}

		public void setRenderedEdicao(boolean renderedEdicao) {
			this.renderedEdicao = renderedEdicao;
		}

		public boolean isRenderedMultiAlcadas() {
			return renderedMultiAlcadas;
		}

		public void setRenderedMultiAlcadas(boolean renderedMultiAlcadas) {
			this.renderedMultiAlcadas = renderedMultiAlcadas;
		}

		public String getTituloAdd() {
			return tituloAdd;
		}

		public void setTituloAdd(String tituloAdd) {
			this.tituloAdd = tituloAdd;
		}

		public Double getAlcadaMulti() {
			return alcadaMulti;
		}

		public void setAlcadaMulti(Double alcadaMulti) {
			this.alcadaMulti = alcadaMulti;
		}

		public List<GestaoVendaFB> getListaGestoes() {
			return listaGestoes;
		}

		public void setListaGestoes(List<GestaoVendaFB> listaGestoes) {
			this.listaGestoes = listaGestoes;
		}

		public List<AlcadaCondPagtoFBDTO> getLista() {
			if (gestaoFilter == null) {
				gestaoFilter = new GestaoVendaFB();
			}
			if (condPagtoFilter == null) {
				condPagtoFilter = new CondPagtoFB();
			}
			if (lista == null) {
				AlcadaCondPagtoFBDTORN alcadaCondPagtoFBDTORN = new AlcadaCondPagtoFBDTORN();
				lista = alcadaCondPagtoFBDTORN.listar(gestaoFilter.getId(), condPagtoFilter.getId());
			}
			return lista;
		}

		public void setLista(List<AlcadaCondPagtoFBDTO> lista) {
			this.lista = lista;
		}

		public List<AlcadaCondPagtoFBDTO> getListaMultiAlcadas() {
			return listaMultiAlcadas;
		}

		public void setListaMultiAlcadas(List<AlcadaCondPagtoFBDTO> listaMultiAlcadas) {
			this.listaMultiAlcadas = listaMultiAlcadas;
		}

		public List<SelectItem> getGestoesSelected() {
			if (this.gestoesSelected == null) {
				 this.gestoesSelected = new ArrayList<SelectItem>();
				 GestaoVendaFBRN gestaoVendaFBRN = new GestaoVendaFBRN();
				 this.gestoesSelected = gestaoVendaFBRN.montaDadosSelected(gestaoVendaFBRN.listar(), "");
			}
			return gestoesSelected;
		}

		public void setGestoesSelected(List<SelectItem> gestoesSelected) {
			this.gestoesSelected = gestoesSelected;
		}

		public List<SelectItem> getCondsSelected() {
			if(condsSelected == null) {
				this.condsSelected = new ArrayList<SelectItem>();
				CondPagtoFBRN condPagtoFBRN = new CondPagtoFBRN();
				this.condsSelected = condPagtoFBRN.montaDadosSelect(condPagtoFBRN.listar(), "");
			}
			return condsSelected;
		}

		public void setCondsSelected(List<SelectItem> condsSelected) {
			this.condsSelected = condsSelected;
		}

		public void addHome() {
			MenuAcessoController.addHome(menu, ContextoUtil.getContextoBean().getUsuarioLogado(), FacesContext.getCurrentInstance());
		}

		public void addRecentes() {
			MenuAcessoController.addRecentes(menu, ContextoUtil.getContextoBean().getUsuarioLogado());
		}

		public void addFavoritos() {
			MenuAcessoController.addFavoritos(menu, ContextoUtil.getContextoBean().getUsuarioLogado(), FacesContext.getCurrentInstance());
		}

		public void clearSession() {
			System.out.println("[ClienteConsultaBean][clearSession]");
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			if(request.getParameter("id")!=null){
				HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			    session.removeAttribute("alcadaPagtoBean");
			}
		}
}
