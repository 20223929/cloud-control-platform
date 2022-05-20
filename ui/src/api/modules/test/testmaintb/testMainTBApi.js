import request from 'ecip-web/utils/request'

export const getData = (params, data) => {
  return request({
    url: 'api/v1/test/testmaintb/testMainTB/data',
    method: 'post',
    params: params,
    data: data
  })
}

export const getById = (id) => {
  return request({
    url: `api/v1/test/testmaintb/testMainTB/${id}`,
    method: 'get'
  })
}

export const getList = (params, withChild) => {
  return request({
    url: `api/v1/test/testmaintb/testMainTB/list`,
    method: 'get',
    params: { ...params, withChild: withChild || false }
  })
}

export const removeByIds = (ids) => {
  return request({
    url: `api/v1/test/testmaintb/testMainTB/removeByIds`,
    method: 'put',
    data: ids || []
  })
}

export const deleteByIds = (ids) => {
  return request({
    url: `api/v1/test/testmaintb/testMainTB/deleteByIds`,
    method: 'delete',
    data: ids || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/test/testmaintb/testMainTB',
    method: 'post',
    data: data
  })
}

export const update = (id, data) => {
  return request({
    url: `api/v1/test/testmaintb/testMainTB/${id}`,
    method: 'put',
    data: data
  })
}