//<![CDATA[
function handleDashBoardVendaAnual(xhr, status, args) {
	
	var chartBarDashBoard;
	var dataChartBarDashBoard;
	var listafatChart;
	
	console.log("handleDashBoardVendaAnual - inicio");
	
	if(args.vendedorId !== undefined){
		console.log("vendedorIdDashBoardFaturamento: " + args.vendedorId);
	}
	
	if(args.listafatChart !== undefined) {
		console.log("Entrou na lista ");
		
		var obj = $.parseJSON(args.listafatChart);

		dataChartBarDashBoard = new google.visualization.DataTable();
		dataChartBarDashBoard.addColumn('string', 'Total');
		
		var maximo = 0;
		varCrescAnoAnt = 0;
		varCrescMesAnt = 0;
		
	    $.each(obj, function (index, val) {
	    	console.log(val.descricao +" - " + val.valor +" - " + val.valorMesAnt+" - " + val.valorAnoAnt);

			varCrescAnoAnt = ((((val.valor)-val.valorAnoAnt)/val.valorAnoAnt)*100);
			varCrescMesAnt = ((((val.valor)-val.valorMesAnt)/val.valorMesAnt)*100);

			console.log('varCrescAnoAnt: '+varCrescAnoAnt);
			console.log('varCrescMesAnt: '+varCrescMesAnt);
			
			dataChartBarDashBoard.addColumn('number', 'Mês Atual');
			dataChartBarDashBoard.addColumn('number', 'Mês Ant ('+varCrescMesAnt.toFixed(2)+'%)');
			dataChartBarDashBoard.addColumn('number', 'Ano Ant ('+varCrescAnoAnt.toFixed(2)+'%)');

			dataChartBarDashBoard.addRows([['Total', val.valor, val.valorMesAnt, val.valorAnoAnt]]);

			var total = 0;
			
	    	if (val.valor > total){
	    		total = val.valor;
	    	}
	    	if (val.valorMesAnt > total){
	    		total = val.valorMesAnt;
	    	}
	    	if (val.valorAnoAnt > total){
	    		total = val.valorAnoAnt;
	    	}	    		    					
	    	if (total > maximo){
	    		maximo = total;
	    	}


	    });
	    	
	    var formatter = new google.visualization.NumberFormat({fractionDigits:0, decimalSymbol: ',',groupingSymbol: '.', negativeColor: 'red', negativeParens: true}); //, prefix: 'R$ '
	    formatter.format(dataChartBarDashBoard, 1);
		formatter.format(dataChartBarDashBoard, 2);
		formatter.format(dataChartBarDashBoard, 3);
	    	
		maximo = maximo+10;
		//alert("Maximo - " + maximo);
		console.log("Maximo - " + maximo);
     	var options = {
    			//pieHole: 0.4,
    			//legend: 'none',
				chartArea: {width:350, height: '138'},
    			legend: { position: 'top', maxLines: 1 },
    			vAxis: {viewWindow: {max: maximo,min:0},title: 'Faturamento', textStyle: {fontSize:9}, gridlines: { count: 4 }},
        		hAxis: {slantedText:true, slantedTextAngle:45, textStyle: {fontSize:9}, gridlines: {count: 10} },
    			showRowNumber: true,
        	};
     	
     	var collumnBarDB = $.parseJSON(args.collumnBarDB);
	
	    	chartBarDashBoard = new google.visualization.ColumnChart(document.getElementById(collumnBarDB));
	    	chartBarDashBoard.draw(dataChartBarDashBoard, options);
		

		//console.log("handleDashBoardVenda - entrou");
    }

   console.log("handleDashBoardVendaAnual - fim");

}

// ]]>