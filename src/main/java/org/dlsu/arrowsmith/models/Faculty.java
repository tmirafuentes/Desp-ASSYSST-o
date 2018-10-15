package org.dlsu.arrowsmith.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Faculty extends User {
	private long faculty_id;
	private int years_of_service;
	private String faculty_type;
	private User user;
	private Preference preference;
	private Set<DeloadOffer> deloadOffers;
	private Set<Offering> offerings;
	private Set<Load> loads;
	private Set<Specialization> specializations;

	public Faculty(long faculty_id, int years_of_service, String faculty_type, User user) {
		this.faculty_id = faculty_id;
		this.years_of_service = years_of_service;
		this.faculty_type = faculty_type;
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getFaculty_id() {
		return faculty_id;
	}

	public void setFaculty_id(long faculty_id) {
		this.faculty_id = faculty_id;
	}

	public int getYears_of_service() {
		return years_of_service;
	}

	public void setYears_of_service(int years_of_service) {
		this.years_of_service = years_of_service;
	}

	public String getFaculty_type() {
		return faculty_type;
	}

	public void setFaculty_type(String faculty_type) {
		this.faculty_type = faculty_type;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "preference_id")
	public Preference getPreference() {
		return preference;
	}

	public void setPreference(Preference preference) {
		this.preference = preference;
	}

	@OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
	public Set<DeloadOffer> getDeloadOffers() {
		return deloadOffers;
	}

	public void setDeloadOffers(Set<DeloadOffer> deloadOffers) {
		this.deloadOffers = deloadOffers;
	}

	@OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
	public Set<Offering> getOfferings() {
		return offerings;
	}

	public void setOfferings(Set<Offering> offerings) {
		this.offerings = offerings;
	}

	@OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
	public Set<Load> getLoads() {
		return loads;
	}

	public void setLoads(Set<Load> loads) {
		this.loads = loads;
	}

	@OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
	public Set<Specialization> getSpecializations() {
		return specializations;
	}

	public void setSpecializations(Set<Specialization> specializations) {
		this.specializations = specializations;
	}
}