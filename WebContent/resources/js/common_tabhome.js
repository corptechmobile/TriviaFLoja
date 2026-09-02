// Tabs
function openTab(idMenu, title, urlDescricao, url) {
	
	w3_close();
	
	var countLisInTab = $("#tabViewVar div[class='ui-tabs-navscroller'] ul[role='tablist']").children().length + 1; // think about it ;)
	var tabId = 'tab_' + idMenu;
	var tabHomeId = document.getElementById("tab_home_id").value;
	
	//console.log('addTab: ' + title);
	//console.log('tabId: ' + tabId);
	//console.log('idMenu: ' + idMenu);
	//console.log('total tabs: ' + countLisInTab);
	//console.log('urlDescricao: ' + urlDescricao);
	
	if ($("#tabViewVar ul[role='tablist'] li a[href='#tabViewVar:" + tabId + "']").length == 0 && tabHomeId != idMenu) { 
		
		$("#tabViewVar ul[role='tablist']:first").append('<li class="ui-state-default ui-corner-top" role="tab" aria-expanded="false" aria-selected="false" tabindex="0"><a href="#tabViewVar:'+tabId+'" tabindex="-1">'+title+'</a><span id="'+tabId+'" class="ui-icon ui-icon-closethick"></span></li>');//.click();
		$('#tabViewVar div[class=ui-tabs-panels]:first').append('<div id="tabViewVar:'+tabId+'" class="ui-tabs-panel ui-widget-content ui-corner-bottom ui-helper-hidden" role="tabpanel" aria-hidden="true"><div id="tabViewVar:Content:'+tabId+'"><center>LOADING<BR/><img src="'+progressBarWMS+'"/></center></div></div>');

		$('div #tabViewVar\\:Content\\:' + tabId).load(url, {limit: 25}, function(response, status, xhr) {
			if ( status == "error" ) {
				//$( "#error" ).html( msg + xhr.status + " " + xhr.statusText );
				$('div #tabViewVar\\:Content\\:' + tabId).html('<div class="ui-messages ui-widget">'+ 
																	'<div class="ui-messages-error ui-corner-all">'+
																	'<span class="ui-messages-error-icon"></span>'+
																	'<ul>'+
																		'<li>'+
																			'<span class="ui-messages-error-summary">Error! '+xhr.statusText+'</span>'+
																			'<span class="ui-messages-error-detail">Não foi possível carregar essa funcionalidade, entre em contato com Suporte Corptech - 81 3224.2233 .</span>'+
																		'</li>'+
																	'</ul>'+
																	'</div>'+
																'</div>');
			}else if(status == "success"){
				$('div #tabViewVar\\:Title\\:' + tabId).html(urlDescricao);
			}
		});
		
		updateAfterDeleteTab(-1);
		$('#tabViewVar ul[role=tablist] li span#'+tabId).click(function(e) {
			//console.log('closeTab id: ' + this.id);
			//console.log('closeTab Index: ' + $('#tabViewVar ul[role=tablist] li span#'+tabId).index());
			
			var $li = $(this).parent();
			var myIndex = $li.parent().children().index( $li );
			
			PF('tabWidgetVar').remove(myIndex);
		});
		
		//$('a[href=\'#tabViewVar:' + tabId + '\']').click();
		
		countLisInTab = ($("#tabViewVar div[class='ui-tabs-navscroller'] ul[role='tablist']").children().length - 1);
		PF('tabWidgetVar').select(countLisInTab);
		
	} else {
		if(tabHomeId != idMenu){
			$('a[href=\'#tabViewVar:' + tabId + '\']').click();
		}else{
			$('a[href=\'#tabViewVar:tab_home\']').click();
		}
	}
	
}

function openHome(idMenu, title, urlDescricao, url) {
	updateAfterDeleteTab(-1);
	$('div #content_home').load(url + "?id=" + idMenu, {limit: 25}, function(response, status, xhr) {
		if ( status == "error" ) {
			$('div #tabViewVar\\:Content\\:' + tabId).html('<div class="ui-messages ui-widget">'+ 
																'<div class="ui-messages-error ui-corner-all">'+
																'<span class="ui-messages-error-icon"></span>'+
																'<ul>'+
																	'<li>'+
																		'<span class="ui-messages-error-summary">Error! '+xhr.statusText+'</span>'+
																		'<span class="ui-messages-error-detail">Não foi possível carregar essa funcionalidade, entre em contato com Suporte Corptech - 81 3224.2233 .</span>'+
																	'</li>'+
																'</ul>'+
																'</div>'+
															'</div>');
		}else if(status == "success"){
			$('div #tabViewVar\\:Title\\:tab_' + idMenu).html(urlDescricao);
		}
	});					
	document.getElementById("tab_home_id").value = idMenu;
	$('a[href=\'#tabViewVar\\:tab_home\']').click();
	
}

function updateAfterDeleteTab(ind){
	//console.log('updateAfterDeleteTab: ' + ind);
	PrimeFaces.cw('TabView','tabWidgetVar',{id:'tabViewVar',dynamic:false,cache:false,onTabClose:function(index){ return;}, onTabChange:function(index){ return;},effectDuration:'normal',scrollable:true});
}

