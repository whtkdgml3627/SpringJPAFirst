select * from t_board order by bno desc;

select * from t_reply;

select b.bno, min(b.title) as title, min(b.writer) as writer, count(tr.rno)
from t_board b left outer join t_reply tr
on tr.board_bno = b.bno
group by b.bno
order by b.bno desc
limit 0, 10
;

select b.bno, b.title,
	(select count(rno) from t_reply tr where tr.board_bno = bno)
from t_board b
order by b.bno desc
limit 0, 10
;

select
        b1_0.bno,
        b1_0.title,
        b1_0.writer,
        count(r1_0.rno) 
    from
        t_board b1_0 
    left join
        t_reply r1_0 
            on r1_0.board_bno=b1_0.bno 
    group by
        b1_0.bno,
        b1_0.content,
        b1_0.mod_date,
        b1_0.reg_date,
        b1_0.title,
        b1_0.writer 
;