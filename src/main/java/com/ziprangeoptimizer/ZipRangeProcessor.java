package com.ziprangeoptimizer;

import com.ziprangeoptimizer.model.ZipRange;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Main process class for Zip Code Optimizer
 * This class provides the API processZipRange it takes a list
 * of ZipRange as a list and returns a new optimized ZipRange list
 *
 */
public class ZipRangeProcessor {

    /**
     * This process takes a list of ZipRange as parameter
     * Creates a result of a final zip code range with minimum set of ranges
     * @param zipRangeList
     * @return finalZipRange
     */
    public List<ZipRange> processZipRange(List<ZipRange> zipRangeList) {
        // Initialize response
        List<ZipRange> finalZipRange = new ArrayList<>();

        // process each input
        for (ZipRange zipRange : zipRangeList) {
            // Step 1. generate list with first entry
            if (CollectionUtils.isEmpty(finalZipRange)) {
                // for first entry add the entry to response
                // no further processing needed
                finalZipRange.add(zipRange);
            } else {
                // Step 2.  process zip code ranges for optimization
                optimizeZipCodeRange(finalZipRange, zipRange);
            }

        }

        return finalZipRange;
    }

    /**
     * this methods take a list of Zip code range and a new range input
     * validates logic to see if zip code ranges can be optimizable.
     * if optimizable, then It will modify the zip code range list and return
     * an optimized list
     *
     * @param finalZipRange
     * @param zipRangeInput
     */
    public void optimizeZipCodeRange(List<ZipRange> finalZipRange, ZipRange zipRangeInput) {
        // Step 1. extract list to an iterator
        // entry can be deleted for a new rage entry
        ListIterator<ZipRange> zipRangeListIterator = finalZipRange.listIterator();

        // set a flag for new zip range to be added
        Boolean isZipRangeModified = false;
        // iterate through each existing entry to process the new range entry for optimization
        while(zipRangeListIterator.hasNext()) {
            ZipRange currentZipRange = zipRangeListIterator.next();

            if (zipRangeInput.getBegZip() < currentZipRange.getBegZip()
                    && zipRangeInput.getEndZip() > currentZipRange.getEndZip()) {
                // Case 1, the current range is in between the new range
                // remove the existing range
                zipRangeListIterator.remove();
                // set flag for newInput range to be added to replace the removed range
                isZipRangeModified = true;
            } else if (zipRangeInput.getBegZip() > currentZipRange.getEndZip() ||
                    zipRangeInput.getEndZip() < currentZipRange.getBegZip()){
                // Case 2, the new range is either before or after existing range
                // do not need to modify existing range
                // just set flag to add new range
                isZipRangeModified = true;
            } else if (zipRangeInput.getBegZip() < currentZipRange.getBegZip() &&
                    zipRangeInput.getEndZip() < currentZipRange.getEndZip()){
                // Case 3, new range extends beginning range of current range
                isZipRangeModified = true;
                // modify input range to incorporate end zip code with current
                zipRangeInput.setEndZip(currentZipRange.getEndZip());
                // remove existing range to be updated with modified current range
                zipRangeListIterator.remove();
            } else if (zipRangeInput.getBegZip() > currentZipRange.getBegZip() &&
                    zipRangeInput.getEndZip() > currentZipRange.getEndZip()){
                // Case 4, new range extends ending range of current range
                isZipRangeModified = true;
                // modify input range to incorporate end zip code with current
                zipRangeInput.setBegZip(currentZipRange.getBegZip());
                // remove existing range to be updated with modified current range
                zipRangeListIterator.remove();
            }

        }

        // if range has been modified, add to list
        if (isZipRangeModified) {
            finalZipRange.add(zipRangeInput);
        }

    }
}
