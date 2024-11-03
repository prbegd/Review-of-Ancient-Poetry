package com.xingab612.reviewofancientpoetry.misc;

import android.app.Activity;

import com.xingab612.reviewofancientpoetry.beans.AncientPoetryType;
import com.xingab612.reviewofancientpoetry.util.ChineseNumberUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Data {
    private final Activity activity;
    private final String fileName = "data.dat";
    private static ArrayList<AncientPoetryType> data;

    public Data(Activity activity) {
        this.activity = activity;
    }

    /**
     * 读取数据文件<br>
     * 注: <strong>在更改数据后, 必须调用 {@link #save()} 方法保存数据到文件中</strong>
     *
     * @return 数据列表
     */
    public ArrayList<AncientPoetryType> readData() {
        if (data != null) {
            return data;
        }

        try (FileInputStream fis = activity.openFileInput(fileName);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            data = (ArrayList<AncientPoetryType>) ois.readObject();
        } catch (FileNotFoundException e) {
            writeDefaultData(); // 当文件未找到时写入默认数据
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error reading data file", e);
        }
        return data;
    }

    public void writeData(ArrayList<AncientPoetryType> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(activity.openFileOutput(fileName, Activity.MODE_PRIVATE))) {
            oos.writeObject(data);
            Data.data = data;
        } catch (IOException e) {
            throw new RuntimeException("Error writing data file", e);
        }
    }

    // 将数据保存到文件中
    public void save() {
        writeData(readData());
    }

    private void writeDefaultData() {
        writeData(createDefaultData());
    }

    private ArrayList<AncientPoetryType> createDefaultData() {
        ArrayList<AncientPoetryType> dataList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            String grade = ChineseNumberUtil.convert(i + 1);
            dataList.add(new AncientPoetryType(grade + "年级上册"));
            dataList.add(new AncientPoetryType(grade + "年级下册"));
        }
        return dataList;
    }
}

