package br.com.webapp.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.fornecedor.FornecedorFB;
import br.com.webapp.model.fb.produtocb.ProdutoCBFB;
import br.com.webapp.model.fb.produtocb.ProdutoCBFBRN;
import br.com.webapp.model.fb.romaneio.RomaneioContagemFB;
import br.com.webapp.model.fb.romaneio.RomaneioContagemFBRN;
import br.com.webapp.model.fb.romaneio.RomaneioFB;
import br.com.webapp.model.fb.romaneio.RomaneioFBRN;
import br.com.webapp.model.fb.romaneio.RomaneioItemDTOFB;
import br.com.webapp.model.fb.romaneio.RomaneioItemFB;
import br.com.webapp.model.fb.romaneio.RomaneioItemFBRN;
import br.com.webapp.model.fb.romaneio.RomaneioItemPedidoFBRN;
import br.com.webapp.web.util.DAOException;
import br.com.webapp.web.util.UtilMessage;

@ManagedBean(name="romaneioGerenciarBean")
@SessionScoped
public class RomaneioGerenciarBean implements Serializable {

	private static final long serialVersionUID = -3590909713841298628L;

	@ManagedProperty(value="#{contextoBean}")
	private ContextoBean contextoBean;
	
	private String integracao;
	
	private boolean tabVisualizarLeituras;
	private boolean tabAssociarCodigoBarras;
	
	private boolean disableExcluirLeituras;
	private boolean renderedBtnFinalizar;
	private boolean renderedBtnAjuste;
	private boolean renderedBtnCancelar;
	private boolean renderedTabAjusteProduto;
	private int currentTab;
	
	private Integer selecionadaId;
	private RomaneioFB selecionada;
	private RomaneioItemFB itemSelecionado;
	private RomaneioItemDTOFB itemAjusteSelecionado;
	private EmpresaFB empresa;
	private FornecedorFB fornecedor;
	private RomaneioItemFB romaneioItemFB;
	private List<RomaneioItemDTOFB> listRomaneioItemDTOFB;
	private RomaneioContagemFB romaneioContagemFB;
	//private CodigoBarraDTO codigoBarraDTO;
	private ProdutoCBFB produtoCB;
	private Integer produtoSelecionadoId;
	private String itemUnidade;
	private String itemProduto;
	private Double itemQtdRomaneio;
	private Double itemQtdConferido;
	private double itemQtdAjuste;
	private double itemQtdRetirada;
	private double novoAjuste;
	private double totalQtdPedido;
	private double saldoAAjustar;
	private double qtdAjustada;
	private double qtdAjuste;
	private Integer qtdDecimal;
	
	private Boolean controlalote; // assoc produto x codbarras
	private Date dtVencLot; // assoc produto x codbarras
	private String codigolote; // assoc produto x codbarras
	
	private List<RomaneioItemFB> itens;
	private List<RomaneioContagemFB> itensMov;
	//private List<CodigoBarraDTO> listaCodBarras;
	
	//private List<RomaneioDivergFB> listaDivergencia;
	private boolean btnTransmitir;
	
	private Double totalQtdRomaneio;
	private Double totalQtdConferida;
	private Double totalQtdAjuste;
	private Double totalQtdRetirada;
	
	
	
	@PostConstruct
	public void init(){ 
		currentTab = 0;
		renderedTabAjusteProduto = false;
		//this.integracao = Funcoes.INTEGRACAO;
	}
	
	public void gerenciar(){
		tabVisualizarLeituras = false;
		tabAssociarCodigoBarras = false;
		
		//planilhaCegaItemMovDTO = null;
		//codigoBarraDTO = null;
		//listaCodBarras = null;
		selecionada = new RomaneioFBRN().carregar(selecionadaId);
		
		itens = null;
		
		listRomaneioItemDTOFB = new ArrayList<RomaneioItemDTOFB>();
		renderedTabAjusteProduto = false;
		
	}
	
	
	public void atualizar(){
		selecionada = null;
		gerenciar();
		RequestContext.getCurrentInstance().execute("PF('tabViewRomaneioItens').select(0);");
	}
	
