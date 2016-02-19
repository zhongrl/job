
/** 执行中任务列表 **/
var column = [[
    {field:'scheduleJobId',title:'scheduleJobId', hidden:true},
    {field:'jobName',title:'任务名称',align:'center', width: '10%'},
    {field:'jobGroup',title:'任务分组',align:'center', width: '10%'},
    {field:'jobTrigger',title:'触发器',align:'center', width: '20%'},
    {field:'cronExpression',title:'执行Cron表达式',align:'center', width: '10%'},
    {field:'status',title:'任务运行状态',align:'center', width: '10%'}
]];

$('#executingJobList').datagrid({
     rownumbers: true,
     pagination: true,
     pageList: [10,20,40,50,100,500],
     url: "/job-console/job/qeury/executingJobs",

     onClickRow: function (rowIndex, rowData) {
     $(this).datagrid('unselectRow', rowIndex);
     },
     columns: column,
     toolbar:[
        {
            text:'刷新',
            iconCls: 'icon-reload',
            handler:function(){
                $('#executingJobList').datagrid("reload");
            }
        }
     ]
 });

