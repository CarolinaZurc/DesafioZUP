package com.zup.blog_Post.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zup.blog_Post.Model.Usuario;
import com.zup.blog_Post.Model.Veiculos;
import com.zup.blog_Post.Repository.UsuarioRepository;
import com.zup.blog_Post.Repository.VeiculosRepository;

@Service
public class VeiculoService {

	@Autowired
	private VeiculosRepository repositoryVeiculos;

	@Autowired
	private UsuarioRepository usuariorepository;

	public Optional<Veiculos> cadastrarVeiculo(Veiculos veiculos) {

		String ano = veiculos.getAno();

		String ultimaLetra = ano.substring(ano.length() - 1);
		char[] ultimoChar = ultimaLetra.toCharArray();
		char ultimoNumero = ultimoChar[ultimoChar.length - 1];

		if (ultimoNumero == '1' || ultimoNumero == '0') {
			veiculos.setDiaRodizio("Segunda-Feira");
		} else if (ultimoNumero == '2' || ultimoNumero == '3') {
			veiculos.setDiaRodizio("Terça-Feira");
		} else if (ultimoNumero == '4' || ultimoNumero == '5') {
			veiculos.setDiaRodizio("Quarta-Feira");
		} else if (ultimoNumero == '6' || ultimoNumero == '7') {
			veiculos.setDiaRodizio("Quinta-Feira");
		} else if (ultimoNumero == '8' || ultimoNumero == '9') {
			veiculos.setDiaRodizio("Sexta-Feira");
		}

		Usuario usuario = veiculos.getUsuario();
		usuario.getMeusVeiculos().add(veiculos);
		usuariorepository.save(usuario);
		return Optional.ofNullable(repositoryVeiculos.save(veiculos));

	}

	public void rodizioAtivo(long id) {

		Date dataAtual = new Date();
		Calendar calendario = new GregorianCalendar();
		calendario.setTime(dataAtual);
		String diaSemana = "data";
		int dia = calendario.get(calendario.DAY_OF_WEEK);
		switch (dia) {
		case Calendar.SUNDAY:
			diaSemana = "Domingo";
			break;
		case Calendar.MONDAY:
			diaSemana = "Segunda-Feira";
			break;
		case Calendar.TUESDAY:
			diaSemana = "Terça-Feira";
			break;
		case Calendar.WEDNESDAY:
			diaSemana = "Quarta-Feira";
			break;
		case Calendar.THURSDAY:
			diaSemana = "Quinta-Feira";
			break;
		case Calendar.FRIDAY:
			diaSemana = "Sexta-Feira";
			break;
		case Calendar.SATURDAY:
			diaSemana = "Sábado";
			break;
		}

		Optional<Usuario> usuario = usuariorepository.findById(id);
		List<Veiculos> meusVeiculos = usuario.get().getMeusVeiculos();

		for (Veiculos veiculo : meusVeiculos) {
			System.out.println(veiculo.getDiaRodizio());
			if (veiculo.getDiaRodizio().matches(diaSemana)) {
				veiculo.setRodizioAtivo(true);
				repositoryVeiculos.save(veiculo);
			} else {
				veiculo.setRodizioAtivo(false);
				repositoryVeiculos.save(veiculo);
				System.out.println(diaSemana);
				System.out.println(veiculo.getDiaRodizio());
			}
		}
	}
}