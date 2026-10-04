package com.rq.manager.authusers.util;

import com.github.f4b6a3.tsid.TsidCreator;

/**
 * Utility for generating TSID (Time-Sorted Identifier) values.
 * TSIDs are 64-bit integers that are time-ordered, globally unique,
 * and much lighter than UUIDs while remaining safe for use in APIs.
 */
public class TsidUtil {

    private TsidUtil() {
        // Utility class — no instantiation
    }

    /**
     * Generates a new unique TSID as a Long.
     *
     * @return a time-sorted unique Long identifier
     */
    public static Long generate() {
        return TsidCreator.getTsid().toLong();
    }
}
