package com.techbank.account.cmd.api.controllers;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techbank.account.cmd.api.commands.RestoreReadDbCommand;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;

@RestController
@RequestMapping(path = "/api/v1/restoreReadDb")
public class RestoreReadDbController {
	
	private final Logger logger = Logger.getLogger(RestoreReadDbController.class.getName());
	
	@Autowired
	private CommandDispatcher commandDispatcher;
	
	@PostMapping
	public ResponseEntity<BaseResponse> restoreReadDb(){
		
		try {
			commandDispatcher.send(new RestoreReadDbCommand());
			return new ResponseEntity<>(new BaseResponse("Read DB restored successfully."), HttpStatus.OK);
		}catch(IllegalStateException e) {
			logger.log(Level.WARNING,MessageFormat.format("Client made a bade request {0}.", e.toString()));
			return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			e.printStackTrace();
			var safeErrorMessage = "Erro while proccessing read database ";
			return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
