List<Entry> entries = [];

void initEntryList() {
    //@@TODO: scrape the /data directory and load entry IDs and metadata.

    Entry a = new Entry();
    a.ID = 0;
    a.title = 'Lorem Ipsum';

    Entry b = new Entry();
    b.ID = 1;
    b.title = 'Dolor Sit Amet';

    entries.add(a);
    entries.add(b);
}

class Entry {
    int ID;
    String title;
    // date
    // text
    // glosses
    // tags
    // ...
}