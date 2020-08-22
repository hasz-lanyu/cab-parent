//使用组件生成表格
$('#usertb').bootstrapTable({
  sortable: true,                     //是否启用排序
  sortOrder: "desc",                   //排序方式
  sortName: 'id',             //默认根据那个字段排序
  striped: true,               //换行变色
  //查询数据的接口地址
  url: '/user',
  method: 'get',
  //查询参数，可以用于增加表格的筛选条件，在下文的function queryParams()中实现
  queryParams: queryParams,
  //设置接口返回值中用于填充表格数据的字段
  //发送restful风格参数
  // queryParamsType:'limit',
  dataField: "data",
  contentType: "application/json",
  sidePagination: 'server',
  //是否显示视图切换
  showToggle: true,
  //禁用缓存  级别
  cache: false,
  toolbar: '#toolbar',                //工具按钮用哪个容器
  //   buttonsAlign:right,             //工具栏按钮的位置
  //表格高度
  //height: 200,
  search: true,                       //是否显示表格搜索，此搜索是客户端搜索

  clickToSelect: true,                //是否启用点击选中行
  showRefresh: true,                  //是否显示刷新按钮

  //接口返回数据的处理方法，使用该方法可以控制生成该组件所需的total和data字段
  responseHandler: responseHandler,
  pagination: true,
  pageNumber: 1,
  pageSize: 5,
  pageList: [10, 30, 100],
  //列，此处举例：序号生成方法+列按钮如何添加事件，并非上图对应的列信息
  columns: [
    {
      checkbox: true
    }, {
      field: 'id',
      title: 'id',
      visible: false,
      /*    formatter:function(value,row,index){
            var pageSize=$('#dataxJobInfoTable').bootstrapTable('getOptions').pageSize;
            var pageNumber=$('#dataxJobInfoTable').bootstrapTable('getOptions').pageNumber;
            return pageSize * (pageNumber - 1) + index + 1;
          }*/
    }, {
      field: 'workId',
      title: '代号',
      sortable: true,
    },{
      field: 'username',
      title: '用户名',
    }, {
      field: 'deptName',
      title: '部门',
    }, {
      field: 'phone',
      title: '手机',
      visible: false,
    }, {
      field: 'email',
      title: '邮箱',
      visible: false,
    }, {
      field: 'gender',
      title: '性别',
      visible: false,
    }, {
      field: 'address',
      title: '地址',
      visible: false,
    }, {
      field: 'createTime',
      title: '创建时间',
      visible: false,

    }, {
      field: 'password',
      title: '密码',
      visible: false,
    }, {
      field: 'operate',
      title: '操作',
      formatter: function (value, row, index) {
        return'<a id="edit" class="btn btn-success btn-xs" style="margin-left:10px"><i class="fa fa-eye"></i> 编辑</a>'
            +
            '<a id="delete" class="btn btn-danger btn-xs"  style="margin-left:10px"><i class="fa fa-close"></i> 删除</a>';
      },

      //可以为该列绑定事件，如下所示，每个按钮绑定了事件
      events: {
        'click #delete': function (e, value, row, index) {
          //动态控制模态框的显示内容，为模态框的按钮动态的绑定事件，包括删除事件和执行事件，
          //这样模态框就可以复用
          layer.confirm('确定删除?', {btn: ['删除', '取消']}, function (index) {
            deleteUser(row.id);
            layer.close(index);
          });
          // $("#opDataxJobRowModal").modal();
        },
        'click #runDataxJob': function (e, value, row, index) {
          $("#warningText").text("确认执行任务： " + row.dataxJobName + " ?");
          $("#confirmBtn").text("执行");
          $("#confirmBtn").on("click", function () {
            runDataxJob(row);
          });
          $("#opDataxJobRowModal").modal();

        },
        'click #pauseJob': function (e, value, row, index) {
          $("#warningText").text("是否暂停任务： " + row.dataxJobName + " ?");
          $("#confirmBtn").text("暂停");
          $("#confirmBtn").on("click", function () {
            pauseJob(row.xxlJobId)
          });
          $("#opDataxJobRowModal").modal();
        },
        'click #edit': function (e, value, row, index) {
          $('.modal-title').html('修改用户');
          $("#userAdd").html('修改');
          $('#uid').val(row.id);
          $('#username').val(row.username);
          $('#password').val(row.password);
          $('#phone').val(row.phone);
          $('#email').val(row.email);
          $('#address').val(row.address);
          if (row.gender == 1) {
            $('#female').attr('checked', 'checked');
            $('#male').removeAttr('checked');
            $('#maleclass').removeClass('active');
            $('#femaleclass').addClass('active');
          } else {
            $('#male').attr('checked', 'checked');
            $('#female').removeAttr('checked')
          }
          $("#usermodal").modal({
            keyboard: true
          })
        }
      }},

    {
      field: 'assign',
      title: '分配',
      formatter: function (value, row, index) {
        return'<a id="role" class="btn btn-primary btn-xs" style="margin-left:10px"> 角色</a>'
            +
            '<a id="permission" class="btn btn-info btn-xs"  style="margin-left:10px">权限</a>';
      },
      /*
                events: {
                  'click #delete': function (e, value, row, index) {
                    //动态控制模态框的显示内容，为模态框的按钮动态的绑定事件，包括删除事件和执行事件，
                    //这样模态框就可以复用
                    layer.confirm('确定删除?', {btn: ['删除', '取消']}, function (index) {
                      deleteUser(row.id);
                      layer.close(index);
                    });
                    // $("#opDataxJobRowModal").modal();
                  },
                  'click #runDataxJob': function (e, value, row, index) {
                    $("#warningText").text("确认执行任务： " + row.dataxJobName + " ?");
                    $("#confirmBtn").text("执行");
                    $("#confirmBtn").on("click", function () {
                      runDataxJob(row);
                    });
                    $("#opDataxJobRowModal").modal();

                  },
                },
      */

    }]
});

//生成查询参数
function queryParams(params) {
  var search = $('.search').children('input').val();
  return {
    pageSize: params.limit,
    pageNumber: params.offset / params.limit + 1,
    key: search
    //这里就俩条件：任务名称和任务描述
    /*      dataxJobName: $("#dataxJobName").val(),
          jobDesc: $("#jobDesc").val()*/
  }
}

//请求成功将后端返回的数据进行处理以满足bootstrap-table
function responseHandler(r) {
  if (r.code == "9999") {
    return {
      total: 0, //总页数,前面的key必须为"total"
      data: null //行数据，前面的key要与之前设置的dataField的值一致.
    }
  }
  //如果没有错误则返回数据，渲染表格
  return {
    total: r.total, //总页数,前面的key必须为"total"
    data: r.data //行数据，前面的key要与之前设置的dataField的值一致.
  };
};