package com.sso.sunucutakip.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.sso.sunucutakip.entitiy.GirisEntity;
import com.sso.sunucutakip.entitiy.Kullanici;
import com.sso.sunucutakip.entitiy.Rol;

public class DAO {

	private static DAO uniqueInstance;

	public static DAO getInstance() {

		if (uniqueInstance == null) {
			uniqueInstance = new DAO();
		}
		return uniqueInstance;
	}

	SessionFactory sessionFactory;

	public DAO() {
		sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
	}

	public Kullanici checkUser(GirisEntity girisEntity) {
		Kullanici kullanici = null;
		Session session = sessionFactory.openSession();

		Criteria criteria = session.createCriteria(Kullanici.class);

		criteria.add(Restrictions.eq("kullaniciAdi", girisEntity.getUserName()));

		criteria.add(Restrictions.eq("sifre", girisEntity.getPassWord()));

		List<Kullanici> list = criteria.list();

		if (list.size() > 0)
			kullanici = list.get(0);
		session.close();

		return kullanici;
	}

	public void rolEkle(Rol rol) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			session.save(rol);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		
	}

	public List<Rol> rolListele() {
		Session session = sessionFactory.openSession();

		Criteria criteria = session.createCriteria(Rol.class);

		List<Rol> list = criteria.list();
		
		return list;

	}

	public void rolSil(Rol secilenRol) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			session.delete(secilenRol);
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		
	}

	
	
	

}
