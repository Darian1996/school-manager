/**

 @Name：layuiAdmin 用户管理 管理员管理 角色管理
 @Author：star1029
 @Site：http://www.layui.com/admin/
 @License：LPPL

 */


layui.define(['table', 'form'], function (exports) {
    var $ = layui.$
        , admin = layui.admin
        , view = layui.view
        , table = layui.table
        , form = layui.form;

    //用户管理
    let userListTable = table.render({
        elem: '#LAY-user-manage'
        // , url: './json/useradmin/webuser.json' //模拟接口
        , url: '/user/getAllPage' // 后台查询用户接口
        , toolbar: true // 开启导出
        , title: '用户管理' // 导出的文件名
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 100, title: 'ID', sort: true}
            , {field: 'username', title: '用户名', minWidth: 100}
            , {field: 'actualName', title: '真实姓名'}
            // , {field: 'avatar', title: '头像', width: 100, templet: '#imgTpl'}
            // , {field: 'phone', title: '手机'}
            // , {field: 'email', title: '邮箱'}
            // , {field: 'sex', width: 80, title: '性别'}
            // , {field: 'ip', title: 'IP'}
            // , {field: 'jointime', title: '加入时间', sort: true}
            , {title: '操作', width: 225, align: 'center', fixed: 'right', toolbar: '#table-useradmin-webuser'}
        ]]
        , page: true
        , limit: 90
        // , height: 'full-320' 内部撑满去掉
        , text: '对不起，加载出现异常！'
    });

    //监听工具条
    table.on('tool(LAY-user-manage)', function (obj) {
        var data = obj.data;
        if (obj.event === 'disable') {
            layer.confirm('确定禁用此用户？', function (index) {
                admin.req({
                        url: '/user/disable',
                        data: data,
                        done: function (res) {
                            userListTable.reload();
                            customBase.alertCustomMsg('已经禁用');
                        }
                    }
                    , 'post'
                );

            });
        } else if (obj.event === 'edit') {
            admin.popup({
                title: '编辑用户'
                , area: ['500px', '450px']
                , id: 'LAY-popup-user-add'
                , success: function (layero, index) {
                    view(this.id).render('teacher/teacher/userform', data).done(function () {
                        form.render(null, 'layuiadmin-form-useradmin');

                        //监听提交
                        form.on('submit(LAY-user-front-submit)', function (data) {
                            // 提交 Ajax 成功后，关闭当前弹层并重载表格
                            var field = data.field; //获取提交的字段
                            console.log("更新用户信息的 field", field);

                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            admin.req({
                                    url: '/user/addOrUpdate',
                                    data: field,
                                    done: function (res) {
                                        userListTable.reload();
                                        customBase.alertCustomMsg('更新成功');
                                        layer.close(index); //执行关闭
                                    }
                                }
                                , 'post'
                            );

                        });
                    });
                }
            });
        } else if (obj.event == 'enable') {
            layer.confirm('确定启用此用户？', function (index) {
                admin.req({
                        url: '/user/enable',
                        data: data,
                        done: function (res) {
                            userListTable.reload();
                            customBase.alertCustomMsg('已经启用');

                        }
                    }
                    , 'post'
                );

            });
        }
    });

    //管理员管理
    table.render({
        elem: '#LAY-user-back-manage'
        , url: './json/useradmin/mangadmin.json' //模拟接口
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', sort: true}
            , {field: 'loginname', title: '登录名'}
            , {field: 'telphone', title: '手机'}
            , {field: 'email', title: '邮箱'}
            , {field: 'role', title: '角色'}
            , {field: 'jointime', title: '加入时间', sort: true}
            , {field: 'check', title: '审核状态', templet: '#buttonTpl', minWidth: 80, align: 'center'}
            , {title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-useradmin-admin'}
        ]]
        , page: true
        , limit: 300
        , height: 'full-320'
        , text: '对不起，加载出现异常！'
    });

    //监听工具条
    table.on('tool(LAY-user-back-manage)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.prompt({
                formType: 1
                , title: '敏感操作，请验证口令'
            }, function (value, index) {
                layer.close(index);
                layer.confirm('确定删除此管理员？', function (index) {
                    console.log(obj)
                    obj.del();
                    layer.close(index);
                });
            });
        } else if (obj.event === 'edit') {
            admin.popup({
                title: '编辑管理员'
                , area: ['420px', '450px']
                , id: 'LAY-popup-user-admin-add'
                , success: function (layero, index) {
                    view(this.id).render('user/administrators/adminform', data).done(function () {
                        form.render(null, 'layuiadmin-form-admin');

                        //监听提交
                        form.on('submit(LAY-user-back-submit)', function (data) {
                            var field = data.field; //获取提交的字段

                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            //$.ajax({});
                            layui.table.reload('LAY-user-back-manage'); //重载表格
                            layer.close(index); //执行关闭
                        });
                    });
                }
            });
        }
    });

    //角色管理
    let roleListTable = table.render({
        elem: '#LAY-user-back-role'
        // ,url: './json/useradmin/role.json' //模拟接口
        , url: '/role/getAll'
        , toolbar: true // 开启导出
        , title: '角色' // 导出的文件名
        , loading: true // 显示加载动画
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', sort: true}
            , {field: 'name', title: '角色名', width: 180}
            , {field: 'description', title: '具体描述'}
            , {field: 'permissionListString', title: '拥有权限'}
            , {field: 'menuListString', title: '拥有菜单'}
            , {field: 'isDeleted', title: '删除状态', templet: '#buttonTpl', align: 'center', width: 90}
            , {title: '操作', width: 225, align: 'center', fixed: 'right', toolbar: '#table-useradmin-admin'}
        ]]

        , text: '对不起，加载出现异常！'
    });

    //监听工具条
    table.on('tool(LAY-user-back-role)', function (obj) {
        var data = obj.data;
        console.log("data", data);
        if (obj.event === 'disable') {
            layer.confirm('确定禁用此角色？', function (index) {
                admin.req({
                        url: '/role/disable',
                        data: data,
                        done: function (res) {
                            roleListTable.reload();
                            customBase.alertCustomMsg('已经禁用');
                        }
                    }
                    , 'post'
                );

            });
        } else if (obj.event === 'edit') {
            admin.popup({
                title: '更新角色信息'
                , area: ['500px', '480px']
                , id: 'LAY-popup-role-add'
                , success: function (layero, index) {
                    view(this.id).render('security/role/roleform', data).done(function () {
                        form.render(null, 'layuiadmin-form-role');

                        //监听提交
                        form.on('submit(LAY-user-role-submit)', function (data) {
                            var field = data.field; //获取提交的字段
                            console.log("更新角色信息的 field", field);

                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            admin.req({
                                    url: '/role/addOrUpdate',
                                    data: field,
                                    done: function (res) {
                                        roleListTable.reload();
                                        customBase.alertCustomMsg('更新成功');
                                        layer.close(index); //执行关闭
                                    }
                                }
                                , 'post'
                            );
                        });
                    });
                }
            });
        } else if (obj.event == 'enable') {
            layer.confirm('确定启用此角色？', function (index) {
                admin.req({
                        url: '/role/enable',
                        data: data,
                        done: function (res) {
                            roleListTable.reload();
                            customBase.alertCustomMsg('已经启用');

                        }
                    }
                    , 'post'
                );

            });
        }
    });


    // 后台接口管理
    let securityListTable = table.render({
        elem: '#LAY-user-back-security'
        , url: '/security/getAll' // 查询后台接口管理列表
        , toolbar: true // 开启导出
        , title: '后台接口列表' // 导出的文件名
        , loading: true // 显示加载动画
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', sort: true}
            , {field: 'urlPattern', title: '接口'}
            , {field: 'description', title: '具体描述'}
            , {field: 'orderId', width: 80, title: '排序'}
            , {field: 'isDeleted', title: '删除状态', templet: '#buttonTpl', align: 'center', width: 90}
            , {title: '操作', width: 225, align: 'center', fixed: 'right', toolbar: '#table-user-security-delete'}
        ]]
        , text: '对不起，加载出现异常！'
    });

    // 后台接口管理 监听工具条
    table.on('tool(LAY-user-back-security)', function (obj) {
        var data = obj.data;
        console.log("data", data);
        if (obj.event === 'disable') {
            layer.confirm('确定禁用此接口？', function (index) {
                admin.req({
                        url: '/security/disable',
                        data: data,
                        done: function (res) {
                            securityListTable.reload();
                            customBase.alertCustomMsg('已经禁用');
                        }
                    }
                    , 'post'
                );

            });
        } else if (obj.event === 'edit') {
            admin.popup({
                title: '更新接口信息'
                , area: ['500px', '480px']
                , id: 'LAY-popup-security-add'
                , success: function (layero, index) {
                    view(this.id).render('security/security/securityform', data).done(function () {
                        form.render(null, 'layuiadmin-form-security');

                        //监听提交
                        form.on('submit(LAY-user-security-submit)', function (data) {
                            var field = data.field; //获取提交的字段
                            console.log("更新接口信息的 field", field);

                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            admin.req({
                                    url: '/security/addOrUpdate',
                                    data: field,
                                    done: function (res) {
                                        securityListTable.reload();
                                        customBase.alertCustomMsg('更新成功');
                                        layer.close(index); //执行关闭
                                    }
                                }
                                , 'post'
                            );
                        });
                    });
                }
            });
        } else if (obj.event == 'enable') {
            layer.confirm('确定启用接口？', function (index) {
                admin.req({
                        url: '/security/enable',
                        data: data,
                        done: function (res) {
                            securityListTable.reload();
                            customBase.alertCustomMsg('已经启用');

                        }
                    }
                    , 'post'
                );

            });
        }
    });

    // 后台权限组
    let permissionListTable = table.render({
        elem: '#LAY-user-back-permission'
        , url: '/permission/getAllDTO' // 查询权限组管理列表
        , toolbar: true // 开启导出
        , title: '后台权限组列表' // 导出的文件名
        , loading: true // 显示加载动画
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', sort: true}
            , {field: 'name', title: '权限组名字', width: 150}
            , {field: 'description', title: '具体描述'}
            , {field: 'securityListString', title: '接口的列表'}
            , {field: 'isDeleted', title: '删除状态', templet: '#buttonTpl', align: 'center', width: 90}
            , {title: '操作', width: 225, align: 'center', fixed: 'right', toolbar: '#table-user-permission-delete'}
        ]]
        , text: '对不起，加载出现异常！'
    });

    // 后台权限组 监听工具条
    table.on('tool(LAY-user-back-permission)', function (obj) {
        var data = obj.data;
        console.log("data", data);
        if (obj.event === 'disable') {
            layer.confirm('确定禁用此权限组？', function (index) {
                admin.req({
                        url: '/permission/disable',
                        data: data,
                        done: function (res) {
                            permissionListTable.reload();
                            customBase.alertCustomMsg('已经禁用');
                        }
                    }
                    , 'post'
                );

            });
        } else if (obj.event === 'edit') {
            admin.popup({
                title: '更新权限组信息'
                , area: ['500px', '480px']
                , id: 'LAY-popup-permission-add'
                , success: function (layero, index) {
                    view(this.id).render('security/permission/permissionform', data).done(function () {
                        form.render(null, 'layuiadmin-form-permission');

                        //监听提交
                        form.on('submit(LAY-user-permission-submit)', function (data) {
                            var field = data.field; //获取提交的字段
                            console.log("更新接口信息的 field", field);

                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            admin.req({
                                    url: '/permission/addOrUpdate',
                                    data: field,
                                    done: function (res) {
                                        permissionListTable.reload();
                                        customBase.alertCustomMsg('更新成功');
                                        layer.close(index); //执行关闭
                                    }
                                }
                                , 'post'
                            );
                        });
                    });
                }
            });
        } else if (obj.event == 'enable') {
            layer.confirm('确定启用权限组？', function (index) {
                admin.req({
                        url: '/permission/enable',
                        data: data,
                        done: function (res) {
                            permissionListTable.reload();
                            customBase.alertCustomMsg('已经启用');

                        }
                    }
                    , 'post'
                );

            });
        }
    });

    // 前端菜单管理
    let menuListTable = table.render({
        elem: '#LAY-user-back-menu'
        , url: '/menu/getAllDTO' // 前端菜单管理
        , toolbar: true // 开启导出
        , title: '前端菜单管理' // 导出的文件名
        , loading: true // 显示加载动画
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', sort: true}
            , {field: 'parentId', title: '一级菜单', toolbar: '#super-parent-menu', align: 'center', width: 90}
            , {field: 'parentId', title: '父级菜单', toolbar: '#super-parent-menuId', align: 'center', width: 90}
            , {field: 'name', title: '菜单标识'}
            , {field: 'title', title: '菜单描述'}
            , {field: 'icon', title: '图标', toolbar: '#menu-icon-id', width: 60}
            , {field: 'jump', title: '跳转的链接'}
            , {field: 'orderId', width: 80, title: '排序'}
            , {field: 'isDeleted', title: '删除状态', toolbar: '#buttonTpl', align: 'center', width: 90}
            , {title: '操作', width: 300, align: 'center', fixed: 'right', toolbar: '#table-user-menu-delete'}
        ]]
        , text: '对不起，加载出现异常！'
    });

    // 前端菜单管理 监听工具条
    table.on('tool(LAY-user-back-menu)', function (obj) {
        var data = obj.data;
        console.log("data", data);
        if (obj.event === 'disable') {
            layer.confirm('确定禁用此菜单？', function (index) {
                admin.req({
                        url: '/menu/disable',
                        data: data,
                        done: function (res) {
                            menuListTable.reload();
                            customBase.alertCustomMsg('已经禁用');
                        }
                    }
                    , 'post'
                );

            });
        } else if (obj.event === 'edit') {
            admin.popup({
                title: '更新此菜单信息'
                , area: ['700px', '480px']
                , id: 'LAY-popup-menu-add'
                , success: function (layero, index) {
                    console.log(data);
                    view(this.id).render('security/menu/menuform', data).done(function () {
                        form.render(null, 'layuiadmin-form-menu');

                        //监听提交
                        form.on('submit(LAY-user-menu-submit)', function (data) {
                            var field = data.field; //获取提交的字段
                            console.log("更新的 field", field);

                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            admin.req({
                                    url: '/menu/addOrUpdate',
                                    data: field,
                                    done: function (res) {
                                        menuListTable.reload();
                                        customBase.alertCustomMsg('更新成功');
                                        layer.close(index); //执行关闭
                                    }
                                }
                                , 'post'
                            );
                        });
                    });
                }
            });
        } else if (obj.event == 'enable') {
            layer.confirm('确定启用此菜单？', function (index) {
                admin.req({
                        url: '/menu/enable',
                        data: data,
                        done: function (res) {
                            menuListTable.reload();
                            customBase.alertCustomMsg('已经启用');

                        }
                    }
                    , 'post'
                );

            });
        } else if (obj.event == 'addChildrenMenu') {
            // 添加子菜单信息
            admin.popup({
                title: '添加子菜单信息'
                , area: ['800px', '480px']
                , id: 'LAY-popup-menu-child-add'
                , success: function (layero, index) {
                    console.log(data);
                    data.parentId = data.id;
                    data.id = '';
                    data.gmtCreated = '';
                    data.gmtModified = '';
                    data.icon = '';
                    data.isDeleted = '';
                    data.jump = '';
                    data.lastModifiedUserId = '';
                    data.list = []
                    data.name = '';
                    data.orderId = '';
                    data.title = '';
                    view(this.id).render('security/menu/menuform', data).done(function () {
                        form.render(null, 'layuiadmin-form-menu');

                        //监听提交
                        form.on('submit(LAY-user-menu-submit)', function (data) {
                            var field = data.field; //获取提交的字段
                            console.log("更新的 field", field);

                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            admin.req({
                                    url: '/menu/addOrUpdate',
                                    data: field,
                                    done: function (res) {
                                        menuListTable.reload();
                                        customBase.alertCustomMsg('更新成功');
                                        layer.close(index); //执行关闭
                                    }
                                }
                                , 'post'
                            );
                        });
                    });
                }
            });
        }
    });

    // 年级管理列表
    let gradeListTable = table.render({
        elem: '#LAY-user-back-grade'
        , url: '/grade/getAll' // 年级管理列表
        , toolbar: true // 开启导出
        , title: '年级管理列表' // 导出的文件名
        , loading: true // 显示加载动画
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', sort: true}
            , {field: 'gradeNumber', title: '年级序号'}
            , {field: 'name', title: '年级名字'}
            , {field: 'isDeleted', title: '删除状态', templet: '#buttonTpl', align: 'center', width: 90}
            , {title: '操作', width: 225, align: 'center', fixed: 'right', toolbar: '#table-user-grade-delete'}
        ]]
        , text: '对不起，加载出现异常！'
    });

    // 年级管理列表 监听工具条
    table.on('tool(LAY-user-back-grade)', function (obj) {
        var data = obj.data;
        console.log("data", data);
        if (obj.event === 'disable') {
            layer.confirm('确定禁用此年级？', function (index) {
                admin.req({
                        url: '/grade/disable',
                        data: data,
                        done: function (res) {
                            gradeListTable.reload();
                            customBase.alertCustomMsg('已经禁用');
                        }
                    }
                    , 'post'
                );

            });
        } else if (obj.event === 'edit') {
            admin.popup({
                title: '更新年级信息'
                , area: ['500px', '480px']
                , id: 'LAY-popup-grade-add'
                , success: function (layero, index) {
                    view(this.id).render('teacher/grade/gradeform', data).done(function () {
                        form.render(null, 'layuiadmin-form-grade');

                        //监听提交
                        form.on('submit(LAY-user-grade-submit)', function (data) {
                            var field = data.field; //获取提交的字段
                            console.log("更新接口信息的 field", field);

                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            admin.req({
                                    url: '/grade/addOrUpdate',
                                    data: field,
                                    done: function (res) {
                                        gradeListTable.reload();
                                        customBase.alertCustomMsg('更新成功');
                                        layer.close(index); //执行关闭
                                    }
                                }
                                , 'post'
                            );
                        });
                    });
                }
            });
        } else if (obj.event == 'enable') {
            layer.confirm('确定启用此年级？', function (index) {
                admin.req({
                        url: '/grade/enable',
                        data: data,
                        done: function (res) {
                            gradeListTable.reload();
                            customBase.alertCustomMsg('已经启用此年级');

                        }
                    }
                    , 'post'
                );

            });
        }
    });

    // 班级管理列表
    let classesListTable = table.render({
        elem: '#LAY-user-back-classes'
        , url: '/classes/getAllDTO' // 班级管理列表
        , toolbar: true // 开启导出
        , title: '班级管理列表' // 导出的文件名
        , loading: true // 显示加载动画
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', sort: true}
            , {field: 'gradeId', title: '年级id'}
            , {field: 'gradeName', title: '年级名字'}
            , {field: 'classesNumber', title: '班级序号'}
            , {field: 'name', title: '班级名字'}
            , {field: 'isDeleted', title: '删除状态', templet: '#buttonTpl', align: 'center', width: 90}
            , {title: '操作', width: 225, align: 'center', fixed: 'right', toolbar: '#table-user-classes-delete'}
        ]]
        , text: '对不起，加载出现异常！'
    });

    // 班级管理列表 监听工具条
    table.on('tool(LAY-user-back-classes)', function (obj) {
        var data = obj.data;
        console.log("data", data);
        if (obj.event === 'disable') {
            layer.confirm('确定禁用此班级？', function (index) {
                admin.req({
                        url: '/classes/disable',
                        data: data,
                        done: function (res) {
                            classesListTable.reload();
                            customBase.alertCustomMsg('已经禁用');
                        }
                    }
                    , 'post'
                );

            });
        } else if (obj.event === 'edit') {
            admin.popup({
                title: '更新班级信息'
                , area: ['500px', '480px']
                , id: 'LAY-popup-classes-add'
                , success: function (layero, index) {
                    view(this.id).render('teacher/classes/classesform', data).done(function () {
                        form.render(null, 'layuiadmin-form-classes');

                        //监听提交
                        form.on('submit(LAY-user-classes-submit)', function (data) {
                            var field = data.field; //获取提交的字段
                            console.log("更新接口信息的 field", field);

                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            admin.req({
                                    url: '/classes/addOrUpdate',
                                    data: field,
                                    done: function (res) {
                                        classesListTable.reload();
                                        customBase.alertCustomMsg('更新成功');
                                        layer.close(index); //执行关闭
                                    }
                                }
                                , 'post'
                            );
                        });
                    });
                }
            });
        } else if (obj.event == 'enable') {
            layer.confirm('确定启用此班级？', function (index) {
                admin.req({
                        url: '/classes/enable',
                        data: data,
                        done: function (res) {
                            classesListTable.reload();
                            customBase.alertCustomMsg('已经启用此班级');

                        }
                    }
                    , 'post'
                );

            });
        }
    });

    // 课程管理列表
    let courseListTable = table.render({
        elem: '#LAY-user-back-course'
        , url: '/course/getAllDTO' // 课程管理列表
        , toolbar: true // 开启导出
        , title: '课程管理列表' // 导出的文件名
        , loading: true // 显示加载动画
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', sort: true}
            , {field: 'code', title: '英文code'}
            , {field: 'name', title: '课程名字'}
            , {field: 'isDeleted', title: '删除状态', templet: '#buttonTpl', align: 'center', width: 90}
            , {title: '操作', width: 350, align: 'center', fixed: 'right', toolbar: '#table-user-course-delete'}
        ]]
        , text: '对不起，加载出现异常！'
    });

