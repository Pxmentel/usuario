package com.pxmentel.usuario.business;

import com.pxmentel.usuario.business.converter.UsuarioConverter;
import com.pxmentel.usuario.business.dto.UsuarioDTO;
import com.pxmentel.usuario.infrastructure.entity.Usuario;
import com.pxmentel.usuario.infrastructure.exceptions.ConflictException;
import com.pxmentel.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.pxmentel.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

  private final UsuarioRepository usuarioRepository;
  private final UsuarioConverter usuarioConverter;
  private final PasswordEncoder passwordEncoder;

  public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO){
    emailExistente(usuarioDTO.getEmail());
    usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

    Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
    return usuarioConverter.paraUsuarioDTO(
        usuarioRepository.save(usuario)
    );
  }

  public void emailExistente (String email) {
    try {
      boolean existe = verificaEmailExistente(email);
      if (existe) {
        throw new RuntimeException("Email já cadastrado " + email);
      }
    } catch (ConflictException e) {
      throw new ConflictException("Email já cadastrado " + e.getCause());
    }
  }

  public boolean verificaEmailExistente (String email) {return usuarioRepository.existsByEmail(email);}

  public Usuario buscarUsuarioPorEmail (String email) {
    return usuarioRepository.findByEmail(email).orElseThrow(
        () -> new ResourceNotFoundException("Email não encontrado" + email));
  }

  public void deletaUsuarioPorEmail (String email) {
    usuarioRepository.deleteByEmail(email);
  }

}
