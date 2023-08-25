package com.techbank.account.cmd.api.controllers;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techbank.account.cmd.api.commands.DepositFundsCommand;
import com.techbank.account.cmd.api.dto.OpenAccountResponse;
import com.techbank.account.common.dto.BaseResponse;
import com.techbank.cqrs.core.exception.AggregateNotFoundException;
import com.techbank.cqrs.core.infrastructure.CommandDispatcher;

@RestController
@RequestMapping(path = "/api/v1/depositFunds")
public class DepositFundsController {
	
	private final Logger logger = Logger.getLogger(DepositFundsController.class.getName());
	
	@Autowired
	private CommandDispatcher commandDispatcher;
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<BaseResponse> depositFunds( @PathVariable String id,@RequestBody DepositFundsCommand command){
		
		try {
			command.setId(id);
			commandDispatcher.send(command);
			return new ResponseEntity<>(new BaseResponse("Deposit funds request compled successfully."), HttpStatus.OK);

		}catch(IllegalStateException | AggregateNotFoundException e) {
			logger.log(Level.WARNING,MessageFormat.format("Client made a bade request {0}.", e.toString()));
			return new ResponseEntity<>(new BaseResponse(e.toString()), HttpStatus.BAD_REQUEST);
		
		}catch(Exception e) {
			e.printStackTrace();
			var safeErrorMessage = MessageFormat.format("Erro while proccessing request to deposit funds bank acccount for id {0}", id);
			logger.log(Level.WARNING,safeErrorMessage);
			return new ResponseEntity<>(new BaseResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
