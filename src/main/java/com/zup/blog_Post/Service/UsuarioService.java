package com.zup.blog_Post.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zup.blog_Post.Model.Usuario;
import com.zup.blog_Post.Repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repositoryUsuario;

	public Optional<Usuario> cadastrarUsuario(Usuario usuario) {
		Optional<Usuario> cpfExistente = repositoryUsuario.findByCpf(usuario.getCpf());
		if (cpfExistente.isPresent()) {
			return Optional.empty();
		}

		Optional<Usuario> emailExistente = repositoryUsuario.findByEmail(usuario.getEmail());
		if (emailExistente.isPresent()) {
			return Optional.empty();
		}
		Optional<Usuario> usuarioCadastrado = Optional.ofNullable(repositoryUsuario.save(usuario));
		if (usuarioCadastrado.isPresent()) {
			return usuarioCadastrado;
		} else {
			return Optional.empty();
		}

	}
}