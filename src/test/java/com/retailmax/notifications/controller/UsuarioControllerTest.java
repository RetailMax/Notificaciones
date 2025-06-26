package com.retailmax.notifications.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retailmax.notifications.model.Usuario;
import com.retailmax.notifications.service.UsuarioService;
import com.retailmax.notifications.assemblers.UsuarioModelAssemblers;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.hateoas.EntityModel;


@WebMvcTest(UsuarioController.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Pruebas del Controlador de Usuarios")
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioModelAssemblers assembler;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuario;
    private Usuario usuario2;
    private List<Usuario> usuarios;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNroId("USR-001");
        usuario.setNombre("Juan Pérez");
        usuario.setCorreoElectronico("juan.perez@email.com");
        usuario.setEstado("ACTIVO");

        usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNroId("USR-002");
        usuario2.setNombre("Ana Gómez");
        usuario2.setCorreoElectronico("ana.gomez@email.com");
        usuario2.setEstado("INACTIVO");

        usuarios = Arrays.asList(usuario, usuario2);
    }

    @Test
    @DisplayName("Debería listar todos los usuarios exitosamente")
    void testListarUsuarios() throws Exception {
        when(usuarioService.findAll()).thenReturn(usuarios);
        when(assembler.toModel(any(Usuario.class))).thenAnswer(invocation -> EntityModel.of(invocation.getArgument(0)));
        when(assembler.toModelForCreation(any(Usuario.class))).thenAnswer(invocation -> EntityModel.of(invocation.getArgument(0)));
        when(assembler.toModelForUpdate(any(Usuario.class))).thenAnswer(invocation -> EntityModel.of(invocation.getArgument(0)));

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$._embedded.usuarioList").isArray())
                .andExpect(jsonPath("$._embedded.usuarioList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.usuarioList[0].nroId").value("USR-001"));
        verify(usuarioService, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería retornar 204 cuando no hay usuarios")
    void testListarUsuariosVacio() throws Exception {
        when(usuarioService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isNoContent());
        verify(usuarioService, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener un usuario por ID exitosamente")
    void testObtenerUsuarioPorId() throws Exception {
        when(usuarioService.findById(1L)).thenReturn(usuario);
        when(assembler.toModel(any(Usuario.class))).thenReturn(EntityModel.of(usuario));
        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nroId").value("USR-001"));
        verify(usuarioService, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería retornar 404 cuando el usuario no existe")
    void testObtenerUsuarioPorIdNoExiste() throws Exception {
        when(usuarioService.findById(999L)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/api/v1/usuarios/999"))
                .andExpect(status().isNotFound());
        verify(usuarioService, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Debería crear un nuevo usuario exitosamente")
    void testCrearUsuario() throws Exception {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNroId("USR-003");
        nuevoUsuario.setNombre("Carlos Ruiz");
        nuevoUsuario.setCorreoElectronico("carlos.ruiz@email.com");
        nuevoUsuario.setEstado("ACTIVO");

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(3L);
        usuarioGuardado.setNroId("USR-003");
        usuarioGuardado.setNombre("Carlos Ruiz");
        usuarioGuardado.setCorreoElectronico("carlos.ruiz@email.com");
        usuarioGuardado.setEstado("ACTIVO");

        when(usuarioService.save(any(Usuario.class))).thenReturn(usuarioGuardado);
        when(assembler.toModelForCreation(any(Usuario.class))).thenReturn(EntityModel.of(usuarioGuardado));

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoUsuario)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.nroId").value("USR-003"));
        verify(usuarioService, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debería crear un nuevo usuario duplicado y retornar 400")
    void testCrearUsuarioDuplicado() throws Exception {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNroId("USR-001");
        nuevoUsuario.setNombre("Juan Pérez");
        nuevoUsuario.setCorreoElectronico("juan.perez@email.com");
        nuevoUsuario.setEstado("ACTIVO");

        when(usuarioService.save(any(Usuario.class))).thenThrow(new DuplicateKeyException("Duplicado"));
        when(assembler.toModelForCreation(any(Usuario.class))).thenReturn(EntityModel.of(nuevoUsuario));
        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoUsuario)))
                .andExpect(status().isBadRequest());
        verify(usuarioService, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debería actualizar un usuario existente exitosamente")
    void testActualizarUsuario() throws Exception {
        Usuario actualizado = new Usuario();
        actualizado.setId(1L);
        actualizado.setNroId("USR-001");
        actualizado.setNombre("Juan Pérez Modificado");
        actualizado.setCorreoElectronico("juan.perez@email.com");
        actualizado.setEstado("INACTIVO");

        when(usuarioService.update(eq(1L), any(Usuario.class))).thenReturn(actualizado);
        when(assembler.toModelForUpdate(any(Usuario.class))).thenReturn(EntityModel.of(actualizado));

        mockMvc.perform(put("/api/v1/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez Modificado"))
                .andExpect(jsonPath("$.estado").value("INACTIVO"));
        verify(usuarioService, times(1)).update(eq(1L), any(Usuario.class));
    }

    @Test
    @DisplayName("Debería retornar 404 al actualizar un usuario que no existe")
    void testActualizarUsuarioNoExiste() throws Exception {
        Usuario actualizado = new Usuario();
        actualizado.setNroId("USR-999");
        actualizado.setNombre("No existe");
        actualizado.setCorreoElectronico("no@existe.com");
        actualizado.setEstado("ACTIVO");

        when(usuarioService.update(eq(999L), any(Usuario.class))).thenThrow(new EntityNotFoundException());
        mockMvc.perform(put("/api/v1/usuarios/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isNotFound());
        verify(usuarioService, times(1)).update(eq(999L), any(Usuario.class));
    }

    @Test
    @DisplayName("Debería eliminar un usuario existente exitosamente")
    void testEliminarUsuario() throws Exception {
        doNothing().when(usuarioService).deleteById(1L);
        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());
        verify(usuarioService, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debería retornar 404 al eliminar un usuario que no existe")
    void testEliminarUsuarioNoExiste() throws Exception {
        doThrow(new EntityNotFoundException()).when(usuarioService).deleteById(999L);
        mockMvc.perform(delete("/api/v1/usuarios/999"))
                .andExpect(status().isNotFound());
        verify(usuarioService, times(1)).deleteById(999L);
    }

    @Test
    @DisplayName("Debería cambiar el estado de un usuario exitosamente")
    void testCambiarEstadoUsuario() throws Exception {
        Usuario actualizado = new Usuario();
        actualizado.setId(1L);
        actualizado.setNroId("USR-001");
        actualizado.setNombre("Juan Pérez");
        actualizado.setCorreoElectronico("juan.perez@email.com");
        actualizado.setEstado("INACTIVO");

        when(usuarioService.cambiarEstado(eq(1L), eq("INACTIVO"))).thenReturn(actualizado);
        when(assembler.toModelForUpdate(any(Usuario.class))).thenReturn(EntityModel.of(actualizado));

        mockMvc.perform(patch("/api/v1/usuarios/1/estado?estado=INACTIVO"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.estado").value("INACTIVO"));
        verify(usuarioService, times(1)).cambiarEstado(eq(1L), eq("INACTIVO"));
    }

    @Test
    @DisplayName("Debería retornar 404 al cambiar estado de usuario que no existe")
    void testCambiarEstadoUsuarioNoExiste() throws Exception {
        when(usuarioService.cambiarEstado(eq(999L), eq("INACTIVO"))).thenThrow(new EntityNotFoundException());
        mockMvc.perform(patch("/api/v1/usuarios/999/estado?estado=INACTIVO"))
                .andExpect(status().isNotFound());
        verify(usuarioService, times(1)).cambiarEstado(eq(999L), eq("INACTIVO"));
    }

    @Test
    @DisplayName("Debería validar datos requeridos al crear usuario")
    void testCrearUsuarioDatosInvalidos() throws Exception {
        Usuario invalido = new Usuario();
        invalido.setNroId(""); // Inválido
        invalido.setNombre(""); // Inválido
        invalido.setCorreoElectronico("correo-no-valido"); // Inválido
        invalido.setEstado("");

        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debería validar datos requeridos al actualizar usuario")
    void testActualizarUsuarioDatosInvalidos() throws Exception {
        Usuario invalido = new Usuario();
        invalido.setNombre("");
        invalido.setCorreoElectronico("correo-no-valido");
        invalido.setEstado("");

        mockMvc.perform(put("/api/v1/usuarios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debería manejar errores internos del servidor")
    void testErrorInternoServidor() throws Exception {
        when(usuarioService.findById(1L)).thenThrow(new RuntimeException("Error interno"));
        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Debería validar formato de JSON inválido")
    void testJsonInvalido() throws Exception {
        String jsonInvalido = "{ 'nroId': 'USR-004', 'nombre': 'Nombre', 'correoElectronico': 'correo@email.com' ";
        mockMvc.perform(post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInvalido))
                .andExpect(status().isBadRequest());
    }
}