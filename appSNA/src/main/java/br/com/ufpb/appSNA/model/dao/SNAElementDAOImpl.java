package br.com.ufpb.appSNA.model.dao;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.com.ufpb.appSNA.model.beans.SNAElement;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.DAOUtil;


public class SNAElementDAOImpl implements SNAElementDAO {

	@Override
	public Long create(SNAElement objeto) throws Exception {
		String query = "Insert into elemento(twitter_id, screen_name) values(?,?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		SNAElement elem = new SNAElement();
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);			
			
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long update(SNAElement objeto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SNAElement findById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SNAElement> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(SNAElement objeto) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
