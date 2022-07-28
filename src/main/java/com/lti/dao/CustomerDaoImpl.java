package com.lti.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.lti.entity.Account;
import com.lti.entity.Beneficiary;
import com.lti.entity.Customer;
import com.lti.entity.Transaction;
import com.lti.entity.TransactionType;

@Component
public class CustomerDaoImpl implements CustomerDao {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public Customer openAccount(Customer customer) {
		em.persist(customer);
		return customer;
	}

	@Transactional
	public Account accountSummary(int accountNumber) {
		return em.find(Account.class, accountNumber);
	}

	@Transactional
	public List<Transaction> accountStatement(int accountNumber) {
		String jpql = "select t from Transaction t where t.account.accountNumber=:accno";
		TypedQuery<Transaction> query = em.createQuery(jpql, Transaction.class);
		query.setParameter("accno", accountNumber);
		return query.getResultList();
	}

	@Transactional
	public Transaction fundTransfer(Account fromAccount, Account toAccount, double amount) {
		Transaction transaction = new Transaction();
		transaction.setAccount(toAccount);
		transaction.setAmount(amount);
		transaction.setTransactionDate(LocalDate.now());
		transaction.setTransactionType(TransactionType.NEFT);
		transaction.setMode("Credit");
		em.persist(transaction);

		Transaction transaction1 = new Transaction();
		transaction1.setAccount(fromAccount);
		transaction1.setAmount(amount);
		transaction1.setTransactionDate(LocalDate.now());
		transaction1.setTransactionType(TransactionType.NEFT);
		transaction1.setMode("Debit");
		em.persist(transaction1);

		fromAccount.setBalance(fromAccount.getBalance() - amount);
		em.merge(fromAccount);
		toAccount.setBalance(toAccount.getBalance() + amount);
		em.merge(toAccount);
		return transaction;
	}

	@Transactional
	public Beneficiary addBeneficiary(Beneficiary beneficiary) {
		em.persist(beneficiary);
		return beneficiary;
	}

	public List<Beneficiary> viewAllBeneficiaries(int accountNumber) {
		String jpql = "select b from Beneficiary b where b.customerAccountNumber=:accNo";
//		String jpql = "select u from Customer c where c.userId=:cid and c.password=:pwd";

//		TypedQuery<Customer> query = em.createQuery(jpql, Customer.class);
//		query.setParameter("cid", custId);
//		query.setParameter("pwd", password);
		TypedQuery<Beneficiary> query = em.createQuery(jpql, Beneficiary.class);
		query.setParameter("accNo", accountNumber);
		return query.getResultList();
	}

	@Transactional
	public void deleteBeneficiary(int beneficiaryId) {
		Beneficiary beneficiary = findBeneficiaryById(beneficiaryId);
		em.remove(beneficiary);
	}

	public Beneficiary findBeneficiaryById(int beneficiaryId) {
		return em.find(Beneficiary.class, beneficiaryId);
	}

//	@Transactional
//	public Transaction addOrUpdateTransaction(Transaction transaction) {
//		// TODO Auto-generated method stub
//		Transaction tr = em.merge(transaction);
//		return tr;
//
//	}

	@Transactional
	public List<Transaction> findtoprec(int accountNumber) {
		String jpql = "select t from Transaction t where t.account.accountNumber=:accNo and rownum<=5 ORDER BY transactionId DESC";
		TypedQuery<Transaction> query = em.createQuery(jpql, Transaction.class);
		query.setParameter("accNo", accountNumber);
		return query.getResultList();
	}

	@Transactional
	public boolean isCustomerExists(int accountNumber) {
//		String jpql="select count(c.custId)>0 from Customer c where c.custId=:custId";
//		TypedQuery<Customer> query=em.createNamedQuery(jpql, Customer.class);
//		query.setParameter("custId",custId);
//		Customer cust=query.getSingleResult();
//		return cust!=null?true:false;
		return em.find(Account.class, accountNumber) != null;
	}

	@Transactional
	public Customer addOrUpdateCustomer(Customer customer) {
		Customer c = em.merge(customer);
		return c;
	}

	@Transactional
	public boolean login(int customerId, String password) {
		String jpql = "select c from Customer c where c.custId=:cid and c.customerPassword=:pwd";

		TypedQuery<Customer> query = em.createQuery(jpql, Customer.class);
		query.setParameter("cid", customerId);
		query.setParameter("pwd", password);

		Customer customer = query.getSingleResult();
		return customer != null ? true : false;
	}
}
