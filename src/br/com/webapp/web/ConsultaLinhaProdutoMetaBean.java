package br.com.webapp.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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

import br.com.webapp.model.fb.diasuteis.DiasUteisFB;
import br.com.webapp.model.fb.diasuteis.DiasUteisFBRN;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.gestaovendamob.GestaoVendaMobFB;
import br.com.webapp.model.fb.gestaovendamob.GestaoVendaMobFBRN;
import br.com.webapp.model.fb.linhaprodutometa.LinhaProdutoMetaFBDTO;
import br.com.webapp.model.fb.vendedor.VendedorFB;
import br.com.webapp.model.fb.vendedor.VendedorFBRN;
import br.com.webapp.model.fb.linhaprodutometa.LinhaProdutoMetaFBRN;
import br.com.webapp.model.fb.linhaprodutometa.LinhaProdutoMetaFB;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;

@ManagedBean(name = "consultaLinhaProdutoMetaBean")
@SessionScoped
public class ConsultaLinhaProdutoMetaBean implements Serializable, IMenuAcesso{
	private static final long serialVersionUID = 1347363942692684685L;

	private Integer menu = MenuAcessoController.CONSULTALINHAPRODUTOMETA_BEAN;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private LinhaProdutoMetaFB selecionada;
	private Integer selecionadaId;
	private List<LinhaProdutoMetaFBDTO> listaItens;
	
	private String descEmpresa;
	private String descVendedor;
	private String anoMes;
	
	private Double totalMetaMensal;
	private Double totalMetaProp;
	private Double totalFatMensal;
	private Double totalDifMetaFat;
	private Double totalTendencia;
	private Double totalPercMeta;
	private Double totalPercTendencia;
	private Double totalMetaDiaria;
	private Double totalFatDiaria;
	private Double totalNovaMetaDiaria;
	private Double totalCoberturaReal;
	private Double totalCoberturaCarteira;
	private Double totalPercCobertura;
	private Double totalMixReal;
	private Double totalMixAtual;
	private Double totalPercMix;
	
	private Integer progress;
	 
	private DiasUteisFB diasUteis;
	private Integer dUteis;
	private Integer prazoDecorrido;
	
	private EmpresaFB empresaFilter;
	private VendedorFB vendedorFilter;
	private GestaoVendaMobFB gestaoVendaMobFilter;
	private String visualizarFilter;
	private boolean incluirDevolucao;

	private List<SelectItem> empresaSelect;
	private List<SelectItem> vendedorSelect;
	private List<SelectItem> gestaoVendaMobFilterSelect;
	
	private String tituloAdd = "CADASTRAR";
	
