const editor = Vue.component('editor', {
    template: `
        <div>
            <h1>{{title}}</h1>
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
            console.log('saving', this.entry.ID, this.entry.text);
        }
    },

    data: function() {
        return {
            entry: {
                ID: -1,
                text: ''
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