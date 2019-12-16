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
			
			System.out.println("call pub_search_goods('"+pUid+"',"+gType+",'"+gpt_begin+"','"+gpt_end+"',"+gState+",'"+gn+"');");
		
		// ���ô洢����
			// call pub_search_goods(puid,gtp,gpt_begin,gpt_end,gs,gn)
		
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			List<Goods> lg = null;
			
			try {
				// ��ȡ���ݿ����Ӷ���
					conn = DBCPUtil.getConnection();
				// ����sql���ռλ����
					String sql = "call pub_search_goods(?,?,?,?,?,?)";
				// ����sql�������
					ps = conn.prepareStatement(sql);
				// ռλ����ֵ
					ps.setString(1, pUid);
					ps.setInt(2, gType);
					ps.setString(3, gpt_begin);
					ps.setString(4, gpt_end);
					ps.setInt(5, gState);
					ps.setString(6, gn);
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
	public int editGoods(String gId, String gName, String gInfo, int gType, double gPrice, String gImg) {
		// ����QueryRunner������������
		QueryRunner runner = null;
		int flag = 0;
		
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String call = "call goods_edit(?,?,?,?,?,?)";
			// ����params��������	
				Object[] callParams = { gId, gName, gInfo, gType, gPrice,gImg };
			// ִ�д洢����
				flag = runner.execute(call, callParams);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return flag;  // 0�ɹ���-1ʧ��
	}


	@Override
	public int offGoods(String gId) {
		// ����QueryRunner������������
		QueryRunner runner = null;
		
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String call = "call goods_off(?)";
			// ����params��������	
				Object[] callParams = { gId };
			// ִ�д洢����
				runner.execute(call, callParams);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// ���²�ѯһ��gState
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int gs = 0;
		try {
			// ��ȡ���ݿ����Ӷ���
				conn = DBCPUtil.getConnection();
			// ����sql���ռλ����
				String sql = "select gstate from goods where gid=?";
			// ����sql�������
				ps = conn.prepareStatement(sql);
			// ռλ����ֵ
				ps.setString(1, gId);
			// ִ��
				rs = ps.executeQuery();
			// ����ִ�н��
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
		// ����QueryRunner������������
		QueryRunner runner = null;
		// String gId = Utils.genRandID();
		
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String call = "call goods_create(?,?,?,?,?,?,?,?)";
			// ����params��������
				String gPubTime = Utils.localeDateTime();
				Object[] callParams = { gId, gName, gInfo, gType, gPubTime, gPrice, gImg, pUid };
			// ִ�д洢����
				runner.execute(call, callParams);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// ��ѯ�Ƿ����ɹ�
		int success = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// ��ȡ���ݿ����Ӷ���
				conn = DBCPUtil.getConnection();
			// ����sql���ռλ����
				String sql = "select * from goods where gid=?";
			// ����sql�������
				ps = conn.prepareStatement(sql);
			// ռλ����ֵ
				ps.setString(1, gId);
			// ִ��
				rs = ps.executeQuery();
			// ����ִ�н��
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
		// ����QueryRunner������������
		QueryRunner runner = null;
		int flag = 0;
		
		try {
			// ����QueryRunner����
				runner = new QueryRunner(DBCPUtil.getDataSource());
			// ����sql���
				String sql = "update publisher set ppwd=?,pinfo=? where puid=?";
			// ����params��������
				Object[] params = { pPwd, pInfo, pUid };
			// ����query����
				flag = runner.update(sql, params);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