	public void cancelar() {
		RomaneioFBRN romaneioFBRN = new RomaneioFBRN();
		try {
			romaneioFBRN.cancelar(selecionada, contextoBean.getUsuarioLogado());
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Romaneio Cancelado com Sucesso!"));
		} catch (DAOException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
			e.printStackTrace();
		}
	}
	
	public void ajustar(){
		
		try {

			RomaneioFBRN romaneioFBRN = new RomaneioFBRN();
			selecionada.setStatus(RomaneioFB.STATUS_FINALIZADO);
			selecionada.setMomentoFinalizado(new Date());
			selecionada.setUsuarioFinalizado(contextoBean.getUsuarioLogado().getId().toString());
			romaneioFBRN.salvar(selecionada, contextoBean.getUsuarioLogado());
			
			romaneioFBRN.integracaoRomaneio(selecionada.getRomaneioId());
			
			gerenciar();
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Romaneio conferido com sucesso!"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		}
		
		RequestContext.getCurrentInstance().execute("PF('tabViewRomaneioItens').select(0);");
		
	}
	
	public void changeAjuste(Double qtdAjuste) {
//		itemQtdRetirada = qtdAjuste;
//		saldoAAjustar = (itemQtdRomaneio-(itemQtdConferido+itemQtdRetirada));
		//qtdAjustada += qtdAjuste;
		System.out.println(" ");
	}
	
	
	public void openItensAAjustar() {
		renderedTabAjusteProduto = true;
		
		RomaneioItemFBRN romaneioItemFBRN = new RomaneioItemFBRN();
		listRomaneioItemDTOFB = romaneioItemFBRN.listarParaAjuste(itemSelecionado);
		
		novoAjuste = 0d;
		qtdAjustada = 0d;
		totalQtdPedido = 0.0;
		Double totalQtdConferido = 0.0;
		Double totalQtdRetirado = 0.0;
		
		
		for(RomaneioItemDTOFB rs : listRomaneioItemDTOFB){
			totalQtdPedido += rs.getQtdPedido();
			totalQtdConferido += rs.getQtdConferida();
			totalQtdRetirado += rs.getQtdRetirada();
			itemUnidade = rs.getUnidadeDesc();
			itemQtdRomaneio = rs.getQtdRomaneio();
			itemQtdConferido = rs.getQtdConferida();
			itemQtdAjuste = rs.getQtdAjuste();
			itemQtdRetirada = rs.getQtdRetirada();
			itemProduto = rs.getProdutoCod()+" - "+rs.getProdutoDesc();
			novoAjuste = rs.getQtdRetirada();
			qtdAjustada += rs.getQtdRetirada();
			qtdDecimal = rs.getQtdDecimal();
		}

		saldoAAjustar = itemQtdRomaneio-(itemQtdConferido+totalQtdRetirado);
		
		RequestContext.getCurrentInstance().execute("PF('tabViewRomaneioItens').select(1);");
	}
	
	public void editarCorte(RomaneioItemDTOFB itemAjuste) {
		if(itemAjuste!=null) {
			BigDecimal totalAjuste = BigDecimal.ZERO;
			for(RomaneioItemDTOFB rs : listRomaneioItemDTOFB){
				if(!(itemAjuste.getPedVendaId()==rs.getPedVendaId() && itemAjuste.getProdutoId()==rs.getProdutoId())){
					BigDecimal qtdRetiradaRS = rs.getQtdRetirada() != null ? BigDecimal.valueOf(rs.getQtdRetirada()) : BigDecimal.ZERO;
					totalAjuste =totalAjuste.add(qtdRetiradaRS);
				}
			}
			
			// Converte o valor atual da retirada para BigDecimal
	        BigDecimal qtdRetiradaAjuste = itemAjuste.getQtdRetirada() != null 
	                ? BigDecimal.valueOf(itemAjuste.getQtdRetirada()) 
	                : BigDecimal.ZERO;
	        
	        // Converte os limites do Bean/Escopo para BigDecimal e calcula a diferença exata
	        BigDecimal bQtdRomaneio = itemQtdRomaneio != null ? BigDecimal.valueOf(itemQtdRomaneio) : BigDecimal.ZERO;
	        BigDecimal bQtdConferido = itemQtdConferido != null ? BigDecimal.valueOf(itemQtdConferido) : BigDecimal.ZERO;
	        
	        // 10.0 exato (36.3 - 26.3)
	        BigDecimal limiteSaldo = bQtdRomaneio.subtract(bQtdConferido); 
	        
	        // Total que o usuário está tentando aplicar
	        BigDecimal totalComRetirada = totalAjuste.add(qtdRetiradaAjuste);
	        
	        // Comparação segura entre os BigDecimal
	        if (totalComRetirada.compareTo(limiteSaldo) > 0) {
	            // Zera mantendo o tipo Double exigido pelo DTO
	            itemAjuste.setQtdRetirada(0d);
	            
	            FacesContext.getCurrentInstance().addMessage(null, 
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, null, UtilMessage.mensagem("msg.romaneio.qtd.maior.saldo")));
	        } else {
	            RomaneioItemPedidoFBRN romaneioItemPedidoFBRN = new RomaneioItemPedidoFBRN(); 
	            // Passa o Double normalmente como o método original espera
	            romaneioItemPedidoFBRN.updateQuantidade(itemAjuste.getRomaneioItemPedidoId(), itemAjuste.getQtdRetirada());
	        
	            FacesContext.getCurrentInstance().addMessage(null, 
	                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Quantidade ajustada!"));

	            openItensAAjustar();
	        }   
	    }
	    
	    isRenderedBtnFinalizar();

	}
	
	public void excluirLeitura(){
		try {
			RomaneioContagemFBRN romaneioContagemFBRN = new RomaneioContagemFBRN();
			romaneioContagemFBRN.excluirLeitura(romaneioContagemFB);
			
			RomaneioItemFBRN romaneioItemFBRN = new RomaneioItemFBRN();
			ProdutoCBFBRN produtoCBFBRN = new ProdutoCBFBRN();
			ProdutoCBFB produtoCBFB = produtoCBFBRN.carregar(romaneioContagemFB.getProdutoId(), romaneioContagemFB.getCodBarra());
			
			Double qtdConferida = romaneioContagemFB.getQtd();
			if(produtoCBFB!=null) {
				qtdConferida = produtoCBFB.getQtd()*romaneioContagemFB.getQtd();
			}
			
			romaneioItemFBRN.atualizarQtdConferida(romaneioContagemFB.getRomaneioId(), romaneioContagemFB.getProdutoId(), qtdConferida);
			
			RomaneioFBRN romaneioFBRN = new RomaneioFBRN();
			romaneioFBRN.atualizarStatus(romaneioContagemFB.getRomaneioId(), RomaneioFB.STATUS_EM_CONFERENCIA);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", UtilMessage.mensagem("msg.excluido.leituras")));
			
			romaneioContagemFB = null;
			itensMov=null;
			itens = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
			e.printStackTrace();
			
			
		}
	}

	public void excluirTodasLeituras(){
		try {
			RomaneioContagemFBRN romaneioContagemFBRN = new RomaneioContagemFBRN();
			romaneioContagemFBRN.excluirTodasLeituras(romaneioItemFB.getRomaneioId(), romaneioItemFB.getProdutoId());
			
			RomaneioItemFBRN romaneioItemFBRN = new RomaneioItemFBRN();
			romaneioItemFBRN.atualizarQtdConferida(romaneioItemFB.getRomaneioId(), romaneioItemFB.getProdutoId(), romaneioItemFB.getQtdConferida());
			
			RomaneioFBRN romaneioFBRN = new RomaneioFBRN();
			romaneioFBRN.atualizarStatus(romaneioItemFB.getRomaneioId(), RomaneioFB.STATUS_EM_CONFERENCIA);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", UtilMessage.mensagem("msg.romaneiocontagem.excluido.leituras")));
			
			romaneioItemFB = null;
			itensMov=null;
			itens = null;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
			e.printStackTrace();
		}
	}

	
	public void transmitir() {
		try {
			RomaneioFBRN romaneioFBRN = new RomaneioFBRN();
			romaneioFBRN.finalizar(selecionada, contextoBean.getUsuarioLogado());
			
			gerenciar();
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Planilha Cega transmitida!"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
		}
		
		RequestContext.getCurrentInstance().execute("PF('tabViewRomaneioItens').select(0);");
	}
	
	
	
	public void visualizarLeituras(){
		itensMov = null;
		tabVisualizarLeituras = true;
		tabAssociarCodigoBarras = false;
	}
	
	public void associarCodigoBarras(){
		produtoCB = null;
		tabVisualizarLeituras = false;
		tabAssociarCodigoBarras = true;
		//codigoBarraDTO = null;
		//listaCodBarras = null;
		
		codigolote = null;
		dtVencLot = null;
		controlalote = false;
	}
	
	public void confirmarAssocProduto(){
		
		try {
			ProdutoCBFBRN produtoCBRN = new ProdutoCBFBRN();
			//produtoCBRN.associarInRomaneioFB(selecionada, produtoCB, codigolote, dtVencLot);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Código de Barras Associado!"));
			
			tabAssociarCodigoBarras = false;
			itens = null;
			//listaCodBarras = null;
			
			codigolote = null;
			dtVencLot = null;
			controlalote = false;
			
			RequestContext.getCurrentInstance().execute("PF('tabViewRomaneioItens').select(0);");
			RequestContext.getCurrentInstance().execute("PF('tabViewRomaneioItens').remove(1);");
			
		} catch (Exception e) {
			
			selectCodigoBarras();
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", e.getMessage()));
			e.printStackTrace();
		}
		
	}
	
	public void onTabCloseAssocCodBarras(TabCloseEvent event) {
		tabAssociarCodigoBarras = false;
		tabVisualizarLeituras = false;
	}
	
	public void onTabChange(TabChangeEvent event) {
		try {
			currentTab = Integer.parseInt(event.getTab().getAttributes().get("activeIndex").toString());
		} catch (Exception e) {
			//e.printStackTrace();
		}
    }
         
    public void onTabClose(TabCloseEvent event) {
    	try {
	        if(event.getTab().getId().equals("tabDtVendaProdBean") || event.getTab().getId().equals("tabDtVendaFPagtoBean")) {
	        	currentTab = 0;
	        	renderedTabAjusteProduto = false;
	        }else if(event.getTab().getId().equals("tabDtVendaProdBeanPedVenda")) {
	        	if(renderedTabAjusteProduto) {
	        		currentTab = 1;
	        	}else {
	        		currentTab = 0;
	        	}
	        	
	        }else if(event.getTab().getId().equals("tabViewItemAjuste")) {
	        	currentTab = 0;
	        	renderedTabAjusteProduto = false;
	        }
    	} catch (Exception e) {
			//e.printStackTrace();
		}
    }
	
	public void selectCodigoBarras(){
		
//		Produto produto = new ProdutoRN().carregar(planilhaCegaItemMovDTO.getProdutoId());
//		controlalote = produto.getControlalote();
//		
//		produtoCB = new ProdutoCBRN().novo(contextoBean.getUsuarioLogado(), planilhaCegaItemMovDTO.getProdutoId(), codigoBarraDTO.getCodigobarras());
//		produtoCB.setProduto(produto);
//		produtoCB.setQtd(1);
	}
	
	

	public Integer getSelecionadaId() {
		return selecionadaId;
	}

	public void setSelecionadaId(Integer selecionadaId) {
		this.selecionadaId = selecionadaId;
	}

	public RomaneioFB getSelecionada() {
		if(selecionada==null && selecionadaId != null){
			RomaneioFBRN romaneioFBRN = new RomaneioFBRN();
			selecionada = romaneioFBRN.carregar(selecionadaId);
		}
		return selecionada;
	}

	public void setSelecionada(RomaneioFB selecionada) {
		this.selecionada = selecionada;
	}
	
	public RomaneioItemFB getItemSelecionado() {
		return itemSelecionado;
	}

	public void setItemSelecionado(RomaneioItemFB itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}

	public RomaneioItemDTOFB getItemAjusteSelecionado() {
		return itemAjusteSelecionado;
	}

	public void setItemAjusteSelecionado(RomaneioItemDTOFB itemAjusteSelecionado) {
		this.itemAjusteSelecionado = itemAjusteSelecionado;
	}

	public EmpresaFB getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaFB empresa) {
		this.empresa = empresa;
	}

