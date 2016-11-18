package imageutil;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ImageComparatorTest {

    @Test
    public void testDrawDifferences() throws Exception {
        boolean actual = new ImageComparator(ImageUtilsEnum.IMAGE1_PATH.getText(), ImageUtilsEnum.IMAGE2_PATH.getText(),
                ImageUtilsEnum.IMAGE_RESULT_FILE_NAME.getText()).drawDifferences();

        assertEquals(false, actual);

    }

    @Test(expected = NullPointerException.class)
    public void testDrawDifferencesWithNullPath() throws Exception {
        new ImageComparator(null, ImageUtilsEnum.IMAGE2_PATH.getText(),
                ImageUtilsEnum.IMAGE_RESULT_FILE_NAME.getText()).drawDifferences();

    }
}