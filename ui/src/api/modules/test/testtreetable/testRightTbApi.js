import request from 'ecip-web/utils/request'


export const getData = (params, data) => {
  return request({
    url: 'api/v1/test/testtreetable/testRightTb/data',
    method: 'post',
    params: params,
    data: data || {}
  })
}

export const findByPrimaryKey = (id) => {
  return request({
    url: `api/v1/test/testtreetable/testRightTb/${id}`,
    method: 'get'
  })
}

export const removeByIds = (ids) => {
  return request({
    url: 'api/v1/test/testtreetable/testRightTb/removeByIds',
    method: 'put',
    data: ids || []
  })
}

export const deleteByIds = (ids) => {
  return request({
    url: 'api/v1/test/testtreetable/testRightTb/deleteByIds',
    method: 'delete',
    data: ids || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/test/testtreetable/testRightTb',
    method: 'post',
    data: data
  })
}

export const update = (id, data) => {
  return request({
    url: `api/v1/test/testtreetable/testRightTb/${id}`,
    method: 'put',
    data: data
  })
}