package com.asainfo.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NRTManager;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

import com.asainfo.pojo.PageBean;
import com.asainfo.pojo.Shell;
import com.asainfo.util.LuceneManager;

public class IndexService {
	public void addIndex(Shell shell) {
		try {
			NRTManager nrtManager = LuceneManager.getInstance().getNrtManager();
			Document doc = getDocument(shell);
			nrtManager.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Document getDocument(Shell shell) {
		Document doc = new Document();
		doc.add(new Field("id", shell.getId() + "", Field.Store.YES,
				Field.Index.NOT_ANALYZED_NO_NORMS));
		doc.add(new Field("shellname", shell.getShellName(), Field.Store.YES,
				Field.Index.ANALYZED));
		doc.add(new Field("descs", shell.getDescs(), Field.Store.NO,
				Field.Index.ANALYZED));
		doc.add(new Field("time", shell.getTime(), Field.Store.YES,
				Field.Index.ANALYZED));
		doc.add(new Field("filename", shell.getFileName(), Field.Store.NO,
				Field.Index.NOT_ANALYZED_NO_NORMS));
		doc.add(new Field("username", shell.getUsername(), Field.Store.YES,
				Field.Index.ANALYZED));
		return doc;
	}

	public void deleteIndex(String id) {
		try {
			LuceneManager.getInstance().getNrtManager()
					.deleteDocuments(new Term("id", id));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void updateIndex(Shell shell) {
		try {
			NRTManager nrtManager = LuceneManager.getInstance().getNrtManager();
			Document doc = getDocument(shell);
			nrtManager.updateDocument(new Term("id", shell.getId() + ""), doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PageBean searchPageByAfter(String query, int pageIndex, int pageSize) {
		IndexSearcher search = LuceneManager.getInstance().getIndexSearch();
		List<Shell> list = new ArrayList<Shell>();
		try {
			MultiFieldQueryParser parser = new MultiFieldQueryParser(
					Version.LUCENE_35, new String[] { "shellname", "descs",
							"username" }, new StandardAnalyzer(
							Version.LUCENE_35));
			Query q = parser.parse(query);
			//获取总记录数
			TopDocs allNum = search.search(q, 100);
			int count  = allNum.totalHits;
			 //获取上一页的最后一个元素  
	        ScoreDoc lastSd = getLastScoreDoc(pageIndex, pageSize, q, search);  
	        //通过最后一个元素去搜索下一页的元素  
	        TopDocs tds = search.searchAfter(lastSd,q, pageSize); 
	        System.out.println("==========="+tds.totalHits);
	        for(ScoreDoc sd:tds.scoreDocs) { 
	        	Shell shell = new Shell();
	            Document doc = search.doc(sd.doc); 
	            System.out.println(doc.toString());
	            System.out.println(sd.doc+":"+doc.get("id")+"-->"+doc.get("shellname"));  
	            shell.setId(Integer.parseInt(doc.get("id")));
	            shell.setShellName(doc.get("shellname"));
	            shell.setUsername(doc.get("username"));
	            shell.setTime(doc.get("time"));
	            list.add(shell);
	        }  
	        PageBean p = new PageBean();
	        p.setCount(count);
	        p.setData(list);
	        return  p;
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			LuceneManager.getInstance().relaseSearch(search);
		}
		return null;
	}

	private ScoreDoc getLastScoreDoc(int pageIndex, int pageSize, Query query,
			IndexSearcher searcher) throws IOException {
		if (pageIndex == 1){
			return null;// 如果是第一页就返回空
		}
		int num = pageSize * (pageIndex - 1);// 获取上一页的最后数量
		TopDocs tds = searcher.search(query, num);
		return tds.scoreDocs[num - 1];
	}
}