function montaDeleteMenuAcesso(){
	var menuAcessoIdForDelete = document.getElementById('formMenuHome:menuHomeSelecionado').value;
	deleteMenuFavoritos([{name:'menuAcessoIdForDelete', value:menuAcessoIdForDelete}]);
}

function findDefaultMenuItem(){
	$("li a[rel='menufavoritos'], li a[rel='menufavoritos'] span").contextmenu(function() {
		$('#formMenuHome\\:menuHomeSelecionado').val("" +this.target);
	});
	
	$("li a[rel='nocontextmenu'], li a[rel='nocontextmenu'] span, h3 a[href='#']").contextmenu(function(){ return false; });
}

function openFilter(idPanelOpen, idFocusIn){
    $("#"+idPanelOpen).slideToggle();
    $("#"+idFocusIn).focus();
}

function showSejaBemVindo(){
	$("#sejabemvindotexto").css('display','');
}

function fChangeColorMenuPainelHelpDesk(id){
	$("#" + id).hide();
}

function closeTab(tabId){
	$("span#tab_" + tabId).trigger('click');
}

function pollStart(){
	$('#pollStart').load("../templates/common2/poll.jsf", {limit: 25}, function(response, status, xhr) {
		if ( status == "error" ) {
			//console.log('poll: [error] ' + xhr.statusText);
		}else if(status == "success"){
			//console.log('poll: [sucess]');
		}
	});
}

function focusInPainel(goTo, inTop){
	$('html, body').animate({
		scrollTop: $(goTo).offset().top - inTop
	}, 300);
}

function verTabsPedidosAfterExcluir(){
	if ($('div #tabViewVar\\:Title\\:tab_5').length) { // pedvendaconsultabean
		updatePedVendaExcluidaBean(); 
		closeTab('pedVendaNovoBean'); 
	}
	if ($('div #tabViewVar\\:Title\\:tab_7').length) { // pedvendaliberar
		updateListPedVendaBloqueadoBean(); 
	}
}

function verTabsPedidosAfterConcluir(){
	if ($('div #tabViewVar\\:Title\\:tab_5').length) { 
		updatePedVendaConsultaBean();  
	}
	if ($('div #tabViewVar\\:Title\\:tab_7').length) { // pedvendaconsultabean
		updateListPedVendaBloqueadoBean(); 
	}
}

function verTabsPedidosAfterLiberar(){ 
	if ($('div #tabViewVar\\:Title\\:tab_7').length) { // pedvendaliberar
		updateListPedVendaBloqueadoBean(); 
	}
	if ($('div #tabViewVar\\:Title\\:tab_5').length) { 
		updatePedVendaConsultaBean();  
	}
}

function closeTabsPedidos(){
	closeTab('pedVendaNovoBean'); 
}

function focusInPedVendaNovoDescricaoFilter(){
	$('#formItensPedVendaBean\\:descProdFilter').focus();
	$('#formItensPedVendaBean\\:descProdFilter').select();
}

function focusInPedVendaNovoCodBarraFilter(){
	$('#formItensPedVendaBean\\:codBarraFilter').focus();
	$('#formItensPedVendaBean\\:codBarraFilter').select();
}

function verificarPedVendaCadCli(cnpjCPF){
	
	//console.log('verificarPedVendaCadCli: ' + $('div #tabViewVar\\:Title\\:tab_pedVendaNovoBean').length);
	if($('div #tabViewVar\\:Title\\:tab_pedVendaNovoBean').length) { 
	
		closeTab('clienteNovoBean'); 
		$('a[href=\'#tabViewVar:tab_pedVendaNovoBean\']').click();
		
		if($('input #formVerCliPedVendaBean\\:cpf').length) { 
			$('input #formVerCliPedVendaBean\\:cpf').val(cnpjCPF);
		}else if($('#formVerCliPedVendaBean\\:cnpj').length) {
			$('input #formVerCliPedVendaBean\\:cnpj').val(cnpjCPF);
		}
		
		console.log('verificarPedVendaCadCli/cnpjCPF: ' + cnpjCPF);
		
		$('#formVerCliPedVendaBean\\:btnVerCli').click();
	
		console.log('verificarPedVendaCadCli/btnVerCli');
		
	}
}

function updatePanelUrlDescricao(descUrl){
	if($('#tabViewVar\\:Title\\:tab_pedVendaNovoBean').length) { 
		$('#tabViewVar\\:Title\\:tab_pedVendaNovoBean').html(descUrl);
		$('a[href="#tabViewVar\\:tab_pedVendaNovoBean"]').html(descUrl);
	}
}

function closePanelResultFilterPedVendaBean(){
	$('#formItensPedVendaBean\\:panelResultFilterPedVendaBean').hide();
}

function openPanelResultFilterPedVendaBean(){
	PF('UIdtItensPedVendaBean').clearFilters(); 
	PF('panelResultFilterPedVendaBean').show();
}
	

function closePanelResultFilterPedTransfBean(){
	$('#formItensPedTransfBean\\:panelResultFilterPedTransfBean').hide();
}

	
function openPanelResultFilterPedTransfBean(){
	PF('UIdtItensPedTransfBean').clearFilters(); 
	PF('panelResultFilterPedTransfBean').show();
}
	