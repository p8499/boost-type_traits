package com.p8499.lang.ime.rime;

/**
 * Created by Administrator on 12/27/2017.
 */

public class RimeSchemaListItem {
    public String schemaId;
    public String name;
    public Object reserved;

    public String getSchemaId() {
        return schemaId;
    }

    public RimeSchemaListItem setSchemaId(String schemaId) {
        this.schemaId = schemaId;
        return this;
    }

    public String getName() {
        return name;
    }

    public RimeSchemaListItem setName(String name) {
        this.name = name;
        return this;
    }

    public Object getReserved() {
        return reserved;
    }

    public RimeSchemaListItem setReserved(Object reserved) {
        this.reserved = reserved;
        return this;
    }

    @Override
    public String toString() {
        return String.format("{'schemaId':'%s','name':'%s'}", schemaId, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RimeSchemaListItem) {
            RimeSchemaListItem schemaListItem = (RimeSchemaListItem) obj;
            return schemaListItem != null
                    && (Utils.equals(schemaId, schemaListItem.schemaId))
                    && (Utils.equals(name, schemaListItem.name));
        }
        return false;
    }
}
