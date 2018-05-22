import axios from 'axios';
import * as session from "./session.js";

class API {
    constructor (uri) {
        let headers = { "Content-Type": "application/json" };
        let token = session.getToken();

        if (token)
            Object.assign(headers, { "Authorization": "Bearer " + token });

        this.axios = axios.create({
          baseURL: uri,
          timeout: 1000,
          headers: headers
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
