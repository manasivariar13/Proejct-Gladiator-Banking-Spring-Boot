package com.lti.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class OtpDaoImpl implements OtpDao {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public String getOtp(int custId) {
		// TODO Auto-generated method stub
		String jpql = "select o.otp from Otp o where o.dateTime >SYSDATE and o.custId= :custId and rownum = 1 order by o.dateTime desc";
		return (String) em.createQuery(jpql).setParameter("custId", custId).getSingleResult();
	}

}
