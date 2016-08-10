package eu.transkribus.solrSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

public class SearchResult {
	private Map<String,ArrayList<String>> wordHighlights = new HashMap<String,ArrayList<String>>();
	private Map<String,ArrayList<String>> lineHighlights = new HashMap<String,ArrayList<String>>();
	private Map<String,String> authors = new HashMap<String,String>();
	private Map<String,String> parentDocs = new HashMap<String,String>();
	private Map<String,String> pageUrls = new HashMap<String,String>();
	private Map<String,ArrayList<Object>> wordCoords = new HashMap<String,ArrayList<Object>>();
	
	public SearchResult(){
		
	}
	
	public void getFromSolrResponse(QueryResponse response){
		Map<String, Map<String, List<String>>> obj = response.getHighlighting();
		for(String key : obj.keySet()){
			wordHighlights.put(key, (ArrayList<String>) obj.get(key).get("fullTextFromWords"));			
		}
		
		for(SolrDocument result : response.getResults()){
			if(result.getFieldValue("author") != null){
				authors.put(result.getFieldValue("id").toString(), result.getFieldValue("author").toString());
			}
			if(result.getFieldValue("docId") != null){
				parentDocs.put(result.getFieldValue("id").toString(), result.getFieldValue("docId").toString());
			}		
		}
		
		for(SolrDocument result : response.getResults()){
			if(result.getFieldValue("pageUrl") != null){
				pageUrls.put(result.getFieldValue("id").toString(), result.getFieldValue("pageUrl").toString());
			}
		}
		
		for(SolrDocument result : response.getResults()){
			if(result.getFieldValue("wordCoords") != null){
				wordCoords.put(result.getFieldValue("id").toString(), (ArrayList<Object>) result.getFieldValues("wordCoords"));
			}
		}
		
	}
	
	
	public SearchResult(QueryResponse response){
		
		Map<String, Map<String, List<String>>> obj = response.getHighlighting();
		ArrayList<String> highlString = new ArrayList<String>();
		for(String key : obj.keySet()){

			for(String string : obj.get(key).get("fullTextFromWords")){
				highlString.add(string);
			}
			wordHighlights.put(key, highlString);
			highlString.clear();
			for(String string : obj.get(key).get("fullTextFromLines")){
				highlString.add(string);
			}
			lineHighlights.put(key, highlString);
			
			
		}
		
		
	}
	
	public Map<String,ArrayList<String>> getWordsHighlights(){
		return wordHighlights;
	}
	
	public Map<String,ArrayList<String>> getLinesHighlights(){
		return lineHighlights;
	}
	
	public Map<String,String> getAuthors(){
		return authors;
	}
	
	public Map<String,String> getParentDocs(){
		return parentDocs;
	}
	
	public Map<String,String> getPageUrls(){
		return pageUrls;
	}
	
	public Map<String,ArrayList<Object>> getWordCoords(){
		return wordCoords;
	}
	
	

}
