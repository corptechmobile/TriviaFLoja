package br.com.webapp.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.TreeNode;

import br.com.webapp.model.fb.coletorpc.ColetorDivergenciaFB;
import br.com.webapp.model.fb.coletorpc.ColetorDivergenciaFBRN;
import br.com.webapp.model.fb.coletorpc.ColetorPCDivergFB;
import br.com.webapp.model.fb.coletorpc.ColetorPCDivergFBRN;
import br.com.webapp.model.fb.conferente.ConferenteFB;
import br.com.webapp.model.fb.conferente.ConferenteFBRN;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.model.fb.usuario.UsuarioFBRN;
import br.com.webapp.model.fb.usuariocoletordiverg.UsuarioColetorDivergFB;
import br.com.webapp.model.fb.usuariocoletordiverg.UsuarioColetorDivergFBRN;
import br.com.webapp.model.usuario.Usuario;
import br.com.webapp.model.usuario.UsuarioRN;
import br.com.webapp.model.usuariogrupo.UsuarioGrupo;
import br.com.webapp.model.usuariogrupo.UsuarioGrupoRN;
import br.com.webapp.web.adapter.ColetorDivergAdapter;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.UtilMessage;



@ManagedBean(name = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable, IMenuAcesso {
	
	private static final long serialVersionUID = 5842443581454187462L;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	private boolean botNovo = false;
	private EmpresaFB empresaFilter;
	private ConferenteFB conferente;
	private List<EmpresaFB> empresas;
	private String tituloAdd = "CADASTRAR";
	private String senha2;
	private UsuarioGrupo usuarioGrupoFilter;
	private String descricaoFilter;
	private Boolean situacaoFilter;
	private List<SelectItem> usuarioGruposSelect;
	private List<SelectItem> empresasSelect;
	private List<SelectItem> conferentesSelect;
	
	private Usuario usuario;
	private UsuarioFB usuarioFB;
	private List<UsuarioFB> lista;
	private List<ColetorDivergenciaFB> listaDivergencia;
	@ManagedProperty("#{coletorDivergAdapter}")
	private ColetorDivergAdapter coletorDivergAdapter;
	private TreeNode treeTable;
	private TreeNode[] treeTableSelected;
	private Usuario selecionada = new Usuario();
	private Set<ColetorDivergenciaFB> divergenciasSelecionadas = new HashSet<ColetorDivergenciaFB>(0);
	private Set<ColetorDivergenciaFB> divergenciasUsuario = new HashSet<ColetorDivergenciaFB>(0);
	private Integer menu = MenuAcessoController.USUARIOBEAN_MENU;
	
	@PostConstruct
	public void init(){
		this.treeTable = null;	
		situacaoFilter = true;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			addRecentes();
		}
		
		
		
	}
	
	public void buscar(){
		lista = null;
	}
	
	public void limpar(){
		descricaoFilter = null;
		empresaFilter = null;
		situacaoFilter = null;
		usuarioGrupoFilter = null;
		lista = null;
	}
	
	public void salvar(){
		
		try {  
			
			UsuarioRN usuarioRN = new UsuarioRN();
			
			if(conferente!=null) {
				usuario.setConferenteId(conferente.getId());
			}
			
			usuarioRN.salvar(usuario);	
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.editado.usuario")));
			UsuarioColetorDivergFBRN usuarioColetorDivergFBRN = new UsuarioColetorDivergFBRN();
			this.lista = null;
			divergenciasSelecionadas = new HashSet<ColetorDivergenciaFB>();
			
			for(TreeNode node : treeTable.getChildren()) {
				if(node.isSelected()){
					divergenciasSelecionadas.add((ColetorDivergenciaFB) node.getData());
				}
			}
			
			usuarioColetorDivergFBRN.excluir(usuario.getId());
			
			if(divergenciasSelecionadas.size()>0) {
				for(ColetorDivergenciaFB ucd : divergenciasSelecionadas) {
					usuarioColetorDivergFBRN.salvar(usuario.getId(), ucd.getDivergenciaId());
				}
			}
			
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
			e.printStackTrace();
		}
	}

	
	public void editar(){
		
		this.tituloAdd = "EDITAR";
		
		UsuarioRN usuarioRN = new UsuarioRN();
		usuario = usuarioRN.carregar(usuarioFB.getId());
		if(usuario==null) {
			usuario = new Usuario();
			usuario.setId(usuarioFB.getId());
		}
		
		conferente = null;
		
		if(usuario.getConferenteId()!=null) {
			conferente = new ConferenteFBRN().carregar(usuario.getConferenteId());
		}
		
		divergenciasUsuario = new HashSet<ColetorDivergenciaFB>();
		
		List<ColetorDivergenciaFB> listaDivergUsu = new ColetorDivergenciaFBRN().listarPorUsuario(usuarioFB.getId()); 

		for(ColetorDivergenciaFB rs: listaDivergUsu) {
			divergenciasUsuario.add((ColetorDivergenciaFB) rs);
		}
		
		if(this.treeTable!=null){
			editarCheckTreeTable(this.treeTable.getChildren());
		}
		
	}
	
	private void editarCheckTreeTable(List<TreeNode> list){
		for(TreeNode t : list){
			t.setSelected(coletorDivergAdapter.verifyChecked(divergenciasUsuario, (ColetorDivergenciaFB) t.getData()));
			if(t.getChildCount()>0){
				editarCheckTreeTable(t.getChildren());
			}
		}
	}
	
	public void openTabEstoqueEARuaAssoc() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.removeAttribute("usuarioEstoqueEARuaAssocBean");
	}
	
	public String verGrupoUsuario(Integer usuarioId){
		
		UsuarioRN usuarioRN = new UsuarioRN();
		Usuario usuarioVer = (Usuario) usuarioRN.carregar(usuarioId);
		if(usuarioVer!=null) {
			return usuarioVer.getUsuarioGrupo().getDescricao();
		}
		
		return "";
		
	}
	
	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioFB getUsuarioFB() {
		return usuarioFB;
	}

	public void setUsuarioFB(UsuarioFB usuarioFB) {
		this.usuarioFB = usuarioFB;
	}

	public List<UsuarioFB> getLista() {
		
		if(this.lista == null){
			UsuarioFBRN usuarioFBRN = new UsuarioFBRN();
			this.lista = usuarioFBRN.listar(descricaoFilter, situacaoFilter);
		}
		
		return lista;
	}

	public void setLista(List<UsuarioFB> lista) {
		this.lista = lista;
	}

	public ConferenteFB getConferente() {
		return conferente;
	}

	public void setConferente(ConferenteFB conferente) {
		this.conferente = conferente;
	}

	public String getTituloAdd() {
		return tituloAdd;
	}

	public void setTituloAdd(String tituloAdd) {
		this.tituloAdd = tituloAdd;
	}
	
	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}

	public UsuarioGrupo getUsuarioGrupoFilter() {
		return usuarioGrupoFilter;
	}

	public void setUsuarioGrupoFilter(UsuarioGrupo usuarioGrupoFilter) {
		this.usuarioGrupoFilter = usuarioGrupoFilter;
	}

	public List<SelectItem> getUsuarioGruposSelect() {
		if (this.usuarioGruposSelect == null) {
			UsuarioGrupoRN usuarioGrupoRN = new UsuarioGrupoRN();
			List<UsuarioGrupo> usuarioGrupos = new ArrayList<UsuarioGrupo>();
			usuarioGrupos = usuarioGrupoRN.listar();
			this.usuarioGruposSelect = usuarioGrupoRN.montaDadosSelect(usuarioGrupos, "");
		}
		return usuarioGruposSelect;
	}

	public void setUsuarioGruposSelect(List<SelectItem> usuarioGruposSelect) {
		this.usuarioGruposSelect = usuarioGruposSelect;
	}

	public List<SelectItem> getConferentesSelect() {
		if(conferentesSelect==null) {
			ConferenteFBRN conferenteFBRN = new ConferenteFBRN();
			conferentesSelect = conferenteFBRN.montaDadosSelect(conferenteFBRN.listar(), "");
		}		
		return conferentesSelect;
	}

	public void setConferentesSelect(List<SelectItem> conferentesSelect) {
		this.conferentesSelect = conferentesSelect;
	}

	public String getDescricaoFilter() {
		return descricaoFilter;
	}

	public void setDescricaoFilter(String descricaoFilter) {
		this.descricaoFilter = descricaoFilter;
	}


	public Boolean getSituacaoFilter() {
		return situacaoFilter;
	}

	public void setSituacaoFilter(Boolean situacaoFilter) {
		this.situacaoFilter = situacaoFilter;
	}

	// Implementation Menu
	public void addHome() {
		MenuAcessoController.addHome(menu, contextoBean.getUsuarioLogado(), FacesContext.getCurrentInstance());
	}

	public void addRecentes() {
		MenuAcessoController.addRecentes(menu, contextoBean.getUsuarioLogado());
	}

	public void addFavoritos() {
		MenuAcessoController.addFavoritos(menu, contextoBean.getUsuarioLogado(), FacesContext.getCurrentInstance());
	}
	
	public void clearSession() {
		  HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		  if(request.getParameter("id")!=null){
			  HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		      session.removeAttribute("usuarioBean");
		      session.removeAttribute("coletorDivergAdapter");
		  }
	}

	public ColetorDivergAdapter getColetorDivergAdapter() {
		return coletorDivergAdapter;
	}

	public void setColetorDivergAdapter(ColetorDivergAdapter coletorDivergAdapter) {
		this.coletorDivergAdapter = coletorDivergAdapter;
	}

	public TreeNode getTreeTable() {
		if(this.listaDivergencia == null){
			ColetorDivergenciaFBRN coletorDivergenciaFBRN = new ColetorDivergenciaFBRN();
			this.listaDivergencia = coletorDivergenciaFBRN.listar();
			this.treeTable = coletorDivergAdapter.createCheckbox(listaDivergencia);
		}
		return treeTable;
	}

	public void setTreeTable(TreeNode treeTable) {
		this.treeTable = treeTable;
	}
	public void onNodeSelect(NodeSelectEvent event){
		event.getTreeNode().setSelected(true);
	}
	
	public void onNodeUnselect(NodeUnselectEvent event) {
		event.getTreeNode().setSelected(false);
    }
	
	public void novoUnCheckedTreeNode(List<TreeNode> list){
		for(TreeNode node : list) {
			if(node.getChildCount()>0){
				node.setSelected(false);
				novoUnCheckedTreeNode(node.getChildren());
			}else if (node.isSelected()){
				node.setSelected(false);
			}
        }
	}
	public TreeNode[] getTreeTableSelected() {
		return treeTableSelected;
	}

	public void setTreeTableSelected(TreeNode[] treeTableSelected) {
		this.treeTableSelected = treeTableSelected;
	}

	public Usuario getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(Usuario selecionada) {
		this.selecionada = selecionada;
	}

	public Integer getMenu() {
		return menu;
	}

	public void setMenu(Integer menu) {
		this.menu = menu;
	}
	
	public Set<ColetorDivergenciaFB> getDivergenciasSelecionadas() {
		return divergenciasSelecionadas;
	}

	public void setDivergenciasSelecionadas(Set<ColetorDivergenciaFB> divergenciasSelecionadas) {
		this.divergenciasSelecionadas = divergenciasSelecionadas;
	}

	public Set<ColetorDivergenciaFB> getDivergenciasUsuario() {
		return divergenciasUsuario;
	}

	public void setDivergenciasUsuario(Set<ColetorDivergenciaFB> divergenciasUsuario) {
		this.divergenciasUsuario = divergenciasUsuario;
	}
	
}
