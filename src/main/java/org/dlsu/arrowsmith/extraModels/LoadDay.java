package org.dlsu.arrowsmith.extraModels;

import org.dlsu.arrowsmith.models.Offering;

public class LoadDay {

	private Offering offering;
	
	public LoadDay(){
		
	}
	
	public LoadDay(Offering offering){
		this.offering = offering;
	}

	public Offering getOffering() {
		return offering;
	}

	public void setOffering(Offering offering) {
		this.offering = offering;
	}
}
