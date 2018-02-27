let app;
const root = '';

const api = {
    getEntryList: '/entries/list',
    createEntry: '/entries/create'
};

window.onload = function() {

    const routes = [
        { path: '/', component: dashboard },
        { path: '/dashboard', component: dashboard },
        { path: '/edit', component: editor },
        { path: '*', redirect: '/' }
    ]

    // Create the vue-router instance
    const router = new VueRouter({
        routes: routes,
        mode: 'history'
    });

    // Create the Vue instance
    app = new Vue({
        el: '#app',
        router: router
    });

    getJSON('/entries/list').then(response => console.log(response));
}

// GET some JSON data; uses Fetch internally.
// url: URL relative to root.
function getJSON(url) {
    url = root + url;

    return fetch(url, {
        method: 'GET',
        headers: new Headers({
            'Content-Type': 'application/json; charset-utf-8'
        })
    })
    .then(response => response.json()
        .then(body => ({
            headers: response.headers,
            status: response.status,
            body
        }))
        .catch(body => ({
            headers: response.headers,
            status: response.status,
            body: null
        }))
    );
}

// POST some JSON data; uses Fetch internally.
// url: URL relative to root.
// data: Data, as an object or JSON string.
function postJSON(url, data) {
    url = root + url;

    // Make the data into a JSON string.
    if (typeof data === 'object') {
        data = JSON.stringify(data);
    }

    return fetch(url, {
        method: 'POST',
        body: data,
        headers: new Headers({
            'Content-Type': 'application/json; charset-utf-8'
        })
    })
    .then(response => response.json()
        .then(body => ({
            headers: response.headers,
            status: response.status,
            body
        }))
        .catch(body => ({
            headers: response.headers,
            status: response.status,
            body: null
        }))
    );
}