	public FornecedorFB getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(FornecedorFB fornecedor) {
		this.fornecedor = fornecedor;
	}

	public RomaneioItemFB getRomaneioItemFB() {
		return romaneioItemFB;
	}

	public void setRomaneioItemFB(RomaneioItemFB romaneioItemFB) {
		this.romaneioItemFB = romaneioItemFB;
	}

	public List<RomaneioItemDTOFB> getListRomaneioItemDTOFB() {
		return listRomaneioItemDTOFB;
	}

	public void setListRomaneioItemDTOFB(List<RomaneioItemDTOFB> listRomaneioItemDTOFB) {
		this.listRomaneioItemDTOFB = listRomaneioItemDTOFB;
	}

	public List<RomaneioItemFB> getItens() {
		if(itens==null){
			RomaneioItemFBRN romaneioItemFBRN = new RomaneioItemFBRN();
			itens = romaneioItemFBRN.listar(selecionadaId);
			
			totalQtdRomaneio = 0d;
			totalQtdConferida = 0d;
			totalQtdAjuste = 0d;
			totalQtdRetirada = 0d;
			
			for(RomaneioItemFB rs : itens){
				totalQtdRomaneio += rs.getQtdRomaneio();
				totalQtdConferida += rs.getQtdConferida();
				if(rs.getQtdAjuste()!=null) {
					totalQtdAjuste += rs.getQtdAjuste();
				}	
				totalQtdRetirada += rs.getQtdRetirada();
			}
			
			
			
		}
		return itens;
	}

