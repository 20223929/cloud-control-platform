import request from 'ecip-web/utils/request'

export const findUserMerchant = (params, data) => {
  return request({
    url: 'api/v1/merchant/sysUserMerchant/userMerchant',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const getData = (params, data) => {
  return request({
    url: 'api/v1/merchant/sysUserMerchant/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (id) => {
  return request({
    url: `api/v1/merchant/sysUserMerchant/${id}`,
    method: 'get'
  })
}

export const removeByIds = (ids) => {
  return request({
    url: 'api/v1/merchant/sysUserMerchant/removeByIds',
    method: 'put',
    data: ids || []
  })
}

export const deleteByIds = (ids) => {
  return request({
    url: 'api/v1/merchant/sysUserMerchant/deleteByIds',
    method: 'delete',
    data: ids || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/merchant/sysUserMerchant',
    method: 'post',
    data: data
  })
}

export const update = (id, data) => {
  return request({
    url: `api/v1/merchant/sysUserMerchant/${id}`,
    method: 'put',
    data: data
  })
}

export const userPage = (params, data) => {
  return request({
    url: 'api/v1/merchant/sysUserMerchant/userPage',
    method: 'post',
    params: params,
    data: data || {}
  })
}
