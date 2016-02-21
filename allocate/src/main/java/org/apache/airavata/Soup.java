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
 * Queries the Google Scholarly Search Engine (http://scholar.google.com/scholar?hl=en) with a
 * professor name and scrapes their top publications and the number of times their publication was cited.
 * @author Sameet Sapra
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
	 * Fills a JSONArray of citations by applying JSoup logic to a web-scraped HTML.
   *
	 * @return JSONArray representing publications attributed to a inputted
	 *         person
	 * @throws IOException
	 */
	public JSONArray getCitations() throws IOException {
		String pagedUrl = this.url;
		Document doc = Jsoup.connect(pagedUrl).timeout(5000).ignoreHttpErrors(true).followRedirects(true)
				.userAgent("Mozilla").get();

		Elements div_links = doc.select(".gs_fl > a:nth-child(1)");
		Elements publication_names = doc.select(".gs_ri > .gs_rt");
		String pub_name_filtered;
		JSONArray publication_list = new JSONArray();

		for (int i = 0; i < div_links.size(); i++) {
			Element link = div_links.get(i);
			Element pub_name = publication_names.get(i);

			if (!pub_name.select(".gs_rt > span").isEmpty()){
				// Particular parsing with h3 due to special Google Scholarly Search html quirks
				if (!pub_name.select(".gs_rt > a").isEmpty())
					pub_name_filtered = pub_name.select(".gs_rt > a").text();
				else {
					String spanTxt = pub_name.select("h3 > span").text();
					String spanAndHeaderTxt = pub_name.select("h3").text();
					pub_name_filtered = spanAndHeaderTxt.substring(spanTxt.length(), spanAndHeaderTxt.length() - 1);
				}
				pub_name_filtered = pub_name_filtered.trim();
			}
			else{
				pub_name_filtered = pub_name.text();
			}
			String[] words = link.text().split(" ");
			int numCitations = Integer.parseInt(words[2]);
			JSONObject publication = new JSONObject();
			publication.put("name", pub_name_filtered);
			publication.put("num_citations", numCitations);
			publication_list.add(publication);
		}
		return publication_list;
	}

}
