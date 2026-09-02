package br.com.webapp.model.fb.romaneio;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Transient;

import br.com.webapp.model.fb.empresa.EmpresaFB;
import br.com.webapp.model.fb.fornecedor.FornecedorFB;

public class RomaneioFB implements Serializable {

	private static final long serialVersionUID = 5724540951189732587L;
	public static final String STATUS_EM_ABERTO = "0";
	public static final String STATUS_EM_CONFERENCIA = "1";
	public static final String STATUS_CONFERIDO = "2";
	public static final String STATUS_CONFERIDO_COMCORTE = "3";
	public static final String STATUS_FINALIZADO = "4";
	public static final String STATUS_CANCELADO = "5";
	
	private Integer romaneioId;
	private Integer procTranspId;
	private String status;
	private Integer usuarioIdGer;
	private String usuarioGer;
	private Integer usuarioIdConf;
	private String usuarioConf;
	private Integer usuarioIdCorte;
	private String usuarioCorte;
	private Integer usuarioIdFinalizado;
	private String usuarioFinalizado;
	private Integer usuarioIdCanc;
	private String usuarioCanc;
	private Date momentoGer;
	private Date momentoConf;
	private Date momentoCorte;
	private Date momentoFinalizado;
	private Date momentoCanc;
	private Set<RomaneioItemFB> item;

	public RomaneioFB() {}
	
	public Integer getRomaneioId() {
		return romaneioId;
	}

	public void setRomaneioId(Integer romaneioId) {
		this.romaneioId = romaneioId;
	}

	public Integer getProcTranspId() {
		return procTranspId;
	}

	public void setProcTranspId(Integer procTranspId) {
		this.procTranspId = procTranspId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getUsuarioIdGer() {
		return usuarioIdGer;
	}

	public void setUsuarioIdGer(Integer usuarioIdGer) {
		this.usuarioIdGer = usuarioIdGer;
	}

	public Integer getUsuarioIdConf() {
		return usuarioIdConf;
	}

	public void setUsuarioIdConf(Integer usuarioIdConf) {
		this.usuarioIdConf = usuarioIdConf;
	}

	public Integer getUsuarioIdCorte() {
		return usuarioIdCorte;
	}

	public void setUsuarioIdCorte(Integer usuarioIdCorte) {
		this.usuarioIdCorte = usuarioIdCorte;
	}

	public Integer getUsuarioIdFinalizado() {
		return usuarioIdFinalizado;
	}

	public void setUsuarioIdFinalizado(Integer usuarioIdFinalizado) {
		this.usuarioIdFinalizado = usuarioIdFinalizado;
	}

	public Integer getUsuarioIdCanc() {
		return usuarioIdCanc;
	}

	public void setUsuarioIdCanc(Integer usuarioIdCanc) {
		this.usuarioIdCanc = usuarioIdCanc;
	}

	public Date getMomentoGer() {
		return momentoGer;
	}

	public void setMomentoGer(Date momentoGer) {
		this.momentoGer = momentoGer;
	}

	public Date getMomentoConf() {
		return momentoConf;
	}

	public void setMomentoConf(Date momentoConf) {
		this.momentoConf = momentoConf;
	}

	public Date getMomentoCorte() {
		return momentoCorte;
	}

	public void setMomentoCorte(Date momentoCorte) {
		this.momentoCorte = momentoCorte;
	}

	public Date getMomentoFinalizado() {
		return momentoFinalizado;
	}

	public void setMomentoFinalizado(Date momentoFinalizado) {
		this.momentoFinalizado = momentoFinalizado;
	}

	public Date getMomentoCanc() {
		return momentoCanc;
	}

	public void setMomentoCanc(Date momentoCanc) {
		this.momentoCanc = momentoCanc;
	}

	public String getUsuarioGer() {
		return usuarioGer;
	}

	public void setUsuarioGer(String usuarioGer) {
		this.usuarioGer = usuarioGer;
	}

	public String getUsuarioConf() {
		return usuarioConf;
	}

	public void setUsuarioConf(String usuarioConf) {
		this.usuarioConf = usuarioConf;
	}

	public String getUsuarioCorte() {
		return usuarioCorte;
	}

	public void setUsuarioCorte(String usuarioCorte) {
		this.usuarioCorte = usuarioCorte;
	}

	public String getUsuarioFinalizado() {
		return usuarioFinalizado;
	}

	public void setUsuarioFinalizado(String usuarioFinalizado) {
		this.usuarioFinalizado = usuarioFinalizado;
	}

	public String getUsuarioCanc() {
		return usuarioCanc;
	}

	public void setUsuarioCanc(String usuarioCanc) {
		this.usuarioCanc = usuarioCanc;
	}

	public Set<RomaneioItemFB> getItem() {
		return item;
	}

	public void setItem(Set<RomaneioItemFB> item) {
		this.item = item;
	}

}
