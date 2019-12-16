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
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		List<Goods> lg = null;
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String sql = "call pub_search_goods('playlist1',3,'0000-01-01 00:00:00','2999-12-31 23:59:59',1,'%%');";
			// 创建params参数对象
			// 调用query方法
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
//		testDBCPList();  // 不能直接BeanListHandler<Goods>(Goods.class));
//		testPubDao();
//		testUserDao();
//	}
	
	public static void testOrderDao(int x) {
		int flag = 0;
		List<Order> lo = null;
		OrderDao od = new OrderDaoImpl();
		try {
			// 1. 用户加入购物车
			if(x==1) {
				flag = od.userCreateOrder(1, "g002", 45, "46", 1);
				System.out.println("用户加入购物车，flag="+flag);
				// oId = 1207-1004-82c21567-1259-49dc-89e4-19da7bc75f90
			}
			// 4. 用户结算
			if(x==4) {
				String oId = "1207-1004-82c21567-1259-49dc-89e4-19da7bc75f90";
				flag = od.updateOrder(oId, 2); // 用户update
				System.out.println("用户结算，flag="+flag);
			}
//			// 2. 修改购物车数量
//			if(x==2) {
//				String oId = "";
//				flag = od.userEditOrder(oId, 3);
//				System.out.println("用户修改购物车数量，flag="+flag);
//			}
//			// 3. 删除购物车
//			if(x==3) {
//				// 
//				String oId = "";
//				flag = od.userRemoveOrder(oId);
//				System.out.println("用户删除购物车，flag="+flag);
//			}
			// 5. 用户立即购买
			if(x==5) {  
				// 同一种东西可以重复购买
				flag = od.userCreateOrder(2, "g002", 45, "46", 2);
				System.out.println("用户立即购买，flag="+flag);
				// oId = 1207-1006-24c5b825-0b6c-4446-a90e-5d5f7dc947fe
			}
			// --------------------------------------------------------------------
			// 6. 发行方发货
			if(x==6) {
				String oId = "1207-1004-82c21567-1259-49dc-89e4-19da7bc75f90";
				flag = od.updateOrder(oId, 3); // 发行方update
				System.out.println("发行方发货，flag="+flag);
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
			
			// 1. 用户查询购物车
			if(x==1) {
				lo = od.userLoadOrder(uId, 1);
				System.out.println(new Gson().toJson(lo));
			}
			// 2. 用户查询订单
			if(x==2) {
				lo = od.userLoadOrder(uId, 2);
				System.out.println(new Gson().toJson(lo));
			}
			// 3. 发行方查询订单
			if(x==3) {
				lo = od.pubLoadOrder("playlist1");
				System.out.println(new Gson().toJson(lo));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testSendMail() {
		// 发送邮件
		String TO="";
		String emailMsg = "test";
		
		// 查询uMail
		Connection conn3 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		try {
			// 获取数据库连接对象
				conn3 = DBCPUtil.getConnection();
			// 创建sql命令（占位符）
				String sql3 = "select u.uMail "
							 +"FROM `order` o LEFT JOIN `user` u ON o.userid=u.uid "
							 +"WHERE oid=?";
			// 创建sql命令对象
				ps3 = conn3.prepareStatement(sql3);
			// 占位符赋值
				ps3.setString(1, "o002");
			// 执行
				rs3 = ps3.executeQuery();
			// 遍历执行结果
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
