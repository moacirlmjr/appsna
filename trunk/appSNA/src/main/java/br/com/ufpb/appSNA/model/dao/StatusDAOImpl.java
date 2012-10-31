package br.com.ufpb.appSNA.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.com.ufpb.appSNA.model.beans.Relacionamento;
import br.com.ufpb.appSNA.model.beans.SNAElement;
import br.com.ufpb.appSNA.model.beans.Status;
import br.com.ufpb.appSNA.util.BDUtil;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.DAOUtil;

public class StatusDAOImpl implements StatusDAO {

	@Override
	public Long create(Status objeto) throws Exception {
		String query = "Insert into Status (id_usuario, data_criacao, texto, longitude, " +
				"latitude, total_retweet, is_retweeted) values(?, ?, ?, ?, ?, ?, ?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			stmt.setLong(0, objeto.getId_usuario());
			stmt.setDate(1, (Date) objeto.getDataDeCriacao());
			stmt.setString(2, objeto.getTexto());
			stmt.setString(3, String.valueOf(objeto.getLongitude()));
			stmt.setString(4, String.valueOf(objeto.getLatitude()));
			stmt.setInt(4, objeto.getTotalRetweet());
			stmt.setInt(6, objeto.isRetweeted()==false ? 0 : 1);

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
		String query = "Insert into Status (id_usuario, data_criacao, texto, longitude, " +
						"latitude, total_retweet, is_retweeted) values(?, ?, ?, ?, ?, ?, ?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,BDUtil.SENHA);
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(query);
			int count = 0;
			for (Status sta : listaStatus) {
				
				stmt.setLong(0, sta.getId_usuario());
				stmt.setDate(1, (Date) sta.getDataDeCriacao());
				stmt.setString(2, sta.getTexto());
				stmt.setString(3, String.valueOf(sta.getLongitude()));
				stmt.setString(4, String.valueOf(sta.getLatitude()));
				stmt.setInt(4, sta.getTotalRetweet());
				stmt.setInt(6, sta.isRetweeted()==false ? 0 : 1);
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
		String query = "update Status set id_usuario = ?, data_criacao = ?, texto = ?, longitude = ?, " +
		"latitude = ?, total_retweet = ?, is_retweeted = ? ) values(?, ?, ?, ?, ?, ?, ?)" +
		"where id_status = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			
			stmt.setLong(0, objeto.getId_usuario());
			stmt.setDate(1, (Date) objeto.getDataDeCriacao());
			stmt.setString(2, objeto.getTexto());
			stmt.setString(3, String.valueOf(objeto.getLongitude()));
			stmt.setString(4, String.valueOf(objeto.getLatitude()));
			stmt.setInt(4, objeto.getTotalRetweet());
			stmt.setInt(6, objeto.isRetweeted()==false ? 0 : 1);
			
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
		String query = "select * from Status where id_status = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Status sta = new Status();

		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.getResultSet();

			while (rs.next()) {
				sta.setId_status(rs.getLong(0));
				sta.setId_usuario(rs.getLong(1));
				sta.setDataDeCriacao(rs.getDate(2));
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
		String query = "select * from Status;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Status sta = new Status();
		List<Status> listStatus = new LinkedList<Status>();
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER, BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			rs = stmt.getResultSet();

			while (rs.next()) {
				sta = new Status();
				sta.setId_status(rs.getLong(0));
				sta.setId_usuario(rs.getLong(1));
				sta.setDataDeCriacao(rs.getDate(2));
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
		String query = "delete from Status where id_status = ?;";

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

	
