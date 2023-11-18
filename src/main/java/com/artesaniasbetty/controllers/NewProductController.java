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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

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
    private Stage stage;
    private Image imagen;

    // Este método te permite establecer el Stage desde fuera
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initComponents(){
        initSpinner();
        initCombo();
    }
    public void initCombo(){
        ObservableList<String> productosList = FXCollections.observableArrayList(toListText(ProductoDAO.getAllCategories()));
        comboCategoria.setItems(productosList);
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
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        // Obtener la imagen seleccionada
        File imgFile = fileChooser.showOpenDialog(stage);

        // Mostar la imagen
        if (imgFile != null) {
            String imageUrl = null;
            try {
                imageUrl = imgFile.toURI().toURL().toString();
                // Create Image object from the URL
                Image image = new Image(imageUrl);
                imagenProducto.setImage(image);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }


        }
    }

    public void registerProduct(ActionEvent actionEvent) {
        System.out.println(imagenProducto.getImage().getUrl());
        new ProductoDAO().createProduct(campoNombre.getText(),Integer.parseInt(campoPrecio.getText()),
                campoDescripcion.getText(), Integer.parseInt(spinnerCantidad.getValue().toString()),
                comboCategoria.getSelectionModel().getSelectedItem().toString().equalsIgnoreCase("Canastos")?1:2,
                imagenProducto.getImage().getUrl().replace("file:/",""));
    }
}
