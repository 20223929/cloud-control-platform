import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/report/serviceRecvReport/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (sysId) => {
  return request({
    url: `api/v1/report/serviceRecvReport/${sysId}`,
    method: 'get'
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

