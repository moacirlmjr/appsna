package br.com.ufpb.appSNA.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.com.ufpb.appSNA.model.beans.UrlMention;
import br.com.ufpb.appSNA.model.beans.UserMention;
import br.com.ufpb.appSNA.util.BDUtil;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.DAOUtil;

public class URLMentionDAOImpl implements URLMentionDAO {

	@Override
	public Long create(UrlMention objeto) throws Exception {
		String query = "Insert into URLMention (id_usuario, id_status, url) values(?, ?, ?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn
					.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			stmt.setLong(0, objeto.getId_usuario());
			stmt.setLong(1, objeto.getId_status());
			stmt.setString(2, objeto.getUrl());
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
	public void create(List<UrlMention> listaUrlMentions) throws Exception {
		String query = "Insert into URLMention (id_usuario, id_status, url) values(?, ?, ?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(query);
			int count = 0;
			for (UrlMention url : listaUrlMentions) {

				stmt.setLong(0, url.getId_usuario());
				stmt.setLong(1, url.getId_status());
				stmt.setString(2, url.getUrl());

				stmt.addBatch();
				if (((listaUrlMentions.size() - 1) < 20 && count
						% listaUrlMentions.size() == 0)
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
	public Long update(UrlMention objeto) throws Exception {
		String query = "update URLMention set id_usuario = ?, id_status = ?, url = ?"
				+ "where id_urlmention = ?";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);

			stmt.setLong(0, objeto.getId_usuario());
			stmt.setLong(1, objeto.getId_status());
			stmt.setString(2, objeto.getUrl());

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
	public UrlMention findById(Long id) throws Exception {
		String query = "select * from URLMention where id_urlmention = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		UrlMention url = new UrlMention();

		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.getResultSet();

			while (rs.next()) {
				url.setId_usuario(rs.getLong(0));
				url.setId_status(rs.getLong(1));
				url.setId_urlmention(rs.getLong(2));
				url.setUrl(rs.getString(4));				
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}
		
		return url;

	}

	@Override
	public List<UrlMention> list() throws Exception {
		String query = "select * from URLMention;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		UrlMention url = new UrlMention();
		List<UrlMention> listUrl = new LinkedList<UrlMention>();
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			rs = stmt.getResultSet();

			while (rs.next()) {
				url = new UrlMention();
				url.setId_usuario(rs.getLong(0));
				url.setId_status(rs.getLong(1));
				url.setId_urlmention(rs.getLong(2));
				url.setUrl(rs.getString(4));				
				listUrl.add(url);
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

		return listUrl;

	}

	@Override
	public void remove(UrlMention objeto) throws Exception {
		String query = "delete from URLMention where id_urlmention = ?;";

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
