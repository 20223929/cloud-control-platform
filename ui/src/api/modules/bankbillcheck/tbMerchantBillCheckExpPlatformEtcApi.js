import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/bankbillcheck/tbMerchantBillCheckExpPlatformEtc/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/bankbillcheck/tbMerchantBillCheckExpPlatformEtc',
    method: 'post',
    data: data
  })
}

export const update = (transDate, merchantGroupId, merchantId, siteId, data) => {
  return request({
    url: `api/v1/bankbillcheck/tbMerchantBillCheckExpPlatformEtc/${transDate}/${merchantGroupId}/${merchantId}/${siteId}`,
    method: 'put',
    data: data
  })
}

export const errorRegister = (data)=>{
  return request({
    url: 'api/v1/bankbillcheck/tbMerchantBillCheckExpPlatformEtc/errorRegister',
    method: 'post',
    data: data
  })
}

export const confirmRegister = (data)=>{
  return request({
    url: 'api/v1/bankbillcheck/tbMerchantBillCheckExpPlatformEtc/confirmRegister',
    method: 'post',
    data: data
  })
}

export const batchConfirmRegister = (data)=>{
  return request({
    url: 'api/v1/bankbillcheck/tbMerchantBillCheckExpPlatformEtc/batchConfirmRegister',
    method: 'post',
    data: data
  })
}
