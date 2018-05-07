package com.phicomm.doctor.service.response;

import java.util.List;

public class DoctorListResponsePage {

	private List<DoctorListResponse> responses;
	
	private Integer count;

	public List<DoctorListResponse> getResponses() {
		return responses;
	}

	public void setResponses(List<DoctorListResponse> responses) {
		this.responses = responses;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
