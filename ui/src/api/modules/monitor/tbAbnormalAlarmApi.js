import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/monitor/tbAbnormalAlarm/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (sysId) => {
  return request({
    url: `api/v1/monitor/tbAbnormalAlarm/${sysId}`,
    method: 'get'
  })
}


export const dealByIds = (ids) => {
  return request({
    url: 'api/v1/monitor/tbAbnormalAlarm/dealByIds',
    method: 'put',
    data: ids || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/monitor/tbAbnormalAlarm',
    method: 'post',
    data: data
  })
}

export const update = (sysId, data) => {
  return request({
    url: `api/v1/monitor/tbAbnormalAlarm/${sysId}`,
    method: 'put',
    data: data
  })
}
