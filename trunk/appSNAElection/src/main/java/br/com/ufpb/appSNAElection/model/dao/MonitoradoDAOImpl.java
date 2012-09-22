package br.com.ufpb.appSNAElection.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import br.com.ufpb.appSNAElection.model.beans.Monitorado;
import br.com.ufpb.appSNAElection.util.BDUtil;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.DAOUtil;

import com.mysql.jdbc.Statement;

public class MonitoradoDAOImpl implements MonitoradoDAO {

	@Override
	public Long create(Monitorado objeto) throws Exception {
		String query = "Insert into monitorado(twitter_id, screen_name) values(?,?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setLong(1, objeto.getTwitterId());
			stmt.setString(2, objeto.getScreen_name());
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
	public Long update(Monitorado objeto) throws Exception {
		String query = "update monitorado set twitterId = ?, screen_name = ? where id = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, objeto.getTwitterId());
			stmt.setString(2, objeto.getScreen_name());
			stmt.setLong(3, objeto.getId());
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
	public Monitorado findById(Long id) throws Exception {
		String query = "select * from monitorado where id = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Monitorado monitorado = new Monitorado();

		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.getResultSet();

			while (rs.next()) {
				monitorado.setId(rs.getLong(0));
				monitorado.setScreen_name(rs.getString(1));
				monitorado.setTwitterId(rs.getLong(2));
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

		return monitorado;
	}

	@Override
	public List<Monitorado> list() throws Exception {
		String query = "select * from monitorado;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Monitorado monitorado = new Monitorado();
		List<Monitorado> listMonitorado = new LinkedList<Monitorado>();
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			rs = stmt.getResultSet();

			while (rs.next()) {
				monitorado = new Monitorado();
				monitorado.setId(rs.getLong(0));
				monitorado.setScreen_name(rs.getString(1));
				monitorado.setTwitterId(rs.getLong(2));
				listMonitorado.add(monitorado);
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

		return listMonitorado;
	}

	@Override
	public void remove(Monitorado objeto) throws Exception {
		String query = "delete from monitorado where id = ?;";

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

	@Override
	public void create(List<Monitorado> objeto) throws Exception {
		String query = "Insert into monitorado values(?,?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			conn.setAutoCommit(false);
			// run sql objects
			stmt = conn.prepareStatement(query);
			int count = 0;
			for (Monitorado monitorado : objeto) {
				stmt.setLong(1, monitorado.getTwitterId());
				stmt.setString(2, monitorado.getScreen_name());
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
}
