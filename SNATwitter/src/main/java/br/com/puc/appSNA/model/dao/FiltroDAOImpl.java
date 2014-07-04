package br.com.puc.appSNA.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import br.com.puc.appSNA.model.beans.Filtro;
import br.com.puc.appSNA.util.AppSNALog;
import br.com.puc.appSNA.util.Constantes;
import br.com.puc.appSNA.util.DAOUtil;

public class FiltroDAOImpl implements FiltroDAO {

	@Override
	public Long create(Filtro objeto) throws Exception {
		String query = "Insert into filtro(data_criacao,screenNames,biografias,localizacoes,termosStatus,dataInicio,dataFim,end_graphml,status) values(?,?,?,?,?,?,?,?,?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(Constantes.URL, Constantes.USER,
					Constantes.SENHA);
			stmt = conn.prepareStatement(query,
					com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);

			if(objeto.getDataCriacao() == null){
				stmt.setTimestamp(1, null);
			}else{
				stmt.setTimestamp(1, new Timestamp(objeto.getDataCriacao()
						.getTime()));
			}
			stmt.setString(2, objeto.getScreenNames());
			stmt.setString(3, objeto.getBiografias());
			stmt.setString(4, objeto.getLocalizacoes());
			stmt.setString(5, objeto.getTermosStatus());
			if(objeto.getDataInicio() == null){
				stmt.setTimestamp(6, null);
			}else{
				stmt.setTimestamp(6, new Timestamp(objeto.getDataInicio()
						.getTime()));
			}
			
			if(objeto.getDataFim() == null){
				stmt.setTimestamp(7, null);
			}else{
				stmt.setTimestamp(7, new Timestamp(objeto.getDataFim()
						.getTime()));
			}
			
			stmt.setString(8, objeto.getEndGraphml());
			stmt.setString(9, objeto.getStatus());
				

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
	public void create(List<Filtro> listaSNAElem) throws Exception {
		String query = "Insert into filtro(data_criacao,screenNames,biografias,localizacoes,termosStatus,dataInicio,dataFim,end_graphml,status) values(?,?,?,?,?,?,?,?,?);";
		
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(Constantes.URL, Constantes.USER,
					Constantes.SENHA);
			conn.setAutoCommit(false);
			// run sql objects
			stmt = conn.prepareStatement(query);
			int count = 1;
			for (Filtro objeto : listaSNAElem) {

				if(objeto.getDataCriacao() == null){
					stmt.setTimestamp(1, null);
				}else{
					stmt.setTimestamp(1, new Timestamp(objeto.getDataCriacao()
							.getTime()));
				}
				stmt.setString(2, objeto.getScreenNames());
				stmt.setString(3, objeto.getBiografias());
				stmt.setString(4, objeto.getLocalizacoes());
				stmt.setString(5, objeto.getTermosStatus());
				if(objeto.getDataInicio() == null){
					stmt.setTimestamp(6, null);
				}else{
					stmt.setTimestamp(6, new Timestamp(objeto.getDataInicio()
							.getTime()));
				}
				
				if(objeto.getDataFim() == null){
					stmt.setTimestamp(7, null);
				}else{
					stmt.setTimestamp(7, new Timestamp(objeto.getDataFim()
							.getTime()));
				}
				
				stmt.setString(8, objeto.getEndGraphml());
				stmt.setString(9, objeto.getStatus());

				stmt.addBatch();
				if (((listaSNAElem.size() - 1) < 20 && count
						% listaSNAElem.size() == 0)
						|| (count != 0 && count % 20 == 0)) {
					stmt.executeBatch();
				}
				count++;
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
	public Long update(Filtro objeto) throws Exception {
		String query = "update filtro set status = ? "
				+ "where id = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(Constantes.URL, Constantes.USER,
					Constantes.SENHA);
			stmt = conn.prepareStatement(query);

			stmt.setString(1, objeto.getStatus());
			stmt.setLong(2, objeto.getId());
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
	public Filtro findById(Long id) throws Exception {
		String query = "select id, data_criacao,screenNames,biografias,localizacoes,termosStatus,dataInicio,dataFim,end_graphml,status "
				+ "from filtro "
				+ "where id = ? ";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Filtro elem = new Filtro();

		try {
			conn = DAOUtil.returnConnection(Constantes.URL, Constantes.USER,
					Constantes.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				elem.setId(rs.getLong(1));
				elem.setDataCriacao(new Date(rs.getTimestamp(2).getTime()));
				elem.setScreenNames(rs.getString(3));
				elem.setBiografias(rs.getString(4));
				elem.setLocalizacoes(rs.getString(5));
				elem.setTermosStatus(rs.getString(6));
				
				if(rs.getTimestamp(7) != null){
					elem.setDataInicio(new Date(rs.getTimestamp(7).getTime()));
				}
				
				if(rs.getTimestamp(8) != null){
					elem.setDataFim(new Date(rs.getTimestamp(8).getTime()));
				}
				elem.setEndGraphml(rs.getString(9));
				elem.setStatus(rs.getString(10));
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}
		return elem;
	}

	@Override
	public List<Filtro> list() throws Exception {
		String query = "select id, data_criacao,screenNames,biografias,localizacoes,termosStatus,dataInicio,dataFim,end_graphml,status "
				+ "from filtro;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Filtro elem = new Filtro();
		List<Filtro> listElem = new LinkedList<Filtro>();
		try {
			conn = DAOUtil.returnConnection(Constantes.URL, Constantes.USER,
					Constantes.SENHA);
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			while (rs.next()) {

				elem = new Filtro();

				elem.setId(rs.getLong(1));
				elem.setDataCriacao(new Date(rs.getTimestamp(2).getTime()));
				elem.setScreenNames(rs.getString(3));
				elem.setBiografias(rs.getString(4));
				elem.setLocalizacoes(rs.getString(5));
				elem.setTermosStatus(rs.getString(6));
				
				if(rs.getTimestamp(7) != null){
					elem.setDataInicio(new Date(rs.getTimestamp(7).getTime()));
				}
				
				if(rs.getTimestamp(8) != null){
					elem.setDataFim(new Date(rs.getTimestamp(8).getTime()));
				}
				elem.setEndGraphml(rs.getString(9));
				elem.setStatus(rs.getString(10));

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
	public void remove(Filtro objeto) throws Exception {
		String query = "delete from filtro where id = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(Constantes.URL, Constantes.USER,
					Constantes.SENHA);
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
