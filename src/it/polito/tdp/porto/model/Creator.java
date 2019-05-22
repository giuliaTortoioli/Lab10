package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

public class Creator {
	
	private Author aut1;
	private Author aut2;
	private Paper p;
	public Creator(Author aut1, Author aut2) {
		super();
		this.aut1 = aut1;
		this.aut2 = aut2;
	}
	public Author getAut1() {
		return aut1;
	}
	public Author getAut2() {
		return aut2;
	}
	public Paper getP() {
		return p;
	}
	public void setP(Paper p) {
		this.p = p;
	}
	
	
	
}