const editor = Vue.component('editor', {
    template: `
        <div>
            <h1>{{title}}</h1>
            <input v-model="entry.title"></textarea>
            <textarea v-model="entry.text"></textarea>
            <button v-on:click="save">Save</button>
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
            console.log('loading: ' + this.action);
            if (this.action === 'update') {
                if (this.entryID === -1) {
                    this.$router.push({ query: { action: 'create' } });
                }
                else {
                    //@@TODO: Retrieve data.
                }
            }
            else {
                //@@TODO: Set up for new entry.
            }
        },

        save: function() {
            if (this.entry.title === '' || this.entry.text === '') {
                return;
            }

            postJSON(api.createEntry, this.entry).then(response => {
                console.log(response);
            })
        }
    },

    data: function() {
        return {
            entry: {
                ID: -1,
                title: '',
                text: '',
            },
        }
    },

    computed: {
        // Current action. 'create' / 'update'.
        action: function() {
            let query = this.$route.query.action;
            if (typeof query === 'undefined') {
                return 'create';
            }
            return query;
        },

        // Current ID (if in update mode)
        entryID: function() {
            let query = this.$route.query.id;
            if (typeof query === 'undefined') {
                return -1;
            }
            return query;
        },

        // title
        title: function() {
            switch (this.action) {
                case 'create':
                    return 'New Entry';

                case 'update':
                    return 'Edit Entry';
            }
        }
    }
});