// 课程管理列表 监听工具条
    table.on('tool(LAY-user-back-course)', function (obj) {
        var data = obj.data;
        console.log("data", data);
        if (obj.event === 'disable') {
            layer.confirm('确定禁用此课程？', function (index) {
                admin.req({
                        url: '/course/disable',
                        data: data,
                        done: function (res) {
                            courseListTable.reload();
                            customBase.alertCustomMsg('已经禁用');
                        }
                    }
                    , 'post'
                );

            });
        } else if (obj.event === 'edit') {
            admin.popup({
                title: '更新课程信息'
                , area: ['500px', '480px']
                , id: 'LAY-popup-course-add'
                , success: function (layero, index) {
                    view(this.id).render('teacher/course/courseform', data).done(function () {
                        form.render(null, 'layuiadmin-form-course');

                        //监听提交
                        form.on('submit(LAY-user-course-submit)', function (data) {
                            var field = data.field; //获取提交的字段
                            console.log("更新接口信息的 field", field);

                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            admin.req({
                                    url: '/course/addOrUpdate',
                                    data: field,
                                    done: function (res) {
                                        courseListTable.reload();
                                        customBase.alertCustomMsg('更新成功');
                                        layer.close(index); //执行关闭
                                    }
                                }
                                , 'post'
                            );
                        });
                    });
                }
            });
        } else if (obj.event == 'enable') {
            layer.confirm('确定启用此课程？', function (index) {
                admin.req({
                        url: '/course/enable',
                        data: data,
                        done: function (res) {
                            courseListTable.reload();
                            customBase.alertCustomMsg('已经启用此课程');

                        }
                    }
                    , 'post'
                );

            });
        } else if (obj.event = 'update_course_weekly_course') {

            admin.popup({
                title: '更新课程的每周排课规则'
                , area: ['1300px', '600px']
                , id: 'LAY-popup-course-update-weekly-course'
                , success: function (layero, index) {
                    data.bizId = data.id;
                    data.bizType = 'COURSE';
                    view(this.id).render('weeklycourse/weeklycourseform', data).done(function () {
                        console.log('update_course_weekly_course data:', data)
                        // form.render(null, 'weekly_course_form_select_params');

                        // //监听提交
                        // form.on('submit(LAY-user-course-submit)', function (data) {
                        //     var field = data.field; //获取提交的字段
                        //     console.log("更新接口信息的 field", field);
                        //
                        //     //提交 Ajax 成功后，关闭当前弹层并重载表格
                        //     admin.req({
                        //             url: '/course/',
                        //             data: field,
                        //             done: function (res) {
                        //                 courseListTable.reload();
                        //                 customBase.alertCustomMsg('更新成功');
                        //                 layer.close(index); //执行关闭
                        //             }
                        //         }
                        //         , 'post'
                        //     );
                        // });
                    });
                }
            });
        }
    });

    // 年级课程管理列表
    let gradeCourseListTable = table.render({
        elem: '#LAY-user-back-gradecourse'
        , url: '/gradeCourse/getAllDTO' // 年级课程管理列表
        , toolbar: true // 开启导出
        , title: '年级课程管理列表' // 导出的文件名
        , loading: true // 显示加载动画
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', sort: true}
            , {field: 'gradeName', title: '年级名字'}
            , {field: 'courseName', title: '课程名字'}
            , {field: 'courseCount', title: '课程节数'}
            , {field: 'isDeleted', title: '删除状态', templet: '#buttonTpl', align: 'center', width: 90}
            , {title: '操作', width: 225, align: 'center', fixed: 'right', toolbar: '#table-user-gradecourse-delete'}
        ]]
        , text: '对不起，加载出现异常！'
    });

