
package PackageIra;

import java.util.ArrayList;
import java.util.logging.Logger;
import static javafx.application.ConditionalFeature.FXML;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javax.swing.JOptionPane;

/**
 *
 * @author SANTOS
 */
public class Curso {
    private final SimpleStringProperty nome;
    private final SimpleIntegerProperty CargaHorariaTotal;     
    private ArrayList<Cadeira> disciplinas = new ArrayList<Cadeira>();
    

    public Curso(String nome, int cargaHorariaTotal) {
        this.nome = new SimpleStringProperty(nome);
        CargaHorariaTotal = new SimpleIntegerProperty(cargaHorariaTotal);
        
    }

    public String getNome() {
        return nome.get();
    }
    public int getCargaHorariaTotal() {
        return CargaHorariaTotal.get();
    }
    
    public void setNome(String nome){
        this.nome.set(nome);
    }
    public void setCargaHorariaTotal(int CH){
       CargaHorariaTotal.set(CH);
    }
    public void setCarregarDisciplinas(ArrayList<Cadeira> disciplinas){
        this.disciplinas = disciplinas;
        System.out.println("Disciplinas Carregadas");
    }
    public void addDisciplina(Cadeira disciplina){
        disciplinas.add(disciplina);
    }
    
    public void mostrarDisciplinas(){
        for(int i=0;i<disciplinas.size();i++){            
            System.out.println("n:"+(i+1)+" "+disciplinas.get(i).getNome());
        }
    }
}