	public void setItens(List<RomaneioItemFB> itens) {
		this.itens = itens;
	}
	
	public RomaneioContagemFB getRomaneioContagemFB() {
		return romaneioContagemFB;
	}

	public void setRomaneioContagemFB(RomaneioContagemFB romaneioContagemFB) {
		this.romaneioContagemFB = romaneioContagemFB;
	}

	public double getItemQtdRetirada() {
		return itemQtdRetirada;
	}

	public void setItemQtdRetirada(double itemQtdRetirada) {
		this.itemQtdRetirada = itemQtdRetirada;
	}

	public List<RomaneioContagemFB> getItensMov() {
		if(itensMov==null){
			if(romaneioItemFB!=null){
				itensMov = new RomaneioContagemFBRN().listarLeiturasProduto(selecionadaId, romaneioItemFB.getProdutoId());
			}
		}

		return itensMov;
	}

	public void setItensMov(List<RomaneioContagemFB> itensMov) {
		this.itensMov = itensMov;
	}

	public ContextoBean getContextoBean() {
		return contextoBean;
	}

	public void setContextoBean(ContextoBean contextoBean) {
		this.contextoBean = contextoBean;
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
	
//	public List<CodigoBarraDTO> getListaCodBarras() {
//		if(listaCodBarras==null){
//			RomaneioFBItemMovRN planilhaCegaItemMovRN = new RomaneioFBItemMovRN();
//			listaCodBarras = planilhaCegaItemMovRN.listaCodBarrasToAssoc(selecionada);
//		}
//		return listaCodBarras;
//	}
//
//	public void setListaCodBarras(List<CodigoBarraDTO> listaCodBarras) {
//		this.listaCodBarras = listaCodBarras;
//	}
	
//	public List<RomaneioDivergFB> getListaDivergencia() {
//		return listaDivergencia;
//	}
//
//	public void setListaDivergencia(List<RomaneioDivergFB> listaDivergencia) {
//		this.listaDivergencia = listaDivergencia;
//	}
//	
//	public CodigoBarraDTO getCodigoBarraDTO() {
//		return codigoBarraDTO;
//	}
//
//	public void setCodigoBarraDTO(CodigoBarraDTO codigoBarraDTO) {
//		this.codigoBarraDTO = codigoBarraDTO;
//	}
	
	public ProdutoCBFB getProdutoCB() {
		return produtoCB;
	}

	public void setProdutoCB(ProdutoCBFB produtoCB) {
		this.produtoCB = produtoCB;
	}
	
	public Integer getProdutoSelecionadoId() {
		return produtoSelecionadoId;
	}

	public void setProdutoSelecionadoId(Integer produtoSelecionadoId) {
		this.produtoSelecionadoId = produtoSelecionadoId;
	}

	public String getItemUnidade() {
		return itemUnidade;
	}

	public void setItemUnidade(String itemUnidade) {
		this.itemUnidade = itemUnidade;
	}

	public String getItemProduto() {
		return itemProduto;
	}

	public void setItemProduto(String itemProduto) {
		this.itemProduto = itemProduto;
	}

	public double getItemQtdRomaneio() {
		return itemQtdRomaneio;
	}

	public void setItemQtdRomaneio(double itemQtdRomaneio) {
		this.itemQtdRomaneio = itemQtdRomaneio;
	}

	public double getItemQtdConferido() {
		return itemQtdConferido;
	}

	public void setItemQtdConferido(double itemQtdConferido) {
		this.itemQtdConferido = itemQtdConferido;
	}

	public double getItemQtdAjuste() {
		return itemQtdAjuste;
	}

	public void setItemQtdAjuste(double itemQtdAjuste) {
		this.itemQtdAjuste = itemQtdAjuste;
	}

	public double getNovoAjuste() {
		return novoAjuste;
	}

	public void setNovoAjuste(double novoAjuste) {
		this.novoAjuste = novoAjuste;
	}

	public double getTotalQtdPedido() {
		return totalQtdPedido;
	}

	public void setTotalQtdPedido(double totalQtdPedido) {
		this.totalQtdPedido = totalQtdPedido;
	}

	public double getSaldoAAjustar() {
		return saldoAAjustar;
	}

	public void setSaldoAAjustar(double saldoAAjustar) {
		this.saldoAAjustar = saldoAAjustar;
	}

	public double getQtdAjustada() {
		return qtdAjustada;
	}

	public void setQtdAjustada(double qtdAjustada) {
		this.qtdAjustada = qtdAjustada;
	}

	public double getQtdAjuste() {
		return qtdAjuste;
	}

	public void setQtdAjuste(double qtdAjuste) {
		this.qtdAjuste = qtdAjuste;
	}

	public Date getDtVencLot() {
		return dtVencLot;
	}

	public void setDtVencLot(Date dtVencLot) {
		this.dtVencLot = dtVencLot;
	}

	public String getCodigolote() {
		return codigolote;
	}

	public void setCodigolote(String codigolote) {
		this.codigolote = codigolote;
	}
	
	public Boolean getControlalote() {
		return controlalote;
	}

	public void setControlalote(Boolean controlalote) {
		this.controlalote = controlalote;
	}
	
	public Double getTotalQtdRomaneio() {
		return totalQtdRomaneio;
	}

	public void setTotalQtdRomaneio(Double totalQtdRomaneio) {
		this.totalQtdRomaneio = totalQtdRomaneio;
	}

	public Double getTotalQtdConferida() {
		return totalQtdConferida;
	}

	public void setTotalQtdConferida(Double totalQtdConferida) {
		this.totalQtdConferida = totalQtdConferida;
	}

	public Double getTotalQtdAjuste() {
		return totalQtdAjuste;
	}

	public void setTotalQtdAjuste(Double totalQtdAjuste) {
		this.totalQtdAjuste = totalQtdAjuste;
	}

	public Double getTotalQtdRetirada() {
		return totalQtdRetirada;
	}

	public void setTotalQtdRetirada(Double totalQtdRetirada) {
		this.totalQtdRetirada = totalQtdRetirada;
	}

	public boolean isBtnTransmitir() {
		return btnTransmitir;
	}

	public void setBtnTransmitir(boolean btnTransmitir) {
		this.btnTransmitir = btnTransmitir;
	}
	
	public boolean isRenderedBtnFinalizar() {
		renderedBtnFinalizar = false;
		if(!RomaneioFB.STATUS_FINALIZADO.equals(selecionada.getStatus())
				&& !RomaneioFB.STATUS_CANCELADO.equals(selecionada.getStatus())) {
			renderedBtnFinalizar = true;
		}
		
		itens = new RomaneioItemFBRN().listar(selecionadaId);
		
		totalQtdRomaneio = 0d;
		totalQtdConferida = 0d;
		totalQtdAjuste = 0d;
		totalQtdRetirada = 0d;
		
		for(RomaneioItemFB rs : itens){
			totalQtdRomaneio += rs.getQtdRomaneio();
			totalQtdConferida += rs.getQtdConferida();
			if(rs.getQtdAjuste()!=null) {
				totalQtdAjuste += rs.getQtdAjuste();
			}	
			totalQtdRetirada += rs.getQtdRetirada();
		}
		
		if(totalQtdRomaneio==(totalQtdConferida+totalQtdRetirada)) {
			renderedBtnFinalizar = true;
		}else {
			renderedBtnFinalizar = false;
		}
		
		return renderedBtnFinalizar;
	}

	public boolean isDisableExcluirLeituras() {
		disableExcluirLeituras = false;
		if(selecionada.getStatus() == RomaneioFB.STATUS_FINALIZADO
				|| selecionada.getStatus() == RomaneioFB.STATUS_CANCELADO) {
			disableExcluirLeituras = true;
		}
		
		return disableExcluirLeituras;
	}

	public void setDisableExcluirLeituras(boolean disableExcluirLeituras) {
		this.disableExcluirLeituras = disableExcluirLeituras;
	}

	public boolean isRenderedBtnAjuste() {
		renderedBtnAjuste = false;
		if(RomaneioFB.STATUS_CONFERIDO_COMCORTE.equals(selecionada.getStatus())
				&& totalQtdAjuste != null && totalQtdAjuste == 0d) {
			renderedBtnAjuste = true;
		}
		
		return renderedBtnAjuste;
	}

	public void setRenderedBtnAjuste(boolean renderedBtnAjuste) {
		this.renderedBtnAjuste = renderedBtnAjuste;
	}

	public boolean isRenderedBtnCancelar() {
		renderedBtnCancelar = false;
		if(RomaneioFB.STATUS_EM_ABERTO.equals(selecionada.getStatus())) {
			renderedBtnCancelar = true;
		}		
		return renderedBtnCancelar;
	}

	public void setRenderedBtnCancelar(boolean renderedBtnCancelar) {
		this.renderedBtnCancelar = renderedBtnCancelar;
	}

	public void setRenderedBtnFinalizar(boolean renderedBtnFinalizar) {
		this.renderedBtnFinalizar = renderedBtnFinalizar;
	}

	public boolean isRenderedTabAjusteProduto() {
		return renderedTabAjusteProduto;
	}

	public void setRenderedTabAjusteProduto(boolean renderedTabAjusteProduto) {
		this.renderedTabAjusteProduto = renderedTabAjusteProduto;
	}

	public int getCurrentTab() {
		return currentTab;
	}

	public void setCurrentTab(int currentTab) {
		this.currentTab = currentTab;
	}

	public String getIntegracao() {
		return integracao;
	}

	public void setIntegracao(String integracao) {
		this.integracao = integracao;
	}

	public void clearSession() {
		  HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		  if(request.getParameter("id")!=null){
			  HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		      session.removeAttribute("planilhaCegaGerenciarBean");
		  }
	}

	public Integer getQtdDecimal() {
		return qtdDecimal;
	}

	public void setQtdDecimal(Integer qtdDecimal) {
		this.qtdDecimal = qtdDecimal;
	}

}
