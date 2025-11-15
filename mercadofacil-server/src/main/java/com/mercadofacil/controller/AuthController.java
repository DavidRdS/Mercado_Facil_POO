package com.mercadofacil.controller;

import com.mercadofacil.model.Usuario;
import com.mercadofacil.repository.UsuarioRepository;
import com.mercadofacil.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepo;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioRepository usuarioRepo, JwtUtil jwtUtil) {
        this.usuarioRepo = usuarioRepo;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario u) {
        if (usuarioRepo.findByUsername(u.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Usuário já existe");
        }
        usuarioRepo.save(u);
        return ResponseEntity.ok(Map.of("msg", "registrado"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario dados) {
        return usuarioRepo.findByUsername(dados.getUsername())
                .filter(u -> u.getSenha().equals(dados.getSenha()))
                .map(u -> ResponseEntity.ok(Map.of("token", jwtUtil.gerarToken(u.getUsername()))))
                .orElse(ResponseEntity.status(401).body("Credenciais inválidas"));
    }
}
