// 导入表格模版数据
export const userColumns = [
    {
        title: "username",
        key: "username"
    },
    {
        title: "password",
        key: "password"
    },
    {
        title: "avatar",
        key: "avatar"
    },
    {
        title: "departmentId",
        key: "departmentId"
    },
    {
        title: "mobile",
        key: "mobile"
    },
    {
        title: "email",
        key: "email"
    },
    {
        title: "sex",
        key: "sex"
    },
    {
        title: "address",
        key: "address"
    },
    {
        title: "type",
        key: "type"
    },
    {
        title: "status",
        key: "status"
    },
    {
        title: "delFlag",
        key: "delFlag"
    },
    {
        title: "defaultRole",
        key: "defaultRole"
    }
]

export const userData = [
    {
        username: "【记得删除该说明行】唯一用户名不能为空",
        password: "密码不能为空",
        avatar: "头像图片链接",
        departmentId: "部门表主键id",
        email: "邮箱",
        mobile: "手机",
        sex: "性别 0(女) 1(男)",
        address: "地址数据省市编号 请勿乱填",
        type: "用户类型 0(普通用户) 1(管理员)",
        status: "用户状态 0(正常) -1(禁用)",
        delFlag: "删除标志 0(正常) 1(已删)",
        defaultRole: "角色 0(不分配默认角色) 1(分配默认注册用户角色)"
    },
    {
        username: "zwz",
        password: "123456",
        avatar: "https://s1.ax1x.com/2018/05/19/CcdVQP.png",
        departmentId: "40652338142121984",
        email: "916077357@qq.com",
        sex: 1,
        address: "[\"510000\",\"510100\",\"510104\"]",
        mobile: "17858758888",
        type: 0,
        status: 0,
        delFlag: 0,
        defaultRole: 1
    }
]