import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/bankbillcheck/tbCenterBillCheckDetail/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (transDate, merchantGroupId, merchantId, siteId, equipmentId) => {
  return request({
    url: `api/v1/bankbillcheck/tbCenterBillCheckDetail/${transDate}/${merchantGroupId}/${merchantId}/${siteId}/${equipmentId}`,
    method: 'get'
  })
}


export const deleteByIds = (tbCenterBillCheckDetailList) => {
  return request({
    url: 'api/v1/bankbillcheck/tbCenterBillCheckDetail/deleteByIds',
    method: 'delete',
    data: tbCenterBillCheckDetailList || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/bankbillcheck/tbCenterBillCheckDetail',
    method: 'post',
    data: data
  })
}

export const update = (transDate, merchantGroupId, merchantId, siteId, equipmentId, data) => {
  return request({
    url: `api/v1/bankbillcheck/tbCenterBillCheckDetail/${transDate}/${merchantGroupId}/${merchantId}/${siteId}/${equipmentId}`,
    method: 'put',
    data: data
  })
}
