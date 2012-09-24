package br.com.ufpb.appSNA.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.com.ufpb.appSNA.model.beans.Relacionamento;
import br.com.ufpb.appSNA.util.BDUtil;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.DAOUtil;


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
	public void create(List<Relacionamento> objeto) throws Exception {
		String query = "Insert into relacionamento (id_source, id_target) values(?,?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			conn.setAutoCommit(false);
			// run sql objects
			stmt = conn.prepareStatement(query);
			int count = 0;
			for (Relacionamento rel : objeto) {
				stmt.setLong(0, rel.getId_source());
				stmt.setLong(1, rel.getId_target());
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
	public Long update(Relacionamento objeto) throws Exception {
		String query = "update relacionamento set id_source = ?, id_target = ? where id = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
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
	public Relacionamento findById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Relacionamento> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Relacionamento objeto) throws Exception {
		// TODO Auto-generated method stub

	}

}
