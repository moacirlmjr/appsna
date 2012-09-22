package br.com.ufpb.appSNAElection.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import br.com.ufpb.appSNAElection.model.beans.Monitorado;
import br.com.ufpb.appSNAElection.model.beans.Termo;
import br.com.ufpb.appSNAElection.util.BDUtil;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.DAOUtil;

public class TermoDAOImpl implements TermoDAO {

	@Override
	public Long create(Termo objeto) throws Exception {
		String query = "Insert into termo(monitorado_id, conteudo) values(?,?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, objeto.getMonitorado_id());
			stmt.setString(2, objeto.getConteudo());
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
	public Long update(Termo objeto) throws Exception {
		String query = "update termo set conteudo = ? where id = ? and monitorado_id = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setString(1, objeto.getConteudo());
			stmt.setLong(2, objeto.getId());
			stmt.setLong(3, objeto.getMonitorado_id());
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
	public Termo findById(Long id) throws Exception {
		String query = "select * from termo where id = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Termo termo = new Termo();

		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.getResultSet();

			while (rs.next()) {
				termo.setId(rs.getLong(0));
				termo.setMonitorado_id(rs.getLong(1));
				termo.setConteudo(rs.getString(2));
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

		return termo;
	}

	@Override
	public List<Termo> list() throws Exception {
		String query = "select * from termo;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Termo termo = new Termo();
		List<Termo> listTermo = new LinkedList<Termo>();
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			rs = stmt.getResultSet();

			while (rs.next()) {
				termo = new Termo();
				termo.setId(rs.getLong(0));
				termo.setMonitorado_id(rs.getLong(1));
				termo.setConteudo(rs.getString(2));
				listTermo.add(termo);
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

		return listTermo;
	}

	@Override
	public void remove(Termo objeto) throws Exception {
		String query = "delete from termo where id = ?;";

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
	public void create(List<Termo> objeto) throws Exception {
		String query = "Insert into termo(monitorado_id, conteudo) values(?,?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			conn.setAutoCommit(false);
			// run sql objects
			stmt = conn.prepareStatement(query);
			int count = 0;
			for (Termo termo : objeto) {
				stmt.setLong(1, termo.getMonitorado_id());
				stmt.setString(2, termo.getConteudo());
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
	public Monitorado getMonitoradoByTermo(String termo) throws Exception {
		String query = "select distinct m.id, m.screen_name, m.twitter_id from termo t, monitorado m where (t.conteudo like ? or m.screen_name like ?)and t.monitorado_id = m.id;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Monitorado monitorado = new Monitorado();

		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setString(1, termo);
			stmt.setString(2, termo);
			rs = stmt.executeQuery();

			while (rs.next()) {
				monitorado.setId(rs.getLong("id"));
				monitorado.setScreen_name(rs.getString("screen_name"));
				monitorado.setTwitterId(rs.getLong("twitter_id"));
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

		return monitorado;
	}
	
	@Override
	public Termo getTermoByConteudo(String conteudo) throws Exception {
		String query = "select * from termo t where t.conteudo = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Termo termo = new Termo();

		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setString(1, conteudo);
			rs = stmt.getResultSet();

			while (rs.next()) {
				termo.setId(rs.getLong(0));
				termo.setMonitorado_id(rs.getLong(1));
				termo.setConteudo(rs.getString(2));
				break;
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

		return termo;
	}
}