package com.lti.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.lti.entity.Account;

@Component
public class AccountDaoImpl implements AccountDao {
	
	@PersistenceContext
	EntityManager em;

	@Transactional
	public boolean exists(int acno) {
		long count = ((Long) em
				.createQuery("select count(a.accountNumber) from Account a where a.accountNumber = :acno")
				.setParameter("acno", acno).getSingleResult());
		if (count == 0)
			return false;
		else
			return true;
	}

	@Transactional
	public Account findAccountByUserId(int userId) {
		return (Account) em.createQuery("select a from Account a where a.user.id= :userId")
				.setParameter("userId", userId).getSingleResult();

	}

	@Transactional
	public String checkUserHasInternetBankingWithGivenAcno(int accountNumber) {
		return (String) em.createQuery(
				" select c.netBankingRequirement from Account a join a.user u join u.customer c where a.accountNumber = :accountNumber")
				.setParameter("accountNumber", accountNumber).getSingleResult();
	}

	@Transactional
	public boolean checkUserAlreadyRegistered(int accountNumber) {
		return (boolean) em.createQuery(
				" select case when u.userPassword is not null then true else false end from Account a join a.user u where a.accountNumber = :accountNumber")
				.setParameter("accountNumber", accountNumber).getSingleResult();
	}

	@Transactional
	public List<Account> fetchAccountsByUserId(int accountNumber) {
		return em.createQuery("select a from Account a where a.user = :id",Account.class).setParameter("id", accountNumber)
				.getResultList();
	}

	@Transactional
	public Account findAccountByAccountNumber(int accountNumber) {
		return (Account) em.createQuery("select a from Account a where a.accountNumber =:accountNumber")
				.setParameter("accountNumber", accountNumber).getSingleResult();

	}
	
	@Transactional
	public <T> T save(Object object) {
		return (T) em.merge(object);
	}

	@Transactional
	public <T> T fetchById(Class<T> className, int id) {
		return em.find(className, id);
	}
}
