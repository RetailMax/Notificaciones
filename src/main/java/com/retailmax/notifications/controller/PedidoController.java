package com.retailmax.notifications.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.retailmax.notifications.model.Pedido;
import com.retailmax.notifications.service.PedidoService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/pedido")
@CrossOrigin(origins = "*")
@Tag(name = "Pedidos", description = "API para gestión de pedidos")
@ControllerAdvice
public class PedidoController {

    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los pedidos", description = "Retorna una lista de todos los pedidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos encontrados",
                    content = @Content(schema = @Schema(implementation = Pedido.class))),
        @ApiResponse(responseCode = "204", description = "No hay pedidos")
    })
    public ResponseEntity<List<Pedido>> listar() {
        logger.info("Solicitud para listar todos los pedidos");
        List<Pedido> lista = pedidoService.findAll();
        if (lista.isEmpty()) {
            logger.info("No se encontraron pedidos");
            return ResponseEntity.noContent().build();
        }
        logger.info("Se retornaron {} pedidos", lista.size());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID", description = "Retorna un pedido específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado",
                    content = @Content(schema = @Schema(implementation = Pedido.class))),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<Pedido> obtenerPorId(
            @Parameter(description = "ID del pedido", required = true)
            @PathVariable Long id) {
        logger.info("Solicitud para obtener pedido con ID: {}", id);
        Optional<Pedido> pedido = pedidoService.findById(id);
        return pedido.map(ResponseEntity::ok)
                    .orElseGet(() -> {
                        logger.warn("Pedido no encontrado con ID: {}", id);
                        return ResponseEntity.notFound().build();
                    });
    }

    @PostMapping
    @Operation(summary = "Crear nuevo pedido", description = "Crea un nuevo pedido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido creado exitosamente",
                    content = @Content(schema = @Schema(implementation = Pedido.class))),
        @ApiResponse(responseCode = "400", description = "Datos del pedido inválidos")
    })
    public ResponseEntity<Pedido> enviar(
            @Parameter(description = "Datos del pedido", required = true)
            @Valid @RequestBody Pedido pedido) {
        logger.info("Solicitud para crear nuevo pedido: {}", pedido);
        Pedido savedPedido = pedidoService.save(pedido);
        logger.info("Pedido creado exitosamente con ID: {}", savedPedido.getPedidoId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPedido);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pedido", description = "Actualiza un pedido existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido actualizado exitosamente",
                    content = @Content(schema = @Schema(implementation = Pedido.class))),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos del pedido inválidos")
    })
    public ResponseEntity<Pedido> actualizar(
            @Parameter(description = "ID del pedido", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados del pedido", required = true)
            @Valid @RequestBody Pedido pedido) {
        logger.info("Solicitud para actualizar pedido con ID: {}", id);
        
        if (!pedidoService.existsById(id)) {
            logger.warn("No se puede actualizar pedido con ID: {} - no existe", id);
            return ResponseEntity.notFound().build();
        }
        
        pedido.setPedidoId(id);
        Pedido updatedPedido = pedidoService.save(pedido);
        logger.info("Pedido actualizado exitosamente con ID: {}", id);
        return ResponseEntity.ok(updatedPedido);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pedido eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del pedido", required = true)
            @PathVariable Long id) {
        logger.info("Solicitud para eliminar pedido con ID: {}", id);
        
        if (!pedidoService.existsById(id)) {
            logger.warn("No se puede eliminar pedido con ID: {} - no existe", id);
            return ResponseEntity.notFound().build();
        }
        
        pedidoService.deleteById(id);
        logger.info("Pedido eliminado exitosamente con ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos de entrada inválidos");
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato JSON inválido");
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
}