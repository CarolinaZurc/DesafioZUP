package com.zup.blog_Post.Control;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zup.blog_Post.Model.Usuario;
import com.zup.blog_Post.Model.Veiculos;
import com.zup.blog_Post.Repository.UsuarioRepository;
import com.zup.blog_Post.Service.UsuarioService;

@RestController
@RequestMapping("/blogPost/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrarUsuario(@Valid @RequestBody Usuario usuario) {
		Optional<Usuario> usuarioCriado = usuarioService.cadastrarUsuario(usuario);

		if (!usuarioCriado.isEmpty()) {
			Usuario user = usuarioCriado.get();
			@SuppressWarnings("deprecation")
			Link selfLink = new Link("http://localhost:8080/blogPost/usuario/busca" + usuario.getCpf());
			user.add(selfLink);
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Usuario já existente, insira outro CPF ou E-mail.");
		}
	}

	@GetMapping("/busca/{cpf}")
	public ResponseEntity<?> getUsuario(@Valid @PathVariable long cpf) {
		Optional<Usuario> user = usuarioRepository.findByCpf(cpf);
		{
			if (user.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CPF não localizado.");
			} else {
				List<Veiculos> listaVeiculos = user.get().getMeusVeiculos();
				for (Veiculos veiculo : listaVeiculos) {
					@SuppressWarnings("deprecation")
					Link selfLink = new Link("http://localhost:8080/blogPost/veiculo/" + veiculo.getId());
					veiculo.add(selfLink);
				}

				@SuppressWarnings("deprecation")
				Link selfLink = new Link("http://localhost:8080/blogPost/usuario/busca/" + user.get().getCpf());
				user.get().add(selfLink);
				return ResponseEntity.status(200).body(usuarioRepository.findByCpf(cpf));
			}
		}
	}
}
