package br.com.ufpb.appSNA.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import br.com.ufpb.appSNA.model.beans.to.RelacionamentoTO;
import br.com.ufpb.appSNAUtil.model.beans.to.XmlTO;
import br.com.ufpb.appSNAUtil.model.enumeration.TypeEnum;
import br.com.ufpb.appSNAUtil.util.AccountCarrousel;
import br.com.ufpb.appSNAUtil.util.AppSNALog;
import br.com.ufpb.appSNAUtil.util.TwitterUtil;
import br.com.ufpb.appSNAUtil.util.XMLUtil;

public class DinamicNetworkGeneration implements Serializable {

	private static AtomicBoolean mutex;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> listaUsuarios = new ArrayList<String>();
		// listaUsuarios.add("Danyllo_Wagner");
		// listaUsuarios.add("moacirlmjr");
		listaUsuarios.add("ale_patricio");
		listaUsuarios.add("ALLYSONDINIZ");
		listaUsuarios.add("DEZINHAJPA");
		listaUsuarios.add("ARTHURFERRO");
		listaUsuarios.add("atila_jp");
		listaUsuarios.add("AYLTONJR");
		listaUsuarios.add("ELVISREI");
		listaUsuarios.add("evaldodesousa");
		listaUsuarios.add("fabianovidaltur");
		listaUsuarios.add("BRASILTONY");
		listaUsuarios.add("CHIQUELMEBATERA");
		listaUsuarios.add("FLUGARCEZ");
		listaUsuarios.add("IVANILDOPB");
		listaUsuarios.add("KellylopesLOPES");
		listaUsuarios.add("GALVAOJPA");
		listaUsuarios.add("luanadepaulane1");
		listaUsuarios.add("lucasduartereal");
		listaUsuarios.add("Mariacristin339");
		listaUsuarios.add("ONAMEN");
		listaUsuarios.add("jricardoamorim");
		listaUsuarios.add("RINALDOPESSOA");
		listaUsuarios.add("RIQUELSON");
		listaUsuarios.add("NTURISMO_JPPB");
		listaUsuarios.add("ThiagoADVJP");

		AccountCarrousel.startListReady();
		List<RelacionamentoTO> listRelacionametos = new ArrayList<RelacionamentoTO>();
		RelacionamentoTO rc = null;

