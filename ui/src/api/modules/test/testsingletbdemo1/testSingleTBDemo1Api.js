import request from 'ecip-web/utils/request'


export const getData = (params, data) => {
  return request({
    url: 'api/v1/test/testsingletbdemo1/testSingleTBDemo1/data',
    method: 'post',
    params: params,
    data: data
  })
}

export const findByPrimaryKey = (testId) => {
  return request({
    url: `api/v1/test/testsingletbdemo1/testSingleTBDemo1/${testId}`,
    method: 'get'
  })
}

export const deleteByIds = (ids) => {
  return request({
    url: 'api/v1/test/testsingletbdemo1/testSingleTBDemo1/deleteByIds',
    method: 'delete',
    data: ids || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/test/testsingletbdemo1/testSingleTBDemo1',
    method: 'post',
    data: data
  })
}

export const update = (testId, data) => {
  return request({
    url: `api/v1/test/testsingletbdemo1/testSingleTBDemo1/${testId}`,
    method: 'put',
    data: data
  })
}