package com.phicomm.doctor.dataaccess.domain;

import java.util.Date;

public class DoctorRelese {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column doctor_relese.id
	 * @mbg.generated
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column doctor_relese.doctor_id
	 * @mbg.generated
	 */
	private Integer doctorId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column doctor_relese.start_time
	 * @mbg.generated
	 */
	private Date startTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column doctor_relese.end_time
	 * @mbg.generated
	 */
	private Date endTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column doctor_relese.status
	 * @mbg.generated
	 */
	private Integer status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column doctor_relese.create_time
	 * @mbg.generated
	 */
	private Date createTime;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column doctor_relese.id
	 * @return  the value of doctor_relese.id
	 * @mbg.generated
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column doctor_relese.id
	 * @param id  the value for doctor_relese.id
	 * @mbg.generated
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column doctor_relese.doctor_id
	 * @return  the value of doctor_relese.doctor_id
	 * @mbg.generated
	 */
	public Integer getDoctorId() {
		return doctorId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column doctor_relese.doctor_id
	 * @param doctorId  the value for doctor_relese.doctor_id
	 * @mbg.generated
	 */
	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column doctor_relese.start_time
	 * @return  the value of doctor_relese.start_time
	 * @mbg.generated
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column doctor_relese.start_time
	 * @param startTime  the value for doctor_relese.start_time
	 * @mbg.generated
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column doctor_relese.end_time
	 * @return  the value of doctor_relese.end_time
	 * @mbg.generated
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column doctor_relese.end_time
	 * @param endTime  the value for doctor_relese.end_time
	 * @mbg.generated
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column doctor_relese.status
	 * @return  the value of doctor_relese.status
	 * @mbg.generated
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column doctor_relese.status
	 * @param status  the value for doctor_relese.status
	 * @mbg.generated
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column doctor_relese.create_time
	 * @return  the value of doctor_relese.create_time
	 * @mbg.generated
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column doctor_relese.create_time
	 * @param createTime  the value for doctor_relese.create_time
	 * @mbg.generated
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}