// 年级课程管理列表 监听工具条
    table.on('tool(LAY-user-back-gradecourse)', function (obj) {
        var data = obj.data;
        console.log("data", data);
        if (obj.event === 'disable') {
            layer.confirm('确定禁用此年级课程？', function (index) {
                admin.req({
                        url: '/gradeCourse/disable',
                        data: data,
                        done: function (res) {
                            gradeCourseListTable.reload();
                            customBase.alertCustomMsg('已经禁用');
                        }
                    }
                    , 'post'
                );

            });
        } else if (obj.event === 'edit') {
            admin.popup({
                title: '更新年级课程信息'
                , area: ['500px', '480px']
                , id: 'LAY-popup-gradecourse-add'
                , success: function (layero, index) {
                    view(this.id).render('teacher/gradeCourse/gradeCourseform', data).done(function () {
                        form.render(null, 'layuiadmin-form-gradecourse');

                        //监听提交
                        form.on('submit(LAY-user-gradecourse-submit)', function (data) {
                            var field = data.field; //获取提交的字段
                            console.log("更新接口信息的 field", field);

                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            admin.req({
                                    url: '/gradeCourse/addOrUpdate',
                                    data: field,
                                    done: function (res) {
                                        gradeCourseListTable.reload();
                                        customBase.alertCustomMsg('更新成功');
                                        layer.close(index); //执行关闭
                                    }
                                }
                                , 'post'
                            );

                        });
                    });
                }
            });
        } else if (obj.event == 'enable') {
            layer.confirm('确定启用此年级课程？', function (index) {
                admin.req({
                        url: '/gradeCourse/enable',
                        data: data,
                        done: function (res) {
                            gradeCourseListTable.reload();
                            customBase.alertCustomMsg('已经启用此年级课程');

                        }
                    }
                    , 'post'
                );

            });
        }
    });

    // 课程特殊规则管理列表
    let coursespecialruleListTable = table.render({
        elem: '#LAY-user-back-coursespecialrule'
        , url: '/courseSpecialRule/getAllDTO' // 课程特殊规则管理列表
        , toolbar: true // 开启导出
        , title: '课程特殊规则管理列表' // 导出的文件名
        , loading: true // 显示加载动画
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 80, title: 'ID', sort: true}
            , {field: 'bizType', title: '业务维度', width: 90}
            , {field: 'bizId', title: '业务id', width: 80}
            , {field: 'ruleString', title: '规则code', width: 240}
            , {field: 'extStr', title: 'extStr'}
            , {field: 'bizIdDesc', title: '业务描述', width: 90}
            , {field: 'ruleStringDesc', title: '规则描述'}
            , {field: 'extStrDesc', title: 'extStrDesc'}
            , {field: 'isDeleted', title: '删除状态', templet: '#buttonTpl', align: 'center', width: 90}
            , {
                title: '操作',
                width: 225,
                align: 'center',
                fixed: 'right',
                toolbar: '#table-user-coursespecialrule-delete'
            }
        ]]
        , text: '对不起，加载出现异常！'
    });

