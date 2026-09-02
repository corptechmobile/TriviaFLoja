package br.com.webapp.web;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.primefaces.context.RequestContext;

import com.google.gson.Gson;

import br.com.webapp.model.fb.cliente.ClienteFB;
import br.com.webapp.model.fb.cliente.ClienteFBRN;
import br.com.webapp.model.fb.enderecotipo.EnderecoTipoFB;
import br.com.webapp.model.fb.enderecotipo.EnderecoTipoFBRN;
import br.com.webapp.model.fb.estado.EstadoFB;
import br.com.webapp.model.fb.estado.EstadoFBRN;
import br.com.webapp.model.fb.municipio.MunicipioFB;
import br.com.webapp.model.fb.municipio.MunicipioFBRN;
import br.com.webapp.model.fb.pais.PaisFB;
import br.com.webapp.model.fb.pais.PaisFBRN;
import br.com.webapp.model.fb.telefonetipo.TelefoneTipoFB;
import br.com.webapp.model.fb.telefonetipo.TelefoneTipoFBRN;
import br.com.webapp.model.web.cep.WSCepRetorno;
import br.com.webapp.model.web.receita.ReceitaWsResultDTO;
import br.com.webapp.web.util.Funcoes;
import br.com.webapp.web.util.ReceitaWsUtils;
import br.com.webapp.web.util.UtilMessage;
import br.com.webapp.web.util.UtilText;

@ManagedBean(name="clienteNovoBean")
@SessionScoped
public class ClienteNovoBean implements Serializable {

	private static final long serialVersionUID = -7996536549182556428L;
	
	private static final String PAIS_BRASIL = "01058";
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private boolean clienteAtivo;
	private ClienteFB selecionada;
	private boolean editando = false;
	private String focus;
	
	private List<SelectItem> paisesSelect;
	private List<SelectItem> estadosSelect;
	private List<SelectItem> municipiosSelect;
	private List<SelectItem> telefoneTiposSelect;
	private List<SelectItem> enderecoTiposSelect;
	
	private EnderecoTipoFB enderecoTipo;
	private PaisFB pais;
	private EstadoFB estado;
	private MunicipioFB municipio;
	private TelefoneTipoFB telefoneTipo;
	
	private WSCepRetorno wsCepRetorno;
	private ReceitaWsResultDTO receitaWsResultDTO;
	
	@PostConstruct
	public void init(){
		System.out.println("[ClienteNovoBean][init]");
	}
	
	public void novo() {
		
		System.out.println("[ClienteNovoBean][novo]");
		
		editando = false;
		focus = "fieldDados";
		
		selecionada = new ClienteFB();
		selecionada.setAtivo(ClienteFB.CLIENTE_ATIVO);
		selecionada.setTipoPessoa(ClienteFB.TIPO_PESSOA_FISICA);
		selecionada.setPaisId(PAIS_BRASIL);
		
		enderecoTipo = new EnderecoTipoFB();
		
		pais = new PaisFB();
		pais.setId(PAIS_BRASIL);
		
		municipio = new MunicipioFB();
		
		estado = new EstadoFB();
		
		// Codigo para teste
		boolean teste = false;
		if(teste){
			this.selecionada.setCnpjCpf("04600288475");
			this.selecionada.setNumRg("123456");
			this.selecionada.setRazaoSocial("Leonardo da Vinci");
			this.selecionada.setNomeFantasia("Leo");
			this.selecionada.setEmail("leonardo@corptech.com.br");
			
			this.selecionada.setLogradouro("Rua buique, 06");
			this.selecionada.setBairro("Janga");
			this.selecionada.setComplemento("Casa");
			this.selecionada.setPontoReferencia("Uma rua antes do bompreço.");
			this.selecionada.setCep("50030-200");
			
			this.selecionada.setCodArea(81);
			this.selecionada.setNumero("985814806");
			this.selecionada.setRamal("2348");
		}

	}
	
	public void editar() {
		System.out.println("[ClienteNovoBean][editar]");
		focus = "fieldDados";
		editando = true;
		enderecoTipo = new EnderecoTipoFBRN().carregar(selecionada.getEnderecoTipoId());
		pais = new PaisFBRN().carregar(selecionada.getPaisId());
		estado = new EstadoFBRN().carregar(selecionada.getEstadoId());
		municipio = new MunicipioFBRN().carregar(selecionada.getMunicipioId());
		telefoneTipo = new TelefoneTipoFBRN().carregar(selecionada.getTelefoneTipoId());
		
	}
	
