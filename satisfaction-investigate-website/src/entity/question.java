package entity;

import java.util.ArrayList;
import java.util.List;

public class question {
	private int id;
	private String productname;
	private int aspect;
	private List<String> mmr;
	private List<String> mlp;
	private List<String> ilp;
	private boolean answer;
	private int a;
	private int b;
	private int c;
	private int d;
	private int e;
	private int f;
	
	public static List<String> getList(String s)
	{
		
		if(s == null || s.equals(""))
			return null;
		List<String> result = new ArrayList<String>();
		
		String[] list = s.split("。|[?]");
		for(String l : list)
		{
			int index = l.indexOf("^");
			result.add(l.substring(index + 1));
		}
		return result;	
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public int getAspect() {
		return aspect;
	}
	public void setAspect(int aspect) {
		this.aspect = aspect;
	}

	public List<String> getMmr() {
		return mmr;
	}
	public void setMmr(List<String> mmr) {
		this.mmr = mmr;
	}
	public List<String> getMlp() {
		return mlp;
	}
	public void setMlp(List<String> mlp) {
		this.mlp = mlp;
	}
	public List<String> getIlp() {
		return ilp;
	}
	public void setIlp(List<String> ilp) {
		this.ilp = ilp;
	}
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
	public int getC() {
		return c;
	}
	public void setC(int c) {
		this.c = c;
	}
	public int getD() {
		return d;
	}
	public void setD(int d) {
		this.d = d;
	}
	public int getE() {
		return e;
	}
	public void setE(int e) {
		this.e = e;
	}
	public int getF() {
		return f;
	}
	public void setF(int f) {
		this.f = f;
	}


	public boolean isAnswer() {
		return answer;
	}


	public void setAnswer(boolean answer) {
		this.answer = answer;
	}
	
}
