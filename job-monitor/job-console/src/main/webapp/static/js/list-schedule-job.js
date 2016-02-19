
init();
function init() {
   /* $('.validatebox-text').bind('blur', function() {
        $(this).validatebox('enableValidation').validatebox('validate');
    });

    $('#queryButton').click(function() {
        $('#scheduleJobTable').datagrid('load', jsonFromt($('#queryForm').serializeArray()));
    });*/
    var column = [[
        {field:'scheduleJobId', id:"sId", title:'scheduleJobId', checkbox : true},
        {field:'jobName',title:'任务名称',align:'center', width: '10%'},
        {field:'jobGroup',title:'任务分组',align:'center', width: '10%'},
        {field:'jobClass',title:'任务触发类',align:'center', width: '20%'},
        {field:'status',title:'任务状态',align:'center', width: '10%'},
        {field:'jobExecTime',title:'任务执行时间',align:'center', width: '10%'},
        {field:'gmtCreateStr',title:'创建时间',align:'center', width: '10%'},
        {field:'description',title:'任务描述',align:'center', width: '10%'}
    ]];

    $('#scheduleJobTable').datagrid({
        rownumbers: true,
        url: "/job-console/job/qeury/scheduleJobs",
        columns: column,
        method: 'POST',
        fit:true,
        loadMsg:'正在努力为您查找中……',
        sortName: 'authName',
        sortOrder: 'desc',
        remoteSort: true,
        idField:'scheduleJobId',
        singleSelect: true,
        toolbar:[
            {
                text:'添加任务',
                iconCls:'icon-add',
                handler:function(){
                    $("#cronType option:eq(1)").select();
                    $("#saveWin").window("open");
                    /*$("#cType_1,#cType_2,#cType_3,#cType_4").css("display","none");
                    $("#cType_1").css("display","block");*/
                }
            },
            {
                text:'暂停任务',
                handler:function(){
                    var selectId =  $('#scheduleJobTable').datagrid('getSelected')['scheduleJobId'];
                    var times = new Date();
                    times = times.getTime();
                    $.get("/job-console/job/pause-schedule-job", {'scheduleJobId':selectId,'timestamp':times}, function(){
                        init();
                    });
                }
            },
            {
                text:'恢复任务',
                iconCls: 'icon-ok',
                handler:function(){
                    var selectId =  $('#scheduleJobTable').datagrid('getSelected')['scheduleJobId'];
                    var times = new Date();
                    times = times.getTime();
                    $.get("/job-console/job/resume-schedule-job", {'scheduleJobId':selectId,'timestamp':times}, function(){
                        console.log("删除成功");
                        init();
                    });
                }
            },
            {
                text:'立即运行一次',
                handler:function(){
                    var selectId =  $('#scheduleJobTable').datagrid('getSelected')['scheduleJobId'];
                    var times = new Date();
                    times = times.getTime();
                    $.get("/job-console/job/run-once-schedule-job", {'scheduleJobId':selectId,'timestamp':times}, function(){
                        init();
                    });
                }
            },
            /*{
                text:'修改任务',
                iconCls:'icon-edit',
                handler:function(){
                    console.log("update options");
                    $("#updateWin123").window("open");
                }
            },
            {
                text:'重新创建',
                handler:function(){
                    var selectId =  $('#scheduleJobTable').datagrid('getSelected')['scheduleJobId'];
                    $.get("/job-console/job/input-schedule-job", {'scheduleJobId':selectId, 'keywords':'delUpdate'}, function(data){
                        if (data.code == 200) {
                            //打开窗体
                            $("#updateWin").window("open");

                        }
                    });
                }
            },*/
            {
                text:'删除',
                iconCls:'icon-remove',
                handler:function(){
                    $.messager.confirm('提示','确定删除？',function(r){
                        if (r){
                            var selectId =  $('#scheduleJobTable').datagrid('getSelected')['scheduleJobId'];
                            var times = new Date();
                            times = times.getTime();
                            $.get("/job-console/job/delete-schedule-job", {'scheduleJobId':selectId,'timestamp':times}, function(){
                                init();
                            });
                        }
                        return;
                    });
                }
            }
        ]
    });
}

/**
 * 保存定时任务,取消
 */
$("#saveScheduleForm #save,#cancel").click(function(){
    if ($(this).attr("icon") == 'icon-ok') {
        //console.log("ok: "+ $("#saveScheduleForm").form('validate'));
        //选择月
        var cronValFlag = new Boolean(false);
        //console.log("cronType: "+ $("#cronType").val());
        var selType = $("#cronType").val();
        if (selType == 1) {
           if (!$("#dayOfMonth").val() || !$("#hour").val() || !$("#minute").val())
               cronValFlag = true;
        } else if(selType == 2){
            if (!$("#type2_hour").val() || !$("#type2_minute").val())
                cronValFlag = true;
        } else if(selType == 3){
            if (!$("#type3_minute").val() || !$("#type3_second").val())
                cronValFlag = true;
        } else {
            var val = $("div[name=cType_4] input").val();
            if (!val)
                cronValFlag = true;
        }

        if (cronValFlag == true) {
            $.messager.alert('提示','任务执行时间不能为空!');
            return;
        }

        //console.log("======================= jobClass"+ $("#jobClass").val());
        $.get("/job-console/job/validate/jobClass", {"jobClass": $("#jobClass").val()}, function(data){
            if (data.code != 200) {
                $.messager.alert("提示", data.message);
                return;
            }
        });

        //校验jobName
        $.post("/job-console/job/validate/jobName", {"jobName": $("#jobName").val()}, function(data) {
            if (data.code == 200) {
                $.messager.alert('提示','定时任务名字已存在!');
                return;
            }
        });


        if ($("#saveScheduleForm").form('validate')) {
            $.ajax({
                type: "POST",
                url: "/job-console/job/save-schedule-job",
                dataType: "json",
                data: $("#saveScheduleForm").serialize(),
                success: function (data, textStatus) {
                    //init();
                    $('#scheduleJobTable').datagrid("reload");
                    $("#saveScheduleForm")[0].reset();
                }
            });

            $("#saveWin").window("close");
            return;
        } else {
            $.messager.alert('提示','必填值不能为空!');
            return;
        }
    }

    $("#saveScheduleForm")[0].reset();
    $("#saveWin").window("close");
});

/**
 * 执行方式选择
 */
$("#cronType").change(function(){
    //console.log($(this).val() +", "+ $(this).find("option:selected").text());

    var selVal = $(this).find("option:selected").text();
    $("#selCronType").text(selVal);
    $("#selCronTypeDiv div").hide();
    $("#selCronTypeDiv [name='cType_"+ $(this).val() +"']").show();
    $("#selCronTypeDiv input").val("");
});

/**
 * 校验jobclass
 */
$("#jobClass").blur(function() {
    //console.log("=================");
});





