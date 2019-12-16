package com.kyon.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.kyon.dao.UserDao;
import com.kyon.pojo.Browse;
import com.kyon.pojo.Goods;
import com.kyon.tools.DBCPUtil;
import com.kyon.tools.Utils;

public class UserDaoImpl implements UserDao {

	@Override
	public List<Goods> loadLatestGoods(int gType) {
			System.out.println("call user_load_latest_goods("+gType+");");
		// 调用存储过程
			// call user_load_latest_goods(gType)
			
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<Goods> lg = null;
			
			try {
				// 获取数据库连接对象
				conn = DBCPUtil.getConnection();
				// 创建sql命令（占位符）
					String sql = "call user_load_latest_goods(?)";
				// 创建sql命令对象
					ps = conn.prepareStatement(sql);
				// 占位符赋值
					ps.setInt(1, gType);
				// 执行
					rs = ps.executeQuery();
				// lg初始化
					lg = new ArrayList<Goods>();
				// 遍历执行结果
					while(rs.next()) {
						// 读取数据到Goods对象中
							Goods g = new Goods();
							g.setgId(rs.getString("gid"));
							g.setgName(rs.getString("gname"));
							g.setgInfo(rs.getString("ginfo"));
							g.setgType(rs.getInt("gtype"));
							g.setgPubTime(rs.getString("gpubtime"));
							g.setgPrice(rs.getDouble("gprice"));
							g.setgBrowse(rs.getInt("gbrowse"));
							g.setgSell(rs.getInt("gsell"));
							g.setgState(rs.getInt("gstate"));
							g.setgImg(rs.getString("gimg"));
							g.setgVolume(rs.getDouble("gvolume"));
							g.getPub().setpUid(rs.getString("puid"));
							g.getPub().setpName(rs.getString("pname"));
//							g.getPub().setpPwd(rs.getString("ppwd"));
							g.getPub().setpInfo(rs.getString("pinfo"));

						// 将对象存储到lg中
							lg.add(g);
					}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			

			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		return lg;
	}

	@Override
	public List<Goods> searchGoods(int gType, String gPubTime, String gName) {
		// 处理参数
			String gpt_begin = "0000-01-01 00:00:00";
			String gpt_end = "2999-12-31 23:59:59";
			if(!"".equals(gPubTime)) {
				int dates = Utils.getMaxDateOf(gPubTime);
				if(dates>0) {
					gpt_begin = gPubTime+"-01 00:00:00";
					gpt_end = gPubTime+"-"+dates+" 23:59:59";
				}
			}
			
			String gn = "%"+gName+"%";
			
			System.out.println("call user_search_goods("+gType+",'"+gpt_begin+"','"+gpt_end+"','"+gn+"');");
	
		// 调用存储过程
			// call user_search_goods(gtp,gpt_begin,gpt_end,gn)
		
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<Goods> lg = null;
			
			try {
				// 获取数据库连接对象
					conn = DBCPUtil.getConnection();
				// 创建sql命令（占位符）
					String sql = "call user_search_goods(?,?,?,?)";
				// 创建sql命令对象
					ps = conn.prepareStatement(sql);
				// 占位符赋值
					ps.setInt(1, gType);
					ps.setString(2, gpt_begin);
					ps.setString(3, gpt_end);
					ps.setString(4, gn);
				// 执行
					rs = ps.executeQuery();
				// lg初始化
					lg = new ArrayList<Goods>();
				// 遍历执行结果
					while(rs.next()) {
						// 读取数据到Goods对象中
							Goods g = new Goods();
							g.setgId(rs.getString("gid"));
							g.setgName(rs.getString("gname"));
							g.setgInfo(rs.getString("ginfo"));
							g.setgType(rs.getInt("gtype"));
							g.setgPubTime(rs.getString("gpubtime"));
							g.setgPrice(rs.getDouble("gprice"));
							g.setgBrowse(rs.getInt("gbrowse"));
							g.setgSell(rs.getInt("gsell"));
							g.setgState(rs.getInt("gstate"));
							g.setgImg(rs.getString("gimg"));
							g.getPub().setpUid(rs.getString("puid"));
							g.getPub().setpName(rs.getString("pname"));
//							g.getPub().setpPwd(rs.getString("ppwd"));
							g.getPub().setpInfo(rs.getString("pinfo"));

						// 将对象存储到lg中
							lg.add(g);
					}
			} catch(Exception e) {
				e.printStackTrace();
			}
			

			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return lg;
	}

	@Override
	public int browseGoods(String uId, String gId) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		int flag = 0;
		
		String bTime = Utils.localeDateTime();
		System.out.println("call browse_update('"+bTime+"','"+gId+"','"+uId+"')");
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String call = "call browse_update(?,?,?)";
			// 创建params参数对象	
				Object[] callParams = { bTime, gId, uId };
			// 执行存储过程
				runner.execute(call, callParams);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 执行一遍查询
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 获取数据库连接对象
				conn = DBCPUtil.getConnection();
			// 创建sql命令（占位符）
				String sql = "select btime from browse where goodsid=? and userid=?";
			// 创建sql命令对象
				ps = conn.prepareStatement(sql);
			// 占位符赋值
				ps.setString(1, gId);
				ps.setString(2, uId);
			// 执行
				rs = ps.executeQuery();
			// 遍历执行结果
				while(rs.next()) {
					String bt = rs.getString("btime");
					if(bt.equals(bTime))
						flag = 1;
				}			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;  // 1成功，0失败

	}

	@Override
	public int editProfile(String uId, String uMail, String uPwd, double uBalance) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		int flag = 0;
		
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String sql = "update user set umail=?,upwd=?,ubalance=? where uid=?";
			// 创建params参数对象
				Object[] params = { uMail, uPwd, uBalance, uId };
			// 调用query方法
				flag = runner.update(sql, params);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public List<Browse> loadBrowse(String uId) {
		// 调用存储过程
			// call user_load_browse(uid)
			System.out.println("call user_load_browse('"+uId+"')");
		
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<Browse> lb = null;

			try {
				// 获取数据库连接对象
					conn = DBCPUtil.getConnection();
				// 创建sql命令（占位符）
					String sql = "call user_load_browse(?)";
				// 创建sql命令对象
					ps = conn.prepareStatement(sql);
				// 占位符赋值
					ps.setString(1, uId);
				// 执行
					rs = ps.executeQuery();
				// lb初始化 
					lb = new ArrayList<Browse>();
				// 遍历执行结果
					while(rs.next()) {
						// 读取数据到Browse对象中
							Browse b = new Browse();
							b.setbId(rs.getString("bid"));
							b.setbTime(rs.getString("btime"));
						// 读取数据到Goods对象中
							Goods g = new Goods();
							g.setgId(rs.getString("gid"));
							g.setgName(rs.getString("gname"));
							g.setgInfo(rs.getString("ginfo"));
							g.setgType(rs.getInt("gtype"));
							g.setgPubTime(rs.getString("gpubtime"));
							g.setgPrice(rs.getDouble("gprice"));
							g.setgBrowse(rs.getInt("gbrowse"));
							g.setgSell(rs.getInt("gsell"));
							g.setgState(rs.getInt("gstate"));
							g.setgImg(rs.getString("gimg"));
							g.getPub().setpUid(rs.getString("puid"));
							g.getPub().setpName(rs.getString("pname"));
//							g.getPub().setpPwd(rs.getString("ppwd"));
							g.getPub().setpInfo(rs.getString("pinfo"));

						// 将对象存储到lg中
							b.setGoods(g);
							lb.add(b);
					}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			

			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return lb;
	}

}
