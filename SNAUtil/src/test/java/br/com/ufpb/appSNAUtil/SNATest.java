package br.com.ufpb.appSNAUtil;

import java.util.ArrayList;
import java.util.List;

import br.com.ufpb.appSNAUtil.util.AccountCarrousel;
import br.com.ufpb.appSNAUtil.util.TwitterUtil;

public class SNATest {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("moacirlmjr");
		list.add("Danyllo_Wagner");
		try {
			AccountCarrousel.startListReady();
			TwitterUtil.retornarUserId(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
