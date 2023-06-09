CREATE TABLE QRTZ_JOB_DETAILS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  JOB_NAME VARCHAR2(200) NOT NULL,
  JOB_GROUP VARCHAR2(200) NOT NULL,
  DESCRIPTION VARCHAR2(250),
  JOB_CLASS_NAME VARCHAR2(250) NOT NULL,
  IS_DURABLE VARCHAR2(1) NOT NULL,
  IS_NONCONCURRENT VARCHAR2(1) NOT NULL,
  IS_UPDATE_DATA VARCHAR2(1) NOT NULL,
  REQUESTS_RECOVERY VARCHAR2(1) NOT NULL,
  JOB_DATA BLOB,
  PRIMARY KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
);


CREATE TABLE QRTZ_TRIGGERS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  JOB_NAME VARCHAR2(200) NOT NULL,
  JOB_GROUP VARCHAR2(200) NOT NULL,
  DESCRIPTION VARCHAR2(250),
  NEXT_FIRE_TIME NUMBER(13),
  PREV_FIRE_TIME NUMBER(13),
  PRIORITY NUMBER(5),
  TRIGGER_STATE VARCHAR2(16) NOT NULL,
  TRIGGER_TYPE VARCHAR2(8) NOT NULL,
  START_TIME NUMBER(13) NOT NULL,
  END_TIME NUMBER(13),
  CALENDAR_NAME VARCHAR2(200),
  MISFIRE_INSTR NUMBER(2),
  JOB_DATA BLOB,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
    REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP)
);


CREATE TABLE QRTZ_SIMPLE_TRIGGERS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  REPEAT_COUNT NUMBER(7) NOT NULL,
  REPEAT_INTERVAL NUMBER(12) NOT NULL,
  TIMES_TRIGGERED NUMBER(10) NOT NULL,
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);


CREATE TABLE QRTZ_CRON_TRIGGERS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  CRON_EXPRESSION VARCHAR2(120) NOT NULL,
  TIME_ZONE_ID VARCHAR2(80),
  PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
);


CREATE TABLE QRTZ_SIMPROP_TRIGGERS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  STR_PROP_1 VARCHAR2(512),
  STR_PROP_2 VARCHAR2(512),
  STR_PROP_3 VARCHAR2(512),
  INT_PROP_1 INTEGER,
  INT_PROP_2 INTEGER,
  LONG_PROP_1 NUMBER(19),
  LONG_PROP_2 NUMBER(19),
  DEC_PROP_1 NUMBER(13, 4),
  DEC_PROP_2 NUMBER(13, 4),
  BOOL_PROP_1 CHAR(1),
  BOOL_PROP_2 CHAR(1),
  PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS(SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);


CREATE TABLE QRTZ_BLOB_TRIGGERS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  BLOB_DATA BLOB,
  PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);


CREATE TABLE QRTZ_CALENDARS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  CALENDAR_NAME VARCHAR2(200) NOT NULL,
  CALENDAR BLOB NOT NULL,
  PRIMARY KEY (SCHED_NAME, CALENDAR_NAME)
);

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  PRIMARY KEY (SCHED_NAME, TRIGGER_GROUP)
);


CREATE TABLE QRTZ_FIRED_TRIGGERS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  ENTRY_ID VARCHAR2(95) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  INSTANCE_NAME VARCHAR2(200) NOT NULL,
  FIRED_TIME NUMBER(13) NOT NULL,
  SCHED_TIME NUMBER(13) NOT NULL,
  PRIORITY NUMBER(10),
  STATE VARCHAR2(16) NOT NULL,
  JOB_NAME VARCHAR2(200),
  JOB_GROUP VARCHAR2(200),
  IS_NONCONCURRENT VARCHAR2(1),
  REQUESTS_RECOVERY VARCHAR2(1),
  PRIMARY KEY (SCHED_NAME, ENTRY_ID)
);


CREATE TABLE QRTZ_SCHEDULER_STATE (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  INSTANCE_NAME VARCHAR2(200) NOT NULL,
  LAST_CHECKIN_TIME NUMBER(13) NOT NULL,
  CHECKIN_INTERVAL NUMBER(13) NOT NULL,
  PRIMARY KEY (SCHED_NAME, INSTANCE_NAME)
);

CREATE TABLE QRTZ_LOCKS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  LOCK_NAME VARCHAR2(40) NOT NULL,
  PRIMARY KEY (SCHED_NAME, LOCK_NAME)
);
