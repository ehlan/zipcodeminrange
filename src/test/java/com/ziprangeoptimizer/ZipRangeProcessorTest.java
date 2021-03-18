package com.ziprangeoptimizer;

import com.ziprangeoptimizer.model.ZipRange;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ZipRangeProcessorTest {

    private ZipRangeProcessor zipRangeProcessor;
    private ZipRangeProcessor spy;

    List<ZipRange> zipRangeInput;

    @Before
    public void setup() {
        zipRangeProcessor = new ZipRangeProcessor();
        spy = Mockito.spy(zipRangeProcessor);
    }

    @Test
    public void TestProcessZipRangeSingleEntry() {
        zipRangeInput = new ArrayList<>();
        zipRangeInput.add(new ZipRange(94133, 94200));
        List<ZipRange> result = zipRangeProcessor.processZipRange(zipRangeInput);
        assertNotNull(result);
        assertEquals(result.size(), 1, 0);
        assertEquals(result.get(0).getBegZip(), 94133, 0);
        assertEquals(result.get(0).getEndZip(), 94200, 0);

    }

    @Test
    public void TestProcessZipRangeHappyPath() {
        zipRangeInput = new ArrayList<>();
        zipRangeInput.add(new ZipRange(94133, 94136));
        zipRangeInput.add(new ZipRange(94200, 94299));
        ZipRange zipRange = new ZipRange(94600, 94699);

        List<ZipRange> output = new ArrayList<>();
        output.add(new ZipRange(11111, 22222));

        Mockito.doNothing().when(spy).optimizeZipCodeRange(Mockito.<List<ZipRange>>any(), Mockito.any(ZipRange.class));

        List<ZipRange> result = spy.processZipRange(zipRangeInput);
        assertNotNull(result);
        assertEquals(result.size(), 1, 0);
        assertEquals(result.get(0).getBegZip(), 94133, 0);
        assertEquals(result.get(0).getEndZip(), 94136, 0);

    }

    @Test
    public void TestOptimizeZipCodeRangeInputBiggerRange(){
        List<ZipRange> zipRangeInput = new ArrayList<>();
        zipRangeInput.add(new ZipRange(94001, 95001));
        ZipRange inputRange = new ZipRange(93001, 96001);
        zipRangeProcessor.optimizeZipCodeRange(zipRangeInput,inputRange);
        assertEquals(zipRangeInput.get(0).getBegZip(), inputRange.getBegZip());
        assertEquals(zipRangeInput.get(0).getEndZip(), inputRange.getEndZip());
    }

    @Test
    public void TestOptimizeZipCodeRangeInputBeforeRange(){
        List<ZipRange> zipRangeInput = new ArrayList<>();
        zipRangeInput.add(new ZipRange(94001, 95001));
        ZipRange inputRange = new ZipRange(93001, 93999);
        zipRangeProcessor.optimizeZipCodeRange(zipRangeInput,inputRange);
        assertEquals(zipRangeInput.size(), 2);
        assertEquals(zipRangeInput.get(1).getBegZip(), inputRange.getBegZip());
        assertEquals(zipRangeInput.get(1).getEndZip(), inputRange.getEndZip());
    }

    @Test
    public void TestOptimizeZipCodeRangeInputAfterRange(){
        List<ZipRange> zipRangeInput = new ArrayList<>();
        zipRangeInput.add(new ZipRange(94001, 95001));
        ZipRange inputRange = new ZipRange(96001, 97001);
        zipRangeProcessor.optimizeZipCodeRange(zipRangeInput,inputRange);
        assertEquals(zipRangeInput.size(), 2);
        assertEquals(zipRangeInput.get(1).getBegZip(), inputRange.getBegZip());
        assertEquals(zipRangeInput.get(1).getEndZip(), inputRange.getEndZip());
    }

    @Test
    public void TestOptimizeZipCodeRangeInputLeftRange(){
        List<ZipRange> zipRangeInput = new ArrayList<>();
        zipRangeInput.add(new ZipRange(94001, 95001));
        ZipRange inputRange = new ZipRange(93001, 94002);
        zipRangeProcessor.optimizeZipCodeRange(zipRangeInput,inputRange);
        assertEquals(zipRangeInput.size(), 1);
        assertEquals(zipRangeInput.get(0).getBegZip(), Integer.valueOf(93001));
        assertEquals(zipRangeInput.get(0).getEndZip(), Integer.valueOf(95001));
    }

    @Test
    public void TestOptimizeZipCodeRangeInputRightRange(){
        List<ZipRange> zipRangeInput = new ArrayList<>();
        zipRangeInput.add(new ZipRange(94001, 95001));
        ZipRange inputRange = new ZipRange(94999, 96001);
        zipRangeProcessor.optimizeZipCodeRange(zipRangeInput,inputRange);
        assertEquals(zipRangeInput.size(), 1);
        assertEquals(zipRangeInput.get(0).getBegZip(), Integer.valueOf(94001));
        assertEquals(zipRangeInput.get(0).getEndZip(), Integer.valueOf(96001));
    }
}
