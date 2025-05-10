package com.ben3li.stockapi.conrtoladores;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ben3li.stockapi.dto.UbicacionDTO;
import com.ben3li.stockapi.entidades.Ubicacion;
import com.ben3li.stockapi.entidades.UsuarioUbicacion.Rol;
import com.ben3li.stockapi.mappers.UbicacionMapper;
import com.ben3li.stockapi.servicios.UbicacionService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/stockapi")
@RequiredArgsConstructor
public class GestorStockController {
    private final UbicacionService ubicacionService;
    private final UbicacionMapper ubicacionMapper;

    @PostMapping("/ubicaciones")
    public UbicacionDTO crearUbicacion(@RequestBody UbicacionDTO ubicacionDTO, HttpServletRequest request) {
        Ubicacion ubicacion = ubicacionService.crearUbicacion(ubicacionDTO, (UUID) request.getAttribute("userId"));
        return ubicacionMapper.toDto(ubicacion);
    }
    
    @GetMapping("/ubicaciones")
    public ResponseEntity<List<UbicacionDTO>> listarUbicaciones(
        HttpServletRequest request
    ){
        List<UbicacionDTO> ubicaciones= ubicacionService.listarUbicaciones((UUID)request.getAttribute("userId"));
        //List<UbicacionDTO> ubicaciones= ubicacionService.listarUbicaciones(UUID.fromString("f411d521-61fc-4b74-8819-da56c3c157ce"));
       return ResponseEntity.ok(ubicaciones);
    }

    @PutMapping("/ubicaciones/{ubicacionId}/usuarios/{usuarioId}/rol/{rol}")
    public UbicacionDTO insertarUsuario(
        @PathVariable UUID ubicacionId,
        @PathVariable UUID usuarioId,
        @PathVariable Rol rol
    ) {
        return ubicacionService.anhadirUsuarioAUbicacion(ubicacionId, usuarioId, rol);
    }

    @PutMapping("/ubicaciones/{ubicacionId}/usuarios/{usuarioId}")
    public UbicacionDTO quitarUsuario(
        @PathVariable UUID ubicacionId,
        @PathVariable UUID usuarioId
    ) {
        return ubicacionService.quitarUsuarioDeUbicacion(ubicacionId, usuarioId);
    }

    @DeleteMapping("/ubicaciones/{ubicacionId}")
    public ResponseEntity<Void> eliminarUbicacion(@PathVariable UUID ubicacionId, HttpServletRequest request) {
        ubicacionService.eliminarUbicacion(ubicacionId, (UUID) request.getAttribute("userId"));
        return ResponseEntity.noContent().build();
    }

}
