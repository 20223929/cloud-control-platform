import request from 'ecip-web/utils/request'

// 用户分页查询（vue版用户管理）
export const userPage = (params, data) => {
  return request({
    url: 'api/v1/user/sysrUser/page',
    method: 'post',
    params: params,
    data: data || {}
  })
}

// 用户分页查询（EcUserModal）
export const getData = (params, data) => {
  return request({
    url: 'api/v1/user/sysrUser/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const getList = (params) => {
  return request({
    url: 'api/v1/user/sysrUser/list',
    method: 'get',
    params: params
  })
}

export const getById = (id) => {
  return request({
    url: `api/v1/user/sysrUser/${id}`,
    method: 'get'
  })
}

export const findDataByIds = (ids) => {
  return request({
    url: `api/v1/user/sysrUser/findDataByIds`,
    method: 'post',
    data: ids && ids.split(',') || []
  })
}

export const findAppRoles = (userId, tenantId, appList) => {
  return request({
    url: `api/v1/user/sysrUser/${userId}/appRoles`,
    method: 'post',
    params: { tenantId: tenantId },
    data: appList
  })
}

// 查询某租户下的用户列表
export const findTenantUsers = (params) => {
  return request({
    url: `api/v1/user/sysrUser/listByTenant`,
    method: 'get',
    params: params
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/user/sysrUser',
    method: 'post',
    data: data
  })
}

export const update = (id, data) => {
  return request({
    url: `api/v1/user/sysrUser/${id}`,
    method: 'put',
    data: data
  })
}

export const updatePartInfo = (id, data) => {
  return request({
    url: `api/v1/user/sysrUser/${id}/updatePartInfo`,
    method: 'put',
    data: data
  })
}

export const sendSmsCode = (phone) => {
  return request({
    url: `api/v1/user/sysrUser/sendSmsCode`,
    method: 'get',
    params: { phone }
  })
}

export const sendEmailCode = (email) => {
  return request({
    url: `api/v1/user/sysrUser/sendEmailCode`,
    method: 'get',
    params: { email }
  })
}

export const validatePhone = (phone, code) => {
  return request({
    url: `api/v1/user/sysrUser/validatePhone`,
    method: 'put',
    params: { phone, code }
  })
}

export const validateEmail = (email, code) => {
  return request({
    url: `api/v1/user/sysrUser/validateEmail`,
    method: 'put',
    params: { email, code }
  })
}

export const updateInsider = (id, data) => {
  return request({
    url: `api/v1/user/sysrUser/${id}/insider`,
    method: 'put',
    data: data
  })
}

export const deleteByIds = (ids) => {
  return request({
    url: 'api/v1/user/sysrUser/deleteByIds',
    method: 'delete',
    data: ids || []
  })
}

export const checkAccount = (userId, account) => {
  return request({
    url: 'api/v1/user/sysrUser/checkAccount',
    method: 'get',
    params: { id: userId, account: account }
  })
}

export const findUserRoles = (userId) => {
  return request({
    url: `api/v1/user/sysrUser/${userId}/role`,
    method: 'get'
  })
}

export const saveUserRoles = (userId, roleIds) => {
  return request({
    url: `api/v1/user/sysrUser/${userId}/role`,
    method: 'post',
    data: roleIds
  })
}

export const findUserApps = (userId) => {
  return request({
    url: `api/v1/user/sysrUser/${userId}/app`,
    method: 'get'
  })
}

export const saveUserApps = (userId, appIds) => {
  return request({
    url: `api/v1/user/sysrUser/${userId}/app`,
    method: 'post',
    data: appIds
  })
}

export const findCurrUserAbility = () => {
  return request({
    url: `api/v1/user/sysrUser/ability`,
    method: 'get'
  })
}

export const findCurrUserRoles = () => {
  return request({
    url: `api/v1/user/sysrUser/role`,
    method: 'get'
  })
}

// 查找用户关联的组织权限
export const findUserDepts = (userId) => {
  return request({
    url: `api/v1/user/sysrUser/${userId}/userDept`,
    method: 'get'
  })
}

// 授权用户组织权限
export const saveUserDepts = (userId, deptIds) => {
  return request({
    url: `api/v1/user/sysrUser/${userId}/userDept`,
    method: 'post',
    data: deptIds
  })
}

// 清空用户组织权限
export const deleteUserDepts = (userId) => {
  return request({
    url: `api/v1/user/sysrUser/${userId}/userDept`,
    method: 'delete'
  })
}

// 查找用户关联的架构权限
export const findUserOrgs = (userId) => {
  return request({
    url: `api/v1/user/sysrUser/${userId}/userOrg`,
    method: 'get'
  })
}

// 授权用户架构权限
export const saveUserOrgs = (userId, orgIds) => {
  return request({
    url: `api/v1/user/sysrUser/${userId}/userOrg`,
    method: 'post',
    data: orgIds
  })
}

// 清空用户组织权限
export const deleteUserOrgs = (userId) => {
  return request({
    url: `api/v1/user/sysrUser/${userId}/userOrg`,
    method: 'delete'
  })
}

// 重置密码
export const resetPassword = (userId) => {
  return request({
    url: `api/v1/user/sysrUser/${userId}/resetPassword`,
    method: 'put'
  })
}

// 修改密码
export const changePwd = (changePwd) => {
  return request({
    url: `api/v1/user/sysrUser/changePwd`,
    method: 'put',
    data: changePwd
  })
}

// 切换身份
export const impersonate = (userId) => {
  return request({
    url: `api/v1/user/sysrUser/impersonate/${userId}`,
    method: 'post'
  })
}

export const updateSuperior = (superior, userIds) => {
  return request({
    url: `api/v1/user/sysrUser/${superior}/updateSuperior`,
    method: 'post',
    data: userIds
  })
}
