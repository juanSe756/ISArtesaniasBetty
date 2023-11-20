package com.artesaniasbetty.controllers;

import com.artesaniasbetty.dao.ProductoDAO;
import com.artesaniasbetty.model.Categoria;
import com.artesaniasbetty.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModifyProductController {
    public ComboBox comboCategoria;
    public Spinner spinnerCantidad;
    public TextArea campoDescripcion;
    public Button btnConfirmar;
    public Button btnImagen;
    public ImageView imagenProducto;
    public TextField campoNombre;
    public TextField campoPrecio;
    public Button btnCancelar;
    private ProductoDAO productoDAO = new ProductoDAO();

    private int idProduct;

    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setData(Producto producto){
        idProduct = producto.getId();
        campoNombre.setText(producto.getNombre());
        campoDescripcion.setText(producto.getDesc());
        campoPrecio.setText(""+producto.getPrecio());
        campoPrecio.addEventHandler(KeyEvent.KEY_TYPED, event -> SoloNumerosEnteros(event));
        imagenProducto.setImage(new Image("/assets/prods/" + producto.getNombre() + ".jpg"));
        initSpinner(producto.getStock());
        initCombo(producto.getCategoria().getNombre());

    }

    public void SoloNumerosEnteros(KeyEvent keyEvent) {
        try {
            char key = keyEvent.getCharacter().charAt(0);

            if (!Character.isDigit(key))
                keyEvent.consume();

        } catch (Exception ex) {
        }
    }

    public void initCombo(String category){
        ObservableList<String> productosList =
                FXCollections.observableArrayList(toListText(productoDAO.getAllCategories()));
        comboCategoria.setItems(productosList);
        comboCategoria.setValue(category);
    }

    public List<String> toListText(List<Categoria> categorias){
        List<String> categoriasList = new ArrayList<>();
        for (Categoria categoria : categorias) {
            categoriasList.add(categoria.getNombre());
        }
        return categoriasList;
    }
    private void initSpinner(int stock){
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1); // Valores mínimos, máximos y por defecto

        spinnerCantidad.setValueFactory(valueFactory);
        spinnerCantidad.getValueFactory().setValue(stock);
    }
    public void modifyProduct(ActionEvent actionEvent) {
        System.out.println(imagenProducto.getImage().getUrl());
        if (!campoPrecio.getText().isEmpty() && !campoNombre.getText().isEmpty() && imagenProducto.getImage().getUrl() != null) {
            boolean modified = productoDAO.modifyProduct(idProduct,
                    campoNombre.getText(),
                    Double.parseDouble(campoPrecio.getText()), campoDescripcion.getText(),
                    Integer.parseInt(spinnerCantidad.getValue().toString()),
                    comboCategoria.getSelectionModel().getSelectedItem().toString().equalsIgnoreCase("Madera") ? 2 : 1,
                    imagenProducto.getImage().getUrl().replace("file:/", "").replace("%20", " ").replace("%", " ")); //
            if(modified){
                showAlertSucess(new ActionEvent(),"Producto modificado exitosamente");
            }else {
                showAlertError(new ActionEvent(),"Error al modificar el producto");
            }
        }else {
            showAlertError(new ActionEvent(),"Debe rellenar todos los campos");
        }
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
}
