package pl.sudokusolver.recognizerlib.data;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import pl.sudokusolver.recognizerlib._INIT_;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.opencv.core.CvType.CV_8UC1;

@ExtendWith({_INIT_.class})
class MNISTReaderPrivateTest {
    @Test
    void checkIfSizeIsCorrectlySet() throws NoSuchFieldException, IllegalAccessException {
        Field field = MNISTReader.class.getDeclaredField("size");
        field.setAccessible(true);
        Assert.assertEquals("MNIST size should be 28", 28, (short)field.get(null));
    }

    @Test
    void createLabel() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method labels = MNISTReader.class.getDeclaredMethod("createLabel", Integer.TYPE, DataType.class);
        labels.setAccessible(true);
        int size = 2;

        Mat retSimple = (Mat)labels.invoke(null, size, DataType.Simple);
        Assert.assertEquals("Labels matrix isn't created correctly.", CvType.CV_32FC1, retSimple.type());
        Assert.assertEquals("Labels matrix isn't created correctly.", 1, retSimple.cols());
        Assert.assertEquals("Labels matrix isn't created correctly.", size, retSimple.rows());

        Mat retSimpleSVM = (Mat)labels.invoke(null, size, DataType.SimpleSVM);
        Assert.assertEquals("Labels matrix isn't created correctly.", CvType.CV_32SC1, retSimpleSVM.type());
        Assert.assertEquals("Labels matrix isn't created correctly.", 1, retSimpleSVM.cols());
        Assert.assertEquals("Labels matrix isn't created correctly.", size, retSimpleSVM.rows());

        Mat retComplex = (Mat)labels.invoke(null, size, DataType.Complex);
        Assert.assertEquals("Labels matrix isn't created correctly.", CvType.CV_32FC1, retComplex.type());
        Assert.assertEquals("Labels matrix isn't created correctly.", 9, retComplex.cols());
        Assert.assertEquals("Labels matrix isn't created correctly.", size, retComplex.rows());
    }

    @Test
    void createLabelsFromString() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method labels = MNISTReader.class.getDeclaredMethod("getStringFromLable", Integer.TYPE);
        labels.setAccessible(true);

        Assert.assertEquals("Label form int wasn't created cerrectly", "100000000", labels.invoke(null,1));
        Assert.assertEquals("Label form int wasn't created cerrectly", "010000000", labels.invoke(null,2));
        Assert.assertEquals("Label form int wasn't created cerrectly", "000000010", labels.invoke(null,8));
        Assert.assertEquals("Label form int wasn't created cerrectly", "000000000", labels.invoke(null,-1));
    }

    @Test
    void putLabel() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method put = MNISTReader.class.getDeclaredMethod("putLabel", Mat.class, Integer.TYPE, Integer.TYPE, DataType.class);
        put.setAccessible(true);

        int size = 2;
        Mat data = Mat.zeros(size, 1, CvType.CV_32FC1);

        put.invoke(null,data, 1, 0, DataType.Simple);
        put.invoke(null,data, 2, 1, DataType.Simple);

        Assert.assertEquals(1, (int)data.get(0,0)[0]);
        Assert.assertEquals(2, (int)data.get(1,0)[0]);
    }

    @Test
    void putImg() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method put = MNISTReader.class.getDeclaredMethod("putImg", Mat.class, Mat.class, Integer.TYPE);
        put.setAccessible(true);

        int imgSize = 28;
        Mat data = Mat.zeros(1, imgSize*imgSize, CvType.CV_32FC1);
        Mat img = Mat.ones(imgSize, imgSize, CV_8UC1);


        put.invoke(null,data,img, 0);
        for (int i = 0; i < imgSize*imgSize; i++)
            Assert.assertEquals("Img was putted incorrectly", 1.0/255.0, data.get(0,i)[0], 1e-4);
    }
}