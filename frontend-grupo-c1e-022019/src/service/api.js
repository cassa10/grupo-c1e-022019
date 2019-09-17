import axios from 'axios';

const server = 'http://localhost:8080';

const API = {
  get: path => axios.get(`${server}${path}`).then(response => response.data),
  put: (path, body) => axios.put(`${server}${path}`, body).then(response => response.data),
  post: (path, body) => axios.post(`${server}${path}`, body).then(response => response.data),
  delete: (path, body) => axios.delete(`${server}${path}`, body).then(response => response.data),
};

export default API;
