package com.ApiRest.demo.controller;

import com.ApiRest.demo.entity.Producto;
import com.ApiRest.demo.entity.Usuario;
import com.ApiRest.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService=usuarioService;
    }

    @GetMapping(value = "loggin", headers = "Accept=application/json")
    public ResponseEntity<?> logginUsuario(
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String password
    ) {
        if (nickname != null && password != null) {
            List<Usuario> usuarios = usuarioService.getUsuarioByNickname(nickname);
            if (!usuarios.isEmpty()) {
                for (Usuario usuario : usuarios) {
                    if (usuario.getPassword().equals(password)) {
                        return ResponseEntity.ok(Collections.singletonList(usuario));
                    }
                }
            }
            return ResponseEntity.badRequest().body("Usuario o contraseña incorrecta, ingrese sus datos nuevamente");
        } else {
            return ResponseEntity.badRequest().body("Usuario o contraseña incorrecta, ingrese sus datos nuevamente");
        }
    }


    @PostMapping(value = "/crear", headers = "Accept=application/json")
    public ResponseEntity<String> crearUsuario(@RequestBody Usuario usuario) {
        List<Usuario> nicknameUsuarioExistente = usuarioService.getUsuarioByNickname(usuario.getNickname());
        if (nicknameUsuarioExistente.isEmpty()) {
            usuarioService.createUsuario(usuario);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario a registrar ya existe");
        }
    }

    @GetMapping(value = "listar", headers = "Accept=application/json")
    public void getUsuarios(){
        usuarioService.getUsuario();
    }

    @PutMapping(value = "actualizar", headers = "Accept=application/json")
    public void actualizarUsuario(@RequestBody Usuario usuario){
        usuarioService.updateUsuario(usuario);
    }

}
