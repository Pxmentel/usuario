package com.pxmentel.usuario.business;

import com.pxmentel.usuario.business.converter.UsuarioConverter;
import com.pxmentel.usuario.business.dto.UsuarioDTO;
import com.pxmentel.usuario.infrastructure.entity.Usuario;
import com.pxmentel.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

  private final UsuarioRepository usuarioRepository;
  private final UsuarioConverter usuarioConverter;

  public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO){
    Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
    return usuarioConverter.paraUsuarioDTO(
        usuarioRepository.save(usuario)
    );
  }

}
