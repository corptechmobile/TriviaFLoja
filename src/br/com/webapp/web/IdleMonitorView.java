package br.com.webapp.web;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="idleMonitorView")
@SessionScoped
public class IdleMonitorView implements Serializable {
	
	private static final long serialVersionUID = 6733276513895097791L;
	
	private Integer count;
	private String sessionStatus;
	
	public void onIdle() {
	    //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No activity.", "What are you doing over there?"));
		//System.out.println("[IdleMonitorView][onIdle][count]: " + count);
		sessionStatus = "off-line";
	}
	
	public void onActive() {
	    //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Welcome Back", "Well, that's a long coffee break!"));
		//System.out.println("[IdleMonitorView][onActive][count]: " + count);
		sessionStatus = "on-line";
	}
	
	public void onkeepSessionAlive(){
		if(count==null){
			count=0;
		}
		count++;
		
		//System.out.println("[IdleMonitorView][onkeepSessionAlive][count]: " + count);
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getSessionStatus() {
		return sessionStatus;
	}

	public void setSessionStatus(String sessionStatus) {
		this.sessionStatus = sessionStatus;
	}

}
