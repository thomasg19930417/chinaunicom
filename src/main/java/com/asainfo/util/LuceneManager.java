package com.asainfo.util;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NRTManager;
import org.apache.lucene.search.NRTManagerReopenThread;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.SearcherWarmer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

public class LuceneManager {
	private volatile static LuceneManager singleton;
	private static final String INDEX_PATH="D:\\sousuo";
	private static IndexWriter indexWriter;
	private static NRTManager nrtManager;
	private static Analyzer analyzer;
	private static SearcherManager searchManager;
	private static Version version;
	private static Directory directory;
	private LuceneManager() {
	}

	public static LuceneManager getInstance() {
		if (null == singleton) {
			synchronized (LuceneManager.class) {
				if (null == singleton) {
					init();
					singleton = new LuceneManager();
				}
			}
		}
		return singleton;
	}

	private static void init(){
		try {
			directory = FSDirectory.open(new File(INDEX_PATH));
			analyzer = new StandardAnalyzer(Version.LUCENE_35);
			indexWriter = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_35,analyzer ).setOpenMode(OpenMode.CREATE_OR_APPEND));
		    nrtManager = new NRTManager(indexWriter, new SearcherWarmer() {
				public void warm(IndexSearcher arg0) throws IOException {
					System.out.println("open changed ");
				}
			});
		   searchManager =  nrtManager.getSearcherManager(true);
		   NRTManagerReopenThread nrt  = new NRTManagerReopenThread(nrtManager, 5.0, 0.025);
		   nrt.setName("nrtmanager thread");
		   nrt.setDaemon(true);
		   nrt.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public IndexSearcher getIndexSearch(){
		return searchManager.acquire();
	}
	
	public void relaseSearch(IndexSearcher indexSearch){
		try {
			searchManager.release(indexSearch);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void commitIndex(){
		try {
			indexWriter.commit();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public NRTManager getNrtManager(){
		return nrtManager;
	}

}
