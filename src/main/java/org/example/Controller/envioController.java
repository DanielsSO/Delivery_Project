package org.example.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.Models.JDBCUtil;
import org.example.Models.envioModel;

import java.sql.*;

public class envioController {
    public void  crearEnvio(int envia_id, int recibe_id, envioModel envio){
        String sql = "INSERT INTO Envios (envia_id, recibe_id, direccionEnvia, otros_datosEnvia, direccionRecibe, otros_datosRecibe, fecha_envio, numero_rastreo) VALUES (?, ?, ?, ?, ?, ?, CURDATE(), ?)";


            try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, envia_id);
            statement.setInt(2, recibe_id);
            statement.setString(3, envio.getDireccionEnvia());
            statement.setString(4, envio.getOtrosEnvia());
            statement.setString(5, envio.getDireccionRecibe());
            statement.setString(6, envio.getOtrosRecibe());
            statement.setString(7, envio.getNumeroRastreo());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
