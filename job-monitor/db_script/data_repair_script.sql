
-- 天添牛派息到期时间修复, 去除时分秒
update tp_invest_record
set FEndTime = date(FEndTime)
where FStatus = 2;