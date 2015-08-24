package org.springside.examples.oadata.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRO_PROJECT_COMPANY")
public class ProjectStepTypeView{
	
	private Long projectStepTypeId;
	private String projectTypeId;
	private String projectStepTypeName;
	private String projectStepTypeStartUrl;
	private String projectStepTypeViewUrl;
	private String projectStepTypeModifyUrl;
	private Boolean businessStepFlag;
	
	@Id
	public Long getProjectStepTypeId() {
		return projectStepTypeId;
	}
	public void setProjectStepTypeId(Long projectStepTypeId) {
		this.projectStepTypeId = projectStepTypeId;
	}
	
	public String getProjectTypeId() {
		return projectTypeId;
	}
	public void setProjectTypeId(String projectTypeId) {
		this.projectTypeId = projectTypeId;
	}
	public String getProjectStepTypeName() {
		return projectStepTypeName;
	}
	public void setProjectStepTypeName(String projectStepTypeName) {
		this.projectStepTypeName = projectStepTypeName;
	}
	public String getProjectStepTypeStartUrl() {
		return projectStepTypeStartUrl;
	}
	public void setProjectStepTypeStartUrl(String projectStepTypeStartUrl) {
		this.projectStepTypeStartUrl = projectStepTypeStartUrl;
	}
	public String getProjectStepTypeViewUrl() {
		return projectStepTypeViewUrl;
	}
	public void setProjectStepTypeViewUrl(String projectStepTypeViewUrl) {
		this.projectStepTypeViewUrl = projectStepTypeViewUrl;
	}
	public String getProjectStepTypeModifyUrl() {
		return projectStepTypeModifyUrl;
	}
	public void setProjectStepTypeModifyUrl(String projectStepTypeModifyUrl) {
		this.projectStepTypeModifyUrl = projectStepTypeModifyUrl;
	}
	public Boolean getBusinessStepFlag() {
		return businessStepFlag;
	}
	public void setBusinessStepFlag(Boolean businessStepFlag) {
		this.businessStepFlag = businessStepFlag;
	}
}