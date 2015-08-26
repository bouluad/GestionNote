package com.gestion.note.outils;

public class headerLine {

	String t=null;
	String t2="";
	
	public headerLine(String s)
	{
		String tab[];
		
		tab=s.split(" ");

		t=tab[0];
		
	if(tab.length>1)
	{
		for(int i=1;i<tab.length;i++)
		{
			t2=t2+" "+tab[i];
			
		}
	}
		
}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getT2() {
		return t2;
	}

	public void setT2(String t2) {
		this.t2 = t2;
	}
	
	
}
