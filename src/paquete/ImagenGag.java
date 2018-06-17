package paquete;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class ImagenGag {
	
	private URL feedUrl;
	private SyndFeedInput input;
	private SyndFeed feed;
	private List<Post> posteos;
	private Pattern pattern;
	
	public ImagenGag() {
		try {
			feedUrl = new URL("http://9gag-rss.com/api/rss/get?code=9GAGHotNoGif&format=2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		input = new SyndFeedInput();
		try {
			feed = input.build(new XmlReader(feedUrl));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FeedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		posteos = new ArrayList<Post>();
		pattern = Pattern.compile("<img src=\"(.+?)\" />");
		
		getPosts();
	}
	
	private void getPosts() {
		for(@SuppressWarnings("rawtypes") Iterator i = feed.getEntries().iterator(); i.hasNext();) {
			SyndEntry entry = (SyndEntry) i.next();
			Matcher matcher = pattern.matcher(entry.getDescription().getValue());
			matcher.find();
			String titulo = entry.getTitle();
			if(titulo.contains("&#039;"))
				titulo = titulo.replaceAll("&#039;", "'" );
			if(titulo.contains("&rsquo;"))
				titulo = titulo.replaceAll("&rsquo;", "'");
			posteos.add(new Post(titulo, matcher.group(1)));
		}
	}
	
	public Post getPost() {
		Random r = new Random();
		int post = r.nextInt(posteos.size());
		
		return posteos.get(post);
		//System.out.println(posteos.get(post).getTitulo() + " - " + posteos.get(post).getLink());
	}

}
