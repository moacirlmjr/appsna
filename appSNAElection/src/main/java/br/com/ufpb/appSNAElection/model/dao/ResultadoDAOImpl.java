package br.com.ufpb.appSNAElection.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.com.ufpb.appSNAElection.model.beans.Resultado;
import br.com.ufpb.appSNAElection.util.BDUtil;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.DAOUtil;

public class ResultadoDAOImpl implements ResultadoDAO {

	@Override
	public Long create(Resultado objeto) throws Exception {
		String query = "Insert into resultado(screen_name,termo_id,status,data,latitude,longitude,monitorado_id) values(?,?,?,?,?,?,?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(query);
			stmt.setString(1, objeto.getScreen_name());
			stmt.setLong(2, objeto.getTermoId());
			stmt.setString(3, objeto.getStatus());
			stmt.setTimestamp(4, new Timestamp(objeto.getData().getTime()));
			stmt.setString(5, objeto.getLatitude());
			stmt.setString(6, objeto.getLongitude());
			stmt.setLong(7, objeto.getMonitorado_id());
			stmt.executeUpdate();
			conn.commit();
			ResultSet rs = stmt.getGeneratedKeys();
			while (rs.next()) {
				result = rs.getLong(1);
			}
		} catch (SQLException e) {
			conn.rollback();
			AppSNALog.error(e.toString());
		} finally {
			stmt.close();
			conn.close();
		}
		return result;
	}

	@Override
	public void create(List<Resultado> objeto) throws Exception {
		String query = "Insert into resultado(screen_name,termo_id,status,data,latitude,longitude,monitorado_id) values(?,?,?,?,?,?,?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(query);
			int count = 0;
			for (Resultado r : objeto) {
				stmt = conn.prepareStatement(query);
				stmt.setString(1, r.getScreen_name());
				stmt.setLong(2, r.getTermoId());
				stmt.setString(3, r.getStatus());
				stmt.setTimestamp(4, new Timestamp(r.getData().getTime()));
				stmt.setString(5, r.getLatitude());
				stmt.setString(6, r.getLongitude());
				stmt.setLong(7, r.getMonitorado_id());
				stmt.addBatch();
				if (((objeto.size() - 1) < 20 && count % objeto.size() == 0) || (count != 0 && count % 20 == 0)) {
					stmt.executeBatch();
					conn.commit();
				}
				count ++;
			}
		} catch (SQLException e) {
			conn.rollback();
			AppSNALog.error(e.toString());
		} finally {
			stmt.close();
			conn.close();
		}
	}

	@Override
	public Long update(Resultado objeto) throws Exception {
		String query = "update resultado set screen_name = ?, termo_id = ?,"
				+ " data = ?, latitude = ? , longitude = ?, monitorado_id = ? where id = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(query);
			stmt.setString(1, objeto.getScreen_name());
			stmt.setLong(2, objeto.getTermoId());
			stmt.setDate(3, (Date) objeto.getData());
			stmt.setString(4, objeto.getLatitude());
			stmt.setString(5, objeto.getLongitude());
			stmt.setLong(6, objeto.getMonitorado_id());
			stmt.setLong(7, objeto.getId());
			stmt.executeUpdate();
			conn.commit();
			ResultSet rs = stmt.getGeneratedKeys();
			while (rs.next()) {
				result = rs.getLong(1);
			}
		} catch (SQLException e) {
			conn.rollback();
			AppSNALog.error(e.toString());
		} finally {
			stmt.close();
			conn.close();
		}

		return result;
	}

	@Override
	public Resultado findById(Long id) throws Exception {
		String query = "select * from resultado where id = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Resultado result = new Resultado();

		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();

			while (rs.next()) {
				result.setId(rs.getLong(1));
				result.setScreen_name(rs.getString(2));
				result.setTermoId(rs.getLong(3));
				result.setData(rs.getDate(4));
				result.setLatitude(rs.getString(5));
				result.setLongitude(rs.getString(6));
				result.setMonitorado_id(rs.getLong(7));

			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			stmt.close();
			conn.close();
		}

		return result;
	}

	@Override
	public List<Resultado> list() throws Exception {
		String query = "select * from resultado;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Resultado result = new Resultado();
		List<Resultado> listResult = new LinkedList<Resultado>();
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();

			while (rs.next()) {
				result = new Resultado();
				result.setId(rs.getLong(1));
				result.setScreen_name(rs.getString(2));
				result.setTermoId(rs.getLong(3));
				result.setData(rs.getDate(4));
				result.setLatitude(rs.getString(5));
				result.setLongitude(rs.getString(6));
				result.setMonitorado_id(rs.getLong(7));
				listResult.add(result);
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			stmt.close();
			conn.close();
		}

		return listResult;
	}

	@Override
	public void remove(Resultado objeto) throws Exception {
		String query = "delete from resultado where id = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, objeto.getId());
			stmt.execute();
			conn.commit();
			
		} catch (SQLException e) {
			conn.rollback();
			AppSNALog.error(e.toString());
		} finally {
			stmt.close();
			conn.close();
		}

	}
	
	public List<String> listaUsuariosMencionadors(int termo_id) throws Exception {
		String query = "select r.screen_name from resultado r, monitorado m" +
						"where r.monitorado_id = m.id and r.monitorado_id = ? group by r.screen_name;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<String> listResult = new ArrayList<String>();
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setInt(0, termo_id);			
			rs = stmt.executeQuery();
			String result = null;

			while (rs.next()) {
				result = rs.getString(0);
				listResult.add(result);
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			stmt.close();
			conn.close();
		}

		return listResult;
	}
	
	public List<String> listaUsuariosMencionadors(int termo_id, String aaaa_mm_dd) throws Exception {
		String query = "select r.screen_name from resultado r, monitorado m" +
						"where r.monitorado_id = m.id and r.monitorado_id = ? and" +
						"r.data > = ? group by r.screen_name;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<String> listResult = new ArrayList<String>();
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setInt(0, termo_id);	
			stmt.setString(1, aaaa_mm_dd);	
			rs = stmt.executeQuery();
			String result = null;

			while (rs.next()) {
				result = rs.getString(0);
				listResult.add(result);
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			stmt.close();
			conn.close();
		}

		return listResult;
	}
	
	
	

}
