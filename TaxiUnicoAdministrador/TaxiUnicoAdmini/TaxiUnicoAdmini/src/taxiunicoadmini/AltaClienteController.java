/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taxiunicoadmini;

import clases.Cliente;
import clases.Taxista;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import taxiunicoadmini.dbconnection.DBConnection;

/**
 * FXML Controller class
 *
 * @author Alvaro Marquez
 */
public class AltaClienteController implements Initializable {
    DBConnection connectionClass = new DBConnection();
    Connection connection = connectionClass.getConnection();
    //columnas 
    @FXML
    private TableView<Cliente> tableView = new TableView<>();
    @FXML
    private TableColumn<Cliente, String> clientName = new TableColumn<>();
    @FXML
    private TableColumn<Cliente, String> clientEmail = new TableColumn<>();
    @FXML
    private TableColumn<Cliente, String> clientTelephone = new TableColumn<>();
    @FXML
    private TableColumn<Cliente, String> clientUser = new TableColumn<>();
    @FXML
    private TableColumn<Cliente, String> clientStatus = new TableColumn<>();
    @FXML
    private TableColumn<Cliente, String> clientRating = new TableColumn<>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clientName.setCellValueFactory(new PropertyValueFactory<Cliente, String>("Nombre"));
        clientEmail.setCellValueFactory(new PropertyValueFactory<Cliente, String>("Correo"));
        clientTelephone.setCellValueFactory(new PropertyValueFactory<Cliente, String>("Telefono"));
        clientUser.setCellValueFactory(new PropertyValueFactory<Cliente, String>("Usuario"));
        clientStatus.setCellValueFactory(new PropertyValueFactory<Cliente, String>("Estatus"));
        clientRating.setCellValueFactory(new PropertyValueFactory<Cliente, String>("Rating"));
        tableView.setItems(getClientInfo());
    }
    
    public ObservableList<Cliente> getClientInfo() {
        ObservableList<Cliente> clientes = FXCollections.observableArrayList();
        try {
            //preparar para procedimiento almacenado
            CallableStatement statement = connection.prepareCall("{call view_clientes()}");

            //llamar procedimiento almacenado
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            ResultSetMetaData metaData = resultSet.getMetaData();
            //obtener número de columna de cada atributo
            int numCols = metaData.getColumnCount(); //number of column
            int numColUsuario, numColNombre, numColCorreo, numColTelefono, numColRating, numColEstatus;
            numColUsuario = numColNombre = numColCorreo = numColTelefono = numColRating = numColEstatus = 1;
            for (int i = 1; i <= numCols; i++) {
                String colName = metaData.getColumnLabel(i);
                switch (colName) {
                    case "Usuario":
                        numColUsuario = i;
                        break;
                    case "Nombre":
                        numColNombre = i;
                        break;
                    case "Correo":
                        numColCorreo = i;
                        break;
                    case "Telefono":
                        numColTelefono = i;
                        break;
                    case "Estatus":
                        numColEstatus = i;
                        break;
                    case "Rating":
                        numColRating = i;
                        break;
                }
            }
            //añadir clientes a la lista a regresar
            while (resultSet.next()) {
                String usuario = resultSet.getString(numColUsuario);
                String nombre = resultSet.getString(numColNombre);
                String correo = resultSet.getString(numColCorreo);
                String telefono = resultSet.getString(numColTelefono);
                String rating = resultSet.getString(numColRating);
                String estatus = resultSet.getString(numColEstatus);
                boolean booleanEstatus = (Integer.parseInt(estatus) > 0);
                double doubleRating = Double.parseDouble(rating);
                clientes.add(new Cliente(nombre, correo, usuario, telefono, booleanEstatus, doubleRating));
            }
            //else muestra mensaje de error
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientes;
    }

      public void changeScreenButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("visualClien.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
    
      public void cambiarAltaClie(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("altaCliente.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
    
     public void cambiarVisTaxi(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("visualTaxi.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
     
      public void salirApp(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
      
          
        public void verConfig(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("Configuracion.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
        
           public void verHistorial(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("Historial.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }  
           
         public void cambiarAltTaxi(ActionEvent event) throws IOException
    {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("altaTaxista.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        
        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(tableViewScene);
        window.show();
    }
}
