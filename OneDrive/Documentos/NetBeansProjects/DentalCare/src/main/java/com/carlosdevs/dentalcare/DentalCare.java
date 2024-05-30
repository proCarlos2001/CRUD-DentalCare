/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.carlosdevs.dentalcare;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DentalCare {
    
    // Variables.
    PreparedStatement ps = null;
    private ResultSet rs;

    // Se establece una variable para la conexión.
    private Connection conexion;

    public DentalCare() {
        
        // Variables donde se almacenan los datos necesarios para la conexión (Datos de Mysql).
        String usuario = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3308/dentalcare";
        
        // Condición para establecer la conexión y validar si es exitosa o no.
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");                              // Conector jdbc.
            this.conexion = DriverManager.getConnection(url, usuario, password);    // Se le pasan las variables al DriverManager para la conexión.
            System.out.println("Conexion exitosa");                                 // Mensaje de exito si se establece la conexión.
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DentalCare.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("Error de conexion: " + ex.getMessage());            // Mensaje de error si no se establece la conexión.
        }
    }

    // Método para el registro de un nuevo usuario.
    public int RegistroUsuario(String nombreusuario, String email, String telefono, String contraseña, String genero) {
        
        // Variable.
        int respuesta = 0;
        
        // Condición para agregar los datos del usuario a la tabla (registrousuario) de la base de datos (dentalCare) en Mysql.
        try {
            
            // Se reestablece el valor del auto-incremento para el ID.
            ps = conexion.prepareStatement("ALTER TABLE REGISTROUSUARIO AUTO_INCREMENT = 1");
            ps.executeUpdate();
            ps.close();
            
            // Se registra un nuevo usuario a la base de datos.
            ps = conexion.prepareStatement("INSERT INTO REGISTROUSUARIO(NOMBREUSUARIO, EMAIL, TELEFONO, CONTRASEÑA, GENERO) VALUES(?,?,?,?,?)");
            ps.setString(1, nombreusuario);
            ps.setString(2, email);
            ps.setString(3, telefono);
            ps.setString(4, contraseña);
            ps.setString(5, genero);
            respuesta = ps.executeUpdate();
            System.out.println("Usuario registrado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al registrar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return respuesta;
    }
    
    // Método para modificar un usuario registrado.
    public int ModificarUsuario(String nombreusuario, String email, String telefono, String contraseña, String genero, String idregistro) {
        
        // Variable.
        int respuesta = 0;
        
        try {
            
            // Se modifica un usuario registrado en la base de datos.
            ps = conexion.prepareStatement("UPDATE REGISTROUSUARIO SET NOMBREUSUARIO = ?, EMAIL = ?, TELEFONO = ?, CONTRASEÑA = ?, GENERO = ? WHERE IDREGISTRO = ?");
            ps.setString(1, nombreusuario);
            ps.setString(2, email);
            ps.setString(3, telefono);
            ps.setString(4, contraseña);
            ps.setString(5, genero);
            ps.setString(6, idregistro);
            respuesta = ps.executeUpdate();
            System.out.println("Usuario modificado correctamente");  
        } catch (SQLException e) {
            System.out.println("Error al modificar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return respuesta;
    }
    
    // Método para eliminar un usuario registrado.
    public int EliminarUsuario(String idregistro) {
        
        // Variable.
        int respuesta = 0;
        
        try {
            
            // Se elimina un usuario registrado en la base de datos.
            ps = conexion.prepareStatement("DELETE FROM REGISTROUSUARIO WHERE IDREGISTRO = ?");
            ps.setString(1, idregistro);
            respuesta = ps.executeUpdate();
            System.out.println("Usuario eleiminado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al eliminar al usuario");
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return respuesta;
    }
    
    // Se listan o traen los usuarios registrados en la base de datos para poder visualizarlos en la interfaz. 
    public ArrayList <ListarRegistroUsuario> ListarUsuarios() {
        ArrayList <ListarRegistroUsuario> respuesta = new ArrayList<>();
        try {
            
            // Se selecciona la tabla registrousuario a listar.
            ps = conexion.prepareStatement("SELECT * FROM REGISTROUSUARIO");
            rs =ps.executeQuery();
            
            // Condición que válida los campos que van a ser listados.
            while (rs.next()) {
                ListarRegistroUsuario usuario = new ListarRegistroUsuario();
                usuario.setIdRegistro(rs.getString("idRegistro"));
                usuario.setNombreUsuario(rs.getString("NombreUsuario"));
                usuario.setEmail(rs.getString("Email"));
                usuario.setTelefono(rs.getString("Telefono"));
                usuario.setContraseña(rs.getString("Contraseña"));
                usuario.setGenero(rs.getString("Genero"));
                respuesta.add(usuario); 
            }
            
        } catch (SQLException e) {
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return respuesta;
    }
}
