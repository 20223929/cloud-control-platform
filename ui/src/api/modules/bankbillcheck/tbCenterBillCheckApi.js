import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/bankbillcheck/tbCenterBillCheck/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (transDate, merchantGroupId, merchantId, siteId) => {
  return request({
    url: `api/v1/bankbillcheck/tbCenterBillCheck/${transDate}/${merchantGroupId}/${merchantId}/${siteId}`,
    method: 'get'
  })
}

export const batchConfirm = (tbCenterBillCheckList) => {
  return request({
    url: 'api/v1/bankbillcheck/tbCenterBillCheck/batchConfirm',
    method: 'post',
    data: tbCenterBillCheckList || []
  })
}

export const batchSecondConfirm = (tbCenterBillCheckList) => {
  return request({
    url: 'api/v1/bankbillcheck/tbCenterBillCheck/batchSecondConfirm',
    method: 'post',
    data: tbCenterBillCheckList || []
  })
}

export const deleteByIds = (tbCenterBillCheckList) => {
  return request({
    url: 'api/v1/bankbillcheck/tbCenterBillCheck/deleteByIds',
    method: 'delete',
    data: tbCenterBillCheckList || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/bankbillcheck/tbCenterBillCheck',
    method: 'post',
    data: data
  })
}

export const update = (transDate, merchantGroupId, merchantId, siteId, data) => {
  return request({
    url: `api/v1/bankbillcheck/tbCenterBillCheck/${transDate}/${merchantGroupId}/${merchantId}/${siteId}`,
    method: 'put',
    data: data
  })
}

export const myConfirm = (data) => {
  return request({
    url: 'api/v1/bankbillcheck/tbCenterBillCheck/confirm',
    method: 'post',
    data: data
  })
}

export const mySecondConfirm = (data) => {
  return request({
    url: 'api/v1/bankbillcheck/tbCenterBillCheck/secondConfirm',
    method: 'post',
    data: data
  })
}
