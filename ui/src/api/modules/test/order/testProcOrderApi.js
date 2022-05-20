import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/test/order/testProcOrder/data',
    method: 'post',
    params: params,
    data: data
  })
}

export const getById = (id) => {
  return request({
    url: `api/v1/test/order/testProcOrder/getById/${id}`,
    method: 'get'
  })
}

export const remove = (ids) => {
  return request({
    url: 'api/v1/test/order/testProcOrder/remove',
    method: 'put',
    data: ids
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/test/order/testProcOrder',
    method: 'post',
    data: data
  })
}

export const update = (id, data) => {
  return request({
    url: `api/v1/test/order/testProcOrder/${id}`,
    method: 'put',
    data: data
  })
}

export const submit = (id, data) => {
  return request({
    url: `api/v1/test/order/testProcOrder/${id}/submit`,
    method: 'post',
    data
  })
}

