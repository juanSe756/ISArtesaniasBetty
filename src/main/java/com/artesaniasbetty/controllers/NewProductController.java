package com.artesaniasbetty.controllers;

import com.artesaniasbetty.dao.ProductoDAO;
import com.artesaniasbetty.model.Categoria;
import com.artesaniasbetty.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.IntegerStringConverter;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class NewProductController {
    public ComboBox comboCategoria;
    public Spinner spinnerCantidad;
    public TextArea campoDescripcion;
    public Button btnCancelar;
    public Button btnConfirmar;
    public Button btnImagen;
    public ImageView imagenProducto;
    public TextField campoNombre;
    public TextField campoPrecio;
    public Button btnMinimize;
    public Button btnClose;
    private Stage stage;
    private Image imagen;

    // Este método te permite establecer el Stage desde fuera
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initComponents(){
        campoPrecio.addEventHandler(KeyEvent.KEY_TYPED, event -> SoloNumerosEnteros(event));
        initSpinner();
        initCombo();
    }

    public void SoloNumerosEnteros(KeyEvent keyEvent) {
        try {
            char key = keyEvent.getCharacter().charAt(0);

            if (!Character.isDigit(key))
                keyEvent.consume();

        } catch (Exception ex) {
        }
    }
        public void initCombo(){
        ProductoDAO productoDAO = new ProductoDAO();
        ObservableList<String> productosList =
                FXCollections.observableArrayList(toListText(productoDAO.getAllCategories()));
        comboCategoria.setItems(productosList);
        comboCategoria.setValue(productosList.get(0));
    }

    public List<String> toListText(List<Categoria> categorias){
        List<String> categoriasList = new ArrayList<>();
        for (Categoria categoria : categorias) {
            categoriasList.add(categoria.getNombre());
        }
        return categoriasList;
    }
    private void initSpinner(){
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1); // Valores mínimos, máximos y por defecto

        spinnerCantidad.setValueFactory(valueFactory);
    }

    public void uploadImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Imagen");

        // Agregar filtros para facilitar la busqueda
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );

        // Obtener la imagen seleccionada
        File imgFile = fileChooser.showOpenDialog(stage);

        // Mostar la imagen
        if (imgFile != null) {
            String imageUrl = null;
            try {
                imageUrl = imgFile.toURI().toURL().toString();
                Image image = new Image(imageUrl);
                imagenProducto.setImage(image);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void registerProduct(ActionEvent actionEvent) {
        if (!campoPrecio.getText().isEmpty() && !campoNombre.getText().isEmpty() && imagenProducto.getImage() != null) {
            boolean created = new ProductoDAO().createProduct(
                    campoNombre.getText(),
                    Integer.parseInt(campoPrecio.getText()),
                    campoDescripcion.getText(),
                    Integer.parseInt(spinnerCantidad.getValue().toString()),
                    comboCategoria.getSelectionModel().getSelectedItem().toString().equalsIgnoreCase("Canastos") ? 1 : 2,
                    imagenProducto.getImage().getUrl().replace("file:/", "").replace("%20", " ").replace("%", " ")
            );
            if (created) {
                changeViewErrors(new ActionEvent(), false,"Producto creado exitosamente");
                closeApp(new ActionEvent());
            } else {
                changeViewErrors(new ActionEvent(),false, "Error al crear el producto");
            }
        } else {
            changeViewErrors(new ActionEvent(), false,"Debe rellenar todos los campos");
        }
    }

    private void showAlertError(ActionEvent event, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlertSucess(ActionEvent event, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void cancelOperation(ActionEvent actionEvent) {
        Stage currentStage = (Stage) btnCancelar.getScene().getWindow();
        currentStage.close();
    }

    public void minimizeApp(ActionEvent actionEvent) {
        Stage currentStage = (Stage) btnMinimize.getScene().getWindow();
        currentStage.setIconified(true);
    }

    public void closeApp(ActionEvent actionEvent) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
    }
    public void changeViewErrors(ActionEvent actionEvent, boolean type, String message) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/archivesViews/Errores.fxml"));
        BorderPane panel = null;
        try {
            panel = loader.load();

            // Obtén la ventana actual y muestra el nuevo panel en una ventana emergente
            Scene currentScene = btnConfirmar.getScene();
            Stage stage = (Stage) currentScene.getWindow();

            // Crea la escena de la ventana de errores con las dimensiones de la ventana principal
            Scene scene = new Scene(panel, 350, 200);

            Stage panelStage = new Stage();
            panelStage.setScene(scene);
            panelStage.initModality(Modality.APPLICATION_MODAL);
            panelStage.initOwner(stage);
            initComponentsErrores(loader, stage, type, message);
            panelStage.initStyle(StageStyle.UNDECORATED);
            panelStage.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void initComponentsErrores(FXMLLoader loader,Stage stage,boolean type, String message){
        ErrorController errorController = null;
        try {
            errorController = loader.getController();
            errorController.setData(type,message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
