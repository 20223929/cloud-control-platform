import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/report/agentReport/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (sysId) => {
  return request({
    url: `api/v1/report/agentReport/${sysId}`,
    method: 'get'
  })
}


export const deleteByIds = (ids) => {
  return request({
    url: 'api/v1/report/agentReport/deleteByIds',
    method: 'delete',
    data: ids || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/report/agentReport',
    method: 'post',
    data: data
  })
}

export const update = (sysId, data) => {
  return request({
    url: `api/v1/report/agentReport/${sysId}`,
    method: 'put',
    data: data
  })
}

export const getMerchantTreeData = (params, data) => {
  return request({
    url: 'api/v1/merchant/tbMerchant/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}
