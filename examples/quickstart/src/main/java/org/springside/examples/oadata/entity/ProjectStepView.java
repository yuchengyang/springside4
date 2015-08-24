package org.springside.examples.oadata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRO_PROJECT_STEP")
public class ProjectStepView{

	private Long projectStepId;
	private Long projectId;
	private Long projectStepTypeId;
	private String processInstanceId;
	private Boolean isActive;
	private Boolean latestFinished;
	private Boolean reopenFlag;
	private Boolean repeatFlag;
	private Boolean isIntegrity;
	
	@Id
	public Long getProjectStepId() {
		return projectStepId;
	}
	public void setProjectStepId(Long projectStepId) {
		this.projectStepId = projectStepId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getProjectStepTypeId() {
		return projectStepTypeId;
	}
	public void setProjectStepTypeId(Long projectStepTypeId) {
		this.projectStepTypeId = projectStepTypeId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	@Column(name="IS_LATEST_FINISHED")
	public Boolean getLatestFinished() {
		return latestFinished;
	}
	public void setLatestFinished(Boolean latestFinished) {
		this.latestFinished = latestFinished;
	}
	public Boolean getReopenFlag() {
		return reopenFlag;
	}
	public void setReopenFlag(Boolean reopenFlag) {
		this.reopenFlag = reopenFlag;
	}
	public Boolean getRepeatFlag() {
		return repeatFlag;
	}
	public void setRepeatFlag(Boolean repeatFlag) {
		this.repeatFlag = repeatFlag;
	}
	public Boolean getIsIntegrity() {
		return isIntegrity;
	}
	public void setIsIntegrity(Boolean isIntegrity) {
		this.isIntegrity = isIntegrity;
	}
}