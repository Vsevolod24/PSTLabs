CREATE TABLE TEST
(SUBS_ID INTEGER
,BALANCE_AMT INTEGER
,BALANCE_DATE DATE)

PARTITION BY RANGE_N (
BALANCE_DATE BETWEEN date '2000-01-01' and '2018-12-31' Each interval '1' Month,
NO range OR UNKNOWN );

select
    CUR.cr_sid as SUBS_ID,
    CUR.act_date as BALANCE_DATE_CURR,
    CUR.ls_transfer_amount as BALANCE_ATM_CURR,
    LAST1.BALANCE_DATE_1
--LAST1.ls_transfer_amount AS BALANCE_ATM_1
FROM
    (select
         t.cr_sid,
         t.act_date,
         t.ls_transfer_amount
     from ST_XF_CRM.CR_PERSON_LOY_PROG_CASH_BACK t
     WHERE t.act_date IN (select max(t2.act_date)
                          from ST_XF_CRM.CR_PERSON_LOY_PROG_CASH_BACK t2
                          group by t2.cr_sid)) CUR
        left join
    (select t.cr_sid ,max(t.act_date) as BALANCE_DATE_1
     from ST_XF_CRM.CR_PERSON_LOY_PROG_CASH_BACK t
     where t.act_date not in (select max(t.act_date) as BALANCE_DATE_CURR
                              from ST_XF_CRM.CR_PERSON_LOY_PROG_CASH_BACK t group by t.cr_sid)
     group by t.cr_sid) LAST1
    on CUR.cr_sid=LAST1.cr_sid