	@PostConstruct
	public void init(){
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		descEmpresa = null;
		descVendedor = null;
		incluirDevolucao = false;
		
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
			
			Integer idVendedor = null;
			if(vendedorFilter != null) {
				idVendedor = vendedorFilter.getId();
			}
			
			String codEdtGestaoVendaMob = null;
			if(gestaoVendaMobFilter !=null) {
				codEdtGestaoVendaMob = gestaoVendaMobFilter.getCodEdt()+"%";
			}
			Calendar c = Calendar.getInstance();
			
			int mesAtual = c.get(Calendar.MONTH);
			System.out.println("mesAtual: "+mesAtual);
			int mes = Integer.parseInt(anoMes.substring(4,6));
			System.out.println("mes: "+mes);
			
			if(mesAtual!=(mes-1)) {
				c.set(Calendar.DAY_OF_MONTH, 1);
				c.set(Calendar.MONTH, mes-1);

				c.set(Calendar.YEAR, Integer.parseInt(anoMes.substring(0,4)));
				int dia = c.getActualMaximum(Calendar.DAY_OF_MONTH);

				c.set(Calendar.DAY_OF_MONTH, dia);
				
			}
			
			
			diasUteis = new DiasUteisFBRN().carregar(c.getTime());
			System.out.println("Data passada: "+c.getTime());
			dUteis = diasUteis.getDiasUteis();
			prazoDecorrido = diasUteis.getPrazoDecorrido(); 
			
			int diasPrazo = diasUteis.getPrazoDecorrido();
			System.out.println("diasPrazo: "+diasPrazo);
			int diasUts	  = diasUteis.getDiasUteis();
			System.out.println("diasUts: "+diasUts);
			
			listaItens = new LinhaProdutoMetaFBRN().listarMeta(anoMes, empresaId, idVendedor, codEdtGestaoVendaMob, visualizarFilter, incluirDevolucao);
			
			totalMetaMensal = 0.0;
			totalMetaProp = 0.0;
			totalFatMensal = 0.0;
			totalDifMetaFat = 0.0;
			totalTendencia = 0.0;
			totalPercMeta = 0.0;
			totalPercTendencia = 0.0;
			totalMetaDiaria = 0.0;
			totalFatDiaria = 0.0;
			totalNovaMetaDiaria = 0.0;
			totalCoberturaReal = 0.0;
			totalCoberturaCarteira = 0.0;
			totalPercCobertura = 0.0;
			totalMixReal = 0.0;
			totalMixAtual = 0.0;
			totalPercMix = 0.0;
			
			for(LinhaProdutoMetaFBDTO rs:listaItens) {
				if(rs.getMetaMensal()!=null) {
					rs.setMetaProp((rs.getMetaMensal()/diasUts)*diasPrazo);
					totalMetaMensal += rs.getMetaMensal();
					totalMetaProp += rs.getMetaProp();
				}	
				
				if(rs.getFatMensal()!=null) {
					totalFatMensal += rs.getFatMensal();
				}	
				
				if(diasPrazo>0 && rs.getFatMensal()!=null) {
					rs.setTendencia((rs.getFatMensal()/diasPrazo)*(diasUts-diasPrazo)+rs.getFatMensal());
					totalTendencia += rs.getTendencia();
				}
				
				if(rs.getTendencia()!=null && rs.getMetaMensal()!=null) {
					if(rs.getMetaMensal()>0) {
						rs.setPercTendencia((rs.getTendencia()/rs.getMetaMensal())*100);
					}	
				}
				
				if(rs.getMetaMensal()!=null && diasPrazo>0){
					rs.setMetaDiaria(rs.getMetaMensal()/diasUts);
					totalMetaDiaria += rs.getMetaDiaria();
				}	
				
				if(rs.getFatMensal()!=null && diasPrazo>0){
					rs.setFatDiaria(rs.getFatMensal()/diasPrazo);
					totalFatDiaria += rs.getFatDiaria();
				}	
				
				rs.setDifMetaFat(rs.getFatMensal()-rs.getMetaProp());
				
				
				if(rs.getMetaMensal()!=null && rs.getFatMensal()!=null) {
					if(diasUts-diasPrazo>0) {
						rs.setNovaMetaDiaria((rs.getMetaMensal()-rs.getFatMensal())/(diasUts - diasPrazo));
					}	
				}
				
				rs.setPercMetaCobertura(rs.getCoberturaCarteira());
				rs.setPercMetaMix(rs.getMixAtual());
				
				rs.setCoberturaCarteira(rs.getClientesTotal());
				if(rs.getCoberturaCarteira()!=null) {
					//totalCoberturaCarteira = rs.getCoberturaCarteira();
				}	
				totalCoberturaCarteira = rs.getClientesMaster();
				
				
				rs.setCoberturaReal(rs.getClientesVendidos());
				totalCoberturaReal = rs.getClientesVendidoMaster();
				
				if(rs.getCoberturaCarteira()!=null && rs.getCoberturaReal()!=null) {
					if(rs.getCoberturaCarteira()>0) {
						rs.setPercCobertura((rs.getCoberturaReal()/rs.getCoberturaCarteira()) * 100);
					}	 
				}	
				
				rs.setMixReal(rs.getProdutosVendidos());
				if(rs.getMixReal()!=null){
					totalMixReal += rs.getMixReal();
				}	
				rs.setMixAtual(rs.getProdutosTotal());
				if(rs.getMixAtual()!=null) {
					//totalMixAtual = rs.getMixAtual();
				}
			
				totalMixReal = rs.getProdutosVendidoMaster();
				totalMixAtual = rs.getProdutosMaster();
				if(rs.getProdutosVendidos()!=null && rs.getProdutosTotal()!=null) {
					if(rs.getProdutosTotal()>0) {
						rs.setPercMix((rs.getProdutosVendidos()/rs.getProdutosTotal())*100);
					}	
				}	
				
			}
			
			//Calculando totais
			
			totalDifMetaFat = totalFatMensal-totalMetaProp;
			if(totalFatMensal>0 && totalMetaMensal>0) {
				totalPercMeta = (totalFatMensal/totalMetaMensal)*100;
			}
			
			
			
			if(totalMetaMensal>0) {
				totalPercTendencia =  ((totalTendencia/totalMetaMensal)*100);
			}	
			
			if((diasUts - diasPrazo)>0){
				totalNovaMetaDiaria = ((totalMetaMensal-totalFatMensal)/(diasUts - diasPrazo));
			}	
			
			if(totalMixAtual>0) {
				totalPercMix =  ((totalMixReal/totalMixAtual)*100);
			}	
			
			if(totalCoberturaCarteira>0) {
				totalPercCobertura =  ((totalCoberturaReal/totalCoberturaCarteira)*100);
			}	
			
			
			


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String styloTotais(Double valor) {
		String styleCss = "";
		if(valor>=1) {
			styleCss = "customBgBlueMeta";
		}else if(valor<1){ 
			styleCss = "customBgRedMeta";
		}
		if(valor==0.0) {
			styleCss = "";
		}
		
		return styleCss;
	}

	public String styloTotaisMeta(Double valor, Double meta) {
		String styleCss = "";
		if(valor>=meta) {
			styleCss = "customBgBlueMeta";
		}else if(valor<meta){ 
			styleCss = "customBgRedMeta";
		}
		if(valor==0.0) {
			styleCss = "";
		}
		
		return styleCss;
	}
	
		
	public void limpar(){
		anoMes = null;
		empresaFilter = null;
		descEmpresa = null;
		vendedorFilter = null;
		descVendedor = null;
		listaItens = null;
		
	}
	
    public void onComplete() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Carregamento concluido!"));
    }
     