		Map<String, List<Long>> mapListAmigos = new HashMap<String, List<Long>>();
		try {
			for (String user : listaUsuarios) {
				for (String user2 : listaUsuarios) {
					if (TwitterUtil.isFollowing(user, user2)
							&& !user.equals(user2)) {
						rc = new RelacionamentoTO();
						rc.setId_source(user);
						rc.setId_target(user2);
						listRelacionametos.add(rc);
						break;
					} else if (TwitterUtil.isFollowing(user2, user)
							&& !user.equals(user2)) {
						rc = new RelacionamentoTO();
						rc.setId_source(user2);
						rc.setId_target(user);
						listRelacionametos.add(rc);
						break;
					}
				}
			}

			List<String> listSozinhos = new ArrayList<String>();
			List<String> listUsersRelacionados = new ArrayList<String>();
			if (!listRelacionametos.isEmpty()) {
				for (String user : listaUsuarios) {
					for (RelacionamentoTO rc2 : listRelacionametos) {
						if (!(rc2.getId_source().equals(user) || rc2
								.getId_target().equals(user))) {
							listSozinhos.add(user);
						} else {
							listUsersRelacionados.add(user);
						}
					}
				}
			} else {
				listSozinhos = new ArrayList<String>(listaUsuarios);
			}

			List<String> listUsersAmigos = new ArrayList<String>();
			int count = 0;
			for (String single : listSozinhos) {
				if (!listRelacionametos.isEmpty()) {
					for (String user : listUsersRelacionados) {
						List<Long> listAmigos = null;
						if (!verificarMapListAmigos(mapListAmigos, user) && count == 0) {
							listAmigos = TwitterUtil
							.retornarListaAmigosIdsList(user, true);
							mapListAmigos.put(user, listAmigos);
						}else{
							listAmigos = mapListAmigos.get(user);
						}
						if (listAmigos != null) {
							for (Long amigoId : listAmigos) {
								// TwitterUtil.testarRemainingHits();
								String amigoScreenName = AccountCarrousel.CURRENT_ACCOUNT
										.showUser(amigoId).getScreenName();
								if (TwitterUtil.isFollowing(single,
										amigoScreenName)) {
									rc = new RelacionamentoTO();
									rc.setId_source(single);
									rc.setId_target(amigoId + "");
									listRelacionametos.add(rc);

									rc = new RelacionamentoTO();
									rc.setId_source(amigoId + "");
									rc.setId_target(user);
									listRelacionametos.add(rc);

									listaUsuarios.add(amigoId + "");
									listUsersAmigos.add(amigoScreenName + ","
											+ amigoId);
								} else if (TwitterUtil.isFollowing(
										amigoScreenName, single)) {
									rc = new RelacionamentoTO();
									rc.setId_source(amigoId + "");
									rc.setId_target(single);
									listRelacionametos.add(rc);

									rc = new RelacionamentoTO();
									rc.setId_source(user);
									rc.setId_target(amigoId + "");
									listRelacionametos.add(rc);

									listaUsuarios.add(amigoId + "");
									listUsersAmigos.add(amigoScreenName + ","
											+ amigoId);
								}

							}
						}
					}
				} else {
					for (String user : listSozinhos) {
						if (!user.equals(single)) {
							List<Long> listAmigos = null;
							if (!verificarMapListAmigos(mapListAmigos, user) && count == 0) {
								listAmigos = TwitterUtil
								.retornarListaAmigosIdsList(user, true);
								mapListAmigos.put(user, listAmigos);
							}else{
								listAmigos = mapListAmigos.get(user);
							}
							if (listAmigos != null) {
								for (Long amigoId : listAmigos) {
									// TwitterUtil.testarRemainingHits();
									String amigoScreenName = AccountCarrousel.CURRENT_ACCOUNT
											.showUser(amigoId).getScreenName();
									if (TwitterUtil.isFollowing(single,
											amigoScreenName)) {
										rc = new RelacionamentoTO();
										rc.setId_source(single);
										rc.setId_target(amigoId + "");
										listRelacionametos.add(rc);

										rc = new RelacionamentoTO();
										rc.setId_source(amigoId + "");
										rc.setId_target(user);
										listRelacionametos.add(rc);

										listaUsuarios.add(amigoId + "");
										listUsersAmigos.add(amigoScreenName
												+ "," + amigoId);
									} else if (TwitterUtil.isFollowing(
											amigoScreenName, single)) {
										rc = new RelacionamentoTO();
										rc.setId_source(amigoId + "");
										rc.setId_target(single);
										listRelacionametos.add(rc);

										rc = new RelacionamentoTO();
										rc.setId_source(user);
										rc.setId_target(amigoId + "");
										listRelacionametos.add(rc);

										listaUsuarios.add(amigoId + "");
										listUsersAmigos.add(amigoScreenName
												+ "," + amigoId);
									}

								}
							}
						}

					}
				}
				count++;
			}

			List<String> listAux = new ArrayList<String>(listSozinhos);
			for (String user : listAux) {
				for (RelacionamentoTO rc2 : listRelacionametos) {
					if (rc2.getId_source().equals(user)
							|| rc2.getId_target().equals(user)) {
						listSozinhos.remove(user);
						break;
					}
				}
			}
			Map<String, List<Long>> mapListAmigosAmigos = new HashMap<String, List<Long>>();
			count = 0;
			for (String single : listSozinhos) {
				for (String amigo : listUsersAmigos) {
					String amigoScreen = amigo.split(",")[0];
					String amigoId = amigo.split(",")[1];
					List<Long> listAmigosAmigos = null;
					if (!verificarMapListAmigos(mapListAmigosAmigos, amigoScreen) && count == 0) {
						listAmigosAmigos = TwitterUtil
						.retornarListaAmigosIdsList(amigoScreen, true);
						mapListAmigosAmigos.put(amigoScreen, listAmigosAmigos);
					}else{
						listAmigosAmigos = mapListAmigosAmigos.get(amigoScreen);
					}
					if (listAmigosAmigos != null) {
						for (Long amigoamigoId : listAmigosAmigos) {
							// TwitterUtil.testarRemainingHits();
							String amigoamigoScreenName = AccountCarrousel.CURRENT_ACCOUNT
									.showUser(amigoamigoId).getScreenName();
							if (TwitterUtil.isFollowing(single,
									amigoamigoScreenName)) {
								rc = new RelacionamentoTO();
								rc.setId_source(single);
								rc.setId_target(amigoamigoId + "");
								listRelacionametos.add(rc);

								rc = new RelacionamentoTO();
								rc.setId_source(amigoamigoId + "");
								rc.setId_target(amigoId);
								listRelacionametos.add(rc);

								listaUsuarios.add(amigoamigoId + "");
							} else if (TwitterUtil.isFollowing(
									amigoamigoScreenName, single)) {
								rc = new RelacionamentoTO();
								rc.setId_source(amigoamigoId + "");
								rc.setId_target(single);
								listRelacionametos.add(rc);

								rc = new RelacionamentoTO();
								rc.setId_source(amigoId);
								rc.setId_target(amigoamigoId + "");
								listRelacionametos.add(rc);

								listaUsuarios.add(amigoamigoId + "");
							}

						}
					}
					count++;
				}
			}

			XmlTO x1 = new XmlTO("nome", true, "nome", TypeEnum.STRING_TYPE);
			List<XmlTO> listXmlTO = new ArrayList<XmlTO>();
			listXmlTO.add(x1);

			XMLUtil.generateHeader(listXmlTO, true);

			for (String amigo : listaUsuarios) {
				XMLUtil.generateNodes(amigo, amigo.toString());
			}

			for (RelacionamentoTO roTO : listRelacionametos) {
				XMLUtil.generateEdges(roTO.getId_source(), roTO.getId_target());
			}

			XMLUtil.fechaArquivo();
			XMLUtil.salvarXML("grafoDinamico.graphml");

		} catch (Exception e) {
			AppSNALog.error(e.toString());
			e.printStackTrace();
		} finally {
			System.exit(0);
		}

	}

	private static boolean verificarMapListAmigos(
			Map<String, List<Long>> mapListAmigos, String user) {
		if (mapListAmigos.containsKey(user)) {
			return true;
		}

		return false;
	}

}
