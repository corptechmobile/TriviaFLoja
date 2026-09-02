package br.com.webapp.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.webapp.model.fb.usuario.UsuarioFB;
import br.com.webapp.model.fb.usuario.UsuarioFBRN;
import br.com.webapp.web.util.ContextoUtil;
import br.com.webapp.web.util.UtilMessage;

@ManagedBean(name = "mudarSenhaBean")
@SessionScoped
public class MudarSenhaBean implements Serializable {
	
	private static final long serialVersionUID = 8514427308589196584L;
	
	private ContextoBean contextoBean;
	
	private String senha_atual;
	private String senha_nova;
	private String senha_confirma;
	private Integer selecionadaId;
	private UsuarioFB selecionada;
	
	private boolean verificarSenhaAtual;
	
	@PostConstruct
	public void init(){
		setContextoBean(ContextoUtil.getContextoBean());
		limpar();
	}
	
	public void editar() {
		verificarSenhaAtual = false;
		selecionada = null;
	}
	
	public void salvar(){
		
		try {
			
			UsuarioFBRN usuarioFBRN = new UsuarioFBRN();
			selecionada = usuarioFBRN.salvarNovaSenha(selecionada, senha_atual, senha_nova, verificarSenhaAtual);
			
			limpar();
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.senha.alterada.usuario")));
				
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
			e.printStackTrace();
		}
		
	}
	
	private void limpar() {
		senha_atual = "";
		senha_nova = "";
		senha_confirma = "";
	}
	
	//
	public UsuarioFB getSelecionada() {
		if(selecionada == null && selecionadaId != null) {
			selecionada = new UsuarioFBRN().carregar(selecionadaId);
			UsuarioFB usuarioLogado = contextoBean.getUsuarioLogado();

			if(usuarioLogado.getLogin().equals(selecionada.getLogin())) {
				verificarSenhaAtual = true;
			}
		}
		return selecionada;
	}

	public void setSelecionada(UsuarioFB selecionada) {
		this.selecionada = selecionada;
	}

	public String getSenha_atual() {
		return senha_atual;
	}

	public void setSenha_atual(String senha_atual) {
		this.senha_atual = senha_atual;
	}

	public String getSenha_nova() {
		return senha_nova;
	}

	public void setSenha_nova(String senha_nova) {
		this.senha_nova = senha_nova;
	}

	public String getSenha_confirma() {
		return senha_confirma;
	}

	public void setSenha_confirma(String senha_confirma) {
		this.senha_confirma = senha_confirma;
	}

	public Integer getSelecionadaId() {
		return selecionadaId;
	}

	public void setSelecionadaId(Integer selecionadaId) {
		this.selecionadaId = selecionadaId;
	}

	public boolean isVerificarSenhaAtual() {
		return verificarSenhaAtual;
	}

	public void setVerificarSenhaAtual(boolean verificarSenhaAtual) {
		this.verificarSenhaAtual = verificarSenhaAtual;
	}

	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}
	
}