    public void cancel() {
        progress = null;
    }
	
	

	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

	public LinhaProdutoMetaFB getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(LinhaProdutoMetaFB selecionada) {
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


	public String getAnoMes() {
		return anoMes;
	}

	public void setAnoMes(String anoMes) {
		this.anoMes = anoMes;
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

	public String getDescVendedor() {
		return descVendedor;
	}

	public void setDescVendedor(String descVendedor) {
		this.descVendedor = descVendedor;
	}

	public VendedorFB getVendedorFilter() {
		return vendedorFilter;
	}

	public void setVendedorFilter(VendedorFB vendedorFilter) {
		this.vendedorFilter = vendedorFilter;
	}

	public List<LinhaProdutoMetaFBDTO> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<LinhaProdutoMetaFBDTO> listaItens) {
		this.listaItens = listaItens;
	}

	public List<SelectItem> getVendedorSelect() {
		if (this.vendedorSelect == null) {
			this.vendedorSelect = new ArrayList<SelectItem>();
			VendedorFBRN vendedorFBRN = new VendedorFBRN();
			this.vendedorSelect = vendedorFBRN.montaDadosSelect(new VendedorFBRN().listar(""), "");
		}
		
		return vendedorSelect;
	}

	public GestaoVendaMobFB getGestaoVendaMobFilter() {
		return gestaoVendaMobFilter;
	}

	public void setGestaoVendaMobFilter(GestaoVendaMobFB gestaoVendaMobFilter) {
		this.gestaoVendaMobFilter = gestaoVendaMobFilter;
	}

	public List<SelectItem> getGestaoVendaMobFilterSelect() {
		if (this.gestaoVendaMobFilterSelect == null) {
			this.gestaoVendaMobFilterSelect = new ArrayList<SelectItem>();
			GestaoVendaMobFBRN gestaoVendaMobFBRN = new GestaoVendaMobFBRN();
			this.gestaoVendaMobFilterSelect = gestaoVendaMobFBRN.montaDadosSelected(new GestaoVendaMobFBRN().listar(), "");
		}
		return gestaoVendaMobFilterSelect;
	}

	public void setGestaoVendaMobFilterSelect(List<SelectItem> gestaoVendaMobFilterSelect) {
		this.gestaoVendaMobFilterSelect = gestaoVendaMobFilterSelect;
	}

	public void setVendedorSelect(List<SelectItem> vendedorSelect) {
		this.vendedorSelect = vendedorSelect;
	}

	public Double getTotalMetaMensal() {
		return totalMetaMensal;
	}

	public void setTotalMetaMensal(Double totalMetaMensal) {
		this.totalMetaMensal = totalMetaMensal;
	}

	public Double getTotalMetaProp() {
		return totalMetaProp;
	}

	public void setTotalMetaProp(Double totalMetaProp) {
		this.totalMetaProp = totalMetaProp;
	}

	public Double getTotalFatMensal() {
		return totalFatMensal;
	}

	public void setTotalFatMensal(Double totalFatMensal) {
		this.totalFatMensal = totalFatMensal;
	}

	public Double getTotalDifMetaFat() {
		return totalDifMetaFat;
	}

	public void setTotalDifMetaFat(Double totalDifMetaFat) {
		this.totalDifMetaFat = totalDifMetaFat;
	}

	public Double getTotalTendencia() {
		return totalTendencia;
	}

	public void setTotalTendencia(Double totalTendencia) {
		this.totalTendencia = totalTendencia;
	}

	public Double getTotalPercMeta() {
		return totalPercMeta;
	}

	public void setTotalPercMeta(Double totalPercMeta) {
		this.totalPercMeta = totalPercMeta;
	}

	public Double getTotalPercTendencia() {
		return totalPercTendencia;
	}

	public void setTotalPercTendencia(Double totalPercTendencia) {
		this.totalPercTendencia = totalPercTendencia;
	}

	public Double getTotalMetaDiaria() {
		return totalMetaDiaria;
	}

	public void setTotalMetaDiaria(Double totalMetaDiaria) {
		this.totalMetaDiaria = totalMetaDiaria;
	}

	public Double getTotalFatDiaria() {
		return totalFatDiaria;
	}

	public void setTotalFatDiaria(Double totalFatDiaria) {
		this.totalFatDiaria = totalFatDiaria;
	}

	public Double getTotalNovaMetaDiaria() {
		return totalNovaMetaDiaria;
	}

	public void setTotalNovaMetaDiaria(Double totalNovaMetaDiaria) {
		this.totalNovaMetaDiaria = totalNovaMetaDiaria;
	}

	public Double getTotalCoberturaReal() {
		return totalCoberturaReal;
	}

	public void setTotalCoberturaReal(Double totalCoberturaReal) {
		this.totalCoberturaReal = totalCoberturaReal;
	}

	public Double getTotalCoberturaCarteira() {
		return totalCoberturaCarteira;
	}

	public void setTotalCoberturaCarteira(Double totalCoberturaCarteira) {
		this.totalCoberturaCarteira = totalCoberturaCarteira;
	}

	public Double getTotalPercCobertura() {
		return totalPercCobertura;
	}

	public void setTotalPercCobertura(Double totalPercCobertura) {
		this.totalPercCobertura = totalPercCobertura;
	}

	public Double getTotalMixReal() {
		return totalMixReal;
	}

	public void setTotalMixReal(Double totalMixReal) {
		this.totalMixReal = totalMixReal;
	}

	public Double getTotalMixAtual() {
		return totalMixAtual;
	}

	public void setTotalMixAtual(Double totalMixAtual) {
		this.totalMixAtual = totalMixAtual;
	}

	public Double getTotalPercMix() {
		return totalPercMix;
	}

	public void setTotalPercMix(Double totalPercMix) {
		this.totalPercMix = totalPercMix;
	}

	public String getVisualizarFilter() {
		return visualizarFilter;
	}

	public void setVisualizarFilter(String visualizarFilter) {
		this.visualizarFilter = visualizarFilter;
	}

    public boolean isIncluirDevolucao() {
		return incluirDevolucao;
	}

	public void setIncluirDevolucao(boolean incluirDevolucao) {
		this.incluirDevolucao = incluirDevolucao;
	}

	public Integer getdUteis() {
		return dUteis;
	}

	public void setdUteis(Integer dUteis) {
		this.dUteis = dUteis;
	}

	public Integer getPrazoDecorrido() {
		return prazoDecorrido;
	}

	public void setPrazoDecorrido(Integer prazoDecorrido) {
		this.prazoDecorrido = prazoDecorrido;
	}

	public Integer getProgress() {
        if(progress == null) {
            progress = 0;
        }
        else {
            progress = progress + (int)(Math.random() * 35);
             
            if(progress > 100)
                progress = 100;
        }
         
        return progress;
    }
 
    public void setProgress(Integer progress) {
        this.progress = progress;
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
		System.out.println("[ConsultaLinhaProdutoMetaBean][clearSession]");
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		    session.removeAttribute("consultaLinhaProdutoMetaBean");
		}
	}

}
