package br.com.ufpb.appSNA.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import br.com.ufpb.appSNA.model.beans.Relacionamento;
import br.com.ufpb.appSNA.util.BDUtil;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.DAOUtil;

import com.mysql.jdbc.Statement;


public class RelacionamentoDAOImpl implements RelacionamentoDAO {

	@Override
	public Long create(Relacionamento objeto) throws Exception {
		String query = "Insert into relacionamento (id_source, id_target) values(?,?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			stmt.setLong(0, objeto.getId_source());
			stmt.setLong(1, objeto.getId_target());

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
	public void create(List<Relacionamento> listaRelacionamentos) throws Exception {
		String query = "Insert into relacionamento (id_source, id_target) values(?,?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,BDUtil.SENHA);
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(query);
			int count = 0;
			for (Relacionamento rel : listaRelacionamentos) {
				stmt.setLong(0, rel.getId_source());
				stmt.setLong(1, rel.getId_target());
				stmt.addBatch();
				if (((listaRelacionamentos.size() - 1) < 20 && count % listaRelacionamentos.size() == 0) || (count != 0 && count % 20 == 0)) {
					stmt.executeBatch();
				}
				count ++;
			}
			conn.commit();
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

	}

	@Override
	public Long update(Relacionamento objeto) throws Exception {
		String query = "update relacionamento set id_source = ?, id_target = ? where id_relacionamento = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);

			stmt.setLong(0, objeto.getId_source());
			stmt.setLong(1, objeto.getId_target());
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
	public Relacionamento findById(Long id_source) throws Exception {
		String query = "select * from relacionamento where id_relacionamento = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Relacionamento rel = new Relacionamento();

		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, id_source);
			rs = stmt.getResultSet();

			while (rs.next()) {
				rel.setId_source(rs.getLong(1));
				rel.setId_target(rs.getLong(2));			
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

		return rel;

	}

	@Override
	public List<Relacionamento> list() throws Exception {
		String query = "select * from relacionamento;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Relacionamento rel = new Relacionamento();
		List<Relacionamento> listRel = new LinkedList<Relacionamento>();
		
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			rs = stmt.getResultSet();

			while (rs.next()) {
				rel = new Relacionamento();
				rel.setId(rs.getLong(0));
				rel.setId_source(rs.getLong(1));
				rel.setId_target(rs.getLong(2));				
				listRel.add(rel);
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

		return listRel;
	}

	@Override
	public void remove(Relacionamento objeto) throws Exception {
		String query = "delete from relacionamento where id_relacionamento = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
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
