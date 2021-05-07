/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.repositorio;

import com.example.demo.modelos.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ck
 */
@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {

    public List<Cliente> findByNombre(@Param("nombre") String nombre);

    public List<Cliente> findByNombreAndApellido(@Param("nombre") String nombre, @Param("apellido") String apellido);
}
