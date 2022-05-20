import request from 'ecip-web/utils/request'


export const getData = (params, data) => {
  return request({
    url: 'api/v1/test/multipk/multiPkSingleTable/data',
    method: 'post',
    params: params,
    data: data
  })
}

export const findByPrimaryKey = (idOne, idTwo, idThree) => {
  return request({
    url: `api/v1/test/multipk/multiPkSingleTable/${idOne}/${idTwo}/${idThree}`,
    method: 'get'
  })
}

export const removeByIds = (multiPkSingleTableList) => {
  return request({
    url: 'api/v1/test/multipk/multiPkSingleTable/removeByIds',
    method: 'put',
    data: multiPkSingleTableList || []
  })
}

export const deleteByIds = (multiPkSingleTableList) => {
  return request({
    url: 'api/v1/test/multipk/multiPkSingleTable/deleteByIds',
    method: 'delete',
    data: multiPkSingleTableList || []
  })
}

export const save = (data) => {
  return request({
    url: 'api/v1/test/multipk/multiPkSingleTable',
    method: 'post',
    data: data
  })
}

export const update = (idOne, idTwo, idThree, data) => {
  return request({
    url: `api/v1/test/multipk/multiPkSingleTable/${idOne}/${idTwo}/${idThree}`,
    method: 'put',
    data: data
  })
}