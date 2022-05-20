import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/merchant/clearAccountReport/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = () => {
  return request({
    url: `api/v1/merchant/clearAccountReport`,
    method: 'get'
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/merchant/clearAccountReport',
    method: 'post',
    data: data
  })
}

export const update = (data) => {
  return request({
    url: `api/v1/merchant/clearAccountReport`,
    method: 'put',
    data: data
  })
}
