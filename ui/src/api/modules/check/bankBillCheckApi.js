import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/check/bankBillCheck/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (transDate, merchantId, siteId) => {
  return request({
    url: `api/v1/check/bankBillCheck/${transDate}/${merchantId}/${siteId}`,
    method: 'get'
  })
}


export const deleteByIds = (bankBillCheckList) => {
  return request({
    url: 'api/v1/check/bankBillCheck/deleteByIds',
    method: 'delete',
    data: bankBillCheckList || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/check/bankBillCheck',
    method: 'post',
    data: data
  })
}

export const update = (transDate, merchantId, siteId, data) => {
  return request({
    url: `api/v1/check/bankBillCheck/${transDate}/${merchantId}/${siteId}`,
    method: 'put',
    data: data
  })
}
