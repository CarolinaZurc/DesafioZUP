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
import com.zup.blog_Post.Repository.VeiculosRepository;
import com.zup.blog_Post.Service.VeiculoService;

@RestController
@RequestMapping("/blogPost/veiculo")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VeiculoController {

	@Autowired
	private VeiculoService service;

	@Autowired
	private VeiculosRepository veiculoRepository;

	@Autowired
	private UsuarioRepository usuariorepository;

	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrarVeiculo(@Valid @RequestBody Veiculos veiculos) {

		Optional<Veiculos> veiculoCriado = service.cadastrarVeiculo(veiculos);

		if (!veiculoCriado.isEmpty()) {
			Veiculos veiculo = veiculoCriado.get();
			@SuppressWarnings("deprecation")
			Link selfLink = new Link("http://localhost:8080/blogPost/veiculo/" + veiculo.getId());
			veiculo.add(selfLink);

			return ResponseEntity.status(HttpStatus.CREATED).body(veiculo);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Não foi possível cadastrar o veiculo, verifique as informações.");
		}
	}

	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> getVeiculoByUsuario(@Valid @PathVariable long id) {
		Optional<Usuario> usuario = usuariorepository.findById(id);
		service.rodizioAtivo(id);
		Optional<List<Veiculos>> veiculo = veiculoRepository.findByUsuario(usuario.get());

		if (veiculo.isPresent()) {
			@SuppressWarnings("deprecation")
			Link selfLink = new Link("http://localhost:8080/blogPost/usuario/busca/" + usuario.get().getCpf());
			usuario.get().add(selfLink);

			for (Veiculos veiculos : veiculo.get()) {
				@SuppressWarnings("deprecation")
				Link linkVeiculos = new Link("http://localhost:8080/blogPost/veiculo/" + veiculos.getId());
				veiculos.add(linkVeiculos);
			}
			return ResponseEntity.status(200).body(veiculo);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Veiculo não encontrado no sistema.");
		}
	}

	@GetMapping
	public List<Veiculos> getAll() {
		return veiculoRepository.findAll();
	}
}
