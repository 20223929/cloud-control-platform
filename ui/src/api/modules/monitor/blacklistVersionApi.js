import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/monitor/blacklistVersion/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (id) => {
  return request({
    url: `api/v1/monitor/blacklistVersion/${id}`,
    method: 'get'
  })
}


export const deleteByIds = (ids) => {
  return request({
    url: 'api/v1/monitor/blacklistVersion/deleteByIds',
    method: 'delete',
    data: ids || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/monitor/blacklistVersion',
    method: 'post',
    data: data
  })
}

export const update = (id, data) => {
  return request({
    url: `api/v1/monitor/blacklistVersion/${id}`,
    method: 'put',
    data: data
  })
}
