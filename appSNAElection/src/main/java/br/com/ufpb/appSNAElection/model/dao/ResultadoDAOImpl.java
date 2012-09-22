package br.com.ufpb.appSNAElection.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import br.com.ufpb.appSNAElection.model.beans.Resultado;
import br.com.ufpb.appSNAElection.util.BDUtil;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.DAOUtil;

public class ResultadoDAOImpl implements ResultadoDAO {

	@Override
	public Long create(Resultado objeto) throws Exception {
		String query = "Insert into resultado(screen_name,termo_id,data,latitude,longitude,monitorado_id) values(?,?,?,?,?,?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setString(1, objeto.getScreen_name());
			stmt.setLong(2, objeto.getTermoId());
			stmt.setDate(3, (Date) objeto.getData());
			stmt.setFloat(4, objeto.getLatitude());
			stmt.setFloat(5, objeto.getLongitude());
			stmt.setLong(6, objeto.getMonitorado_id());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			while (rs.next()) {
				result = rs.getLong(1);
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}
		return result;
	}

	@Override
	public void create(List<Resultado> objeto) throws Exception {
		String query = "Insert into resultado values(?,?,?,?,?,?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			conn.setAutoCommit(false);
			// run sql objects
			stmt = conn.prepareStatement(query);
			int count = 0;
			for (Resultado r : objeto) {
				stmt.setString(1, r.getScreen_name());
				stmt.setLong(2, r.getTermoId());
				stmt.setDate(3, (Date) r.getData());
				stmt.setFloat(4, r.getLatitude());
				stmt.setFloat(5, r.getLongitude());
				stmt.setLong(6, r.getMonitorado_id());
				stmt.addBatch();
				if (++count % objeto.size() == 0) {
					stmt.executeBatch();
				}
			}
			conn.commit();
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
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
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setString(1, objeto.getScreen_name());
			stmt.setLong(2, objeto.getTermoId());
			stmt.setDate(3, (Date) objeto.getData());
			stmt.setFloat(4, objeto.getLatitude());
			stmt.setFloat(5, objeto.getLongitude());
			stmt.setLong(6, objeto.getMonitorado_id());
			stmt.setLong(7, objeto.getId());
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			while (rs.next()) {
				result = rs.getLong(1);
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
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
			rs = stmt.getResultSet();

			while (rs.next()) {
				result.setId(rs.getLong(0));
				result.setScreen_name(rs.getString(1));
				result.setTermoId(rs.getLong(2));
				result.setData(rs.getDate(3));
				result.setLatitude(rs.getFloat(4));
				result.setLongitude(rs.getFloat(5));
				result.setMonitorado_id(rs.getLong(6));

			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
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
			rs = stmt.getResultSet();

			while (rs.next()) {
				result = new Resultado();
				result.setId(rs.getLong(0));
				result.setScreen_name(rs.getString(1));
				result.setTermoId(rs.getLong(2));
				result.setData(rs.getDate(3));
				result.setLatitude(rs.getLong(4));
				result.setLongitude(rs.getLong(5));
				result.setMonitorado_id(rs.getLong(6));
				listResult.add(result);
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
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
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, objeto.getId());
			stmt.execute();
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

	}

}
