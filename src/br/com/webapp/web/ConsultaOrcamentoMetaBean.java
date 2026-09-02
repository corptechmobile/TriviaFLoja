package br.com.webapp.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.orcamentogrupo.OrcamentoGrupoFB;
import br.com.webapp.model.fb.orcamentogrupo.OrcamentoGrupoFBRN;
import br.com.webapp.model.fb.orcamentometaitem.OrcamentoMetaItemFBDTO;
import br.com.webapp.model.fb.orcamentometaitem.OrcamentoMetaItemFBRN;
import br.com.webapp.model.fb.orcamentometa.OrcamentoMetaFB;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;

@ManagedBean(name = "consultaOrcamentoMetaBean")
@SessionScoped
public class ConsultaOrcamentoMetaBean implements Serializable, IMenuAcesso{
	private static final long serialVersionUID = -1231705989227032512L;

	private Integer menu = MenuAcessoController.CONSULTAORCAMENTOMETA_BEAN;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private OrcamentoMetaFB selecionada;
	private Integer selecionadaId;
	private List<OrcamentoMetaItemFBDTO> listaItens;
	
	private String descEmpresa;
	private String descOrcamentoGrupo;
	private String ano;
	
	private Double prevTotJan;
	private Double fatTotJan;
	private Double percTotJan;
	private Double prevTotFev;
	private Double fatTotFev;
	private Double percTotFev;
	private Double prevTotMar;
	private Double fatTotMar;
	private Double percTotMar;
	private Double prevTotAbr;
	private Double fatTotAbr;
	private Double percTotAbr;
	private Double prevTotMai;
	private Double fatTotMai;
	private Double percTotMai;
	private Double prevTotJun;
	private Double fatTotJun;
	private Double percTotJun;
	private Double prevTotJul;
	private Double fatTotJul;
	private Double percTotJul;
	private Double prevTotAgo;
	private Double fatTotAgo;
	private Double percTotAgo;
	private Double prevTotSet;
	private Double fatTotSet;
	private Double percTotSet;
	private Double prevTotOut;
	private Double fatTotOut;
	private Double percTotOut;
	private Double prevTotNov;
	private Double fatTotNov;
	private Double percTotNov;
	private Double prevTotDez;
	private Double fatTotDez;
	private Double percTotDez;
	private Double prevTotAno;
	private Double fatTotAno;
	private Double percTotAno;
	
	private EmpresaFB empresaFilter;
	private OrcamentoGrupoFB orcamentoGrupoFilter;

	private List<SelectItem> empresaSelect;
	private List<SelectItem> orcamentoGrupoSelect;
	
	private String tituloAdd = "CADASTRAR";
	
