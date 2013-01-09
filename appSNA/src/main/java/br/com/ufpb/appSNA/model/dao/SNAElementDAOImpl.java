package br.com.ufpb.appSNA.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import br.com.ufpb.appSNA.model.beans.SNAElement;
import br.com.ufpb.appSNA.model.beans.to.InadimplenciaTO;
import br.com.ufpb.appSNA.util.BDUtil;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.DAOUtil;

public class SNAElementDAOImpl implements SNAElementDAO {

	@Override
	public Long create(SNAElement objeto) throws Exception {
		String query = "Insert into usuario(id_usuario, nome, screen_name, biografia, localizacao, total_Following,"
				+ "total_Followers, total_Tweets, URL, timeZone, linguagem, data_criacao, url_imagem) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		Long result = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query,
					com.mysql.jdbc.Statement.RETURN_GENERATED_KEYS);

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
			stmt.setTimestamp(12, new Timestamp(objeto.getDataDeCriacao()
					.getTime()));
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
		String query = "Insert into usuario(id_usuario, nome, screen_name, biografia, localizacao, total_Following,"
				+ "total_Followers, total_Tweets, URL, timeZone, linguagem, data_criacao, url_imagem) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";

		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			conn.setAutoCommit(false);
			// run sql objects
			stmt = conn.prepareStatement(query);
			int count = 1;
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
				stmt.setTimestamp(12, new Timestamp(elem.getDataDeCriacao()
						.getTime()));
				stmt.setString(13, elem.getURLImagem());

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
	public Long update(SNAElement objeto) throws Exception {
		String query = "update Usuario set nome = ?, screen_name = ?, biografia = ?, localizacao = ?, total_Following = ?,"
				+ "total_Followers = ?, total_Tweets = ?, URL = ?, timeZone = ?, linguagem = ?, data_criacao = ?, url_imagem = ? "
				+ "where id_usuario = ?;";

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
			stmt.setTimestamp(11, new Timestamp(objeto.getDataDeCriacao()
					.getTime()));
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
		String query = "select id_usuario, nome,screen_name,biografia,"
				+ "localizacao,total_following,total_followers,total_tweets,"
				+ "URL,timezone,linguagem,data_criacao,url_imagem, '1' as 'Tipo', id_label "
				+ "from usuario "
				+ "where id_usuario IN( "
				+ "SELECT identificacao "
				+ "FROM atr_twitter_saida "
				+ ") and id_usuario = ? "
				+ "UNION ALL "
				+ "select id_usuario, nome,screen_name,biografia,"
				+ "localizacao,total_following,total_followers,total_tweets,"
				+ "URL,timezone,linguagem,data_criacao,url_imagem, '0' as 'Tipo', id_label "
				+ "from usuario " + "where id_usuario NOT IN( "
				+ "SELECT identificacao " + "FROM atr_twitter_saida "
				+ ") and id_usuario = ?;";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		SNAElement elem = new SNAElement();

		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, id);
			stmt.setLong(2, id);
			rs = stmt.executeQuery();

			while (rs.next()) {
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
				elem.setDataDeCriacao(rs.getTimestamp(12));
				elem.setURLImagem(rs.getString(13));
				elem.setNegativado(rs.getInt(14));
				elem.setId_label(rs.getLong(15));
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
		String query = "select id_usuario, nome,screen_name,biografia,"
				+ "localizacao,total_following,total_followers,total_tweets,"
				+ "URL,timezone,linguagem,data_criacao,url_imagem, '1' as 'Tipo', id_label "
				+ "from usuario "
				+ "where id_usuario IN( "
				+ "SELECT identificacao "
				+ "FROM atr_twitter_saida "
				+ ") "
				+ "UNION ALL "
				+ "select id_usuario, nome,screen_name,biografia,"
				+ "localizacao,total_following,total_followers,total_tweets,"
				+ "URL,timezone,linguagem,data_criacao,url_imagem, '0' as 'Tipo', id_label "
				+ "from usuario " + "where id_usuario NOT IN( "
				+ "SELECT identificacao " + "FROM atr_twitter_saida " + ");";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		SNAElement elem = new SNAElement();
		List<SNAElement> listElem = new LinkedList<SNAElement>();
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
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
				elem.setDataDeCriacao(rs.getTimestamp(12));
				elem.setURLImagem(rs.getString(13));
				elem.setNegativado(rs.getInt(14));
				elem.setId_label(rs.getLong(15));

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
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, objeto.getId());
			stmt.execute();
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

	}

	public InadimplenciaTO verificarDadosInadimplencia(Long id_usuario)
			throws Exception {
		String query = "SELECT d.qtde/t.total AS grau_inadimplencia, t.total as quantidade_negativas_viz, d.qtde as negativas "
				+ "FROM"
				+ "(SELECT COUNT(ndoc) AS 'qtde' "
				+ "FROM j246_analitivo_negativas an, atr_twitter_saida ts "
				+ "WHERE an.ndoc = ts.cpf_atbr and ts.identificacao= ? "
				+ "GROUP BY ndoc) as d "
				+ ", "
				+ "(SELECT SUM(qtde_negativas) as total "
				+ "FROM( "
				+ "SELECT COUNT(an.ndoc) as qtde_negativas "
				+ "FROM j246_analitivo_negativas an, atr_twitter_saida ts WHERE "
				+ "an.ndoc = ts.cpf_atbr AND ts.identificacao IN "
				+ "(SELECT id_target "
				+ "FROM relacionamento "
				+ "WHERE id_source = ?) " + "GROUP BY ndoc) as u " + ") as t; ";

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<SNAElement> listElem = new LinkedList<SNAElement>();
		InadimplenciaTO to = new InadimplenciaTO();
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, id_usuario);
			stmt.setLong(2, id_usuario);
			rs = stmt.executeQuery();

			while (rs.next()) {
				to = new InadimplenciaTO();
				to.setInadimplencia(rs.getFloat(1));
				to.setVizinhanca(rs.getInt(2));
				to.setNegatividade(rs.getInt(3));
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

		return to;

	}

	public Integer retornarQtdeAmigosNegativos(Long id_usuario) throws Exception {
		String query = "select count(qtde_negativas) from "
				+ "(SELECT COUNT(an.ndoc) as qtde_negativas "
				+ "FROM j246_analitivo_negativas an, atr_twitter_saida ts where "
				+ "an.ndoc = ts.cpf_atbr and ts.identificacao IN "
				+ "(select id_target from relacionamento where id_source = ?) "
				+ "GROUP BY ndoc " + ") as u;";
		
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Integer result = 0;
		try {
			conn = DAOUtil.returnConnection(BDUtil.URL, BDUtil.USER,
					BDUtil.SENHA);
			stmt = conn.prepareStatement(query);
			stmt.setLong(1, id_usuario);
			rs = stmt.executeQuery();

			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			AppSNALog.error(e.toString());
		} finally {
			conn.close();
		}

		return result;
	}

}
