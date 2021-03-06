MYSQL
--------------emptable 생성--------------
CREATE TABLE EMP
(EMPNO INT not null,
ENAME VARCHAR(10),
JOB VARCHAR(9),
MGR INT ,
HIREDATE date,
SAL INT ,
COMM INT ,
DEPTNO INT );
--alter session set nls_date_format='YYYY-MM-DD HH24:MI:SS';


INSERT INTO EMP VALUES
(7369,'SMITH','CLERK',7902,'1980-12-17',800,null,20),
(7499,'ALLEN','SALESMAN',7698,'1981-02-20',1600,300,30),
(7521,'WARD','SALESMAN',7698,'1981-02-22',1250,200,30),
(7566,'JONES','MANAGER',7839,'1981-04-02',2975,30,20),
(7654,'MARTIN','SALESMAN',7698,'1981-09-28',1250,300,30),
(7698,'BLAKE','MANAGER',7839,'1981-04-01',2850,null,30),
(7782,'CLARK','MANAGER',7839,'1981-06-01',2450,null,10),
(7788,'SCOTT','ANALYST',7566,'1982-10-09',3000,null,20),
(7839,'KING','PRESIDENT',null,'1981-11-17',5000,3500,10),
(7844,'TURNER','SALESMAN',7698,'1981-09-08',1500,0,30),
(7876,'ADAMS','CLERK',7788,'1983-01-12',1100,null,20),
(7900,'JAMES','CLERK',7698,'1981-10-03',950,null,30),
(7902,'FORD','ANALYST',7566,'1981-10-3',3000,null,20),
(7934,'MILLER','CLERK',7782,'1982-01-23',1300,null,10);
COMMIT;

--------------emp 테이블 컬럼추가--------------
alter table emp
add imagefilename varchar2(100);
--------------dept 테이블 생성--------------
CREATE TABLE DEPT
(DEPTNO INT,
DNAME VARCHAR(14),
LOC VARCHAR(13) );

INSERT INTO DEPT VALUES (10,'ACCOUNTING','NEW YORK');
INSERT INTO DEPT VALUES (20,'RESEARCH','DALLAS');
INSERT INTO DEPT VALUES (30,'SALES','CHICAGO');
INSERT INTO DEPT VALUES (40,'OPERATIONS','BOSTON');

COMMIT;

--------------adminlist 테이블 생성--------------
create table adminlist(
userid varchar(20) not null,
pwd varchar(20) not null
);

//관리자를 입력
insert into adminlist values('admin', '1004');
insert into adminlist values('adminkim', '1234');

--------------view 생성--------------
create view statistics
as
  select e.empno as "mgrnum", avg(m.sal + IFNULL(m.comm,0)) as "avg", max(m.sal + IFNULL(m.comm,0)) as "max", min(m.sal + IFNULL(m.comm,0)) as "min"
  from emp e join emp m on e.empno = m.mgr 
  group by e.empno;
