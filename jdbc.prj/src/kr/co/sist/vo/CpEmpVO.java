package kr.co.sist.vo;

import java.sql.Date;

public class CpEmpVO {
	private int empno, mgr, deptno;
	private double sal, comm;
	private String ename, job, hiredateStr;
	private Date hiredate;

	public CpEmpVO() {
	}

	public CpEmpVO(int empno, int mgr, int deptno, double sal, double comm, String ename, String job) {
		this.empno = empno;
		this.mgr = mgr;
		this.deptno = deptno;
		this.sal = sal;
		this.comm = comm;
		this.ename = ename;
		this.job = job;
	}

	public CpEmpVO(int empno, int mgr, int deptno, double sal, double comm, String ename, String job,
			String hiredateStr, Date hiredate) {
		super();
		this.empno = empno;
		this.mgr = mgr;
		this.deptno = deptno;
		this.sal = sal;
		this.comm = comm;
		this.ename = ename;
		this.job = job;
		this.hiredateStr = hiredateStr;
		this.hiredate = hiredate;
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

	public int getDeptno() {
		return deptno;
	}

	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}

	public double getSal() {
		return sal;
	}

	public void setSal(double sal) {
		this.sal = sal;
	}

	public double getComm() {
		return comm;
	}

	public void setComm(double comm) {
		this.comm = comm;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
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

	@Override
	public String toString() {
		return "CpEmpVO [empno=" + empno + ", mgr=" + mgr + ", deptno=" + deptno + ", sal=" + sal + ", comm=" + comm
				+ ", ename=" + ename + ", job=" + job + ", hiredateStr=" + hiredateStr + ", hiredate=" + hiredate + "]";
	}

}// class
