import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/bankbillcheck/tbBankBillCheckDetail/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (transDate, merchantGroupId, merchantId, siteId, equipmentId) => {
  return request({
    url: `api/v1/bankbillcheck/tbBankBillCheckDetail/${transDate}/${merchantGroupId}/${merchantId}/${siteId}/${equipmentId}`,
    method: 'get'
  })
}


export const deleteByIds = (tbBankBillCheckDetailList) => {
  return request({
    url: 'api/v1/bankbillcheck/tbBankBillCheckDetail/deleteByIds',
    method: 'delete',
    data: tbBankBillCheckDetailList || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/bankbillcheck/tbBankBillCheckDetail',
    method: 'post',
    data: data
  })
}

export const update = (transDate, merchantGroupId, merchantId, siteId, equipmentId, data) => {
  return request({
    url: `api/v1/bankbillcheck/tbBankBillCheckDetail/${transDate}/${merchantGroupId}/${merchantId}/${siteId}/${equipmentId}`,
    method: 'put',
    data: data
  })
}
