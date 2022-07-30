package com.lti.dao;

import java.util.List;

import com.lti.entity.Account;

public interface AccountDao {

	boolean exists(int acno);
	Account findAccountByUserId(int userId);
	String checkUserHasInternetBankingWithGivenAcno(int accountNumber);
	boolean checkUserAlreadyRegistered(int accountNumber);
	List<Account> fetchAccountsByUserId(int accountNumber);
	Account findAccountByAccountNumber(int accountNumber);
	<T> T save(Object object);

	<T> T fetchById(Class<T> className, int id);
}