import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/report/settlementReport/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const update = (sysId) => {
  return request({
    url: `api/v1/report/settlementReport/${sysId}`,
    method: 'put'
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
