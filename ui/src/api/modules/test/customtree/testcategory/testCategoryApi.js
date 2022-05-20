import request from 'ecip-web/utils/request'


export const getTreeData = (data, params) => {
  return request({
    url: 'api/v1/test/customtree.testcategory/testCategory/data',
    method: 'post',
    params: params,
    data: data
  })
}

export const findByPrimaryKey = (id) => {
  return request({
    url: `api/v1/test/customtree.testcategory/testCategory/${id}`,
    method: 'get'
  })
}

export const removeByIds = (ids) => {
  return request({
    url: 'api/v1/test/customtree.testcategory/testCategory/removeByIds',
    method: 'put',
    data: ids || []
  })
}

export const deleteByIds = (ids) => {
  return request({
    url: 'api/v1/test/customtree.testcategory/testCategory/deleteByIds',
    method: 'delete',
    data: ids || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/test/customtree.testcategory/testCategory',
    method: 'post',
    data: data
  })
}

export const update = (id, data) => {
  return request({
    url: `api/v1/test/customtree.testcategory/testCategory/${id}`,
    method: 'put',
    data: data
  })
}