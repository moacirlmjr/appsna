package br.com.puc.appSNA.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import br.com.puc.appSNA.model.beans.Status;
import br.com.puc.appSNA.util.AppSNALog;
import br.com.puc.appSNA.util.Constantes;
import br.com.puc.appSNA.util.DAOUtil;

import com.mysql.jdbc.Statement;

public class StatusDAOImpl implements StatusDAO {

	@Override
	public Long create(Status objeto) throws Exception {
		String query = "Insert into status (id_status, id_usuario, data_criacao, texto, longitude, " +
				"latitude, total_retweet, is_retweeted, is_retweet, linguagem) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(Constantes.URL, Constantes.USER, Constantes.SENHA);
			stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setLong(1, objeto.getId_status());
			stmt.setLong(2, objeto.getId_usuario());
			stmt.setTimestamp(3, new Timestamp(objeto.getDataDeCriacao().getTime()));
			stmt.setString(4, objeto.getTexto());
			stmt.setString(5, String.valueOf(objeto.getLongitude()));
			stmt.setString(6, String.valueOf(objeto.getLatitude()));
			stmt.setLong(7, objeto.getTotalRetweet());
			stmt.setInt(8, objeto.isRetweeted()? 1 : 0);
			stmt.setInt(9, objeto.isRetweet() ? 1 : 0);
			stmt.setString(10, objeto.getLinguagem());

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
	public void create(List<Status> listaStatus) throws Exception {
		String query = "Insert into status (id_usuario, id_status, data_criacao, texto, longitude, " +
				"latitude, total_retweet, is_retweeted, is_retweet, linguagem) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(Constantes.URL, Constantes.USER,Constantes.SENHA);
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(query);
			int count = 0;
			for (Status sta : listaStatus) {
				
				stmt.setLong(0, sta.getId_usuario());
				stmt.setLong(1, sta.getId_status());
				stmt.setTimestamp(2, new Timestamp(sta.getDataDeCriacao().getTime()));
				stmt.setString(3, sta.getTexto());
				stmt.setString(4, String.valueOf(sta.getLongitude()));
				stmt.setString(5, String.valueOf(sta.getLatitude()));
				stmt.setLong(6, sta.getTotalRetweet());
				stmt.setInt(7, sta.isRetweeted()? 1 : 0);
				stmt.setInt(9, sta.isRetweet() ? 1 : 0);
				stmt.setString(10, sta.getLinguagem());
				
				stmt.addBatch();
				if (((listaStatus.size() - 1) < 20 && count % listaStatus.size() == 0) || (count != 0 && count % 20 == 0)) {
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
	public Long update(Status objeto) throws Exception {
		String query = "update status set id_usuario = ?, data_criacao = ?, texto = ?, longitude = ?, " +
		"latitude = ?, total_retweet = ?, is_retweeted = ? ) values(?, ?, ?, ?, ?, ?, ?)" +
		"where id_status = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(Constantes.URL, Constantes.USER, Constantes.SENHA);
			stmt = conn.prepareStatement(query);
			
			stmt.setLong(0, objeto.getId_usuario());
			stmt.setTimestamp(1, new Timestamp(objeto.getDataDeCriacao().getTime()));
			stmt.setString(2, objeto.getTexto());
			stmt.setString(3, String.valueOf(objeto.getLongitude()));
			stmt.setString(4, String.valueOf(objeto.getLatitude()));
			stmt.setLong(4, objeto.getTotalRetweet());
			stmt.setInt(6, !objeto.isRetweeted() ? 0 : 1);
			
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
	public Status findById(Long id) throws Exception {
		String query = "select * from status where id_status = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Status sta = new Status();

		try {
			conn = DAOUtil.returnConnection(Constantes.URL, Constantes.USER, Constantes.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.getResultSet();

			while (rs.next()) {
				sta.setId_status(rs.getLong(0));
				sta.setId_usuario(rs.getLong(1));
				sta.setDataDeCriacao(rs.getTimestamp(2));
				sta.setTexto(rs.getString(3));
				sta.setLongitude(rs.getLong(4));
				sta.setLatitude(rs.getLong(5));
				sta.setTotalRetweet(rs.getInt(6));
				sta.setRetweeted(rs.getInt(7) == 0 ? false : true);				
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}
		
		return sta;

	}

	@Override
	public List<Status> list() throws Exception {
		String query = "select * from status;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Status sta = new Status();
		List<Status> listStatus = new LinkedList<Status>();
		try {
			conn = DAOUtil.returnConnection(Constantes.URL, Constantes.USER, Constantes.SENHA);
			stmt = conn.prepareStatement(query);
			rs = stmt.getResultSet();

			while (rs.next()) {
				sta = new Status();
				sta.setId_status(rs.getLong(0));
				sta.setId_usuario(rs.getLong(1));
				sta.setDataDeCriacao(rs.getTimestamp(2));
				sta.setTexto(rs.getString(3));
				sta.setLongitude(rs.getLong(4));
				sta.setLatitude(rs.getLong(5));
				sta.setTotalRetweet(rs.getInt(6));
				sta.setRetweeted(rs.getInt(7) == 0 ? false : true);				
				listStatus.add(sta);
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

		return listStatus;

	}

	@Override
	public void remove(Status objeto) throws Exception {
		String query = "delete from status where id_status = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(Constantes.URL, Constantes.USER, Constantes.SENHA);
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

	
