import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/monitor/chargeExcepion/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (transDate, merchantGroupId, merchantId, siteId, equipmentId) => {
  return request({
    url: `api/v1/monitor/chargeExcepion/${transDate}/${merchantGroupId}/${merchantId}/${siteId}/${equipmentId}`,
    method: 'get'
  })
}


export const deleteByIds = (chargeExcepionList) => {
  return request({
    url: 'api/v1/monitor/chargeExcepion/deleteByIds',
    method: 'delete',
    data: chargeExcepionList || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/monitor/chargeExcepion',
    method: 'post',
    data: data
  })
}

export const update = (data) => {
  return request({
    url: 'api/v1/monitor/chargeExcepion/confirm',
    method: 'put',
    data: data
  })
}
