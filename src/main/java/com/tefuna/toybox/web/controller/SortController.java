package com.tefuna.toybox.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tefuna.toybox.sort.AbstractSort;
import com.tefuna.toybox.sort.algorithm.BitonicSort;
import com.tefuna.toybox.sort.algorithm.BogoSort;
import com.tefuna.toybox.sort.algorithm.BubbleSort;
import com.tefuna.toybox.sort.algorithm.GnomeSort;
import com.tefuna.toybox.sort.algorithm.HeapSort;
import com.tefuna.toybox.sort.algorithm.InsertionSort;
import com.tefuna.toybox.sort.algorithm.MergeSort;
import com.tefuna.toybox.sort.algorithm.OddEvenSort;
import com.tefuna.toybox.sort.algorithm.QuickSort;
import com.tefuna.toybox.sort.algorithm.SelectionSort;
import com.tefuna.toybox.sort.algorithm.ShakerSort;
import com.tefuna.toybox.sort.algorithm.ShellSort;
import com.tefuna.toybox.sort.common.constant.SortProperty;
import com.tefuna.toybox.sort.common.element.SortElement;
import com.tefuna.toybox.sort.common.printer.JsonPrinter;
import com.tefuna.toybox.sort.common.printer.Printer;

@RestController
public class SortController {

    /** */
    private static final Logger logger = LoggerFactory.getLogger(SortController.class);

    /**
     * 
     * @param name
     * @param elements
     * @return
     */
    @RequestMapping(value = "/sort", method = RequestMethod.GET)
    public String sort(@RequestParam String name, @RequestParam int elements) {

        logger.info(name + ", " + elements);

        Printer printer = executeSort(name, elements);
        logger.info(printer.printAll());

        return printer.printAll();
    }

    /**
     * 
     * @param name
     * @param elements
     * @return
     */
    private Printer executeSort(String name, int elements) {

        if (StringUtils.isEmpty(name)) {
            logger.error("sort name is empty.");
            return null;
        }

        if (0 > elements && elements > 1000) {
            logger.error("element is out of range :" + elements);
            return null;
        }

        SortElement[] original = new SortElement[elements];
        SortElement[] sortArray = new SortElement[elements];
        for (int i = 0; i < original.length; i++) {
            SortElement element = new SortElement();
            element.setId(i);
            element.setValue((int) (Math.random() * 100));
            element.setPrePosition(i);
            element.setPosition(i);
            element.setSorted(false);
            original[i] = element;
            sortArray[i] = new SortElement(element);
        }

        // TODO sort name compare and convert using enum SortName.
        AbstractSort sort = null;
        switch (name) {
        case "01":
            sort = new BubbleSort();
            break;
        case "02":
            sort = new SelectionSort();
            break;
        case "03":
            sort = new InsertionSort();
            break;
        case "04":
            sort = new QuickSort();
            break;
        case "05":
            sort = new MergeSort();
            break;
        case "06":
            sort = new HeapSort();
            break;
        case "11":
            sort = new ShellSort();
            break;
        case "12":
            sort = new ShakerSort();
            break;
        case "13":
            sort = new GnomeSort();
            break;
        case "14":
            sort = new BitonicSort();
            original = fillToBitonic(original);
            sortArray = fillToBitonic(sortArray);
            break;
        case "15":
            sort = new BogoSort();
            break;
        case "16":
            sort = new OddEvenSort();
            break;
        default:
            logger.error("sort algorithm cannot be specified :" + name);
            return null;
        }

        Printer printer = new JsonPrinter();
        sort.setPrinter(printer);
        sort.execute(original, sortArray);

        return sort.getPrinter();
    }

    /**
     * only for bitonic sort.
     * 
     * @param array
     * @return
     */
    private static SortElement[] fillToBitonic(SortElement[] array) {

        int expval = 1;
        while (expval < array.length) {
            expval = expval * 2;
        }

        SortElement[] bitArray = new SortElement[expval];
        for (int i = 0; i < bitArray.length; i++) {
            if (i < array.length) {
                bitArray[i] = array[i];
            } else {
                SortElement element = new SortElement();
                element.setId(-i);
                element.setValue(Integer.parseInt(SortProperty.MAX_VALUE.getValue()));
                element.setPosition(i);
                element.setPrePosition(i);
                bitArray[i] = element;
            }
        }

        return bitArray;
    }
}
