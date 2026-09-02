package br.com.webapp.model.fb.usuario;

import java.util.List;

import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.DAOFactoryFirebird;
import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilMessage;

public class UsuarioFBRN {
	
	private UsuarioFBDAO usuarioFBDAO;
	
	public UsuarioFBRN(){
		this.usuarioFBDAO = DAOFactoryFirebird.criarUsuarioFBDAO();
	}

	public UsuarioFB carregar(String login) {
		return this.usuarioFBDAO.carregar(login);
	}
	
	public UsuarioFB carregar(Integer usuarioId) {
		return this.usuarioFBDAO.carregar(usuarioId);
	}

	public List<UsuarioFB> listar(String descricaoFilter, Boolean situacaoFilter) {
		return this.usuarioFBDAO.listar(descricaoFilter, situacaoFilter);
	}

	public UsuarioFB salvarNovaSenha(UsuarioFB usuario, String senhaAtual, String senhaNova, boolean verificarSenhaAtual) throws RNException {
		
		try {
			if(verificarSenhaAtual) {
				senhaAtual = Funcoes.senhaMD5(senhaAtual).toUpperCase();
			}
			senhaNova = Funcoes.senhaMD5(senhaNova).toUpperCase();
		} catch (Exception e) {
			throw new RNException(UtilMessage.mensagem("msg.erro.salvar.senha.usuario"));
		}
		
		if(senhaNova == null || senhaNova.equals("")){
			throw new RNException(UtilMessage.mensagem("msg.senha.nova.invalida.usuario"));
		}
		
		if(verificarSenhaAtual && senhaAtual.equals(usuario.getSenha()) == false){
			throw new RNException(UtilMessage.mensagem("msg.senha.atual.invalida.usuario"));
		}
		
		try {
			this.usuarioFBDAO.salvarNovaSenha(usuario.getId(), senhaNova);
		} catch (DAOException e) {
			throw new RNException(UtilMessage.mensagem("msg.erro.salvar.senha.usuario"));
		}
		
		usuario.setSenha(senhaNova);
		
		return usuario;
	}

}
