import request from 'ecip-web/utils/request'


export const getData = (params, data) => {
  return request({
    url: 'api/v1/test/customtree/testGoods/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (id) => {
  return request({
    url: `api/v1/test/customtree/testGoods/${id}`,
    method: 'get'
  })
}

export const removeByIds = (ids) => {
  return request({
    url: 'api/v1/test/customtree/testGoods/removeByIds',
    method: 'put',
    data: ids || []
  })
}

export const deleteByIds = (ids) => {
  return request({
    url: 'api/v1/test/customtree/testGoods/deleteByIds',
    method: 'delete',
    data: ids || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/test/customtree/testGoods',
    method: 'post',
    data: data
  })
}

export const update = (id, data) => {
  return request({
    url: `api/v1/test/customtree/testGoods/${id}`,
    method: 'put',
    data: data
  })
}