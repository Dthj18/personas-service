package com.imprenta.personas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.imprenta.personas.exception.*;
import com.imprenta.personas.model.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.imprenta.personas.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID" + id + " no encontrado"));

    }

    public Usuario save(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new BadRequestException("El email ya está registrado");
        }
        String passwordPlano = usuario.getPassword();
        String passwordHasheado = passwordEncoder.encode(passwordPlano);
        usuario.setPassword(passwordHasheado);

        return usuarioRepository.save(usuario);
    }

    public Usuario update(Long id, Usuario datosActualizados) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no encontrado"));

        if (!usuarioExistente.getEmail().equals(datosActualizados.getEmail()) &&
                usuarioRepository.existsByEmail(datosActualizados.getEmail())) {
            throw new BadRequestException("El email ya está en uso por otro usuario");
        }

        usuarioExistente.setNombre(datosActualizados.getNombre());
        usuarioExistente.setEmail(datosActualizados.getEmail());

        String passwordNuevo = datosActualizados.getPassword();
        if (passwordNuevo != null && !passwordNuevo.isEmpty()) {
            usuarioExistente.setPassword(passwordEncoder.encode(passwordNuevo));

        }
        usuarioExistente.setRol(datosActualizados.getRol());

        return usuarioRepository.save(usuarioExistente);
    }

    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar, usuario con ID " + id + " no existe");
        }
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