// 课程特殊规则管理列表 监听工具条
    table.on('tool(LAY-user-back-coursespecialrule)', function (obj) {
        var data = obj.data;
        console.log("data", data);
        if (obj.event === 'disable') {
            layer.confirm('确定禁用此课程？', function (index) {
                admin.req({
                        url: '/courseSpecialRule/disable',
                        data: data,
                        done: function (res) {
                            coursespecialruleListTable.reload();
                            customBase.alertCustomMsg('已经禁用');
                        }
                    }
                    , 'post'
                );

            });
        } else if (obj.event === 'edit') {
            admin.popup({
                title: '更新课程特殊规则信息'
                , area: ['800px', '480px']
                , id: 'LAY-popup-coursespecialrule-add'
                , success: function (layero, index) {
                    view(this.id).render('teacher/coursespecialrule/coursespecialruleform', data).done(function () {
                        form.render(null, 'layuiadmin-form-coursespecialrule');

                        //监听提交
                        form.on('submit(LAY-user-coursespecialrule-submit)', function (data) {
                            var field = data.field; //获取提交的字段
                            console.log("更新接口信息的 field", field);

                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            admin.req({
                                    url: '/courseSpecialRule/addOrUpdate',
                                    data: field,
                                    done: function (res) {
                                        coursespecialruleListTable.reload();
                                        customBase.alertCustomMsg('更新成功');
                                        layer.close(index); //执行关闭
                                    }
                                }
                                , 'post'
                            );
                        });
                    });
                }
            });
        } else if (obj.event == 'enable') {
            layer.confirm('确定启用此课程特殊规则？', function (index) {
                admin.req({
                        url: '/courseSpecialRule/enable',
                        data: data,
                        done: function (res) {
                            coursespecialruleListTable.reload();
                            customBase.alertCustomMsg('已经启用此课程特殊规则');

                        }
                    }
                    , 'post'
                );

            });
        }
    });


    //班级课程老师管理
    let classcourseteacherListTable = table.render({
        elem: '#LAY-classcourseteacher-manage'
        , url: '/classesCourseTeacher/getAllDTOPage'
        , toolbar: true // 开启导出
        , title: '班级课程老师管理' // 导出的文件名
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', width: 100, title: 'ID', sort: true}
            , {field: 'classesId', title: '班级id'}
            , {field: 'courseId', title: '课程Id'}
            , {field: 'teacherId', title: '老师id'}
            , {field: 'gradeNameAndClassesName', title: '班级名字'}
            , {field: 'courseName', title: '课程姓名'}
            , {field: 'teacherName', title: '老师姓名'}
            , {
                title: '操作',
                width: 225,
                align: 'center',
                fixed: 'right',
                toolbar: '#table-useradmin-classescourseteacher'
            }
        ]]
        , page: true
        , limit: 90
        // , height: 'full-320' 内部撑满去掉
        , text: '对不起，加载出现异常！'
    });

    //监听工具条
    table.on('tool(LAY-classcourseteacher-manage)', function (obj) {
        var data = obj.data;
        if (obj.event === 'disable') {
            layer.confirm('确定禁用此班级课程老师？', function (index) {
                admin.req({
                        url: '/classesCourseTeacher/disable',
                        data: data,
                        done: function (res) {
                            classcourseteacherListTable.reload();
                            customBase.alertCustomMsg('已经禁用');
                        }
                    }
                    , 'post'
                );

            });
        } else if (obj.event === 'edit') {
            admin.popup({
                title: '编辑班级课程老师'
                , area: ['500px', '450px']
                , id: 'LAY-popup-classcourseteacherform-add'
                , success: function (layero, index) {
                    view(this.id).render('teacher/classescourseteacher/classescourseteacherform', data).done(function () {
                        form.render(null, 'layuiadmin-form-classcourseteacher');

                        //监听提交
                        form.on('submit(LAY-classcourseteacherform-front-submit)', function (data) {
                            // 提交 Ajax 成功后，关闭当前弹层并重载表格
                            var field = data.field; //获取提交的字段
                            console.log("更新信息的 field", field);

                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            admin.req({
                                    url: '/classesCourseTeacher/addOrUpdate',
                                    data: field,
                                    done: function (res) {
                                        classcourseteacherListTable.reload();
                                        customBase.alertCustomMsg('更新成功');
                                        layer.close(index); //执行关闭
                                    }
                                }
                                , 'post'
                            );

                        });
                    });
                }
            });
        } else if (obj.event == 'enable') {
            layer.confirm('确定启班级课程老师？', function (index) {
                admin.req({
                        url: '/classesCourseTeacher/enable',
                        data: data,
                        done: function (res) {
                            classcourseteacherListTable.reload();
                            customBase.alertCustomMsg('已经启用');

                        }
                    }
                    , 'post'
                );

            });
        }
    });

    exports('useradmin', {})
});