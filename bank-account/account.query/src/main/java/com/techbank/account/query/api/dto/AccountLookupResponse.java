package com.techbank.account.query.api.dto;

import java.util.List;

import com.techbank.account.common.dto.BaseResponse;
import com.techbank.account.query.domain.BankAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountLookupResponse extends BaseResponse {
	
	private List<BankAccount> accounts;
	
	public AccountLookupResponse(String message) {
		super(message);
	}

	public AccountLookupResponse(String message,List<BankAccount> accounts) {
		super(message);
		this.accounts = accounts;
	}
	
	
	

}
