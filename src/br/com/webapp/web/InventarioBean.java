package br.com.webapp.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.primefaces.component.tabview.Tab;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabCloseEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;

import br.com.coletor.dao.DAOColetorInvContagem;
import br.com.coletor.espelho.EspelhoColetorInvContagem;
import br.com.webapp.model.fb.coletor.ColetorInvFB;
import br.com.webapp.model.fb.coletor.ColetorInvFBRN;
import br.com.webapp.model.fb.coletorcontagem.ColetorInvContagemFB;
import br.com.webapp.model.fb.coletorcontagem.ColetorInvContagemFBDTO;
import br.com.webapp.model.fb.coletorcontagem.ColetorInvContagemFBRN;
import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.empresa.EmpresaFBRN;
import br.com.webapp.model.fb.infogerproduto.InfoGerProdutoFB;
import br.com.webapp.model.fb.infogerproduto.InfoGerProdutoFBRN;
import br.com.webapp.model.fb.produto.ProdutoEstoqueLoteFB;
import br.com.webapp.model.fb.produto.ProdutoEstoqueLoteFBRN;
import br.com.webapp.model.fb.produto.ProdutoFB;
import br.com.webapp.model.fb.produto.ProdutoFBRN;
import br.com.webapp.web.controle.IMenuAcesso;
import br.com.webapp.web.controle.MenuAcessoController;
import br.com.webapp.web.util.ContextoUtil;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.HibernateUtil;
import br.com.webapp.web.util.RNException;
import br.com.webapp.web.util.UtilData;
import br.com.webapp.web.util.UtilMessage;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

@ManagedBean(name="inventarioBean")
@SessionScoped
public class InventarioBean implements Serializable, IMenuAcesso {

	private static final long serialVersionUID = -7991067729718595781L;
	
	private Integer menu = MenuAcessoController.INVENTARIOBEAN_MENU;
	
	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean; 
	
	private Integer selecionadaId;
	private Integer qtdDivergCodBarras;
	private Integer qtdDivergEmbalagem;
	private Integer qtdDivergAssocProd;
	private Integer qtdDivergDescEmb;
	private Integer qtdDivergProduto;
	private Integer contagemId;
	private Integer itemProdId;
	private Integer currentTab;
	private String descEmbFechVenda;
	private String fileTemporaria;
	private ColetorInvFB selecionada;
	private ColetorInvContagemFB contagem;
	private ColetorInvContagemFBDTO leitura;
	private EmpresaFB empresa;
	private ProdutoFB produto;
	private Double totalQtdUn;
	private Double totalQtdEmb;
	private Double totalQtdConv;
	private Double qtdCodBarras;
	
	private List<ColetorInvFB> lista;
	private List<ColetorInvContagemFB> listaColetaInvContagem;
	private List<ColetorInvContagemFBDTO> listaProdutoEmbalagem;
	private List<SelectItem> empresasSelect;
	private List<SelectItem> produtosSelect;
	private List<ProdutoFB> listaProdSemContagem;
	private List<ProdutoFB> prodSelected;
	
	
	// DataTablet
	private List<Integer> contagens;
	
	// TabView
	private boolean tabVisualizarLeituras;
	private boolean tabAssociarCodigoBarras;
	private boolean tabCarregarCusto;
	private boolean tabProdutosSemLeituras;
	
	// Filter
	private Date data1Filter;
	private Date data2Filter;
	private EmpresaFB empresaFilter;
	private ProdutoFB produtoSelFilter;
	private boolean concluidoFilter;
	private boolean exibirCustoFilter;
	
	// Filter Itens
	private String produtoFilter;
	private String produtoFilterSC;
	private String usuarioFilter;
	private String usuarioFilterSC;
	private String agrupadoPorFilter;
	private boolean prodEndGroupFilter;
	private boolean divergenciaFilter;
	private boolean divFinalizarFilter;
	private boolean semContagemFilter;
	private boolean semContFinalizarFilter;
	private boolean comContFinalizarFilter;	
	private boolean groupByProdLocal;
	private boolean groupByIncorporacao;
	private boolean groupByBaixa;
	private boolean buscarProdutosLiberados;
	private boolean buscarTodosProdutos;	
	private boolean podeFinalizar;
	private boolean carregouCusto;
	private boolean disabledBtnAddProdutos;
	private boolean disabledBtnFinalizarSemContagem;
	
	
	// Controle tela
	private boolean editando;
	private boolean openAssocInvUsuario;
	private boolean openGerenciar;
	private boolean openColeta;
	private boolean openResultadoFinal;
	private boolean openVisualizar;
	
	private boolean btnFinalizarColeta;
	private boolean btnNovaColeta;
	private boolean btnDeletes;
	private boolean btnAssocCodigoBarras;
	private boolean btnDeleteColeta;
	private boolean btnAssocUsuaProd;
	private boolean btnDeleteLeituras;
	private boolean btnReservas;
	
	private boolean openCollumnDiferenca;
	private int numColspan;
	
