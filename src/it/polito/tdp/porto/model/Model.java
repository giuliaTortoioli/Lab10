package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {

	private Graph<Author, DefaultEdge> grafo;
	private Map<Integer, Author> autoriMap;
	private Graph<Author, DefaultEdge> grafo2;
	
	
	public void creaGrafo() {
		
		this.grafo = new SimpleGraph<>(DefaultEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.autoriMap.values());
		
		PortoDAO dao = new PortoDAO();
		
		List <Creator> creator = new ArrayList<Creator>(dao.getPaperAndAuthor(autoriMap));
		
		for(Creator c : creator) {
			grafo.addEdge(c.getAut1(), c.getAut2());
		}
		System.out.println("Vertici: "+grafo.vertexSet().size());
		System.out.println("Archi: "+grafo.edgeSet().size());
		System.out.println("Grafo: "+grafo);

	}
	
	public List<Author> getCoautori(Author autore){
		return Graphs.neighborListOf(this.grafo, autore);
	}
	public List<Author> getAutori() {
		PortoDAO dao = new PortoDAO();
		List<Author> autori = new ArrayList<Author>(dao.getAllAutori());
		autoriMap = new HashMap<Integer, Author>();
		for(Author a : autori) {
			autoriMap.put(a.getId(),a);
		}
		return autori;
	}

	public List<Paper> camminoMinimo(Author a1, Author a2) {
		Map <Integer, Paper> paperMap = new HashMap<Integer, Paper>();
		
		this.grafo2 = new SimpleGraph<>(DefaultEdge.class);
		
		Graphs.addAllVertices(this.grafo2, this.autoriMap.values());
		
		PortoDAO dao = new PortoDAO();
		
	   List<Creator> connessi = new ArrayList<> (dao.getPaper(paperMap, this.autoriMap));
	   
	   for(Creator c : connessi) {
			grafo2.addEdge(c.getAut1(), c.getAut2());
		}
	   
		DijkstraShortestPath<Author, DefaultEdge> dijkstra = new DijkstraShortestPath<>(this.grafo2);
		GraphPath<Author, DefaultEdge> path = dijkstra.getPath(a1, a2);
		List<Paper> connessioni = new ArrayList<>();
		for(DefaultEdge c : path.getEdgeList()) {
			c.
		}
		return path.get
	}
}
