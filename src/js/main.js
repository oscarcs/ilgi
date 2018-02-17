let app;
const root = 'https://localhost:8080';

window.onload = function() {

    // Create the Vue instance
    app = new Vue({
        el: '#app',
        data: {
            message: "Hello, Dart!",
        },
        methods: {

        }
    });
}