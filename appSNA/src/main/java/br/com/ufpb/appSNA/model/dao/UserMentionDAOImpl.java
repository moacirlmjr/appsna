package br.com.ufpb.appSNA.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.com.ufpb.appSNA.model.beans.Status;
import br.com.ufpb.appSNA.model.beans.UserMention;
import br.com.ufpb.appSNA.util.BDUtil;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.DAOUtil;

public class UserMentionDAOImpl implements UserMentionDAO {

	@Override
	public Long create(UserMention objeto) throws Exception {
		String query = "Insert into UserMention (id_usuario, id_status, usuario) values(?, ?, ?);";
		
		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			stmt.setLong(1, objeto.getId_usuario());
			stmt.setLong(2, objeto.getId_status());
			stmt.setString(3, objeto.getUsuario());
			
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
	public void create(List<UserMention> listaUserMentions) throws Exception {
		String query = "Insert into UserMention (id_usuario, id_status, usuario) values(?, ?, ?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,BDUtil.SENHA);
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(query);
			int count = 0;
			for (UserMention user : listaUserMentions) {


				stmt.setLong(0, user.getId_usuario());
				stmt.setLong(1, user.getId_status());
				stmt.setString(2, user.getUsuario());
				
				stmt.addBatch();
				if (((listaUserMentions.size() - 1) < 20 && count
						% listaUserMentions.size() == 0)
						|| (count != 0 && count % 20 == 0)) {
					stmt.executeBatch();
				}
				count++;
			}
			conn.commit();
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

	}

	@Override
	public Long update(UserMention objeto) throws Exception {
		String query = "update UserMention set id_usuario = ?, id_status = ?, usuario = ?" +
				"where id_usermention = ?";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			
			stmt.setLong(0, objeto.getId_usuario());
			stmt.setLong(1, objeto.getId_status());
			stmt.setString(2, objeto.getUsuario());
			
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
	public UserMention findById(Long id) throws Exception {
		String query = "select * from UserMention where id_usermention = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		UserMention user = new UserMention();

		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.getResultSet();

			while (rs.next()) {
				user.setId_usuario(rs.getLong(0));
				user.setId_status(rs.getLong(1));
				user.setId_usermention(rs.getLong(2));
				user.setUsuario(rs.getString(4));				
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}
		
		return user;

	}

	@Override
	public List<UserMention> list() throws Exception {
		String query = "select * from UserMention;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		UserMention user = new UserMention();
		List<UserMention> listUsers = new LinkedList<UserMention>();
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			rs = stmt.getResultSet();

			while (rs.next()) {
				user = new UserMention();
				user.setId_usuario(rs.getLong(0));
				user.setId_status(rs.getLong(1));
				user.setId_usermention(rs.getLong(2));
				user.setUsuario(rs.getString(4));				
				listUsers.add(user);
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

		return listUsers;

	}

	@Override
	public void remove(UserMention objeto) throws Exception {
		String query = "delete from UserMention where id_usermention = ?;";

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
