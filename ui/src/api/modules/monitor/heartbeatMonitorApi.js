import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/monitor/heartbeatMonitor/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (sysId) => {
  return request({
    url: `api/v1/monitor/heartbeatMonitor/${sysId}`,
    method: 'get'
  })
}


export const deleteByIds = (ids) => {
  return request({
    url: 'api/v1/monitor/heartbeatMonitor/deleteByIds',
    method: 'delete',
    data: ids || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/monitor/heartbeatMonitor',
    method: 'post',
    data: data
  })
}

export const update = (sysId, data) => {
  return request({
    url: `api/v1/monitor/heartbeatMonitor/${sysId}`,
    method: 'put',
    data: data
  })
}
