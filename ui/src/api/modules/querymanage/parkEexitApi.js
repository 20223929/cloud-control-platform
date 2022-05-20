import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/querymanage/parkEexit/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const deleteByIds = (ids) => {
  return request({
    url: 'api/v1/querymanage/parkEexit/deleteByIds',
    method: 'delete',
    data: ids || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/querymanage/parkEexit',
    method: 'post',
    data: data
  })
}

export const update = (sysId, data) => {
  return request({
    url: `api/v1/querymanage/parkEexit/${sysId}`,
    method: 'put',
    data: data
  })
}

export const info = (data) => {
  return request({
    url: `api/v1/querymanage/parkEexit/detail`,
    method: 'post',
    data: data || {}
  })
}
