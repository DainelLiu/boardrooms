package com.zz.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.zz.dao.IPowerDao;
import com.zz.model.Power;
import com.zz.util.PageBean;

@Component(value="PowerDao")
public class PowerDaoImpl implements IPowerDao {
	
	private SessionFactory sessionFactory;
	
	@Resource(name="sessionFactory")//sessionFactory注入
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public boolean save(Power power) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String returnId = (String) session.save(power);
		session.getTransaction().commit();
		session.close();
		if(!"".equals(returnId) && null != returnId){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean delete(Power power) {
		boolean result = false;
		try{
			if(power != null){
				Session session = sessionFactory.openSession();
				session.beginTransaction();
				session.delete(power);
				session.getTransaction().commit();
				session.close();
				result = true;
			}
		}catch(HibernateException e){
			result = false;
		}
		return result;
	}

	@Override
	public boolean update(Power power) {
		boolean result = false;
		try{
			if(power != null){
				Session session = sessionFactory.openSession();
				session.beginTransaction();
				session.update(power);
				session.getTransaction().commit();
				session.close();
				result = true;
			}
		}catch(HibernateException e){
			result = false;
		}
		return result;
	}
	
	@Override
	public List<Object> list() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Power");
		List<Object> list = query.list();
		session.close();
		return list;
	}

	@Override
	public List<Object> listAll(PageBean page) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Power");
		query.setFirstResult(page.getRowStart());
		query.setMaxResults(page.getPageSize());
		List<Object> list = query.list();
		session.close();
		return list;
	}

	@Override
	public Power getById(String id) {
		Session session = sessionFactory.openSession();
		Power dto = (Power)session.get(Power.class, id);
		session.close();
		return dto;
	}

	@Override
	public List<Object> getByConds(String hql,PageBean page) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(page.getRowStart());
		query.setMaxResults(page.getPageSize());
		List<Object> list = query.list();
		session.close();
		return list;
	}
	
	@Override
	public List<Object> getAllByConds(String hql) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		List<Object> list = query.list();
		session.close();
		return list;
	}

}
