package com.zup.blog_Post.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zup.blog_Post.Model.Usuario;
import com.zup.blog_Post.Model.Veiculos;

@Repository
public interface VeiculosRepository extends JpaRepository<Veiculos, Long> {

	public Optional<Veiculos> findById(long id);

	public Optional<List<Veiculos>> findByUsuario(Usuario usuario);

}