	public void cadastrar(ClienteFB clienteFB) {
		
		novo();
		selecionada.setCnpjCpf(clienteFB.getCnpjCpf());
		selecionada.setTipoPessoa(clienteFB.getTipoPessoa());
		
		System.out.println("[ClienteNovoBean][cadastrar]");
		System.out.println("[ClienteNovoBean][cadastrar][cnpjcpf]" + selecionada.getCnpjCpf());
		
	}
	
	public void salvar() {
		System.out.println("[ClienteNovoBean][salvar]");
		try {
			
			selecionada.setNomeFantasia(UtilText.maiusculo(selecionada.getNomeFantasia()));
			selecionada.setRazaoSocial(UtilText.maiusculo(selecionada.getRazaoSocial()));
			selecionada.setLogradouro(UtilText.maiusculo(selecionada.getLogradouro()));
			selecionada.setBairro(UtilText.maiusculo(selecionada.getBairro()));
			selecionada.setCidade(UtilText.maiusculo(selecionada.getCidade()));
			selecionada.setUsuarioUltAlteracao(contextoBean.getUsuarioLogado().getId());
			selecionada.setDataUltAlteracao(new Date());
			
			if(telefoneTipo!=null) {
				selecionada.setTelefoneTipoId(telefoneTipo.getId());
			}
			
			if(enderecoTipo!=null) {
				selecionada.setEnderecoTipoId(enderecoTipo.getId());
			}
			
			if(municipio!=null) {
				selecionada.setMunicipioId(municipio.getId());
				selecionada.setCidade(municipio.getNome());
			}
			
			ClienteFBRN clienteFBRN = new ClienteFBRN();
			clienteFBRN.salvar(selecionada);
			
			if(editando==false) {
				String request = "verificarPedVendaCadCli('"+Funcoes.formatCnpjCpfCep(selecionada.getCnpjCpf())+"')";
				RequestContext requestContext = RequestContext.getCurrentInstance();
				requestContext.execute(request);
			}else{
				String request = "focusInPainel('#edicaoCliente', 110)";
				RequestContext requestContext = RequestContext.getCurrentInstance();
				requestContext.execute(request);
			}
			
			editando = true;
			
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, UtilMessage.mensagem("msg.salvo.cliente")));
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, e.getMessage()));
			
		}
		
	}
	
	public void buscarCep(){
		
		try {
			
			HttpClient httpClient = new DefaultHttpClient();
			Gson gson = new Gson();
			
			HttpGet request = new HttpGet("https://opencep.com/v1/"+this.selecionada.getCep()+"");
			request.addHeader("content-type", "application/json; charset=utf-8");
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();

			InputStream source = entity.getContent();
			Reader reader = new InputStreamReader(source, "UTF-8");

			wsCepRetorno = gson.fromJson(reader, WSCepRetorno.class);
			if(wsCepRetorno!=null){
			
				this.municipiosSelect=null;
				this.estadosSelect=null;
				
				if(wsCepRetorno.getLogradouro()!=null && wsCepRetorno.getLogradouro().length() > 60) {
					this.selecionada.setLogradouro(wsCepRetorno.getLogradouro().substring(0, 60));
				}else {
					this.selecionada.setLogradouro(wsCepRetorno.getLogradouro());
				}
				
				
				if(wsCepRetorno.getBairro()!=null && wsCepRetorno.getBairro().length() > 30) {
					this.selecionada.setBairro(wsCepRetorno.getBairro().substring(0, 30));
				}else {
					this.selecionada.setBairro(wsCepRetorno.getBairro());
				}
				
				
				this.selecionada.setCidade(wsCepRetorno.getLocalidade());
					
				pais = new PaisFB();
				pais.setId(PAIS_BRASIL);
				this.selecionada.setPaisId(pais.getId());
				
				estado = new EstadoFB();
				estado.setId(wsCepRetorno.getUf());
				if(estado != null) {
					this.selecionada.setEstadoId(estado.getId());
				}else {
					this.selecionada.setEstadoId(null);
				}
				
				municipio = new MunicipioFBRN().carregar(wsCepRetorno.getLocalidade(), wsCepRetorno.getUf());
				if(municipio.getId() != null) {
					this.selecionada.setMunicipioId(municipio.getId());
				}else {
					this.selecionada.setMunicipioId(null);
				}
				
			}else{
				this.selecionada.setLogradouro("");
				this.selecionada.setBairro("");
				this.selecionada.setCidade("");
				this.selecionada.setPaisId(null);
				this.selecionada.setEstadoId(null);
				this.selecionada.setMunicipioId(null);
			}

		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
	
	public void verificarTipoPessoa(){
		if(this.selecionada.getId()==null && this.selecionada.getTipoPessoa().equals(ClienteFB.TIPO_PESSOA_OUTRO)){
//			Integer clienteId = new ClienteRN().gerarId();
//			this.selecionada.setVerErp(clienteId);
//			this.selecionada.getPessoa().setCnpjCpf(String.format("%-14s", clienteId).replace(' ', '0'));	
		}
	}
	
	public void verificarCpfCnpj(){
		ClienteFBRN clienteRN = new ClienteFBRN();
		ClienteFB cliente = clienteRN.carregar(selecionada.getCnpjCpf());
		if(cliente!=null){
			selecionada = cliente;
			editar();
			focus = "fieldDados";
		}else {
			if(selecionada.getTipoPessoa().equals(ClienteFB.TIPO_PESSOA_FISICA)) {
				focus = "panelNumRg";
			}else if(selecionada.getTipoPessoa().equals(ClienteFB.TIPO_PESSOA_JURIDICA)) {
				ReceitaWsResultDTO dto = ReceitaWsUtils.buscarEmpresa(selecionada.getCnpjCpf());
				if(dto!=null){
					selecionada.setRazaoSocial(dto.getNome());
					selecionada.setNomeFantasia(dto.getFantasia());
					String cep = "";
					if(dto.getCep()!=null && !"".equals(dto.getCep())) {
						cep = dto.getCep();
						cep = cep.replace(".", "");
						cep = cep.replace("-", ""); 
					}
					
					selecionada.setCep(cep);
					selecionada.setEmail(dto.getEmail());
					buscarCep();
					
					if(dto.getTelefone()!=null && !"".equals(dto.getTelefone())) {
						String[] telefone = dto.getTelefone().split(" ");
						String codArea = telefone[0];
						codArea = codArea.replace("(", "");
						codArea = codArea.replace(")","");
						if(codArea!=null && !"".equals(codArea)) {
							selecionada.setCodArea(Integer.parseInt(codArea));
						}
						
						String numero = telefone[1];
						if(numero!=null && !"".equals(numero)) {
							selecionada.setNumero(numero);
						}
						
					}
					
					int tamRua = 0;
					if(selecionada.getLogradouro() != null && !"".equals(selecionada.getLogradouro())) {
						tamRua = selecionada.getLogradouro().length();
					}
					
					int tamNumero = 0;
					if(dto.getNumero()!=null && !"".equals(dto.getNumero())){
						tamNumero = dto.getNumero().length();
					}
					
					if((tamRua>tamNumero) && (tamRua-tamNumero) <= 58) {
						selecionada.setLogradouro(selecionada.getLogradouro()+", "+dto.getNumero());
					}
					
					selecionada.setComplemento(dto.getComplemento());

					editar();
				}
				focus = "panelInscEst";
			}else {
				focus = "fieldDados";
			}
		}
	}
	
	public void verificarPais(){
		System.out.println("[ClienteNovoBean][verificarPais]");
		selecionada.setEstadoId(pais.getId());
		estadosSelect = null;
		municipiosSelect = null;
	}
	
	public void verificarEstado() {
		System.out.println("[ClienteNovoBean][verificarEstado]");
		selecionada.setEstadoId(estado.getId());
		municipiosSelect = null;
	}
	
	// gets and sets
	
	public List<SelectItem> getPaisesSelect() {
		if (this.paisesSelect == null) {
			
			this.paisesSelect = new ArrayList<SelectItem>();
			
			PaisFBRN paisRN = new PaisFBRN();
			this.paisesSelect = paisRN.montaDadosSelect(paisRN.listar(), "");
			
		}
		return paisesSelect;
	}
	
	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
	}

	public ClienteFB getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(ClienteFB selecionada) {
		this.selecionada = selecionada;
	}

	public boolean isEditando() {
		return editando;
	}

	public void setEditando(boolean editando) {
		this.editando = editando;
	}

	public EnderecoTipoFB getEnderecoTipo() {
		return enderecoTipo;
	}

	public void setEnderecoTipo(EnderecoTipoFB enderecoTipo) {
		this.enderecoTipo = enderecoTipo;
	}

	public PaisFB getPais() {
		return pais;
	}

	public void setPais(PaisFB pais) {
		this.pais = pais;
	}

	public EstadoFB getEstado() {
		return estado;
	}

	public void setEstado(EstadoFB estado) {
		this.estado = estado;
	}

	public MunicipioFB getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioFB municipio) {
		this.municipio = municipio;
	}

	public TelefoneTipoFB getTelefoneTipo() {
		return telefoneTipo;
	}

	public void setTelefoneTipo(TelefoneTipoFB telefoneTipo) {
		this.telefoneTipo = telefoneTipo;
	}

	public WSCepRetorno getWsCepRetorno() {
		return wsCepRetorno;
	}

	public void setWsCepRetorno(WSCepRetorno wsCepRetorno) {
		this.wsCepRetorno = wsCepRetorno;
	}

	public ReceitaWsResultDTO getReceitaWsResultDTO() {
		return receitaWsResultDTO;
	}

	public void setReceitaWsResultDTO(ReceitaWsResultDTO receitaWsResultDTO) {
		this.receitaWsResultDTO = receitaWsResultDTO;
	}

	public void setPaisesSelect(List<SelectItem> paisesSelect) {
		this.paisesSelect = paisesSelect;
	}

	public void setEstadosSelect(List<SelectItem> estadosSelect) {
		this.estadosSelect = estadosSelect;
	}

	public void setMunicipiosSelect(List<SelectItem> municipiosSelect) {
		this.municipiosSelect = municipiosSelect;
	}

	public void setTelefoneTiposSelect(List<SelectItem> telefoneTiposSelect) {
		this.telefoneTiposSelect = telefoneTiposSelect;
	}

	public void setEnderecoTiposSelect(List<SelectItem> enderecoTiposSelect) {
		this.enderecoTiposSelect = enderecoTiposSelect;
	}

	public List<SelectItem> getEstadosSelect() {
		if (this.estadosSelect == null && selecionada.getPaisId().equals(PAIS_BRASIL)) {
			
			this.estadosSelect = new ArrayList<SelectItem>();
			
			EstadoFBRN estadoRN = new EstadoFBRN();
			this.estadosSelect = estadoRN.montaDadosSelect(estadoRN.listar(), "");
			
		}else if (this.estadosSelect == null){
			this.estadosSelect = new ArrayList<SelectItem>();
		}
		return estadosSelect;
	}
	
	public List<SelectItem> getMunicipiosSelect() {
		if (this.municipiosSelect == null && selecionada.getEstadoId()!=null) {
			
			this.municipiosSelect = new ArrayList<SelectItem>();
			
			MunicipioFBRN municipioRN = new MunicipioFBRN();
			this.municipiosSelect = municipioRN.montaDadosSelect(municipioRN.listar(selecionada.getEstadoId()), "");
			
		}
		return municipiosSelect;
	}
	
	public List<SelectItem> getTelefoneTiposSelect() {
		if (this.telefoneTiposSelect == null) {
			
			this.telefoneTiposSelect = new ArrayList<SelectItem>();
			
			TelefoneTipoFBRN telefoneTipoRN = new TelefoneTipoFBRN();
			this.telefoneTiposSelect = telefoneTipoRN.montaDadosSelect(telefoneTipoRN.listar(), "");
			
		}
		return telefoneTiposSelect;
	}
	
	public List<SelectItem> getEnderecoTiposSelect() {
		if (this.enderecoTiposSelect == null) {
			
			this.enderecoTiposSelect = new ArrayList<SelectItem>();
			
			EnderecoTipoFBRN enderecoTipoRN = new EnderecoTipoFBRN();
			this.enderecoTiposSelect = enderecoTipoRN.montaDadosSelect(enderecoTipoRN.listar(), "");
			
		}
		return enderecoTiposSelect;
	}
	
	
	
	public void clearSession() {
		System.out.println("[ClienteNovoBean][clearSession]");
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	    session.removeAttribute("clienteNovoBean");
	}

	public boolean isClienteAtivo() {
		clienteAtivo = false;
		if(selecionada!=null) {
			if(selecionada.getAtivo()==ClienteFB.CLIENTE_ATIVO) {
				clienteAtivo = true;
			}
		}
		return clienteAtivo;
	}

	public void setClienteAtivo(boolean clienteAtivo) {
		this.clienteAtivo = clienteAtivo;
	}

	public String getFocus() {
		return focus;
	}

	public void setFocus(String focus) {
		this.focus = focus;
	}

}