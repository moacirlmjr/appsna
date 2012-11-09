package br.com.ufpb.appSNA.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.com.ufpb.appSNA.model.beans.HashTagMention;
import br.com.ufpb.appSNA.model.beans.UserMention;
import br.com.ufpb.appSNA.util.BDUtil;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.DAOUtil;

public class HashTagMentionDAOImpl implements HashTagMentionDAO {

	 	  
	@Override
	public Long create(HashTagMention objeto) throws Exception {
		 String query = "Insert into hashtagmention (id_usuario, id_status, hashtag) values(?, ?, ?);";
			
			PreparedStatement stmt = null;
			Connection conn = null;
			Long result = null;
			try {
				conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
				stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

				stmt.setLong(1, objeto.getId_usuario());
				stmt.setLong(2, objeto.getId_status());
				stmt.setString(3, objeto.getHashtag());
				
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
	public void create(List<HashTagMention> listaHash) throws Exception {
		String query = "Insert into hashtagmention (id_usuario, id_status, hashtag) values(?, ?, ?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,BDUtil.SENHA);
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(query);
			int count = 0;
			
			for (HashTagMention hash : listaHash) {

				stmt.setLong(0, hash.getId_usuario());
				stmt.setLong(1, hash.getId_status());
				stmt.setString(2, hash.getHashtag());
				
				stmt.addBatch();
				if (((listaHash.size() - 1) < 20 && count
						% listaHash.size() == 0)
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
	public Long update(HashTagMention objeto) throws Exception {
		String query = "update hashtagmention set id_usuario = ?, id_status = ?, hashtag = ?"
				+ "where id_hashtagmention = ?";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);

			stmt.setLong(0, objeto.getId_usuario());
			stmt.setLong(1, objeto.getId_status());
			stmt.setString(2, objeto.getHashtag());

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
	public HashTagMention findById(Long id) throws Exception {
		String query = "select * from hashtagmention where id_hashtagmention = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		HashTagMention hash = new HashTagMention();

		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.getResultSet();

			while (rs.next()) {
				hash.setId_usuario(rs.getLong(0));
				hash.setId_status(rs.getLong(1));
				hash.setId_hashtagmention(rs.getLong(2));
				hash.setHashtag(rs.getString(4));				
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}
		
		return hash;

	}

	@Override
	public List<HashTagMention> list() throws Exception {
		String query = "select * from hashtagmention;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		HashTagMention hash = new HashTagMention();
		List<HashTagMention> listHash = new LinkedList<HashTagMention>();
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			rs = stmt.getResultSet();

			while (rs.next()) {
				hash = new HashTagMention();
				hash.setId_usuario(rs.getLong(0));
				hash.setId_status(rs.getLong(1));
				hash.setId_hashtagmention(rs.getLong(2));
				hash.setHashtag(rs.getString(4));				
				listHash.add(hash);
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

		return listHash;

	}

	@Override
	public void remove(HashTagMention objeto) throws Exception {
		String query = "delete from hashtagmention where id_hashtagmention = ?;";

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
