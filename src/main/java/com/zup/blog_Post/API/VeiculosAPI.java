package com.zup.blog_Post.API;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.zup.blog_Post.Model.Veiculos;

@FeignClient(url = "https://parallelum.com.br/fipe/api/v1", name = "tabelaFIPE")
public interface VeiculosAPI {

	@GetMapping("/carros/marcas")
	Veiculos getMarcas();

	@GetMapping("/carros/marcas/n/modelos") //o n deve ser substituido pelo codigo desejado e encontrado no "getMarcas"
	Veiculos getModelos(@PathVariable Integer marcaID);

	@GetMapping("/carros/marcas/n/modelos/nn/anos") //o nn deve ser substituido pelo codigo desejado e encontrado no "getModelos"
	Veiculos getAnos(@PathVariable Integer marcaID, @PathVariable Integer modeloID);

	@GetMapping("/carros/marcas/n/modelos/nn/anos/nnn") //o nnn deve ser substituido pelo codigo desejado e encontrado no "getAnos"
	Veiculos getVeiculo(@PathVariable Integer marcaID, @PathVariable Integer modeloID, @PathVariable String anoID);

}
