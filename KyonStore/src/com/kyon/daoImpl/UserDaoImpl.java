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
		// ���ô洢����
			// call user_load_latest_goods(gType)
			
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<Goods> lg = null;
			
			try {
				// ��ȡ���ݿ����Ӷ���
				conn = DBCPUtil.getConnection();
				// ����sql���ռλ����
					String sql = "call user_load_latest_goods(?)";
				// ����sql�������
					ps = conn.prepareStatement(sql);
				// ռλ����ֵ
					ps.setInt(1, gType);
				// ִ��
					rs = ps.executeQuery();
				// lg��ʼ��
					lg = new ArrayList<Goods>();
				// ����ִ�н��
					while(rs.next()) {
						// ��ȡ���ݵ�Goods������
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

						// ������洢��lg��
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
		// �������
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
	
		// ���ô洢����
			// call user_search_goods(gtp,gpt_begin,gpt_end,gn)
		
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<Goods> lg = null;
			
			try {
				// ��ȡ���ݿ����Ӷ���
					conn = DBCPUtil.getConnection();
				// ����sql���ռλ����
					String sql = "call user_search_goods(?,?,?,?)";
				// ����sql�������
					ps = conn.prepareStatement(sql);
				// ռλ����ֵ
					ps.setInt(1, gType);
					ps.setString(2, gpt_begin);
					ps.setString(3, gpt_end);
					ps.setString(4, gn);
				// ִ��
					rs = ps.executeQuery();
				// lg��ʼ��
					lg = new ArrayList<Goods>();
				// ����ִ�н��
					while(rs.next()) {
						// ��ȡ���ݵ�Goods������
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

						// ������洢��lg��
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
		// ����QueryRunner������������
		QueryRunner runner = null;
		int flag = 0;
		
		String bTime = Utils.localeDateTime();
		System.out.println("call browse_update('"+bTime+"','"+gId+"','"+uId+"')");
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String call = "call browse_update(?,?,?)";
			// ����params��������	
				Object[] callParams = { bTime, gId, uId };
			// ִ�д洢����
				runner.execute(call, callParams);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// ִ��һ���ѯ
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// ��ȡ���ݿ����Ӷ���
				conn = DBCPUtil.getConnection();
			// ����sql���ռλ����
				String sql = "select btime from browse where goodsid=? and userid=?";
			// ����sql�������
				ps = conn.prepareStatement(sql);
			// ռλ����ֵ
				ps.setString(1, gId);
				ps.setString(2, uId);
			// ִ��
				rs = ps.executeQuery();
			// ����ִ�н��
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
		
		return flag;  // 1�ɹ���0ʧ��

	}

	@Override
	public int editProfile(String uId, String uMail, String uPwd, double uBalance) {
		// ����QueryRunner������������
		QueryRunner runner = null;
		int flag = 0;
		
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String sql = "update user set umail=?,upwd=?,ubalance=? where uid=?";
			// ����params��������
				Object[] params = { uMail, uPwd, uBalance, uId };
			// ����query����
				flag = runner.update(sql, params);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public List<Browse> loadBrowse(String uId) {
		// ���ô洢����
			// call user_load_browse(uid)
			System.out.println("call user_load_browse('"+uId+"')");
		
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<Browse> lb = null;

			try {
				// ��ȡ���ݿ����Ӷ���
					conn = DBCPUtil.getConnection();
				// ����sql���ռλ����
					String sql = "call user_load_browse(?)";
				// ����sql�������
					ps = conn.prepareStatement(sql);
				// ռλ����ֵ
					ps.setString(1, uId);
				// ִ��
					rs = ps.executeQuery();
				// lb��ʼ�� 
					lb = new ArrayList<Browse>();
				// ����ִ�н��
					while(rs.next()) {
						// ��ȡ���ݵ�Browse������
							Browse b = new Browse();
							b.setbId(rs.getString("bid"));
							b.setbTime(rs.getString("btime"));
						// ��ȡ���ݵ�Goods������
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

						// ������洢��lg��
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
