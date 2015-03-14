
package PackageIra;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author SANTOS
 */
public class Cadeira {
    private final SimpleStringProperty nome;
    private final SimpleIntegerProperty credito;
    private final SimpleIntegerProperty CH;
    private final SimpleStringProperty codigo;
    private final SimpleDoubleProperty nota;  
    private final SimpleIntegerProperty exibido; 
    private final SimpleStringProperty situacao;
    private int periodo; 
    
   
    
    
    
    public Cadeira(String nome, int credito, int CH, String codigo) {
        this.nome = new SimpleStringProperty(nome);
        this.credito = new SimpleIntegerProperty(credito);
        this.CH = new SimpleIntegerProperty(CH);
        this.codigo = new SimpleStringProperty(codigo);
        this.nota = new SimpleDoubleProperty(0);   
        this.exibido = new SimpleIntegerProperty(0);
        this.situacao = new SimpleStringProperty("--");
        this.periodo = 0;
        
        
    }
    
    
    public String getNome(){
        return nome.get();
    }
    public int getCredito() {
        return credito.get();
    }
    public int getCH() {
        return CH.get();
    }
    public double getNota() {
        return nota.get();
    }
    public int getPeriodo(){
        return periodo;
    }
    public String getCodigo() {
        return codigo.get();
    }
    public String getSituacao(){
        return situacao.get();
    }
    public int getExibido(){
        return exibido.get();
    }
    
    
    public void setNome(String nome) {
        this.nome.set(nome);
    }
    public void setCredito(int credito) {
        this.credito.set(credito);
    }
    public void setCH(int CH) {
        this.CH.set(CH);
    }
    public void setCodigo(String codigo) {
        this.codigo.set(codigo);
    } 
    public void setNota(Double nota){
        
            this.nota.set(nota);
        
        
    }
    public void setPeriodo(int p){
        this.exibido.set(p);
        if(p>6){
            this.periodo = 6;
            
        }else{
            this.periodo = p;
        }
    }   
    public void setSituacao(Double nota,Boolean b){
        if(b==true){
            this.situacao.set("Trancado");
        }else{
            if(nota<5){
             this.situacao.set("Reprovado");   
            }
            if(5<=nota&&nota<7){
             this.situacao.set("Aprovado B");  
            }
            if(nota>=7){
             this.situacao.set("Aprovado A");  
            }
        }
    }
    
    
    
    
    public void mostrarDisciplina(){
        System.out.println("Codigo: "+codigo);
        System.out.println("nome: "+nome);
        System.out.println("CargaHoraria: "+String.valueOf(CH));
        System.out.println("Credito: "+String.valueOf(credito));
        System.out.println("Nota: "+String.valueOf(nota));
        System.out.println("periodo: "+String.valueOf(periodo));
        System.out.println("exibido: "+String.valueOf(exibido));
        
        
    }
    
}
