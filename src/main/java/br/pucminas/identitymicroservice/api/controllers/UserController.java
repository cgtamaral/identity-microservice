package br.pucminas.identitymicroservice.api.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.pucminas.identitymicroservice.api.Response;
import br.pucminas.identitymicroservice.api.dtos.UserDTO;
import br.pucminas.identitymicroservice.api.entities.User;
import br.pucminas.identitymicroservice.api.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/public")
@CrossOrigin(origins = "*")

@Api(value = "users", description = "Recurso para gerenciamentto de usuários.", tags={ "users"})
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "Consulta todos os usuários existentes.", nickname = "findAllUsers", notes = "", tags={ "users"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operação bem sucessida!"),
		    @ApiResponse(code = 404, message = "Não foi encontrado nenhum usuário")})
	@GetMapping(value = "/users", produces = "application/json")
	public ResponseEntity<Response<List<UserDTO>>> findAllUsers()
	{
		Response<List<UserDTO>> response = new Response<List<UserDTO>>();
		
		log.info("Consultando usuários existentes na base de dados!");
		List<User> users = userService.findAll();

		if (users.isEmpty()) {
			log.info("Nenhum usuário foi encontrado na base de dados!");
			response.getErrors().add("Nenhum usuário foi encontrado na base de dados!");
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		response.setData(User.convertToDTO(users));
		
		return ResponseEntity.ok(response);
	}
	
	@ApiOperation(value = "Consulta dados de um usuário especifico", nickname = "findUserById", notes = "", tags={ "users"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Operação bem sucessida!"),
		    @ApiResponse(code = 404, message = "Não foi encontrado nenhum usuário")})
	@GetMapping(value = "/users/{userId}", produces = "application/json")
	public ResponseEntity<Response<UserDTO>> findUserById(@ApiParam(value = "Identificador do usuário a ser consultado", required = true) @PathVariable("userId") Long userId)
	{
		Response<UserDTO> response = new Response<UserDTO>();
		
		log.info("Consultando usuário na base de dados: {} " +userId);
		Optional<User> user = userService.findById(userId);
		

		if (!user.isPresent()) {
			log.info("Nenhum usuário foi encontrado para o userId: {}" + userId);
			response.getErrors().add("Nenhum usuário foi encontrado para o userId" + userId);
			
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(User.convertToDTO(user.get()));
			
		return ResponseEntity.ok(response);
	}
}
