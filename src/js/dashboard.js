const dashboard = Vue.component('dashboard', {
    template: `
        <div>
            <h1>Dashboard</h1>
            <router-link to="/edit?action=create">New Entry</router-link>
            <router-link to="/edit?action=update">Edit Entry</router-link>
        </div>
    `,

    methods: {

    }
});