import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/bankbillcheck/tbBankBillCheck/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (transDate, merchantGroupId, merchantId, siteId) => {
  return request({
    url: `api/v1/bankbillcheck/tbBankBillCheck/${transDate}/${merchantGroupId}/${merchantId}/${siteId}`,
    method: 'get'
  })
}


export const deleteByIds = (tbBankBillCheckList) => {
  return request({
    url: 'api/v1/bankbillcheck/tbBankBillCheck/deleteByIds',
    method: 'delete',
    data: tbBankBillCheckList || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/bankbillcheck/tbBankBillCheck',
    method: 'post',
    data: data
  })
}

export const update = (transDate, merchantGroupId, merchantId, siteId, data) => {
  return request({
    url: `api/v1/bankbillcheck/tbBankBillCheck/${transDate}/${merchantGroupId}/${merchantId}/${siteId}`,
    method: 'put',
    data: data
  })
}

export const batchConfirm = (tbBillCheckList) => {
  return request({
    url: 'api/v1/bankbillcheck/tbBankBillCheck/batchConfirm',
    method: 'post',
    data: tbBillCheckList || []
  })
}
