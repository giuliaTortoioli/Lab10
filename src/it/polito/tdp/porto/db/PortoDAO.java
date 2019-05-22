package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Creator;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}
           conn.close();
			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<Author> getAllAutori(){
		
		final String sql = "SELECT * FROM author ORDER BY lastname";
		List<Author> autori = new ArrayList<Author>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				autori.add(autore);
				
			}
			conn.close();
        return autori;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}
	
public List<Creator> getPaperAndAuthor(Map<Integer, Author> autori){
		
		final String sql = "SELECT * FROM creator c1, creator c2"
				+ " where c1.eprintid = c2.eprintid and c1.authorid>c2.authorid"
				+ " group by c1.authorid, c2.authorid";
		List<Creator> result = new ArrayList<Creator>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
             result.add(new Creator(autori.get(rs.getInt("c1.authorid")),autori.get(rs.getInt("c2.authorid"))));
			}
            conn.close();
			return result;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}


public List<Creator> getPaper(Map<Integer, Paper> paper, Map<Integer, Author> autori){
	
	final String sql = "SELECT * , COUNT(*) FROM creator c1, creator"
			+ " c2 WHERE c1.eprintid=c2.eprintid AND " + 
			"c1.authorid != c2.authorid " + 
			"group BY c1.eprintid, c2.eprintid HAVING COUNT(*) >1";
	List<Creator> result = new ArrayList<Creator>();

	try {
		Connection conn = DBConnect.getConnection();
		PreparedStatement st = conn.prepareStatement(sql);
        
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
         result.add(new Creator(autori.get(rs.getInt("c1.authorid")),autori.get(rs.getInt("c2.authorid"))));
         result.get(result.size()-1).setP(paper.get(rs.getInt("c1.eprintid")));
		}
        conn.close();
		return result;

	} catch (SQLException e) {
		// e.printStackTrace();
		throw new RuntimeException("Errore Db");
	}
	
}
}