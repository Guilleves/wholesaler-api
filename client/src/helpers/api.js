import Axios from 'axios';
import * as session from "./session.js";
import Router from '@/router';

class API {
    constructor () {
        let headers = { "Content-Type": "application/json" };
        let token = session.getToken();

        if (token)
            Object.assign(headers, { "Authorization": "Bearer " + token });

        this.axios = Axios.create({
          baseURL: "http://localhost:9090/",
          timeout: 20000,
          headers: headers
        });

        this.axios.interceptors.response.use(response => {
            return response;
        }, error => {
            if (error.response.status === 401) {
                Router.push("/unauthorized/");
            }

            return Promise.reject(error);
        });
    }

    get(url, data) {
        return this.fetch("get", url, data);
    }

    post(url, data) {
        return this.fetch("post", url, data);
    }

    delete(url, data) {
        return this.fetch("delete", url, data);
    }

    put(url, data) {
        return this.fetch("put", url, data);
    }

    fetch(method, url, payload) {
        let body;
        let qs = '';

        if (method === "get" && payload)
          qs = "?" + this.toQueryString(payload);
        else
          body = JSON.stringify(payload);

        // Returns a promise.
        return this.axios.request({
            url: url + qs,
            method: method,
            data: body
        });
    }

    toQueryString(o) {
        if (o === undefined || o === null)
            return "";

        return Object
            .keys(o)
            .filter(key => o[key] !== null)
            .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(o[key])}`)
            .join('&');
    }
}

export default API;
