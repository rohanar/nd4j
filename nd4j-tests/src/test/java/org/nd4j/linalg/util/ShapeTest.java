package org.nd4j.linalg.util;

import org.junit.Test;
import org.nd4j.linalg.BaseNd4jTest;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.factory.Nd4jBackend;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author Adam Gibson
 */
public class ShapeTest extends BaseNd4jTest {

    public ShapeTest(String name, Nd4jBackend backend) {
        super(name, backend);
    }

    @Test
    public void testToOffsetZero() {
        INDArray matrix  =  Nd4j.rand(3,5);
        INDArray rowOne = matrix.getRow(1);
        INDArray row1Copy = Shape.toOffsetZero(rowOne);
        assertEquals(rowOne,row1Copy);
        INDArray rows =  matrix.getRows(1, 2);
        INDArray rowsOffsetZero = Shape.toOffsetZero(rows);
        assertEquals(rows,rowsOffsetZero);

        INDArray tensor = Nd4j.rand(new int[]{3,3,3});
        INDArray getTensor = tensor.slice(1).slice(1);
        INDArray getTensorZero = Shape.toOffsetZero(getTensor);
        assertEquals(getTensor,getTensorZero);



    }

    @Test
    public void testLeadingOnesAndTrailingOnes() {
        INDArray arr = Nd4j.ones(1, 10, 1);
        arr.toString();
        System.out.println(arr);
        INDArray array = Nd4j.zeros(new int[]{1, 10, 1});
        INDArray slice = array.slice(0, 2);
        assertArrayEquals(new int[]{1, 10}, slice.shape());
        System.out.println(Arrays.toString(slice.shape())); //Expect: [1,10] -> OK
        System.out.println(slice);
    }

    @Test
    public void testDupLeadingTrailingZeros(){
        testDupHelper(1,1);
        testDupHelper(1,10);
        testDupHelper(10,1);
        testDupHelper(1, 10, 1);
        testDupHelper(1, 10, 1, 1);
        testDupHelper(1,10,2);
        testDupHelper(2, 10, 1, 1);
        testDupHelper(1, 1, 1, 10);
        testDupHelper(10, 1, 1, 1);
    }

    private  void testDupHelper(int... shape ){
        INDArray arr = Nd4j.ones(shape);
        INDArray arr2 = arr.dup();
        assertArrayEquals(arr.shape(), arr2.shape());
        assertTrue(arr.equals(arr2));
    }

    @Test
    public void testLeadingOnes() {
        INDArray arr = Nd4j.create(1,5,5);
        assertEquals(1,arr.getLeadingOnes());
        INDArray arr2 = Nd4j.create(2,2);
        assertEquals(0,arr2.getLeadingOnes());
        INDArray arr4 = Nd4j.create(1,1,5,5);
        assertEquals(2,arr4.getLeadingOnes());
    }

    @Test
    public void testTrailingOnes() {
        INDArray arr2 = Nd4j.create(5,5,1);
        assertEquals(1,arr2.getTrailingOnes());
        INDArray arr4 = Nd4j.create(5,5,1,1);
        assertEquals(2,arr4.getTrailingOnes());
    }

    @Test
    public void testSumLeadingTrailingZeros(){
        testSumHelper(1,5,5);
        testSumHelper(5,5,1);
        testSumHelper(1,5,1);

        testSumHelper(1,5,5,5);
        testSumHelper(5,5,5,1);
        testSumHelper(1,5,5,1);

        testSumHelper(1,5,5,5,5);
        testSumHelper(5,5,5,5,1);
        testSumHelper(1,5,5,5,1);

        testSumHelper(1,5,5,5,5,5);
        testSumHelper(5, 5, 5, 5, 5, 1);
        testSumHelper(1, 5, 5, 5, 5, 1);
    }

    private  void testSumHelper( int... shape ){
        INDArray array = Nd4j.ones(shape);
        for( int i = 0; i < shape.length; i++) {
            for(int j = 0; j < array.vectorsAlongDimension(i); j++) {
                INDArray vec = array.vectorAlongDimension(j,i);
            }
            array.sum(i);
        }
    }




    @Override
    public char ordering() {
        return 'f';
    }
}