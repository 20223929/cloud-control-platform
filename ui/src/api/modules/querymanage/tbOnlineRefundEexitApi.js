import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/querymanage/tbOnlineRefundEexit/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (sysId) => {
  return request({
    url: `api/v1/querymanage/tbOnlineRefundEexit/${sysId}`,
    method: 'get'
  })
}


export const deleteByIds = (ids) => {
  return request({
    url: 'api/v1/querymanage/tbOnlineRefundEexit/deleteByIds',
    method: 'delete',
    data: ids || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/querymanage/tbOnlineRefundEexit',
    method: 'post',
    data: data
  })
}

export const update = (sysId, data) => {
  return request({
    url: `api/v1/querymanage/tbOnlineRefundEexit/${sysId}`,
    method: 'put',
    data: data
  })
}

export const info = (tbRefundApplySysId) => {
  return request({
    url: `api/v1/querymanage/tbOnlineRefundEexit/${tbRefundApplySysId}`,
    method: 'get'
  })
}
