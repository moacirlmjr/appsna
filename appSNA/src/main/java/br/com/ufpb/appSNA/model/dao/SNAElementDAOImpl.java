package br.com.ufpb.appSNA.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import br.com.ufpb.appSNA.model.beans.SNAElement;
import br.com.ufpb.appSNA.util.BDUtil;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.DAOUtil;


public class SNAElementDAOImpl implements SNAElementDAO {

	@Override
	public Long create(SNAElement objeto) throws Exception {
		String query = "Insert into usuario(id_usuario, nome, screen_name, biografia, localizacao, total_Following," +
			"total_Followers, total_Tweets, URL, timeZone, linguagem, data_criacao, url_imagem) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query, com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);			
			
			stmt.setLong(1, objeto.getId_usuario());
			stmt.setString(2, objeto.getNome());
			stmt.setString(3, objeto.getScreename());
			stmt.setString(4, objeto.getBiografia());
			stmt.setString(5, objeto.getLocalização());
			stmt.setInt(6, objeto.getTotalFollowing());
			stmt.setInt(7, objeto.getTotalFollowers());
			stmt.setInt(8, objeto.getTotalTweets());
			stmt.setString(9, objeto.getURL());
			stmt.setString(10, objeto.getTimeZone());
			stmt.setString(11, objeto.getLinguagem());
			stmt.setLong(12,  objeto.getDataDeCriacao());
			stmt.setString(13, objeto.getURLImagem());

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
	public void create(List<SNAElement> listaSNAElem) throws Exception {
		String query = "Insert into usuario(id_usuario, nome, screen_name, biografia, localizacao, total_Following," +
			"total_Followers, total_Tweets, URL, timeZone, linguagem, data_criacao, url_imagem) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";


		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			conn.setAutoCommit(false);
			// run sql objects
			stmt = conn.prepareStatement(query);
			int count = 0;
			for (SNAElement elem : listaSNAElem) {
				
				stmt.setLong(1, elem.getId_usuario());
				stmt.setString(2, elem.getNome());
				stmt.setString(3, elem.getScreename());
				stmt.setString(4, elem.getBiografia());
				stmt.setString(5, elem.getLocalização());
				stmt.setInt(6, elem.getTotalFollowing());
				stmt.setInt(7, elem.getTotalFollowers());
				stmt.setInt(8, elem.getTotalTweets());
				stmt.setString(9, elem.getURL());
				stmt.setString(10, elem.getTimeZone());
				stmt.setString(11, elem.getLinguagem());
				stmt.setLong(12,  elem.getDataDeCriacao());
				stmt.setString(13, elem.getURLImagem());

				stmt.addBatch();
				if (((listaSNAElem.size() - 1) < 20 && count % listaSNAElem.size() == 0) || (count != 0 && count % 20 == 0)) {
					stmt.executeBatch();
				}
				count ++;
			}
			conn.commit();
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
			e.printStackTrace();
		} finally {
			conn.close();
		}

		
	}

	@Override
	public Long update(SNAElement objeto) throws Exception {
		String query = "update Usuario set nome = ?, screen_name = ?, biografia = ?, localizacao = ?, total_Following = ?," +
				"total_Followers = ?, total_Tweets = ?, URL = ?, timeZone = ?, linguagem = ?, data_criacao = ?, url_imagem = ? " +
				"where id_usuario = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			
			stmt.setString(1, objeto.getNome());
			stmt.setString(2, objeto.getScreename());
			stmt.setString(3, objeto.getBiografia());
			stmt.setString(4, objeto.getLocalização());
			stmt.setInt(5, objeto.getTotalFollowing());
			stmt.setInt(6, objeto.getTotalFollowers());
			stmt.setInt(7, objeto.getTotalTweets());
			stmt.setString(8, objeto.getURL());
			stmt.setString(9, objeto.getTimeZone());
			stmt.setString(10, objeto.getLinguagem());
			stmt.setLong(11, objeto.getDataDeCriacao());
			stmt.setString(12, objeto.getURLImagem());
			stmt.setLong(13, objeto.getId());
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
	public SNAElement findById(Long id) throws Exception {
		String query = "select * from Usuario where id_usuario = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		SNAElement elem = new SNAElement();

		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.getResultSet();

			while (rs.next()) {
				elem.setId(rs.getLong(0));
				elem.setNome(rs.getString(1));
				elem.setScreename(rs.getString(2));
				elem.setBiografia(rs.getString(3));
				elem.setLocalização(rs.getString(4));
				elem.setTotalFollowing(rs.getInt(5));
				elem.setTotalFollowers(rs.getInt(6));
				elem.setTotalTweets(rs.getInt(7));
				elem.setURL(rs.getString(8));
				elem.setTimeZone(rs.getString(9));
				elem.setLinguagem(rs.getString(10));
				elem.setDataDeCriacao(rs.getLong(11));
				elem.setURLImagem(rs.getString(12));
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

		return elem;

	}

	@Override
	public List<SNAElement> list() throws Exception {
		String query = "select * from Usuario;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		SNAElement elem = new SNAElement();
		List<SNAElement> listElem = new LinkedList<SNAElement>();
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();

			while (rs.next()) {
				
				elem = new SNAElement();
				
				elem.setId_usuario(rs.getLong(1));
				elem.setNome(rs.getString(2));
				elem.setScreename(rs.getString(3));
				elem.setBiografia(rs.getString(4));
				elem.setLocalização(rs.getString(5));
				elem.setTotalFollowing(rs.getInt(6));
				elem.setTotalFollowers(rs.getInt(7));
				elem.setTotalTweets(rs.getInt(8));
				elem.setURL(rs.getString(9));
				elem.setTimeZone(rs.getString(10));
				elem.setLinguagem(rs.getString(11));
				elem.setDataDeCriacao(rs.getLong(12));
				elem.setURLImagem(rs.getString(13));
				
				listElem.add(elem);
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

		return listElem;

	}

	@Override
	public void remove(SNAElement objeto) throws Exception {
		String query = "delete from Usuario where id_usuario = ?;";

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





