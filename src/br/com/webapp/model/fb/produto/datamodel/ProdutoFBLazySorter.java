package br.com.webapp.model.fb.produto.datamodel;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import br.com.webapp.model.fb.produto.ProdutoFB;

public class ProdutoFBLazySorter implements Comparator<ProdutoFB> {
 
    private String sortField;
    private SortOrder sortOrder;
     
    public ProdutoFBLazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    public int compare(ProdutoFB prod1, ProdutoFB prod2) {
        try {
            Object value1 = ProdutoFB.class.getField(this.sortField).get(prod1);
            Object value2 = ProdutoFB.class.getField(this.sortField).get(prod2);
 
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
