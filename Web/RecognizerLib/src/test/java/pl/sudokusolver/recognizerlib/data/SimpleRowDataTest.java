package pl.sudokusolver.recognizerlib.data;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib._INIT_;
import pl.sudokusolver.recognizerlib._TestUtility_;

import static org.opencv.core.CvType.CV_8U;


@ExtendWith({_INIT_.class})
class SimpleRowDataTest {

    @Test
    void getDataFromImgTest(){
        String res = _TestUtility_.getPathToResource("/digits.png");
        SimpleRowData data = new SimpleRowData(res, (short)20);

        Assert.assertEquals("Incorrect data type ", DataType.Simple, data.getDataType());
        Assert.assertEquals("Incorrect sample size", 20 , data.getSize());

        Assert.assertEquals("Data rows and Labals rows number must be equals",
                                                    data.getLabels().rows(), data.getData().rows());

        Assert.assertEquals("Incorrect data cols number", 400, data.getData().cols());
        Assert.assertEquals("Incorrect labels cols number", 1, data.getLabels().cols());
    }

    @Test
    void simpleCreationTest(){
        Mat data = Mat.zeros(3,3, CV_8U);
        Mat label = Mat.zeros(3,3, CV_8U);
        for(int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++){
                data.put(i,j, 10*i+j);
                label.put(i,j, 10*i+j);
            }
        SimpleRowData rowData = new SimpleRowData(data,label, (short)1);

        Assert.assertEquals("Incorrect sample size", 1 , rowData.getSize());

        Assert.assertEquals("Incorrect data rows number", 3, rowData.getData().rows());
        Assert.assertEquals("Incorrect data cols number", 3, rowData.getData().cols());

        Assert.assertEquals("Incorrect labels rows number", 3, rowData.getLabels().rows());
        Assert.assertEquals("Incorrect labels cols number", 3, rowData.getLabels().cols());


        for(int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++){
                Assert.assertArrayEquals("Equals value", rowData.getData().get(i,j), rowData.getLabels().get(i,j), 0.00001);
            }
    }

}