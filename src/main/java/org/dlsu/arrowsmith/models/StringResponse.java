package org.dlsu.arrowsmith.models;

//use if you're returning a plain String as JSON
public class StringResponse {

    private String response;
    private String firstName, lastName, facultyType;
    private Float maxLoad;
    private Integer maxPreparation;

    public StringResponse(String s) { 
       this.response = s;
    }

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFacultyType() {
		return facultyType;
	}

	public void setFacultyType(String facultyType) {
		this.facultyType = facultyType;
	}

	public Float getMaxLoad() {
		return maxLoad;
	}

	public void setMaxLoad(Float maxLoad) {
		this.maxLoad = maxLoad;
	}

	public Integer getMaxPreparation() {
		return maxPreparation;
	}

	public void setMaxPreparation(Integer maxPreparation) {
		this.maxPreparation = maxPreparation;
	}
    
}