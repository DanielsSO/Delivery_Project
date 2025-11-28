package org.example.Controller;

import org.example.Models.GoogleUser;
import org.example.Models.usuarioModel;
import org.example.utils.JDBCUtil;

import java.sql.*;

public class usuarioController {
    public boolean crear(usuarioModel usuario) {
        String verificar = "SELECT id, tipo FROM Usuarios WHERE email = ?";
        String insertar = "INSERT INTO Usuarios (nombre, apellido, pasword, email, tipo) VALUES (?, ?, ?, ?, ?)";
        String actualizar = "UPDATE Usuarios SET nombre = ?, apellido = ?, pasword = ?, tipo = 'USUARIO' WHERE id = ?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement stmtVerificar = connection.prepareStatement(verificar)) {

            stmtVerificar.setString(1, usuario.getEmail());
            ResultSet rs = stmtVerificar.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String tipoExistente = rs.getString("tipo");

                if ("CLIENTE".equals(tipoExistente)) {
                    // Actualiza a USUARIO
                    try (PreparedStatement stmtActualizar = connection.prepareStatement(actualizar)) {
                        stmtActualizar.setString(1, usuario.getNombre());
                        stmtActualizar.setString(2, usuario.getApellido());
                        stmtActualizar.setString(3, usuario.getPassword());
                        stmtActualizar.setInt(4, id);
                        stmtActualizar.executeUpdate();
                        System.out.println("Cliente actualizado a USUARIO.");
                        return true;
                    }
                } else {
                    System.out.println("El email ya está registrado como USUARIO.");
                    return false;
                }

            } else {
                try (PreparedStatement stmtInsertar = connection.prepareStatement(insertar)) {
                    stmtInsertar.setString(1, usuario.getNombre());
                    stmtInsertar.setString(2, usuario.getApellido());
                    stmtInsertar.setString(3, usuario.getPassword());
                    stmtInsertar.setString(4, usuario.getEmail());
                    stmtInsertar.setString(5, "USUARIO");
                    stmtInsertar.executeUpdate();
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public usuarioModel login(String email, String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            return null;
        }

        String sqlBuscar = "SELECT id, email, pasword, tipo FROM Usuarios WHERE email = ?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sqlBuscar)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String storedPassword = rs.getString("pasword");
                String tipo = rs.getString("tipo");

                if ("CLIENTE".equals(tipo) && (storedPassword == null || storedPassword.isEmpty())) {
                    String actualizar = "UPDATE Usuarios SET pasword = ?, tipo = 'USUARIO' WHERE id = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(actualizar)) {
                        updateStmt.setString(1, password);
                        updateStmt.setInt(2, id);
                        updateStmt.executeUpdate();
                    }

                    usuarioModel usuario = new usuarioModel();
                    usuario.setEmail(email);
                    usuario.setPassword(password);
                    usuario.setTipo("USUARIO");
                    return usuario;
                }

                if ("USUARIO".equals(tipo) && storedPassword != null && storedPassword.equals(password)) {
                    usuarioModel usuario = new usuarioModel();
                    usuario.setEmail(email);
                    usuario.setPassword(password);
                    usuario.setTipo(tipo);
                    return usuario;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int buscaroCrearCliente(String nombre, String apellido, String email, String telefono, String password) {
        String buscar = "SELECT Id FROM Usuarios WHERE email = ?";
        String crear = "INSERT INTO Usuarios (nombre, apellido, pasword, email, telefono, tipo) VALUES (?, ?, ?, ?, ?, 'CLIENTE')";

        try (Connection connection = JDBCUtil.getConnection()) {
            PreparedStatement statementBuscar = connection.prepareStatement(buscar);
            statementBuscar.setString(1, email);
            ResultSet resultSet = statementBuscar.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("Id");
            }

            PreparedStatement statementCrear = connection.prepareStatement(crear, Statement.RETURN_GENERATED_KEYS);
            statementCrear.setString(1, nombre);
            statementCrear.setString(2, (apellido != null && !apellido.isEmpty()) ? apellido : "N/A");
            statementCrear.setString(3, password != null ? password : "");
            statementCrear.setString(4, email);
            statementCrear.setString(5, telefono);

            statementCrear.executeUpdate();

            ResultSet key = statementCrear.getGeneratedKeys();
            if (key.next()) {
                return key.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public usuarioModel loginConGoogle(GoogleUser gUser) {
        if (gUser == null || gUser.getEmail() == null || gUser.getEmail().isEmpty()) {
            return null;
        }

        String buscar = "SELECT Id, nombre, apellido, email, tipo FROM Usuarios WHERE email = ?";
        String crear = "INSERT INTO Usuarios (nombre, apellido, pasword, email, tipo) VALUES (?, ?, '', ?, 'USUARIO')";

        try (Connection connection = JDBCUtil.getConnection()) {

            PreparedStatement stmtBuscar = connection.prepareStatement(buscar);
            stmtBuscar.setString(1, gUser.getEmail());
            ResultSet rs = stmtBuscar.executeQuery();

            if (rs.next()) {
                usuarioModel usuario = new usuarioModel();
                usuario.setId(rs.getInt("Id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTipo(rs.getString("tipo"));
                return usuario;
            }

            // No existe -> crear
            PreparedStatement stmtCrear = connection.prepareStatement(crear, Statement.RETURN_GENERATED_KEYS);
            String nombre = gUser.getName() != null ? gUser.getName() : "GoogleUser";
            String apellido = "";
            stmtCrear.setString(1, nombre);
            stmtCrear.setString(2, apellido);
            stmtCrear.setString(3, gUser.getEmail());

            stmtCrear.executeUpdate();

            ResultSet key = stmtCrear.getGeneratedKeys();
            if (key.next()) {
                usuarioModel usuario = new usuarioModel();
                usuario.setId(key.getInt(1));
                usuario.setNombre(nombre);
                usuario.setApellido(apellido);
                usuario.setEmail(gUser.getEmail());
                usuario.setTipo("USUARIO");
                return usuario;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
