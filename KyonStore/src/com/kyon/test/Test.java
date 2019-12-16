package com.kyon.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.google.gson.Gson;
import com.kyon.pojo.*;
import com.kyon.dao.*;
import com.kyon.daoImpl.*;
import com.kyon.tools.DBCPUtil;
import com.kyon.tools.MailUtil;
import com.kyon.tools.Utils;


public class Test {

	public static void testLoginDao() {
		LoginDao ld = new LoginDaoImpl();
		ld.checkUPwd("kyon45", "1234");
//		System.out.println(success);
	}
	
	public static void testGenRandID() {
		String randID = Utils.genRandID();
		System.out.println(randID);
	}
	
	public static void testRegDao() {
		RegDao rd = new RegDaoImpl();
		int flag = 0;
		try {
			flag = rd.userReg("48@kyon.com", "kyon48", "123");
		} catch (Exception e) {
			flag = -45;
			e.printStackTrace();
		}
		System.out.println(flag);
		
	}
	
	public static void testDBCPList() {
		// ����QueryRunner������������
		QueryRunner runner = null;
		List<Goods> lg = null;
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String sql = "call pub_search_goods('playlist1',3,'0000-01-01 00:00:00','2999-12-31 23:59:59',1,'%%');";
			// ����params��������
			// ����query����
				lg = (List<Goods>) runner.query(sql, new BeanListHandler<Goods>(Goods.class));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println(new Gson().toJson(lg));
	}

	public static void testPubDao() {
		PubDao pd = new PubDaoImpl();
		int flag = -2;
		List<Goods> lg = new ArrayList<Goods>();
		try {
			lg = pd.searchGoods("playlist1",0,"",1,"");
		} catch (Exception e) {
			flag = -45;
			e.printStackTrace();
		}
		System.out.println(new Gson().toJson(lg));
	}
	
	public static void testUserDao() {
		UserDao ud = new UserDaoImpl();
		List<Browse> lb = new ArrayList<Browse>();
		try {
			lb = ud.loadBrowse("45");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(new Gson().toJson(lb));
	}
	
//	public static void main(String[] args) {
//		 testLoginDao();
		// testGenRandID();
//		testRegDao();
//		System.out.println(Utils.getMaxDateOf("2016-02"));
//		testDBCPList();  // ����ֱ��BeanListHandler<Goods>(Goods.class));
//		testPubDao();
//		testUserDao();
//	}
	
	public static void testOrderDao(int x) {
		int flag = 0;
		List<Order> lo = null;
		OrderDao od = new OrderDaoImpl();
		try {
			// 1. �û����빺�ﳵ
			if(x==1) {
				flag = od.userCreateOrder(1, "g002", 45, "46", 1);
				System.out.println("�û����빺�ﳵ��flag="+flag);
				// oId = 1207-1004-82c21567-1259-49dc-89e4-19da7bc75f90
			}
			// 4. �û�����
			if(x==4) {
				String oId = "1207-1004-82c21567-1259-49dc-89e4-19da7bc75f90";
				flag = od.updateOrder(oId, 2); // �û�update
				System.out.println("�û����㣬flag="+flag);
			}
//			// 2. �޸Ĺ��ﳵ����
//			if(x==2) {
//				String oId = "";
//				flag = od.userEditOrder(oId, 3);
//				System.out.println("�û��޸Ĺ��ﳵ������flag="+flag);
//			}
//			// 3. ɾ�����ﳵ
//			if(x==3) {
//				// 
//				String oId = "";
//				flag = od.userRemoveOrder(oId);
//				System.out.println("�û�ɾ�����ﳵ��flag="+flag);
//			}
			// 5. �û���������
			if(x==5) {  
				// ͬһ�ֶ��������ظ�����
				flag = od.userCreateOrder(2, "g002", 45, "46", 2);
				System.out.println("�û���������flag="+flag);
				// oId = 1207-1006-24c5b825-0b6c-4446-a90e-5d5f7dc947fe
			}
			// --------------------------------------------------------------------
			// 6. ���з�����
			if(x==6) {
				String oId = "1207-1004-82c21567-1259-49dc-89e4-19da7bc75f90";
				flag = od.updateOrder(oId, 3); // ���з�update
				System.out.println("���з�������flag="+flag);
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testOrderDao2(int x) {
		List<Order> lo = null;
		OrderDao od = new OrderDaoImpl();
		
		try {
			String uId = "46";
			
			// 1. �û���ѯ���ﳵ
			if(x==1) {
				lo = od.userLoadOrder(uId, 1);
				System.out.println(new Gson().toJson(lo));
			}
			// 2. �û���ѯ����
			if(x==2) {
				lo = od.userLoadOrder(uId, 2);
				System.out.println(new Gson().toJson(lo));
			}
			// 3. ���з���ѯ����
			if(x==3) {
				lo = od.pubLoadOrder("playlist1");
				System.out.println(new Gson().toJson(lo));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testSendMail() {
		// �����ʼ�
		String TO="";
		String emailMsg = "test";
		
		// ��ѯuMail
		Connection conn3 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		try {
			// ��ȡ���ݿ����Ӷ���
				conn3 = DBCPUtil.getConnection();
			// ����sql���ռλ����
				String sql3 = "select u.uMail "
							 +"FROM `order` o LEFT JOIN `user` u ON o.userid=u.uid "
							 +"WHERE oid=?";
			// ����sql�������
				ps3 = conn3.prepareStatement(sql3);
			// ռλ����ֵ
				ps3.setString(1, "o002");
			// ִ��
				rs3 = ps3.executeQuery();
			// ����ִ�н��
				while(rs3.next()) {
					TO = rs3.getString("uMail");
				}
			TO = "45@kyon.com";
			System.out.println(TO);
			if(!"".equals(TO))
				MailUtil.sendMail(TO, emailMsg);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			rs3.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ps3.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn3.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//testOrderDao2(3);
		testSendMail();
		
	}

}
