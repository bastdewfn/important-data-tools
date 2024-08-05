package com.dewfn.busstation.importantdata.filter;

import com.dewfn.busstation.importantdata.ImportantDataStore;
import com.dewfn.busstation.importantdata.AccessImportDataException;

public interface ImportantDataDecryptFilter {
    boolean filter(ImportantDataStore importantDataStore) throws AccessImportDataException;
}
