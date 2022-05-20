import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/report/clearBill/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}
export const getMerchantTreeData = (params, data) => {
  return request({
    url: 'api/v1/merchant/tbMerchant/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}
