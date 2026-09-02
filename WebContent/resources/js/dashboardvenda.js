//<![CDATA[
function handleDashBoardVenda(xhr, status, args) {
	
	var chartLineDashBoardEmpilhador;
	var dataChartLineDashBoardEmpilhador;
	var listaEmpDiaChart;
	
	console.log("handleDashBoardVenda - inicio");
	
	if(args.vendedorId !== undefined){
		console.log("vendedorIdDashBoardFaturamento: " + args.vendedorId);
	}
	
	if(args.listaEmpDiaChart !== undefined) {
		console.log("Entrou na lista ");
		
		var obj = $.parseJSON(args.listaEmpDiaChart);

		dataChartLineDashBoardEmpilhador = new google.visualization.DataTable();
		dataChartLineDashBoardEmpilhador.addColumn('string', 'Dia');
		dataChartLineDashBoardEmpilhador.addColumn('number', 'Faturamento no Período');
		//dataChartLineDashBoardEmpilhador.addColumn('number', 'Fat. Mês Ant');
		//dataChartLineDashBoardEmpilhador.addColumn('number', 'Fat. Ano Ant');
		var maximo = 0;
	    $.each(obj, function (index, val) {
	    	console.log(val.descricao +" - " + val.valor);
	    	//console.log(val.descricao +" - " + val.valor +" - " + val.valorMesAnt+" - " + val.valorAnoAnt);
			dataChartLineDashBoardEmpilhador.addRows([[val.descricao, val.valor]]);

			var total = val.valor;	
	    	if (total > maximo){
	    		maximo = total;
	    	}


	    });
	    	
	    var formatter = new google.visualization.NumberFormat({fractionDigits:0, decimalSymbol: ',',groupingSymbol: '.', negativeColor: 'red', negativeParens: true}); //, prefix: 'R$ '
	    formatter.format(dataChartLineDashBoardEmpilhador, 1);
		//formatter.format(dataChartLineDashBoardEmpilhador, 2);
		//formatter.format(dataChartLineDashBoardEmpilhador, 3);
	    	
		maximo = maximo+10;
		//alert("Maximo - " + maximo);
		//console.log("Maximo - " + maximo);
     	var options = {
    			//pieHole: 0.4,
    			//legend: 'none',
				chartArea: {width:700, height: '138'},
    			legend: { position: 'top', maxLines: 1 },
    			vAxis: {viewWindow: {max: maximo,min:0},title: 'Faturamento', textStyle: {fontSize:9}, gridlines: { count: 4 }},
        		hAxis: {slantedText:true, slantedTextAngle:90, textStyle: {fontSize:9}, gridlines: {count: 10} },
    			showRowNumber: true,
        	};
     	
     	var collumnLineDBEmp = $.parseJSON(args.collumnLineDBEmp);
	
	    	chartLineDashBoardEmpilhador = new google.visualization.ColumnChart(document.getElementById(collumnLineDBEmp));
	    	chartLineDashBoardEmpilhador.draw(dataChartLineDashBoardEmpilhador, options);
		

		//console.log("handleDashBoardVenda - entrou");
    }

   console.log("handleDashBoardEmpilhador - fim");

}

// ]]>