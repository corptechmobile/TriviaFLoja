package br.com.webapp.model.fb.empresa;

import java.util.List;

import br.com.webapp.model.fb.usuario.UsuarioFB;

public interface EmpresaFBDAO {
	public EmpresaFB carregar(Integer empresaId);
	public List<EmpresaFB> listar(UsuarioFB usuarioFB);
	public List<EmpresaFB> listar();
}
