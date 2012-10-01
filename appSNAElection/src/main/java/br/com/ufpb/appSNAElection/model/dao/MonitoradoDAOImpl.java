package br.com.ufpb.appSNAElection.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.com.ufpb.appSNAElection.model.beans.Monitorado;
import br.com.ufpb.appSNAElection.model.beans.to.RelatorioOcorrenciasTO;
import br.com.ufpb.appSNAElection.util.BDUtil;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.DAOUtil;

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
			conn.setAutoCommit(false);
			stmt = conn
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setLong(1, objeto.getTwitterId());
			stmt.setString(2, objeto.getScreen_name());
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
	public Long update(Monitorado objeto) throws Exception {
		String query = "update monitorado set twitterId = ?, screen_name = ? where id = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, objeto.getTwitterId());
			stmt.setString(2, objeto.getScreen_name());
			stmt.setLong(3, objeto.getId());
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
			rs = stmt.executeQuery();

			while (rs.next()) {
				monitorado.setId(rs.getLong(1));
				monitorado.setScreen_name(rs.getString(2));
				monitorado.setTwitterId(rs.getLong(3));
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			stmt.close();
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
			rs = stmt.executeQuery();

			while (rs.next()) {
				monitorado = new Monitorado();
				monitorado.setId(rs.getLong(1));
				monitorado.setScreen_name(rs.getString(2));
				monitorado.setTwitterId(rs.getLong(3));
				listMonitorado.add(monitorado);
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			stmt.close();
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

	@Override
	public void create(List<Monitorado> objeto) throws Exception {
		String query = "Insert into monitorado values(?,?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			conn.setAutoCommit(false);
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
			conn.rollback();
			AppSNALog.error(e.toString());
		} finally {
			stmt.close();
			conn.close();
		}
	}

	@Override
	public List<RelatorioOcorrenciasTO> listRelatorioOcorrencia()
			throws Exception {
		String query = "select m.screen_name,r.screen_name, count(r.screen_name) "
				+ "from resultado r, monitorado m "
				+ "where r.monitorado_id = m.id and r.monitorado_id in (5, 6, 8, 11) "
				+ "group by r.screen_name " + "order by m.screen_name;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<RelatorioOcorrenciasTO> listRO = new LinkedList<RelatorioOcorrenciasTO>();
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);

			stmt = conn.prepareStatement(query);

			rs = stmt.executeQuery();

			while (rs.next()) {
				RelatorioOcorrenciasTO ro = new RelatorioOcorrenciasTO();
				ro.setSource(rs.getString(1));
				ro.setTarget(rs.getString(2));
				ro.setWeight(rs.getInt(3));
				listRO.add(ro);
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			stmt.close();
			conn.close();
		}

		return listRO;
	}

}
