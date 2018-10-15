package org.dlsu.arrowsmith.extraModels;

public class Person {
	
	private String idnumber, password;
	
	public Person(){
		
	}
	
	public Person(String idnumber, String password){
		this.idnumber = idnumber;
		this.password = password;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
