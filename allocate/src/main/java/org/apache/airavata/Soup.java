package org.apache.airavata;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.IOException;

/**
 * Created by ssapra on 2/20/16.
 */
public class Soup {
	private String url;
	private String professorName;
	private int page;

	public Soup(String url, String name) {

		this.url = url;
		this.professorName = name;
		this.url += "&q=" + this.professorName + "&num=20";
		this.page = 1;
	}

	/**
	 * 
	 * @return JSONArray representing publications attributed to a inputted
	 *         person
	 * @throws IOException
	 */
	public JSONArray getCitations() throws IOException {
		String pagedUrl = this.url;
		System.out.println(pagedUrl);
//		/String[] tagsToDelete = { "[HTML]", "[PDF]","[BOOK]","[B]","[CITATION]","[C]" };
		Document doc = Jsoup.connect(pagedUrl).timeout(5000).ignoreHttpErrors(true).followRedirects(true)
				.userAgent("Mozilla").get();

		Elements div_links = doc.select(".gs_fl > a:nth-child(1)");
		Elements publication_names = doc.select(".gs_ri > .gs_rt");
		String pub_name_filtered;
		JSONArray publication_list = new JSONArray();
		for (int i = 0; i < div_links.size(); i++) {
			Element link = div_links.get(i);
			Element pub_name = publication_names.get(i);

			if (!pub_name.select(".gs_rt>span").isEmpty()){
				if (!pub_name.select(".gs_rt>a").isEmpty())
					pub_name_filtered = pub_name.select(".gs_rt>a").text();
				else{
					String span = pub_name.select("h3>span").text();
					String total = pub_name.select("h3").text();
					pub_name_filtered = total.substring(span.length(), total.length() - 1);
				}
			}
			else{
				pub_name_filtered = pub_name.text();
			}
			if (pub_name_filtered.charAt(0) == ' ') pub_name_filtered = pub_name_filtered.substring(1);
			String[] words = link.text().split(" ");
			int numCitations = Integer.parseInt(words[2]);
			System.out.println(pub_name_filtered + " : " + numCitations);
			JSONObject publication = new JSONObject();
			publication.put("name", pub_name_filtered);
			publication.put("num_citations", numCitations);
			publication_list.add(publication);
		}
		return publication_list;
	}

}
