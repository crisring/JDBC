package kr.co.sist.vo;

import java.sql.Date;

public class CpEmpVO {

	private int empno, mgr, sal, comm, deptno;
	private String ename, job, hiredateStr;
	private Date hiredate;

	public CpEmpVO(int empno, int mgr, int sal, int comm, int deptno, String ename, String job) {
		super();
		this.empno = empno;
		this.mgr = mgr;
		this.sal = sal;
		this.comm = comm;
		this.deptno = deptno;
		this.job = job;
		this.ename = ename;
	}

	public CpEmpVO() {
	}

	public int getEmpno() {
		return empno;
	}

	public void setEmpno(int empno) {
		this.empno = empno;
	}

	public int getMgr() {
		return mgr;
	}

	public void setMgr(int mgr) {
		this.mgr = mgr;
	}

	public int getSal() {
		return sal;
	}

	public void setSal(int sal) {
		this.sal = sal;
	}

	public int getComm() {
		return comm;
	}

	public void setComm(int comm) {
		this.comm = comm;
	}

	public int getDeptno() {
		return deptno;
	}

	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getHiredateStr() {
		return hiredateStr;
	}

	public void setHiredateStr(String hiredateStr) {
		this.hiredateStr = hiredateStr;
	}

	public Date getHiredate() {
		return hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	@Override
	public String toString() {
		return "CpEmpVO [empno=" + empno + ", mgr=" + mgr + ", sal=" + sal + ", comm=" + comm + ", deptno=" + deptno
				+ ", job=" + job + ", hiredateStr=" + hiredateStr + ", hiredate=" + hiredate + "]";
	}

}// class
