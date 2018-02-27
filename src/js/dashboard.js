const dashboard = Vue.component('dashboard', {
    template: `
        <div>
            <h1>Dashboard</h1>
            <router-link to="/edit?action=create">New Entry</router-link>
            <ul>
                <li v-for="entry of entries">
                    <router-link :to="'/edit?action=update&id=' + entry.ID">Edit "{{entry.title}}"</router-link>
                </li>
            </ul>
        </div>
    `,

    created: function() {
        this.load();
    },

    afterRouteUpdate: function() {
        this.load();
    },

    methods: {
        load: function() {
            getJSON(api.getEntryList).then((response) => {
                this.entries = response.body;
                console.log(response);
            });
        }
    },

    data: function() {
        return {
            entries: [],
        }
    },
});