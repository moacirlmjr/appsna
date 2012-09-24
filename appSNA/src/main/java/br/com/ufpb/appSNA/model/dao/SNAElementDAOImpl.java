package br.com.ufpb.appSNA.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import br.com.ufpb.appSNA.model.beans.SNAElement;
import br.com.ufpb.appSNA.util.BDUtil;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.DAOUtil;


public class SNAElementDAOImpl implements SNAElementDAO {

	@Override
	public Long create(SNAElement objeto) throws Exception {
		String query = "Insert into elemento(nome, screen_name, biografia, localizacao, totalFollowing," +
				"totalFollowers, totalTweets, URL, timeZone, linguagem, dataDeCricao, URLImagem) values(?,?,?,?,?,?,?,?,?,?,?,?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		SNAElement elem = new SNAElement();
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query, com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);			
			
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
			stmt.setDate(11, (Date) objeto.getDataDeCriacao());
			stmt.setString(12, objeto.getURLImagem());

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
	public void create(List<SNAElement> objeto) throws Exception {
		String query = "Insert into elemento(nome, screen_name, biografia, localizacao, totalFollowing," +
			"totalFollowers, totalTweets, URL, timeZone, linguagem, dataDeCricao, URLImagem) values(?,?,?,?,?,?,?,?,?,?,?,?);";


		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			conn.setAutoCommit(false);
			// run sql objects
			stmt = conn.prepareStatement(query);
			int count = 0;
			for (SNAElement elem : objeto) {
				stmt.setString(1, elem.getNome());
				stmt.setString(2, elem.getScreename());
				stmt.setString(3, elem.getBiografia());
				stmt.setString(4, elem.getLocalização());
				stmt.setInt(5, elem.getTotalFollowing());
				stmt.setInt(6, elem.getTotalFollowers());
				stmt.setInt(7, elem.getTotalTweets());
				stmt.setString(8, elem.getURL());
				stmt.setString(9, elem.getTimeZone());
				stmt.setString(10, elem.getLinguagem());
				stmt.setDate(11, (Date) elem.getDataDeCriacao());
				stmt.setString(12, elem.getURLImagem());

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
	public Long update(SNAElement objeto) throws Exception {
		String query = "update elemento set nome = ?, screen_name = ?, biografia = ?, localizacao = ?, totalFollowing = ?," +
				"totalFollowers = ?, totalTweets = ?, URL = ?, timeZone = ?, linguagem = ?, dataDeCricao = ?, URLImagem = ? where id = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
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
			stmt.setDate(11, (Date) objeto.getDataDeCriacao());
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
		String query = "select * from monitorado where id = ?;";

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
				elem.setDataDeCriacao(rs.getDate(11));
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
		String query = "select * from elemento;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		SNAElement elem = new SNAElement();
		List<SNAElement> listElem = new LinkedList<SNAElement>();
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			rs = stmt.getResultSet();

			while (rs.next()) {
				elem = new SNAElement();
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
				elem.setDataDeCriacao(rs.getDate(11));
				elem.setURLImagem(rs.getString(12));
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
		String query = "delete from elemento where id = ?;";

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





