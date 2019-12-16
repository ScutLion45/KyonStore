package com.kyon.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.kyon.dao.PubDao;
import com.kyon.pojo.Goods;
import com.kyon.tools.DBCPUtil;
import com.kyon.tools.Utils;

public class PubDaoImpl implements PubDao {

	@Override
	public List<Goods> searchGoods(String pUid, int gType, String gPubTime, int gState, String gName) {
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
			
			System.out.println("call pub_search_goods('"+pUid+"',"+gType+",'"+gpt_begin+"','"+gpt_end+"',"+gState+",'"+gn+"');");
		
		// 调用存储过程
			// call pub_search_goods(puid,gtp,gpt_begin,gpt_end,gs,gn)
		
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<Goods> lg = null;
			
			try {
				// 获取数据库连接对象
					conn = DBCPUtil.getConnection();
				// 创建sql命令（占位符）
					String sql = "call pub_search_goods(?,?,?,?,?,?)";
				// 创建sql命令对象
					ps = conn.prepareStatement(sql);
				// 占位符赋值
					ps.setString(1, pUid);
					ps.setInt(2, gType);
					ps.setString(3, gpt_begin);
					ps.setString(4, gpt_end);
					ps.setInt(5, gState);
					ps.setString(6, gn);
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
	public int editGoods(String gId, String gName, String gInfo, int gType, double gPrice, String gImg) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		int flag = 0;
		
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String call = "call goods_edit(?,?,?,?,?,?)";
			// 创建params参数对象	
				Object[] callParams = { gId, gName, gInfo, gType, gPrice,gImg };
			// 执行存储过程
				flag = runner.execute(call, callParams);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return flag;  // 0成功，-1失败
	}


	@Override
	public int offGoods(String gId) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String call = "call goods_off(?)";
			// 创建params参数对象	
				Object[] callParams = { gId };
			// 执行存储过程
				runner.execute(call, callParams);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 重新查询一遍gState
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int gs = 0;
		try {
			// 获取数据库连接对象
				conn = DBCPUtil.getConnection();
			// 创建sql命令（占位符）
				String sql = "select gstate from goods where gid=?";
			// 创建sql命令对象
				ps = conn.prepareStatement(sql);
			// 占位符赋值
				ps.setString(1, gId);
			// 执行
				rs = ps.executeQuery();
			// 遍历执行结果
				while(rs.next()) {
					gs = rs.getInt("gstate");
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
		
		return gs;  // gState
	}


	@Override
	public int createGoods(String gId, String gName, String gInfo, int gType, double gPrice, String gImg, String pUid) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		// String gId = Utils.genRandID();
		
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String call = "call goods_create(?,?,?,?,?,?,?,?)";
			// 创建params参数对象
				String gPubTime = Utils.localeDateTime();
				Object[] callParams = { gId, gName, gInfo, gType, gPubTime, gPrice, gImg, pUid };
			// 执行存储过程
				runner.execute(call, callParams);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// 查询是否插入成功
		int success = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 获取数据库连接对象
				conn = DBCPUtil.getConnection();
			// 创建sql命令（占位符）
				String sql = "select * from goods where gid=?";
			// 创建sql命令对象
				ps = conn.prepareStatement(sql);
			// 占位符赋值
				ps.setString(1, gId);
			// 执行
				rs = ps.executeQuery();
			// 遍历执行结果
				while(rs.next()) {
					if(!"".equals(rs.getString("gid"))) {
						success = 1;
					}
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
		
		return success;
	}


	@Override
	public int editProfile(String pUid, String pPwd, String pInfo) {
		// 声明QueryRunner对象及其他变量
		QueryRunner runner = null;
		int flag = 0;
		
		try {
			// 创建QueryRunner对象
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// 创建sql语句
				String sql = "update publisher set ppwd=?,pinfo=? where puid=?";
			// 创建params参数对象
				Object[] params = { pPwd, pInfo, pUid };
			// 调用query方法
				flag = runner.update(sql, params);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
