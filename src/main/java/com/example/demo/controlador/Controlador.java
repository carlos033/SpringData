/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controlador;

import javax.validation.Valid;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.exceptions.ExcepcionServicio;
import com.example.demo.modelos.Cliente;
import com.example.demo.serviciosI.IServiciosCl;
import com.example.demo.util.Transformadores;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author ck
 */
@RestController
@RequestMapping(path = "/clientes")
public class Controlador {

    @Autowired
    private Transformadores transformador;
    @Autowired
    private IServiciosCl sCliente;

    @GetMapping("/todos")
    @ResponseBody
    public List<ClienteDTO> buscarTodos() {
        List<Cliente> list = new ArrayList<>();
        try {
            list = sCliente.buscarTodos();
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no hay clientes");
        }
        return list.stream().map((Cliente cliente) -> transformador.convertirADTOC(cliente)).collect(Collectors.toList());
    }

    @GetMapping("{nombre}/nombre")
    @ResponseBody
    public List<ClienteDTO> bustarXnombre(@PathVariable("nombre") String nombre) {
        List<Cliente> listaClientes = new ArrayList<>();
        try {
            listaClientes = sCliente.buscarXNombre(nombre);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        }
        return listaClientes.stream().map((Cliente cliente) -> transformador.convertirADTOC(cliente)).collect(Collectors.toList());
    }

    @GetMapping("/nombreCompleto")
    @ResponseBody
    public List<ClienteDTO> buscaXNombreCompleto(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido) {
        List<Cliente> listaClientes = new ArrayList<>();
        try {
            listaClientes = sCliente.buscarXnombreCompleto(nombre, apellido);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        }
        return listaClientes.stream().map((Cliente cliente) -> transformador.convertirADTOC(cliente)).collect(Collectors.toList());
    }

    @PostMapping("/Crear")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO aniadirCliente(@Valid @RequestBody ClienteDTO dTO) {
        Cliente cliente = transformador.convertirAEntidadC(dTO);
        sCliente.crearCliente(cliente);
        ClienteDTO resultado = transformador.convertirADTOC(cliente);
        return resultado;
    }
}