	private String tituloRowExpansion;
	private String tituloAdd = "Cadastrar Novo Invent�rio";	
	
	private String diretorioDestino = "c:\\temp\\";

	private FileUploadEvent file;
	
	@PostConstruct
	public void init(){
		this.novo();
		//this.onStartDatas();
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			addRecentes();
		}
	}

	public void buscar(){
		lista = null;
		
		openAssocInvUsuario = false;
		openGerenciar = false;
		openVisualizar = false;
		editando = false;
		
	}
	
	public void buscarItem(){
		listaProdutoEmbalagem = new ColetorInvContagemFBRN().listarProdutoEmbalagem(selecionada.getId(), produtoFilter, usuarioFilter, divergenciaFilter, agrupadoPorFilter);
		qtdDivergAssocProd = 0;
		for(ColetorInvContagemFBDTO rs:listaProdutoEmbalagem) {
			if(rs.getProdutoId()==null) {
				qtdDivergAssocProd += 1;
			}
		}
		
		qtdDivergCodBarras = 0;
		qtdDivergEmbalagem = 0;
		qtdDivergDescEmb   = 0;
		qtdDivergProduto   = 0;
		
		List<ColetorInvContagemFBDTO> listaDivergencia = new ColetorInvContagemFBRN().verificarDivergencias(selecionada.getId());
		for(ColetorInvContagemFBDTO rs2:listaDivergencia) {
			if(rs2.getQtdDivCodBarra()>1) {
				qtdDivergCodBarras += rs2.getQtdDivCodBarra();
			}			
			
			if(rs2.getQtdDivProduto() != null && rs2.getQtdDivProduto()>1) {
				qtdDivergProduto += rs2.getQtdDivProduto();
			}			

			if(rs2.getQtdDivEmb()>1) {
				qtdDivergEmbalagem += rs2.getQtdDivEmb();
			}			

			if(rs2.getQtdDivDescEmb()>1) {
				qtdDivergEmbalagem += rs2.getQtdDivDescEmb();
			}			

			
		}
		
		podeFinalizar = true;
		if((qtdDivergCodBarras>1 || 
				qtdDivergProduto>1 || 
					qtdDivergEmbalagem>1 || 
							qtdDivergCodBarras>1 || 
								qtdDivergAssocProd>0) && listaProdutoEmbalagem!=null) {
			podeFinalizar = false;
		}
		
		openAssocInvUsuario = false;
		openGerenciar = true;
		btnReservas = false;
		produtoSelFilter = null;
	}
	 
	public void buscarItemSC(){
		listaProdSemContagem = new ProdutoFBRN().listaProdutosEstoqueSemContagem(selecionada.getEmpresaId(), selecionada.getId(), produtoFilterSC); 
		openProdutosSemLeituras();
	}
	
	public void closeFilterProduto(){
		//this.filterCliente = false;
	}
	
	public void addLeituras() {
		
		Session session = HibernateUtil.getSessionFactoryFirebird().getCurrentSession();
		
		DAOColetorInvContagem daoColetorInvContagem = new DAOColetorInvContagem(session);
		
		if(prodSelected.size()>0){
			try {
				
				for(ProdutoFB rs:prodSelected) {
					EspelhoColetorInvContagem leitura = new EspelhoColetorInvContagem();
					String chave = ColetorInvContagemFBRN.gerarChaveContagem(selecionada.getId(), 1, rs.getId(), rs.getProdutoCodBarras());
					leitura.setChave(chave);
					if(rs.getProdutoCodBarras()!=null && !"".equals(rs.getProdutoCodBarras())) {
						leitura.setCodBarra(rs.getProdutoCodBarras());
					}else {
						leitura.setCodBarra("SEM GTIN");
					}
					
					leitura.setColetorInvId(selecionada.getId());
					leitura.setDtLeitura(UtilData.formatarData(new Date(), UtilData.FORMATO_DATA_HORA));
					leitura.setProdutoId(rs.getId());
					leitura.setQtdUn(0d);
					leitura.setQtdEmb(1d);
					leitura.setUsuarioId(1);
					leitura.setFlagZerar(1);
					
					daoColetorInvContagem.inserir(leitura);
					
				}
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.inventario.contagem.add")));
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
				e.printStackTrace();
			} finally {
				prodSelected = new ArrayList<ProdutoFB>();
				listaProdSemContagem = new ProdutoFBRN().listaProdutosEstoqueSemContagem(selecionada.getEmpresaId(), selecionada.getId(), null);
				buscarItem();
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.inventario.contagem.empty.selected")));
		}
		
	}
	
	public List<ProdutoFB> completeProduto(String query) {
		List<ProdutoFB> filteredProduto = new ArrayList<ProdutoFB>();
        if (query != null && !query.equals("")) {
        	filteredProduto = new ProdutoFBRN().listar(query);
		}  
        return filteredProduto;
    }
	
	
	public void excluirTodasLeituras() {
		ColetorInvContagemFBRN coletorInvContagemFBRN = new ColetorInvContagemFBRN();
		try {
			coletorInvContagemFBRN.excluir(selecionadaId, leitura.getProdutoId(), leitura.getProdutoCod(), agrupadoPorFilter);
			gerenciar();
			visualizarLeituras();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.excluir.todas.leituras")));
		} catch (DAOException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
			e.printStackTrace();
		}
		
	}
	
	public void excluirLeitura() {
		ColetorInvContagemFBRN coletorInvContagemFBRN = new ColetorInvContagemFBRN();
		try {
			coletorInvContagemFBRN.excluir(contagemId);
			gerenciar();
			visualizarLeituras();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.excluir.leitura")));
		} catch (DAOException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
			e.printStackTrace();
		}
	}

	public void visualizarLeituras() {
		tabVisualizarLeituras = true;
		tabCarregarCusto = false;
		tabProdutosSemLeituras = false;
		tabAssociarCodigoBarras = false;
		selecionada = new ColetorInvFBRN().editar(selecionadaId);
		listaColetaInvContagem = new ColetorInvContagemFBRN().listar(selecionadaId, leitura, descEmbFechVenda, agrupadoPorFilter);
		totalQtdUn = 0d;
		totalQtdEmb = 0d;
		totalQtdConv = 0d;
		
		for(ColetorInvContagemFB rs:listaColetaInvContagem) {
			if(rs.getQtdUn()!=null) {
				totalQtdUn += rs.getQtdUn();
			}
			
			if(rs.getQtdEmb()!=null) {
				totalQtdEmb += rs.getQtdEmb();
			}
			
			if(rs.getQtdConv()!=null) {
				totalQtdConv += rs.getQtdConv();
			}
			
		}
	}
	
	public void associarCodigoBarras() {
		tabVisualizarLeituras = false;
		tabCarregarCusto = false;
		tabProdutosSemLeituras = false;
		tabAssociarCodigoBarras = true;

		selecionada = new ColetorInvFBRN().editar(selecionadaId);
		produtoSelFilter = null;
		buscarItem();
	}	
	
	public void confirmarAssocProduto() {
		ProdutoFBRN produtoFBRN = new ProdutoFBRN();
		try {
			produtoFBRN.atualizarCodBarras(produtoSelFilter.getId(), leitura.getProdutoCod());
		
			ColetorInvContagemFBRN coletorInvContagemFBRN = new ColetorInvContagemFBRN();
			coletorInvContagemFBRN.atualizarProduto(selecionadaId, produtoSelFilter.getId(), leitura.getProdutoCod());

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.atualizar.prod.codbarra.inventario")));
			
			gerenciar();
			visualizarLeituras();

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", UtilMessage.mensagem("msg.erro.atualizar.prod.codbarra.inventario")));
			e.printStackTrace();
		}
		
		
	}
	
	public void limpar(){
		// onStartDatas();
		empresaFilter = null;
		concluidoFilter = false;
		lista = null;
		
		openAssocInvUsuario = false;
		openGerenciar = false;
		openVisualizar = false;
		
	}
	
	public void limparItem() {
		produtoFilter = null;
		usuarioFilter = null;
		divergenciaFilter = false;
		agrupadoPorFilter = "produto";
		buscarItem();
	}
	
	public void limparItemSC() {
		produtoFilterSC = null;
		usuarioFilterSC = null;
		buscarItemSC();
	}
	
	public void limparVisualizar() {
		tituloRowExpansion = null;
	}

	public void novo(){
		openAssocInvUsuario = false;
		openGerenciar = false;
		editando = false;
		
		this.tituloAdd = "Cadastrar Novo Invent�rio";
		
		selecionadaId = null;
		selecionada = new ColetorInvFBRN().novo(contextoBean.getUsuarioLogado());
		agrupadoPorFilter = "produto";
		divergenciaFilter = false;
		podeFinalizar = false;
		carregouCusto = false;
		exibirCustoFilter = false;
		listaProdSemContagem = new ArrayList<ProdutoFB>();
		prodSelected = new ArrayList<ProdutoFB>();
	}
	
	public void novoInventario(){
		openAssocInvUsuario = false;
		openGerenciar = false;
		
		empresa = null;
		selecionada = new ColetorInvFBRN().novo(contextoBean.getUsuarioLogado());
		selecionadaId = null;
		
		
		this.tituloAdd = "Cadastrar novo invent�rio";
		
	}
	
	public void editar(){
		editando = true;
		openAssocInvUsuario = false;
		openGerenciar = false;
		try {
			selecionada = new ColetorInvFBRN().editar(selecionadaId);
			empresa = new EmpresaFBRN().carregar(selecionada.getEmpresaId());
			tituloAdd = "Editar: "+selecionada.getId()+" - "+selecionada.getDescricao();
		} catch (Exception e) {
			e.printStackTrace();
			
			selecionada = new ColetorInvFB();
			selecionadaId = null;
			empresa = null;
			
			RequestContext.getCurrentInstance().execute("closeEdicaoInventario();");
			
		}
	}

	public void salvar(){
		try {
			
			ColetorInvFBRN coletorInvFBRN = new ColetorInvFBRN();
			selecionada.setEmpresaId(empresa.getId());
			selecionada = coletorInvFBRN.salvarNovo(selecionada, editando);
			selecionadaId = selecionada.getId();
			selecionada = null;
			
			limpar();
			buscar();
			
			if(editando) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.editar.inventario")));
			}else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.salvar.inventario")));
			}	
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
			e.printStackTrace();
		}
		
	}

	
	public void enviarArquivos(FileUploadEvent event) throws BiffException, IOException, DAOException {
		
		InfoGerProdutoFBRN infoGerProdutoFBRN = new InfoGerProdutoFBRN();
		byte[] img = event.getFile().getContents();
		fileTemporaria = new ColetorInvFBRN().encrypt(event.getFile().getFileName()) + event.getFile().getFileName().substring(event.getFile().getFileName().lastIndexOf('.'), event.getFile().getFileName().length());
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
		String arquivo = scontext.getRealPath("/uploads/files/" + fileTemporaria);
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(arquivo);
			fos.write(img);
			fos.close();
			
			impCusto(arquivo);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.custo.inventario")));
			carregouCusto = true;
			buscarItem();
			
		} catch (FileNotFoundException ex) {
			carregouCusto = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ex.getMessage()));
			ex.printStackTrace();
			infoGerProdutoFBRN.rollBack();
		} catch (IOException ex) {
			carregouCusto = false;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ex.getMessage()));
			ex.printStackTrace();
			infoGerProdutoFBRN.rollBack();
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ex.getMessage()));
			ex.printStackTrace();
			infoGerProdutoFBRN.rollBack();
		}
		
		
	}	
	
	
	public void openCusto() {
		tabCarregarCusto = true;
		tabProdutosSemLeituras = false;
		tabAssociarCodigoBarras = false;
		tabVisualizarLeituras = false;
	}
	
	public void openProdutosSemLeituras() {
		tabCarregarCusto = false;
		tabProdutosSemLeituras = true;
		tabAssociarCodigoBarras = false;
		tabVisualizarLeituras = false;
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("focus('tabProdutosSemLeituras');");
		
		
	}
	
	
	public void impCusto(String arquivo) throws BiffException, IOException, DAOException, RNException {
		
				InfoGerProdutoFBRN infoGerProdutoFBRN = new InfoGerProdutoFBRN();
				
				Workbook workbook = Workbook.getWorkbook(new File(arquivo));
				
				Sheet sheet = workbook.getSheet(0);
	
				int linhas = sheet.getRows();
				int colunas = sheet.getColumns();
	
				System.out.println("Numero de linhas: " + linhas);
				System.out.println("Numero de colunas: " + colunas);
	
				for (int i = 1; i < linhas;i++) {
	
					try {

						Cell a1 = sheet.getCell(0, i);
						Cell b2 = sheet.getCell(1, i);
	
						if(a1.getContents()!=null && b2.getContents()!=null) {
							InfoGerProdutoFB infoGerProdutoFB = infoGerProdutoFBRN.carregar(Integer.parseInt(a1.getContents()));
							infoGerProdutoFB.setCustoMedio(Double.parseDouble(b2.getContents().replace(".", "").replace(",", ".")));
							infoGerProdutoFB.setCustoMedioOnLine(Double.parseDouble(b2.getContents().replace(".", "").replace(",", ".")));
							infoGerProdutoFB.setCustoGerAtual(Double.parseDouble(b2.getContents().replace(".", "").replace(",", ".")));
							
							infoGerProdutoFBRN.update(infoGerProdutoFB);
							
							//System.out.println("Codigo "+a1.getContents()+": "+b2.getContents());
							
						}
					
					}catch (Exception e) {
						throw new RNException(String.format(UtilMessage.mensagem("msg.erro.importar.custo"), String.valueOf(i+1))); 
					}	
	
				
				}
		
	}
	
	public void finalizar(){
		try {		
			listaProdSemContagem = new ProdutoFBRN().listaProdutosEstoqueSemContagem(selecionada.getEmpresaId(), selecionada.getId(), null); 
			if(listaProdSemContagem !=null && listaProdSemContagem.size()>0){
				openProdutosSemLeituras();
				throw new RNException(UtilMessage.mensagem("msg.erro.produtos.comestoque.semcontagem"));
			}
			
			
			ColetorInvFBRN coletorInvFBRN = new ColetorInvFBRN();
			coletorInvFBRN.finalizar(selecionada);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.finalizado.inventario")));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", e.getMessage()));
			e.printStackTrace();
		}
	}	
	
	
	public void gerenciar(){
		
		selecionada = new ColetorInvFBRN().editar(selecionadaId);
		empresa = new EmpresaFBRN().carregar(selecionada.getEmpresaId());

		buscarItem();
		openAssocInvUsuario = false;
		openVisualizar = false;
		openGerenciar = true;
		btnReservas = false;
		produtoSelFilter = null;
		tabProdutosSemLeituras = false;
		
	}
	
	public void visualizar() {
		selecionada = new ColetorInvFBRN().editar(selecionadaId);
		empresa = new EmpresaFBRN().carregar(selecionada.getEmpresaId());

		buscarItem();
		openAssocInvUsuario = false;
		openVisualizar = true;
		openGerenciar = true;
		produtoSelFilter = null;
		
	}	
	
	public String converterDateToString(Date data) {
		String dataFormatada = null;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		dataFormatada = df.format(data);
		System.out.println(dataFormatada);
		return dataFormatada;
	}
	
	public void onStartDatas(){
		
		if(data1Filter==null || data2Filter==null && concluidoFilter){
			Calendar caIni = Calendar.getInstance();
			caIni.set(Calendar.DATE, 1);
			caIni.set(Calendar.HOUR, 0);
			caIni.set(Calendar.MINUTE, 0);
			caIni.set(Calendar.SECOND, 0);
			caIni.set(Calendar.AM_PM, Calendar.AM);
			
			this.data1Filter = caIni.getTime();
			
			Calendar caFim = Calendar.getInstance();
			caFim.set(Calendar.HOUR, 11);
			caFim.set(Calendar.MINUTE, 59);
			caFim.set(Calendar.SECOND, 59);
			caFim.set(Calendar.AM_PM, Calendar.PM);
			
			this.data2Filter = caFim.getTime();
		}else{
			this.data1Filter = null;
			this.data2Filter = null;
		}
	
	}
	
	public void excluir(){
		try {
			
			ColetorInvFBRN coletorInvFBRN = new ColetorInvFBRN();
			coletorInvFBRN.excluir(selecionada.getId());
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", UtilMessage.mensagem("msg.excluir.inventario")));
			
			selecionada = null;
			selecionadaId = null;
			empresa = null;
			buscar();

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
			e.printStackTrace();
		}
	}
	
	public void onTabClose(TabCloseEvent event) {
		if(event.getTab().getId().equals("tabLeiturasInvItemMov")) {
			tabVisualizarLeituras = false;
		}
		if(event.getTab().getId().equals("tabAssocItemMov")) {
			tabAssociarCodigoBarras = false;
		}
		if(event.getTab().getId().equals("tabCarregarCusto")) {
			tabCarregarCusto = false;
		}
		if(event.getTab().getId().equals("tabProdutosSemLeituras")) {
			tabProdutosSemLeituras = false;
		}
		

	}
	
	
	public void onRowFilterSelect(SelectEvent event){
	   System.out.println("[onRowFilterSelect] total Selected: " + prodSelected.size());
	}

	public void unRowFilterSelect(UnselectEvent event){
		System.out.println("[unRowFilterSelect] total Selected: " + prodSelected.size());
	}

	public void onRowFilterSelectAll(ToggleSelectEvent event){
		System.out.println("[onRowFilterSelectAll] total Selected: " + prodSelected.size());
	}
	
	


	
	// gets and sets
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
	
	public Integer getQtdDivergCodBarras() {
		return qtdDivergCodBarras;
	}

	public void setQtdDivergCodBarras(Integer qtdDivergCodBarras) {
		this.qtdDivergCodBarras = qtdDivergCodBarras;
	}

	public Integer getQtdDivergEmbalagem() {
		return qtdDivergEmbalagem;
	}

	public void setQtdDivergEmbalagem(Integer qtdDivergEmbalagem) {
		this.qtdDivergEmbalagem = qtdDivergEmbalagem;
	}

	public Integer getQtdDivergAssocProd() {
		return qtdDivergAssocProd;
	}

	public void setQtdDivergAssocProd(Integer qtdDivergAssocProd) {
		this.qtdDivergAssocProd = qtdDivergAssocProd;
	}

	public Integer getQtdDivergDescEmb() {
		return qtdDivergDescEmb;
	}

	public void setQtdDivergDescEmb(Integer qtdDivergDescEmb) {
		this.qtdDivergDescEmb = qtdDivergDescEmb;
	}

	public Integer getQtdDivergProduto() {
		return qtdDivergProduto;
	}

	public void setQtdDivergProduto(Integer qtdDivergProduto) {
		this.qtdDivergProduto = qtdDivergProduto;
	}

	public ColetorInvContagemFB getContagem() {
		return contagem;
	}

	public void setContagem(ColetorInvContagemFB contagem) {
		this.contagem = contagem;
	}

	public ColetorInvContagemFBDTO getLeitura() {
		return leitura;
	}

	public void setLeitura(ColetorInvContagemFBDTO leitura) {
		this.leitura = leitura;
	}

	public Integer getContagemId() {
		return contagemId;
	}

	public void setContagemId(Integer contagemId) {
		this.contagemId = contagemId;
	}

	public Integer getItemProdId() {
		return itemProdId;
	}

	public void setItemProdId(Integer itemProdId) {
		this.itemProdId = itemProdId;
	}

	public Integer getCurrentTab() {
		return currentTab;
	}

	public void setCurrentTab(Integer currentTab) {
		this.currentTab = currentTab;
	}

	public String getDescEmbFechVenda() {
		return descEmbFechVenda;
	}

	public void setDescEmbFechVenda(String descEmbFechVenda) {
		this.descEmbFechVenda = descEmbFechVenda;
	}

	public String getFileTemporaria() {
		return fileTemporaria;
	}

	public void setFileTemporaria(String fileTemporaria) {
		this.fileTemporaria = fileTemporaria;
	}

	public EmpresaFB getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaFB empresa) {
		this.empresa = empresa;
	}

	public ProdutoFB getProduto() {
		return produto;
	}

	public void setProduto(ProdutoFB produto) {
		this.produto = produto;
	}

	public Double getTotalQtdUn() {
		return totalQtdUn;
	}

	public void setTotalQtdUn(Double totalQtdUn) {
		this.totalQtdUn = totalQtdUn;
	}

	public Double getTotalQtdEmb() {
		return totalQtdEmb;
	}

	public void setTotalQtdEmb(Double totalQtdEmb) {
		this.totalQtdEmb = totalQtdEmb;
	}

	public Double getTotalQtdConv() {
		return totalQtdConv;
	}

	public void setTotalQtdConv(Double totalQtdConv) {
		this.totalQtdConv = totalQtdConv;
	}

	public Double getQtdCodBarras() {
		return qtdCodBarras;
	}

	public void setQtdCodBarras(Double qtdCodBarras) {
		this.qtdCodBarras = qtdCodBarras;
	}

	public Integer getMenu() {
		return menu;
	}

	public void setMenu(Integer menu) {
		this.menu = menu;
	}

	public ColetorInvFB getSelecionada() {
		return selecionada;
	}

	public void setSelecionada(ColetorInvFB selecionada) {
		this.selecionada = selecionada;
	}

	public List<ColetorInvFB> getLista() {
		if(lista==null){
			this.lista = new ColetorInvFBRN().listar(empresaFilter, data1Filter, data2Filter, concluidoFilter);
		}
		return lista;
	}

	public void setLista(List<ColetorInvFB> lista) {
		this.lista = lista;
	}

	public List<ColetorInvContagemFB> getListaColetaInvContagem() {
		return listaColetaInvContagem;
	}

	public void setListaColetaInvContagem(List<ColetorInvContagemFB> listaColetaInvContagem) {
		this.listaColetaInvContagem = listaColetaInvContagem;
	}

	public List<ColetorInvContagemFBDTO> getListaProdutoEmbalagem() {
		return listaProdutoEmbalagem;
	}

	public void setListaProdutoEmbalagem(List<ColetorInvContagemFBDTO> listaProdutoEmbalagem) {
		this.listaProdutoEmbalagem = listaProdutoEmbalagem;
	}

	public List<SelectItem> getEmpresasSelect() {
		if(empresasSelect==null) {
			EmpresaFBRN empresaFBRN = new EmpresaFBRN();
			empresasSelect = empresaFBRN.montaDadosSelect(empresaFBRN.listar(contextoBean.getUsuarioLogado()), "");
		}

		return empresasSelect;
	}

	public void setEmpresasSelect(List<SelectItem> empresasSelect) {
		this.empresasSelect = empresasSelect;
	}

	public List<SelectItem> getProdutosSelect() {
		if(produtosSelect==null) {
			ProdutoFBRN produtoFBRN = new ProdutoFBRN();
			produtosSelect = produtoFBRN.montaDadosSelect(produtoFBRN.listar(null), "");
		}

		return produtosSelect;
	}

	public void setProdutosSelect(List<SelectItem> produtosSelect) {
		this.produtosSelect = produtosSelect;
	}

	public List<ProdutoFB> getListaProdSemContagem() {
		return listaProdSemContagem;
	}

	public void setListaProdSemContagem(List<ProdutoFB> listaProdSemContagem) {
		this.listaProdSemContagem = listaProdSemContagem;
	}

	public List<ProdutoFB> getProdSelected() {
		return prodSelected;
	}

	public void setProdSelected(List<ProdutoFB> prodSelected) {
		this.prodSelected = prodSelected;
	}

	public List<Integer> getContagens() {
		return contagens;
	}

	public void setContagens(List<Integer> contagens) {
		this.contagens = contagens;
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
	
	public boolean isTabCarregarCusto() {
		return tabCarregarCusto;
	}

	public void setTabCarregarCusto(boolean tabCarregarCusto) {
		this.tabCarregarCusto = tabCarregarCusto;
	}

	public boolean isTabProdutosSemLeituras() {
		return tabProdutosSemLeituras;
	}

	public void setTabProdutosSemLeituras(boolean tabProdutosSemLeituras) {
		this.tabProdutosSemLeituras = tabProdutosSemLeituras;
	}

	public Date getData1Filter() {
		return data1Filter;
	}

	public void setData1Filter(Date data1Filter) {
		this.data1Filter = data1Filter;
	}

	public Date getData2Filter() {
		return data2Filter;
	}

	public void setData2Filter(Date data2Filter) {
		this.data2Filter = data2Filter;
	}

	public EmpresaFB getEmpresaFilter() {
		return empresaFilter;
	}

	public void setEmpresaFilter(EmpresaFB empresaFilter) {
		this.empresaFilter = empresaFilter;
	}

	public ProdutoFB getProdutoSelFilter() {
		return produtoSelFilter;
	}

	public void setProdutoSelFilter(ProdutoFB produtoSelFilter) {
		this.produtoSelFilter = produtoSelFilter;
	}

	public boolean isConcluidoFilter() {
		return concluidoFilter;
	}

	public void setConcluidoFilter(boolean concluidoFilter) {
		this.concluidoFilter = concluidoFilter;
	}

	public boolean isExibirCustoFilter() {
		return exibirCustoFilter;
	}

	public void setExibirCustoFilter(boolean exibirCustoFilter) {
		this.exibirCustoFilter = exibirCustoFilter;
	}

	public String getProdutoFilter() {
		return produtoFilter;
	}

	public void setProdutoFilter(String produtoFilter) {
		this.produtoFilter = produtoFilter;
	}

	public String getProdutoFilterSC() {
		return produtoFilterSC;
	}

	public void setProdutoFilterSC(String produtoFilterSC) {
		this.produtoFilterSC = produtoFilterSC;
	}

	public String getUsuarioFilterSC() {
		return usuarioFilterSC;
	}

	public void setUsuarioFilterSC(String usuarioFilterSC) {
		this.usuarioFilterSC = usuarioFilterSC;
	}

	public String getUsuarioFilter() {
		return usuarioFilter;
	}

	public void setUsuarioFilter(String usuarioFilter) {
		this.usuarioFilter = usuarioFilter;
	}

	public String getAgrupadoPorFilter() {
		return agrupadoPorFilter;
	}

	public void setAgrupadoPorFilter(String agrupadoPorFilter) {
		this.agrupadoPorFilter = agrupadoPorFilter;
	}

	public boolean isProdEndGroupFilter() {
		return prodEndGroupFilter;
	}

	public void setProdEndGroupFilter(boolean prodEndGroupFilter) {
		this.prodEndGroupFilter = prodEndGroupFilter;
	}

	public boolean isDivergenciaFilter() {
		return divergenciaFilter;
	}

	public void setDivergenciaFilter(boolean divergenciaFilter) {
		this.divergenciaFilter = divergenciaFilter;
	}

	public boolean isDivFinalizarFilter() {
		return divFinalizarFilter;
	}

	public void setDivFinalizarFilter(boolean divFinalizarFilter) {
		this.divFinalizarFilter = divFinalizarFilter;
	}

	public boolean isSemContagemFilter() {
		return semContagemFilter;
	}

	public void setSemContagemFilter(boolean semContagemFilter) {
		this.semContagemFilter = semContagemFilter;
	}

	public boolean isSemContFinalizarFilter() {
		return semContFinalizarFilter;
	}

	public void setSemContFinalizarFilter(boolean semContFinalizarFilter) {
		this.semContFinalizarFilter = semContFinalizarFilter;
	}

	public boolean isComContFinalizarFilter() {
		return comContFinalizarFilter;
	}

	public void setComContFinalizarFilter(boolean comContFinalizarFilter) {
		this.comContFinalizarFilter = comContFinalizarFilter;
	}

	public boolean isGroupByProdLocal() {
		return groupByProdLocal;
	}

	public void setGroupByProdLocal(boolean groupByProdLocal) {
		this.groupByProdLocal = groupByProdLocal;
	}

	public boolean isGroupByIncorporacao() {
		return groupByIncorporacao;
	}

	public void setGroupByIncorporacao(boolean groupByIncorporacao) {
		this.groupByIncorporacao = groupByIncorporacao;
	}

	public boolean isGroupByBaixa() {
		return groupByBaixa;
	}

	public void setGroupByBaixa(boolean groupByBaixa) {
		this.groupByBaixa = groupByBaixa;
	}

	public boolean isBuscarProdutosLiberados() {
		return buscarProdutosLiberados;
	}

	public void setBuscarProdutosLiberados(boolean buscarProdutosLiberados) {
		this.buscarProdutosLiberados = buscarProdutosLiberados;
	}

	public boolean isBuscarTodosProdutos() {
		return buscarTodosProdutos;
	}

	public void setBuscarTodosProdutos(boolean buscarTodosProdutos) {
		this.buscarTodosProdutos = buscarTodosProdutos;
	}

	public boolean isPodeFinalizar() {
		return podeFinalizar;
	}

	public void setPodeFinalizar(boolean podeFinalizar) {
		this.podeFinalizar = podeFinalizar;
	}

	public boolean isDisabledBtnAddProdutos() {
		return disabledBtnAddProdutos;
	}

	public void setDisabledBtnAddProdutos(boolean disabledBtnAddProdutos) {
		this.disabledBtnAddProdutos = disabledBtnAddProdutos;
	}

	public boolean isDisabledBtnFinalizarSemContagem() {
		return disabledBtnFinalizarSemContagem;
	}

	public void setDisabledBtnFinalizarSemContagem(boolean disabledBtnFinalizarSemContagem) {
		this.disabledBtnFinalizarSemContagem = disabledBtnFinalizarSemContagem;
	}

	public boolean isOpenAssocInvUsuario() {
		return openAssocInvUsuario;
	}

	public void setOpenAssocInvUsuario(boolean openAssocInvUsuario) {
		this.openAssocInvUsuario = openAssocInvUsuario;
	}

	public boolean isOpenGerenciar() {
		return openGerenciar;
	}

	public void setOpenGerenciar(boolean openGerenciar) {
		this.openGerenciar = openGerenciar;
	}

	public boolean isOpenColeta() {
		return openColeta;
	}

	public void setOpenColeta(boolean openColeta) {
		this.openColeta = openColeta;
	}

	public boolean isOpenResultadoFinal() {
		return openResultadoFinal;
	}

	public void setOpenResultadoFinal(boolean openResultadoFinal) {
		this.openResultadoFinal = openResultadoFinal;
	}

	public boolean isOpenVisualizar() {
		return openVisualizar;
	}

	public void setOpenVisualizar(boolean openVisualizar) {
		this.openVisualizar = openVisualizar;
	}

	public boolean isBtnFinalizarColeta() {
		return btnFinalizarColeta;
	}

	public void setBtnFinalizarColeta(boolean btnFinalizarColeta) {
		this.btnFinalizarColeta = btnFinalizarColeta;
	}

	public boolean isBtnNovaColeta() {
		return btnNovaColeta;
	}

	public void setBtnNovaColeta(boolean btnNovaColeta) {
		this.btnNovaColeta = btnNovaColeta;
	}

	public boolean isBtnDeletes() {
		return btnDeletes;
	}

	public void setBtnDeletes(boolean btnDeletes) {
		this.btnDeletes = btnDeletes;
	}

	public boolean isBtnAssocCodigoBarras() {
		return btnAssocCodigoBarras;
	}

	public void setBtnAssocCodigoBarras(boolean btnAssocCodigoBarras) {
		this.btnAssocCodigoBarras = btnAssocCodigoBarras;
	}

	public boolean isBtnDeleteColeta() {
		return btnDeleteColeta;
	}

	public void setBtnDeleteColeta(boolean btnDeleteColeta) {
		this.btnDeleteColeta = btnDeleteColeta;
	}

	public boolean isBtnAssocUsuaProd() {
		return btnAssocUsuaProd;
	}

	public void setBtnAssocUsuaProd(boolean btnAssocUsuaProd) {
		this.btnAssocUsuaProd = btnAssocUsuaProd;
	}

	public boolean isBtnDeleteLeituras() {
		return btnDeleteLeituras;
	}

	public void setBtnDeleteLeituras(boolean btnDeleteLeituras) {
		this.btnDeleteLeituras = btnDeleteLeituras;
	}

	public boolean isBtnReservas() {
		return btnReservas;
	}

	public void setBtnReservas(boolean btnReservas) {
		this.btnReservas = btnReservas;
	}

	public boolean isOpenCollumnDiferenca() {
		return openCollumnDiferenca;
	}

	public void setOpenCollumnDiferenca(boolean openCollumnDiferenca) {
		this.openCollumnDiferenca = openCollumnDiferenca;
	}

	public int getNumColspan() {
		return numColspan;
	}

	public void setNumColspan(int numColspan) {
		this.numColspan = numColspan;
	}

	public String getTituloRowExpansion() {
		return tituloRowExpansion;
	}

	public void setTituloRowExpansion(String tituloRowExpansion) {
		this.tituloRowExpansion = tituloRowExpansion;
	}

	public String getTituloAdd() {
		return tituloAdd;
	}

	public void setTituloAdd(String tituloAdd) {
		this.tituloAdd = tituloAdd;
	}
	
	public FileUploadEvent getFile() {
		return file;
	}

	public void setFile(FileUploadEvent file) {
		this.file = file;
	}

	// Implementation Menu
	@Override
	public void addHome() {
		MenuAcessoController.addHome(menu, ContextoUtil.getContextoBean().getUsuarioLogado(), FacesContext.getCurrentInstance());		
	}

	@Override
	public void addRecentes() {
		MenuAcessoController.addRecentes(menu, ContextoUtil.getContextoBean().getUsuarioLogado());
	}

	@Override
	public void addFavoritos() {
		MenuAcessoController.addFavoritos(menu, ContextoUtil.getContextoBean().getUsuarioLogado(), FacesContext.getCurrentInstance());
	}

	@Override
	public void clearSession() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if(request.getParameter("id")!=null){
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		    session.removeAttribute("inventarioBean");
		}
	}


}
