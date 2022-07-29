package com.lti.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.lti.entity.Account;
import com.lti.entity.AccountStatus;
import com.lti.entity.AccountType;
import com.lti.entity.Address;
import com.lti.entity.Admin;
import com.lti.entity.Beneficiary;
import com.lti.entity.Customer;
import com.lti.entity.Gender;
import com.lti.entity.Income;
import com.lti.entity.Transaction;
import com.lti.entity.TransactionType;
import com.lti.entity.User;
import com.lti.exception.ServiceException;

@Component
public class CustomerDaoImpl implements CustomerDao {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public Customer openAccount(Customer customer) {
		Customer cust = em.merge(customer);
		return cust;

//		Account acc = new Account();
//		acc.setCustomer(cust);
//		acc.setType(AccountType.Savings);
//		acc.setAccountStatus(AccountStatus.Pending);
//		em.persist(acc);
//
//		Address address = new Address();
//		address.setCustomer(cust);
//		em.persist(address);
//
//		Income income = new Income();
//		income.setCustomer(cust);
//		em.persist(income);

		
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

	@Transactional
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

	@Transactional
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
	public User login(int userId, String password) {
		try {
			String jpql = "select u from User u where u.userId=:uid and u.loginPassword=:pwd";

			TypedQuery<User> query = em.createQuery(jpql, User.class);
			query.setParameter("uid", userId);
			query.setParameter("pwd", password);

			User user = query.getSingleResult();
			return user;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ServiceException("Invalid email/password");
		}
	}

	@Transactional
	public String signup(User user) {
//		User u = em.merge(user);
		em.persist(user);
		em.flush();
		return String.valueOf(user.getUserId());
	}

//	@Transactional
//	public boolean adminExists(int adminId) {
//		String jpql = "select count(a.adminId) from Admin a where a.adminId = :adminId";
//		return (int) em.createQuery(jpql).setParameter("adminId", adminId).getSingleResult() == 1 ? true : false;
//	}

	@Transactional
	public boolean adminLogin(int adminId, String adminPassword) {
		System.out.println(adminId + " " + adminPassword);
		String jpql = "select a from Admin a where a.adminId=:aid and a.adminPassword=:pwd";

		TypedQuery<Admin> query = em.createQuery(jpql, Admin.class);
		query.setParameter("aid", adminId);
		query.setParameter("pwd", adminPassword);

		Admin admin;

		try {
			admin = query.getSingleResult();
			return admin != null ? true : false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.err.println(e.getMessage());
			return false;
		}

	}

	@Transactional
	public Admin addAdmin() {
		Admin admin = new Admin();
		admin.setAdminPassword("test123");
		admin.setName("Ashwith");

		em.persist(admin);
		return admin;
	}

	@Transactional
	public List<Customer> pendingRequest() {
		String jpql = "select c from Customer c join Account a on c.custId=a.customer.custId and a.accountStatus=:acSt";
		TypedQuery<Customer> query = em.createQuery(jpql, Customer.class);
		query.setParameter("acSt", AccountStatus.Pending);
		return query.getResultList();
	}

	@Transactional
	public String updatePendingRequest(int customerId, String response) {
		String jpql = "update Account a set a.accountStatus=:accSt where c.custId=:cId";
		TypedQuery<Account> query = em.createQuery(jpql, Account.class);
		query.setParameter("accSt", response);
		query.setParameter("cId", customerId);

		try {
			Account acc = query.getSingleResult();
			return "Account status changed";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Account status change failed";
		}

//		Account acc = new Account();
//		acc.setCustomer(cust);
//
//		em.persist(acc);
	}
}
