/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PackageIra;
import java.io.*;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;


/**
 * FXML Controller class
 *
 * @author SANTOS
 */
public class SelecionarCursoController implements Initializable {
   //atributos da seleção de curso
    ImageIcon iconErro;   
    @FXML
    private Curso cursoUsr;   
    @FXML        
    private ObservableList<String> listaComboBoxCursos = FXCollections.observableArrayList();
    @FXML
    private Label labelCursoSelected;
    @FXML
    private Label resultIRA;
    @FXML
    private ComboBox<String> selectCurso;
    private String nomeArquivo;
    private ArrayList<String> listaDeCursos = new ArrayList<String>();
    private ArrayList<Cadeira> todasDisciplinasCursoSelect = new ArrayList<Cadeira>();
    private ArrayList<Cadeira> disciplinaSelecionadas = new ArrayList<Cadeira>();
    private ArrayList<Curso> todosCursos = new ArrayList<Curso>();     
    //fim dos atributos da seleção de curso
    
    //atributos da seleçao de disciplinas
    @FXML
    private ComboBox<String> selectDisciplina;
    @FXML
    private CheckBox trancadoSituacao = new CheckBox();
    @FXML
    private TextField  txtPeriodo ;   
    @FXML        
    private ObservableList<String> listaComboBoxDisciplinas = FXCollections.observableArrayList();
    @FXML
    private TextField txtNota = new TextField();
    @FXML
    TableView<Cadeira> tabela = new TableView<Cadeira>();
    @FXML
    ObservableList<Cadeira> dadosSelecionados = FXCollections.observableArrayList();
    @FXML
    TableColumn<Cadeira, String> codigoDisciplina;
    @FXML
    TableColumn<Cadeira, String> situacaoDisciplina;
    @FXML
    TableColumn<Cadeira, String> nomeDisciplina;
    @FXML          
    TableColumn<Cadeira, Integer> chDisciplina;
    @FXML
    TableColumn<Cadeira, Integer> creditoDisciplina;    
    @FXML
    TableColumn<Cadeira, Integer> perioDisciplina;
    @FXML
    TableColumn<Cadeira, Double> notaDisciplina;
    private Boolean erro  = true;
    //fim atributos de disciplinas
    

            
    public void ButtonSelectCurso(ActionEvent evt){
        //evento que ocorre quando o usuario clicar no botao e selecionar o curso
       
       
       
       resultIRA.setText("");
       try{
           if(selectCurso.getValue()==null){throw new IllegalArgumentException();}
                labelCursoSelected.setText("Você Selecionou: "+selectCurso.getValue());       
                nomeArquivo = selectCurso.getValue()+".txt";
       for(int i=0;i<todosCursos.size();i++){
           if(todosCursos.get(i).getNome().equals(selectCurso.getValue())){
               cursoUsr = new Curso(selectCurso.getValue(),todosCursos.get(i).getCargaHorariaTotal());               
           }
       }
       
            
       System.out.println(nomeArquivo);
       lerArquivoDisciplina();         
       System.out.println("quantidade de disciplinas "+todasDisciplinasCursoSelect.size());
       listaComboBoxDisciplinas.clear();
       cursoUsr.setCarregarDisciplinas(todasDisciplinasCursoSelect);
       for(int i=0;i<todasDisciplinasCursoSelect.size();i++ ){
           listaComboBoxDisciplinas.addAll(todasDisciplinasCursoSelect.get(i).getCodigo()+" - "+todasDisciplinasCursoSelect.get(i).getNome());
       }
       
       selectDisciplina.setItems(listaComboBoxDisciplinas);
       }catch(IllegalArgumentException e){
          
           iconErro = new ImageIcon(getClass().getResource("/PackageIra/icones/icone2.png"));
           JOptionPane.showMessageDialog(null, "SELECIONE UM CURSO DE GRADUAÇÃO!", "ATENÇÃO!", 0,iconErro); 
       }
       
        
    }     
    public void ButtonSelectDisciplina(ActionEvent evt){
        resultIRA.setText("");
        String disciplinaSelecionada;
        try{
            if(selectDisciplina.getValue()==null){throw new IllegalArgumentException();}
            for(int i=0;i<todasDisciplinasCursoSelect.size();i++){
            if(selectDisciplina.getValue().equals(listaComboBoxDisciplinas.get(i))){
           
                StringBuilder s = new StringBuilder(selectDisciplina.getValue());  
                        s.delete(0, 8); //deletar codigo do ObservableList para colocar na tabela
                disciplinaSelecionada = String.valueOf(s);
                
                
                if(trancadoSituacao.isSelected()){
                    System.out.println("Trancado");
                        
                    
                   
                    dadosSelecionados.add(new Cadeira(disciplinaSelecionada,
                        todasDisciplinasCursoSelect.get(i).getCredito(),
                        todasDisciplinasCursoSelect.get(i).getCH(),
                        todasDisciplinasCursoSelect.get(i).getCodigo()));
                    
                        dadosSelecionados.get(dadosSelecionados.size()-1).setSituacao(dadosSelecionados.get(dadosSelecionados.size()-1).getNota(), trancadoSituacao.isSelected());
                        
                }else{
                    
                  dadosSelecionados.add(new Cadeira(disciplinaSelecionada,
                  todasDisciplinasCursoSelect.get(i).getCredito(),
                  todasDisciplinasCursoSelect.get(i).getCH(),
                  todasDisciplinasCursoSelect.get(i).getCodigo()));
                  
                  try{
                      String p = txtPeriodo.getText();
                      String n = txtNota.getText();
                      
                      n = n.replaceAll(",", ".");
                      
                      
                      int q = Integer.parseInt(p);
                      Double d = Double.parseDouble(n);                     
                      
                     
                      
                      if(d < 0 || d > 10 ){throw new IllegalArgumentException();}
                      if(q<=0 || q > 20){throw new IllegalArgumentException();}                     
                     
                      dadosSelecionados.get(dadosSelecionados.size()-1).setNota(d);                     
                      dadosSelecionados.get(dadosSelecionados.size()-1).setPeriodo(q);                  
                      dadosSelecionados.get(dadosSelecionados.size()-1).setSituacao(dadosSelecionados.get(dadosSelecionados.size()-1).getNota(), trancadoSituacao.isSelected());
                      
                      dadosSelecionados.get(dadosSelecionados.size()-1).mostrarDisciplina();
                   
                     
                  }catch(NumberFormatException e){
                     
                      iconErro = new ImageIcon(getClass().getResource("/PackageIra/icones/icone3.png"));
                      JOptionPane.showMessageDialog(null, "- Valores para o Periodo ou Nota devem ser numericos\n- Valor do Periodo deve ser: \n  1 para o 1°Semestre\n  2 para o 2°Semestre\n  3 para o 3°Semestre\n  assim por diante",
                                                    "ATENÇÃO!", 0,iconErro);
                      
                      dadosSelecionados.remove(dadosSelecionados.size()-1);
                  }catch(IllegalArgumentException e){
                     
                      iconErro = new ImageIcon(getClass().getResource("/PackageIra/icones/icone2.png"));
                      JOptionPane.showMessageDialog(null, "Nota ou Periodo Inválido", "ATENÇÃO!", 0,iconErro);
                      dadosSelecionados.remove(dadosSelecionados.size()-1);
                  }

                } 
            } 
        }
        }catch(IllegalArgumentException e){            
                iconErro = new ImageIcon(getClass().getResource("/PackageIra/icones/icone4.png"));
                JOptionPane.showMessageDialog(null, "Selecione as disciplina para o calculo do IRA \ne pressione Adicionar", "ATENÇÃO!", 0,iconErro);
        }
        
        
      System.out.println("TEM :"+dadosSelecionados.size()+" DISCIPLINAS SELECIONADAS ");     
             trancadoSituacao.setSelected(false);
    }
    public void ButtonRemover(ActionEvent evt){
        dadosSelecionados.removeAll(tabela.getSelectionModel().getSelectedItems());
        resultIRA.setText("");
    }
    public void ButtonCalcularIRA(ActionEvent evt){
            
        String ira = String.format("%.0f", CalcularIRA());       
        resultIRA.setText("IRA-IDV: "+ira);
        
            UIManager UI=new UIManager();
            UI.put("OptionPane.background",new ColorUIResource(192,192,192));
            UI.put("Panel.background",new ColorUIResource(195,195,195));
            iconErro = new ImageIcon(getClass().getResource("/PackageIra/icones/icone5.png"));
            JOptionPane.showMessageDialog(null, "Seu IRA: "+ ira+"\n\nVocê pode remover Disciplinas selecionando\nna tabela e clicando em Remover", "RESULTADO!", 0,iconErro);
       
    }
    public void ButtonAddNovoDisciplina(ActionEvent evt){
          String newNome;
          String newCodigo;
          int newPeriodo = 0;
          int newCredito = 0;
          int newCH = 0;
          Double newNota = 0.0;
          boolean newSituacao = false;
          int trancado;  
          ImageIcon iconPergunta = new ImageIcon(getClass().getResource("/PackageIra/icones/icone6.png")); 
          ImageIcon iconErros = new ImageIcon(getClass().getResource("/PackageIra/icones/icone2.png")); 
         
         
         String nome = (String) JOptionPane.showInputDialog(null,"Digite o NOME da Disciplina","NOME",JOptionPane.QUESTION_MESSAGE,iconPergunta,null,null);
         newNome = String.valueOf(nome);
         
         String codigo = (String) JOptionPane.showInputDialog(null,"Digite o CODIGO da Disciplina","DISCIPLINA",JOptionPane.QUESTION_MESSAGE,iconPergunta,null,null);
         newCodigo = String.valueOf(codigo);
         
         
         boolean bandeira = true;
         while(bandeira){
         try{
             String periodo = (String) JOptionPane.showInputDialog(null,"Digite o PERIODO em que a \ndisciplina foi cursada","PERIODO",JOptionPane.QUESTION_MESSAGE,iconPergunta,null,null);
             if(Integer.parseInt(periodo)<1){
                     throw new NumberFormatException();
                 }
             newPeriodo = Integer.parseInt(periodo);
         bandeira = false;
         }catch(NumberFormatException e){
             bandeira = true;
             JOptionPane.showMessageDialog(null, "Valor do Periodo deve ser: \n  1 para o 1°Periodo\n  2 para o 2°Periodo\n  3 para o 3°Periodo\n  assim por diante","ATENÇÃO!",0,iconErros);
         }
         }
         
         bandeira = true;
         while(bandeira){
             try{
                 String credito = (String) JOptionPane.showInputDialog(null,"Digite a quantidade de \nCREDITOS dessa disciplina","CREDITOS",JOptionPane.QUESTION_MESSAGE,iconPergunta,null,null);
                 if(Integer.parseInt(credito)<1){
                     throw new NumberFormatException();
                 }
                 newCredito = Integer.parseInt(credito);
                 bandeira = false;
             }catch(NumberFormatException e){
                 bandeira = true;
                JOptionPane.showMessageDialog(null, "valor de CREDITOS invalidos!","ATENÇÃO!",0,iconErros);
             }
         }
         
         bandeira = true;
         while(bandeira){
             try{
                 String cargaHoraria = (String) JOptionPane.showInputDialog(null,"Digite a quantidade \nde Carga Horaria(CH) da disciplina","CH",JOptionPane.QUESTION_MESSAGE,iconPergunta,null,null);
                 if(Integer.parseInt(cargaHoraria)<1){
                     throw new NumberFormatException();
                 }
                 newCH = Integer.parseInt(cargaHoraria);
                 bandeira = false;
             }catch(NumberFormatException e){
                 bandeira = true;
                JOptionPane.showMessageDialog(null, "valor de CH invalida!","ATENÇÃO!",0,iconErros);
             }
         }
         
         trancado = JOptionPane.showConfirmDialog(null, "Você trancou a disciplina de\n "+newNome+"?","TRANCAMENTO",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,iconPergunta);
         
         if(trancado == JOptionPane.YES_OPTION){
             newSituacao = true;
         }else{
            bandeira = true;
         while(bandeira){
             try{
                 String nota = (String) JOptionPane.showInputDialog(null,"Digite a NOTA da Disciplina","NOTA",JOptionPane.QUESTION_MESSAGE,iconPergunta,null,null);
                 nota = nota.replaceAll(",", ".");
                 if(Double.parseDouble(nota)<0 || Double.parseDouble(nota)>10){
                     throw new NumberFormatException();
                 }
                 newNota = Double.parseDouble(nota);
                 bandeira = false;
             }catch(NumberFormatException e){
                 bandeira = true;
                JOptionPane.showMessageDialog(null, "valor da NOTA invalida!","NOTA",0,iconErros);
             }
         } 
             
         }
         
         
         
         
         
         
         dadosSelecionados.add(new Cadeira(newNome,newCredito,newCH,newCodigo));
         dadosSelecionados.get(dadosSelecionados.size()-1).setNota(newNota);                     
         dadosSelecionados.get(dadosSelecionados.size()-1).setPeriodo(newPeriodo);                  
         dadosSelecionados.get(dadosSelecionados.size()-1).setSituacao(dadosSelecionados.get(dadosSelecionados.size()-1).getNota(), newSituacao);
         dadosSelecionados.get(dadosSelecionados.size()-1).mostrarDisciplina();
         
         
         
         
        
         
         
         
         
         
         
        
    }
    public void lerArquivoDisciplina(){
        //metodo para ler arquivos com disciplinas
        try {
            todasDisciplinasCursoSelect.clear();            
            File file = new File(nomeArquivo);
            
           // BufferedReader in = new BufferedReader(new FileReader(file));
            BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/arquivos/"+nomeArquivo)));          
           
           String linhaArquivo;
           
            while((linhaArquivo = in.readLine())!=null){
                
                String[] dados = linhaArquivo.split(";");
                              
                    if(dados.length>1){    
                       todasDisciplinasCursoSelect.add(new Cadeira(dados[0],Integer.parseInt(dados[1]),Integer.parseInt(dados[2]),dados[3])); 
                    }
                    
            }
            
          
            in.close();
            
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();           
        } 
    }
    public void lerArquivoCurso(){
        //metodo para ler arquivo com Cursos
        try {
            File file = new File("Cursos.txt");
           
         //   BufferedReader in = new BufferedReader(new FileReader(file));
            BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/arquivos/Cursos.txt")));          
           
            String linhaArquivo;
            while((linhaArquivo = in.readLine())!=null){
                
                String[] curso = linhaArquivo.split(";");                                                 
                System.out.println("nome do curso:"+curso[0]);
                System.out.println("CH total:"+curso[1]);
                listaDeCursos.add(curso[0]);
                todosCursos.add(new Curso(curso[0],Integer.parseInt(curso[1])));
            }
            
          
            in.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
    
    public Double CalcularIRA(){
        //variaveis para calcular IRA
     int T=0; //somatório de carga horária das disciplinas trancadas;
     int C=0; //somatório de carga horária das disciplinas cursadas ou trancadas;
     Double somatorio1 = 0.0; //parte superior da formula
     int somatorio2 = 0; //parte inferior da formula
        for(int i=0;i<dadosSelecionados.size();i++){
            if(dadosSelecionados.get(i).getSituacao().equals("Trancado")){
                T += dadosSelecionados.get(i).getCH();
            }
            int pi = dadosSelecionados.get(i).getPeriodo();
            System.out.println("VALOR DE Pi:"+pi);
            int ci = dadosSelecionados.get(i).getCH();
            Double ni =dadosSelecionados.get(i).getNota();
            C += ci;
            somatorio1 += pi*ci*ni; 
            somatorio2 += pi*ci;
        }
        System.out.println("VALOR DE T:"+T);
        System.out.println("VALOR DE C:"+C);
        System.out.println("VALOR DO SOMATORIO 1:"+somatorio1);
        System.out.println("VALOR DO SOMATORIO 2:"+somatorio2);
        Double a = (1 - ((0.5)*T)/C);
        Double b = somatorio1/somatorio2;
        System.out.println("VALOR DO IRA:"+(a*b*1000));
        return a*b*1000;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         
        lerArquivoCurso();        
        for(int i=0;i<listaDeCursos.size();i++){
            listaComboBoxCursos.addAll(listaDeCursos.get(i));                                      
        }
       
   
        selectCurso.setItems(listaComboBoxCursos);        
        codigoDisciplina.setCellValueFactory(new PropertyValueFactory<Cadeira,String>("codigo"));
        nomeDisciplina.setCellValueFactory(new PropertyValueFactory<Cadeira,String>("nome"));
        situacaoDisciplina.setCellValueFactory(new PropertyValueFactory<Cadeira, String>("situacao"));
        chDisciplina.setCellValueFactory(new PropertyValueFactory<Cadeira,Integer>("CH"));
        creditoDisciplina.setCellValueFactory(new PropertyValueFactory<Cadeira,Integer>("credito"));        
        perioDisciplina.setCellValueFactory(new PropertyValueFactory<Cadeira,Integer>("exibido"));
        notaDisciplina.setCellValueFactory(new PropertyValueFactory<Cadeira,Double>("nota"));   
       
        
       // 
        
        selectCurso.setTooltip(new Tooltip("Selecione seu curso"));
        selectDisciplina.setTooltip(new Tooltip("Disciplinas inseridas no histórico por intermédio de aproveitamento, tanto interno como externo, não farão parte do cálculo do IRA"));
        txtPeriodo.setTooltip(new Tooltip("Nas disciplinas anuais, será considerado o semestre de início delas"));
        txtNota.setTooltip(new Tooltip("A disciplina reprovada por freqüência terá nota final zero."));       
        tabela.setEditable(true); 
        tabela.setItems(dadosSelecionados);
        
        
        
        
    }    
   
}