	@PostConstruct
	public void init(){
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		descEmpresa = null;
		descOrcamentoGrupo = null;
		
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
		try {
			Integer empresaId = null;
			if(empresaFilter != null) {
				empresaId = empresaFilter.getId();
			}
			
			Integer idOrcamentoGrupo = null;
			if(orcamentoGrupoFilter != null) {
				idOrcamentoGrupo = orcamentoGrupoFilter.getId();
			}

			prevTotJan = 0.0;
			fatTotJan = 0.0;
			percTotJan = 0.0;
			prevTotFev = 0.0;
			fatTotFev = 0.0;
			percTotFev = 0.0;
			prevTotMar = 0.0;
			fatTotMar = 0.0;
			percTotMar = 0.0;
			prevTotAbr = 0.0;
			fatTotAbr = 0.0;
			percTotAbr = 0.0;
			prevTotMai = 0.0;
			fatTotMai = 0.0;
			percTotMai = 0.0;
			prevTotJun = 0.0;
			fatTotJun = 0.0;
			percTotJun = 0.0;
			prevTotJul = 0.0;
			fatTotJul = 0.0;
			percTotJul = 0.0;
			prevTotAgo = 0.0;
			fatTotAgo = 0.0;
			percTotAgo = 0.0;
			prevTotSet = 0.0;
			fatTotSet = 0.0;
			percTotSet = 0.0;
			prevTotOut = 0.0;
			fatTotOut = 0.0;
			percTotOut = 0.0;
			prevTotNov = 0.0;
			fatTotNov = 0.0;
			percTotNov = 0.0;
			prevTotDez = 0.0;
			fatTotDez = 0.0;
			percTotDez = 0.0;
			prevTotAno = 0.0;
			fatTotAno = 0.0;
			percTotAno = 0.0;

			
			listaItens = new OrcamentoMetaItemFBRN().listarAno(ano, empresaId, idOrcamentoGrupo);
			for(OrcamentoMetaItemFBDTO rs: listaItens) {
				
				prevTotJan += rs.getPrevJan(); 
				fatTotJan  += rs.getFatJan();
				prevTotFev += rs.getPrevFev(); 
				fatTotFev  += rs.getFatFev();
				prevTotMar += rs.getPrevMar(); 
				fatTotMar  += rs.getFatMar();
				prevTotAbr += rs.getPrevAbr(); 
				fatTotAbr  += rs.getFatAbr();
				prevTotMai += rs.getPrevMai(); 
				fatTotMai  += rs.getFatMai();
				prevTotJun += rs.getPrevJun(); 
				fatTotJun  += rs.getFatJun();
				prevTotJul += rs.getPrevJul(); 
				fatTotJul  += rs.getFatJul();
				prevTotAgo += rs.getPrevAgo(); 
				fatTotAgo  += rs.getFatAgo();
				prevTotSet += rs.getPrevSet(); 
				fatTotSet  += rs.getFatSet();
				prevTotOut += rs.getPrevOut(); 
				fatTotOut  += rs.getFatOut();
				prevTotNov += rs.getPrevNov(); 
				fatTotNov  += rs.getFatNov();
				prevTotDez += rs.getPrevDez(); 
				fatTotDez  += rs.getFatDez();
				prevTotAno += rs.getPrevTotal(); 
				fatTotAno  += rs.getFatTotal();
			}
			
			percTotJan = 0.0;
			if(prevTotJan > 0) {
				percTotJan = (fatTotJan/prevTotJan)*100;
			}	
			percTotFev = 0.0;
			if(prevTotFev > 0) {
				percTotFev = (fatTotFev/prevTotFev)*100;
			}	
			percTotMar = 0.0;
			if(prevTotMar > 0) {
				percTotMar = (fatTotMar/prevTotMar)*100;
			}	
			percTotAbr = 0.0;
			if(prevTotAbr > 0) {
				percTotAbr = (fatTotAbr/prevTotAbr)*100;
			}	
			percTotMai = 0.0;
			if(prevTotMai > 0) {
				percTotMai = (fatTotMai/prevTotMai)*100;
			}	
			percTotJun = 0.0;
			if(prevTotJun > 0) {
				percTotJun = (fatTotJun/prevTotJun)*100;
			}	
			percTotJul = 0.0;
			if(prevTotJul > 0) {
				percTotJul = (fatTotJul/prevTotJul)*100;
			}	
			percTotAgo = 0.0;
			if(prevTotAgo > 0) {
				percTotAgo = (fatTotAgo/prevTotAgo)*100;
			}	
			percTotSet = 0.0;
			if(prevTotSet > 0) {
				percTotSet = (fatTotSet/prevTotSet)*100;
			}	
			percTotOut = 0.0;
			if(prevTotOut > 0) {
				percTotOut = (fatTotOut/prevTotOut)*100;
			}	
			percTotNov = 0.0;
			if(prevTotNov > 0) {
				percTotNov = (fatTotNov/prevTotNov)*100;
			}	
			percTotDez = 0.0;
			if(prevTotDez > 0) {
				percTotDez = (fatTotDez/prevTotDez)*100;
			}	
			percTotAno = 0.0;
			if(prevTotAno > 0) {
				percTotAno = (fatTotAno/prevTotAno)*100;
			}	


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String styloTotais(Double valor) {
		String styleCss = "";
		if(valor<100) {
			styleCss = "customBgGreenMeta";
		}else if(valor>100){ 
			styleCss = "customBgRedMeta";
		}else{
			styleCss = "customBgYellowMeta";
		}
		if(valor==0.0) {
			styleCss = "";
		}
		
		return styleCss;
	}
	
		
	public void limpar(){
		ano = null;
		empresaFilter = null;
		descEmpresa = null;
		orcamentoGrupoFilter = null;
		descOrcamentoGrupo = null;
		listaItens = null;
		
	}
	

	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

	public OrcamentoMetaFB getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(OrcamentoMetaFB selecionada) {
		this.selecionada = selecionada;
	}

	public Integer getSelecionadaId() {
		return selecionadaId;
	}

	public void setSelecionadaId(Integer selecionadaId) {
		this.selecionadaId = selecionadaId;
	}

	public String getTituloAdd() {
		return tituloAdd;
	}

	public void setTituloAdd(String tituloAdd) {
		this.tituloAdd = tituloAdd;
	}

	public String getDescEmpresa() {
		return descEmpresa;
	}

	public void setDescEmpresa(String descEmpresa) {
		this.descEmpresa = descEmpresa;
	}

	public EmpresaFB getEmpresaFilter() {
		return empresaFilter;
	}

	public void setEmpresaFilter(EmpresaFB empresaFilter) {
		this.empresaFilter = empresaFilter;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public List<OrcamentoMetaItemFBDTO> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<OrcamentoMetaItemFBDTO> listaItens) {
		this.listaItens = listaItens;
	}

	public Double getPrevTotJan() {
		return prevTotJan;
	}

	public void setPrevTotJan(Double prevTotJan) {
		this.prevTotJan = prevTotJan;
	}

	public Double getFatTotJan() {
		return fatTotJan;
	}

	public void setFatTotJan(Double fatTotJan) {
		this.fatTotJan = fatTotJan;
	}

	public Double getPercTotJan() {
		return percTotJan;
	}

	public void setPercTotJan(Double percTotJan) {
		this.percTotJan = percTotJan;
	}

	public Double getPrevTotFev() {
		return prevTotFev;
	}

	public void setPrevTotFev(Double prevTotFev) {
		this.prevTotFev = prevTotFev;
	}

	public Double getFatTotFev() {
		return fatTotFev;
	}

	public void setFatTotFev(Double fatTotFev) {
		this.fatTotFev = fatTotFev;
	}

	public Double getPercTotFev() {
		return percTotFev;
	}

	public void setPercTotFev(Double percTotFev) {
		this.percTotFev = percTotFev;
	}

	public Double getPrevTotMar() {
		return prevTotMar;
	}

	public void setPrevTotMar(Double prevTotMar) {
		this.prevTotMar = prevTotMar;
	}

	public Double getFatTotMar() {
		return fatTotMar;
	}

	public void setFatTotMar(Double fatTotMar) {
		this.fatTotMar = fatTotMar;
	}

	public Double getPercTotMar() {
		return percTotMar;
	}

	public void setPercTotMar(Double percTotMar) {
		this.percTotMar = percTotMar;
	}

	public Double getPrevTotAbr() {
		return prevTotAbr;
	}

	public void setPrevTotAbr(Double prevTotAbr) {
		this.prevTotAbr = prevTotAbr;
	}

	public Double getFatTotAbr() {
		return fatTotAbr;
	}

	public void setFatTotAbr(Double fatTotAbr) {
		this.fatTotAbr = fatTotAbr;
	}

	public Double getPercTotAbr() {
		return percTotAbr;
	}

	public void setPercTotAbr(Double percTotAbr) {
		this.percTotAbr = percTotAbr;
	}

	public Double getPrevTotMai() {
		return prevTotMai;
	}

	public void setPrevTotMai(Double prevTotMai) {
		this.prevTotMai = prevTotMai;
	}

	public Double getFatTotMai() {
		return fatTotMai;
	}

	public void setFatTotMai(Double fatTotMai) {
		this.fatTotMai = fatTotMai;
	}

	public Double getPercTotMai() {
		return percTotMai;
	}

	public void setPercTotMai(Double percTotMai) {
		this.percTotMai = percTotMai;
	}

	public Double getPrevTotJun() {
		return prevTotJun;
	}

	public void setPrevTotJun(Double prevTotJun) {
		this.prevTotJun = prevTotJun;
	}

	public Double getFatTotJun() {
		return fatTotJun;
	}

	public void setFatTotJun(Double fatTotJun) {
		this.fatTotJun = fatTotJun;
	}

	public Double getPercTotJun() {
		return percTotJun;
	}

	public void setPercTotJun(Double percTotJun) {
		this.percTotJun = percTotJun;
	}

	public Double getPrevTotJul() {
		return prevTotJul;
	}

	public void setPrevTotJul(Double prevTotJul) {
		this.prevTotJul = prevTotJul;
	}

	public Double getFatTotJul() {
		return fatTotJul;
	}

	public void setFatTotJul(Double fatTotJul) {
		this.fatTotJul = fatTotJul;
	}

	public Double getPercTotJul() {
		return percTotJul;
	}

	public void setPercTotJul(Double percTotJul) {
		this.percTotJul = percTotJul;
	}

	public Double getPrevTotAgo() {
		return prevTotAgo;
	}

	public void setPrevTotAgo(Double prevTotAgo) {
		this.prevTotAgo = prevTotAgo;
	}

	public Double getFatTotAgo() {
		return fatTotAgo;
	}

	public void setFatTotAgo(Double fatTotAgo) {
		this.fatTotAgo = fatTotAgo;
	}

	public Double getPercTotAgo() {
		return percTotAgo;
	}

	public void setPercTotAgo(Double percTotAgo) {
		this.percTotAgo = percTotAgo;
	}

	public Double getPrevTotSet() {
		return prevTotSet;
	}

	public void setPrevTotSet(Double prevTotSet) {
		this.prevTotSet = prevTotSet;
	}

	public Double getFatTotSet() {
		return fatTotSet;
	}

	public void setFatTotSet(Double fatTotSet) {
		this.fatTotSet = fatTotSet;
	}

	public Double getPercTotSet() {
		return percTotSet;
	}

	public void setPercTotSet(Double percTotSet) {
		this.percTotSet = percTotSet;
	}

	public Double getPrevTotOut() {
		return prevTotOut;
	}

	public void setPrevTotOut(Double prevTotOut) {
		this.prevTotOut = prevTotOut;
	}

	public Double getFatTotOut() {
		return fatTotOut;
	}

	public void setFatTotOut(Double fatTotOut) {
		this.fatTotOut = fatTotOut;
	}

	public Double getPercTotOut() {
		return percTotOut;
	}

	public void setPercTotOut(Double percTotOut) {
		this.percTotOut = percTotOut;
	}

	public Double getPrevTotNov() {
		return prevTotNov;
	}

	public void setPrevTotNov(Double prevTotNov) {
		this.prevTotNov = prevTotNov;
	}

	public Double getFatTotNov() {
		return fatTotNov;
	}

	public void setFatTotNov(Double fatTotNov) {
		this.fatTotNov = fatTotNov;
	}

	public Double getPercTotNov() {
		return percTotNov;
	}

	public void setPercTotNov(Double percTotNov) {
		this.percTotNov = percTotNov;
	}

	public Double getPrevTotDez() {
		return prevTotDez;
	}

	public void setPrevTotDez(Double prevTotDez) {
		this.prevTotDez = prevTotDez;
	}

	public Double getFatTotDez() {
		return fatTotDez;
	}

	public void setFatTotDez(Double fatTotDez) {
		this.fatTotDez = fatTotDez;
	}

	public Double getPercTotDez() {
		return percTotDez;
	}

	public void setPercTotDez(Double percTotDez) {
		this.percTotDez = percTotDez;
	}

	public Double getPrevTotAno() {
		return prevTotAno;
	}

	public void setPrevTotAno(Double prevTotAno) {
		this.prevTotAno = prevTotAno;
	}

	public Double getFatTotAno() {
		return fatTotAno;
	}

	public void setFatTotAno(Double fatTotAno) {
		this.fatTotAno = fatTotAno;
	}

	public Double getPercTotAno() {
		return percTotAno;
	}

	public void setPercTotAno(Double percTotAno) {
		this.percTotAno = percTotAno;
	}

	public List<SelectItem> getEmpresaSelect() {
		if (this.empresaSelect == null) {
			this.empresaSelect = new ArrayList<SelectItem>();
			EmpresaFBRN empresaFBRN = new EmpresaFBRN();
			this.empresaSelect = empresaFBRN.montaDadosSelect(new EmpresaFBRN().listar(), "");
		}
		
		return empresaSelect;
	}

	public void setEmpresaSelect(List<SelectItem> empresaSelect) {
		this.empresaSelect = empresaSelect;
	}

	public String getDescOrcamentoGrupo() {
		return descOrcamentoGrupo;
	}

	public void setDescOrcamentoGrupo(String descOrcamentoGrupo) {
		this.descOrcamentoGrupo = descOrcamentoGrupo;
	}

	public OrcamentoGrupoFB getOrcamentoGrupoFilter() {
		return orcamentoGrupoFilter;
	}

	public void setOrcamentoGrupoFilter(OrcamentoGrupoFB orcamentoGrupoFilter) {
		this.orcamentoGrupoFilter = orcamentoGrupoFilter;
	}

	public List<SelectItem> getOrcamentoGrupoSelect() {
		if (this.orcamentoGrupoSelect == null) {
			this.orcamentoGrupoSelect = new ArrayList<SelectItem>();
			OrcamentoGrupoFBRN orcamentoGrupoFBRN = new OrcamentoGrupoFBRN();
			this.orcamentoGrupoSelect = orcamentoGrupoFBRN.montaDadosSelect(new OrcamentoGrupoFBRN().listar(), "");
		}
		
		return orcamentoGrupoSelect;
	}

	public void setOrcamentoGrupoSelect(List<SelectItem> orcamentoGrupoSelect) {
		this.orcamentoGrupoSelect = orcamentoGrupoSelect;
	}

	// Implementation Menu
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
		System.out.println("[ConsultaOrcamentoMetaBean][clearSession]");
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		    session.removeAttribute("consultaOrcamentoMetaBean");
		}
	}

}
