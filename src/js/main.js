let app;
const root = 'https://localhost:8080';

window.onload = function() {

    const routes = [
        { path: '/', component: dashboard },
        { path: '/dashboard', component: dashboard },
        { path: '/edit', component: editor },
        { path: '*', component: dashboard }
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
}