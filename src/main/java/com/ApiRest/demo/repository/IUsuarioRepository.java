package com.ApiRest.demo.repository;

import com.ApiRest.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUsuarioRepository extends JpaRepository <Usuario, Long> {

    List<Usuario> findByNickname(String nickname);

    List<Usuario> findByPassword(String password);

}
