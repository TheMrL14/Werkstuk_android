package be.lennert.werkstuk;

import com.google.common.truth.Truth;

import org.junit.Test;

import be.lennert.werkstuk.utils.StringUtils;

public class BasicUnitTest {

    @Test
    public void roundToNearestTen_Vallidator(){
        Truth.assertThat(StringUtils.roundToNearestTen(2.50)).isEqualTo("2");
        Truth.assertThat(StringUtils.roundToNearestTen(11.10)).isEqualTo("10");
    }

    @Test
    public void generateStringId_Vallidator(){
        Truth.assertThat(StringUtils.generateStringId("Test id")).isEqualTo("Testid");
    }
}
