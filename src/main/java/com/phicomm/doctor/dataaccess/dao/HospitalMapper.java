package com.phicomm.doctor.dataaccess.dao;

import com.phicomm.doctor.dataaccess.domain.Hospital;

public interface HospitalMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hospital
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hospital
	 * @mbg.generated
	 */
	int insert(Hospital record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hospital
	 * @mbg.generated
	 */
	int insertSelective(Hospital record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hospital
	 * @mbg.generated
	 */
	Hospital selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hospital
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(Hospital record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hospital
	 * @mbg.generated
	 */
	int updateByPrimaryKeyWithBLOBs(Hospital record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hospital
	 * @mbg.generated
	 */
	int updateByPrimaryKey(Hospital record);
}