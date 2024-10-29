package com.ApiRest.demo.service;

import com.ApiRest.demo.entity.Usuario;
import com.ApiRest.demo.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private IUsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(IUsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public void createUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void updateUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void getUsuario() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        usuarioRepository.findAll(sort);
    }

    public List<Usuario> getUsuarioByNickname(String nickname) {
       return usuarioRepository.findByNickname(nickname);
    }

    public List<Usuario> getUsuarioByPassword(String password) {
       return usuarioRepository.findByPassword(password);
    }

}
