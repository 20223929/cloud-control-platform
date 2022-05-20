import request from 'ecip-web/utils/request'


export const getTreeData = (data, params) => {
  return request({
    url: 'api/v1/test/testtree/testTreeTB/data',
    method: 'post',
    params: params,
    data: data
  })
}

export const findByPrimaryKey = (treeId) => {
  return request({
    url: `api/v1/test/testtree/testTreeTB/${treeId}`,
    method: 'get'
  })
}

export const removeByIds = (ids) => {
  return request({
    url: 'api/v1/test/testtree/testTreeTB/removeByIds',
    method: 'put',
    data: ids || []
  })
}

export const deleteByIds = (ids) => {
  return request({
    url: 'api/v1/test/testtree/testTreeTB/deleteByIds',
    method: 'delete',
    data: ids || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/test/testtree/testTreeTB',
    method: 'post',
    data: data
  })
}

export const update = (treeId, data) => {
  return request({
    url: `api/v1/test/testtree/testTreeTB/${treeId}`,
    method: 'put',
    data: data
  })
}