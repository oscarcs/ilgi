Map<int, Entry> entries = {};
int nextID = -1;

void initEntryList() {
    //@@TODO: scrape the /data directory and load entry IDs and metadata.

    addEntry('Lorem Ipsum');
    addEntry('Dolor Sit Amet');
}

void addEntry(String title) {
    Entry e = new Entry();
    e.ID = ++nextID;
    e.title = title;
    entries[e.ID] = e;
}

Entry getEntry(int ID) {
    return entries[ID];
}

List<Entry> getEntries() {
    return entries.values;
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